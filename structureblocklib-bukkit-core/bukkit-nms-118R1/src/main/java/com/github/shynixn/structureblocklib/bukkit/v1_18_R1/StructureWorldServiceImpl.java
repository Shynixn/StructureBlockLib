package com.github.shynixn.structureblocklib.bukkit.v1_18_R1;

import com.github.shynixn.structureblocklib.api.entity.StructureEntity;
import com.github.shynixn.structureblocklib.api.entity.StructurePlaceMeta;
import com.github.shynixn.structureblocklib.api.entity.StructurePlacePart;
import com.github.shynixn.structureblocklib.api.entity.StructureReadMeta;
import com.github.shynixn.structureblocklib.api.service.StructureWorldService;
import com.github.shynixn.structureblocklib.api.service.TypeConversionService;
import com.github.shynixn.structureblocklib.core.entity.GenericWrapper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.data.BlockData;
import org.bukkit.craftbukkit.v1_18_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_18_R1.block.CraftBlock;
import org.bukkit.craftbukkit.v1_18_R1.block.data.CraftBlockData;
import org.bukkit.entity.Entity;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.function.Function;

/**
 * Implementation to interact with structures in the world.
 */
public class StructureWorldServiceImpl implements StructureWorldService {
    private final TypeConversionService conversionService;

    /**
     * Creates a new service with dependencies.
     *
     * @param conversionService dependency.
     */
    public StructureWorldServiceImpl(TypeConversionService conversionService) {
        this.conversionService = conversionService;
    }

    /**
     * Places the blocks in the world defined by the given structure.
     *
     * @param meta      Meta data to describe the placement.
     * @param structure NMS structure.
     */
    @Override
    public void placeStructureToWorld(StructurePlaceMeta meta, Object structure) throws Exception {
        if (!(structure instanceof StructureTemplate)) {
            throw new IllegalArgumentException("StructureTemplate has to be an NMS handle!");
        }

        StructureTemplate template = (StructureTemplate) structure;
        World bukkitWorld = Bukkit.getWorld(meta.getLocation().getWorldName());
        ServerLevel world = ((CraftWorld) bukkitWorld).getHandle();
        BlockPos cornerBlock = new BlockPos((int) meta.getLocation().getX(), (int) meta.getLocation().getY(), (int) meta.getLocation().getZ());
        StructurePlaceSettings info = new StructurePlaceSettings();
        info.setIgnoreEntities(!meta.isIncludeEntitiesEnabled());
        info.setMirror((Mirror) conversionService.convertToMirrorHandle(meta.getMirrorType()));
        info.setRotation((Rotation) conversionService.convertToRotationHandle(meta.getRotationType()));

        if (meta.getIntegrity() < 1.0F) {
            info.clearProcessors();
            float rotation = Mth.clamp(meta.getIntegrity(), 0.0F, 1.0F);
            BlockRotProcessor rotationProcessor = new BlockRotProcessor(rotation);
            Random random = new Random();

            if (meta.getSeed() != 0L) {
                random = new Random(meta.getSeed());
            }

            info.addProcessor(rotationProcessor);
            info.setRandom(random);
        }

        executeProcessors(bukkitWorld, meta, info);
        executeEntityProcessor(meta, bukkitWorld, template.entityInfoList);
        template.placeInWorld(world, cornerBlock, cornerBlock, info, new Random(), 2);
    }

    /**
     * Reads the blocks in the world into an NMS Structure definition.
     *
     * @param meta Meta data to describe the block selection.
     * @return A new NMS Structure definition.
     */
    @Override
    public Object readStructureFromWorld(StructureReadMeta meta) throws Exception {
        ServerLevel world = ((CraftWorld) Bukkit.getWorld(meta.getLocation().getWorldName())).getHandle();
        BlockPos cornerBlock = new BlockPos((int) meta.getLocation().getX(), (int) meta.getLocation().getY(), (int) meta.getLocation().getZ());
        BlockPos offsetBlock = new BlockPos((int) meta.getOffset().getX(), (int) meta.getOffset().getY(), (int) meta.getOffset().getZ());
        Block structureVoid = (Block) Blocks.class.getDeclaredField(meta.getStructureVoidTypeName()).get(null);

        StructureTemplate template = new StructureTemplate();
        template.fillFromWorld(world, cornerBlock, offsetBlock, meta.isIncludeEntitiesEnabled(), structureVoid);
        template.setAuthor(meta.getAuthor());
        return template;
    }

    /**
     * Executes attached processors.
     *
     * @param bukkitWorld World.
     * @param meta        Meta.
     * @param info        Info.
     */
    private void executeProcessors(World bukkitWorld, StructurePlaceMeta meta, StructurePlaceSettings info) {
        info.addProcessor(new StructureProcessor() {
            @Nullable
            @Override
            public StructureTemplate.StructureBlockInfo processBlock(LevelReader levelReader, BlockPos blockPos, BlockPos blockPos1, StructureTemplate.StructureBlockInfo structureBlockInfo, StructureTemplate.StructureBlockInfo structureBlockInfo1, StructurePlaceSettings structurePlaceSettings) {
                // Source and target contain the same block state.
                GenericWrapper<BlockState> targetBlockState = new GenericWrapper<>(structureBlockInfo.state);
                CraftBlock sourceCraftBlock = new CraftBlock(null, structureBlockInfo.pos) {
                    @Override
                    public BlockState getNMS() {
                        return targetBlockState.item;
                    }

                    @Override
                    public void setBlockData(BlockData data, boolean applyPhysics) {
                        targetBlockState.item = ((CraftBlockData) data).getState();
                    }
                };

                // noinspection UnnecessaryLocalVariable Explicit cast is necessary because otherwise the spigot mappings do not work.
                Vec3i sourcePos = blockPos1;
                org.bukkit.block.Block targetBlock = bukkitWorld.getBlockAt(new Location(bukkitWorld, sourcePos.getX(), sourcePos.getY(), sourcePos.getZ()));
                StructurePlacePart<org.bukkit.block.Block, World> structurePlacePart = new StructurePlacePart<org.bukkit.block.Block, World>() {
                    @NotNull
                    @Override
                    public org.bukkit.block.Block getSourceBlock() {
                        return sourceCraftBlock;
                    }

                    @NotNull
                    @Override
                    public org.bukkit.block.Block getTargetBlock() {
                        return targetBlock;
                    }

                    @Override
                    public @NotNull World getWorld() {
                        return bukkitWorld;
                    }
                };

                for (Function<?, Boolean> processor : meta.getProcessors()) {
                    Function<Object, Boolean> processHandle = (Function<Object, Boolean>) processor;
                    boolean result = processHandle.apply(structurePlacePart);

                    if (!result) {
                        return null;
                    }
                }

                if (!meta.isIncludeBlockEnabled()) {
                    return null;
                }

                return new StructureTemplate.StructureBlockInfo(structureBlockInfo1.pos, targetBlockState.item, structureBlockInfo1.nbt);
            }

            @Override
            protected StructureProcessorType<?> getType() {
                return (StructureProcessorType<StructureProcessor>) () -> null;
            }
        });
    }

    /**
     * Executes the entity processors.
     */
    private void executeEntityProcessor(StructurePlaceMeta meta, World bukkitWorld, List<StructureTemplate.StructureEntityInfo> structureEntityInfos) {
        for (StructureTemplate.StructureEntityInfo entityInfo : structureEntityInfos.toArray(new StructureTemplate.StructureEntityInfo[0])) {
            final GenericWrapper<Entity> peekEntity = new GenericWrapper<>(null);
            StructureEntity<Entity, Location> structureEntity = new StructureEntity<Entity, Location>() {
                @Override
                public Optional<Entity> spawnEntity(Location location) {
                    Optional<net.minecraft.world.entity.Entity> optEntity = EntityType.create(entityInfo.nbt, ((CraftWorld) location.getWorld()).getHandle());
                    if (optEntity.isPresent()) {
                        optEntity.get().setUUID(UUID.randomUUID());
                        ((CraftWorld) location.getWorld()).addEntity(optEntity.get(), CreatureSpawnEvent.SpawnReason.CUSTOM);
                        optEntity.get().getBukkitEntity().teleport(location);
                        return Optional.of(optEntity.get().getBukkitEntity());
                    }
                    return Optional.empty();
                }

                @Override
                public Optional<Entity> getEntity() {
                    if (peekEntity.item == null) {
                        Optional<net.minecraft.world.entity.Entity> optEntity = EntityType.create(entityInfo.nbt, ((CraftWorld) bukkitWorld).getHandle());
                        if (optEntity.isPresent()) {
                            peekEntity.item = optEntity.get().getBukkitEntity();
                        }
                    }

                    return Optional.ofNullable(peekEntity.item);
                }

                @Override
                public Location getSourceLocation() {
                    // noinspection UnnecessaryLocalVariable Explicit cast is necessary because otherwise the spigot mappings do not work.
                    Vec3i sourcePos = entityInfo.blockPos;
                    return new Location(null, sourcePos.getX(), sourcePos.getY(), sourcePos.getZ());
                }

                @Override
                public String getNbtData() {
                    return entityInfo.nbt.toString();
                }
            };

            for (Function<?, Boolean> processor : meta.getEntityProcessors()) {
                Function<Object, Boolean> processHandle = (Function<Object, Boolean>) processor;
                boolean result = processHandle.apply(structureEntity);

                if (!result) {
                    structureEntityInfos.remove(entityInfo);
                }
            }

            if (peekEntity.item != null) {
                peekEntity.item.remove();
            }
        }
    }
}

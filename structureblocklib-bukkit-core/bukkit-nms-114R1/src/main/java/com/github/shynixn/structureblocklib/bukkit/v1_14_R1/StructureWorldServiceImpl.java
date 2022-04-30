package com.github.shynixn.structureblocklib.bukkit.v1_14_R1;

import com.github.shynixn.structureblocklib.api.entity.StructurePlaceMeta;
import com.github.shynixn.structureblocklib.api.entity.StructurePlacePart;
import com.github.shynixn.structureblocklib.api.entity.StructureReadMeta;
import com.github.shynixn.structureblocklib.api.service.StructureWorldService;
import com.github.shynixn.structureblocklib.api.service.TypeConversionService;
import com.github.shynixn.structureblocklib.core.entity.GenericWrapper;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.DynamicOps;
import org.bukkit.Bukkit;
import net.minecraft.server.v1_14_R1.*;
import org.bukkit.Location;
import org.bukkit.block.data.BlockData;
import org.bukkit.craftbukkit.v1_14_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_14_R1.block.CraftBlock;
import org.bukkit.craftbukkit.v1_14_R1.block.data.CraftBlockData;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Random;
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
        if (!(structure instanceof DefinedStructure)) {
            throw new IllegalArgumentException("DefinedStructure has to be an NMS handle!");
        }

        DefinedStructure definedStructure = (DefinedStructure) structure;
        org.bukkit.World bukkitWorld =  Bukkit.getWorld(meta.getLocation().getWorldName());
        World world = ((CraftWorld) Bukkit.getWorld(meta.getLocation().getWorldName())).getHandle();
        BlockPosition cornerBlock = new BlockPosition((int) meta.getLocation().getX(), (int) meta.getLocation().getY(), (int) meta.getLocation().getZ());
        DefinedStructureInfo info = new DefinedStructureInfo();
        info.a(!meta.isIncludeEntitiesEnabled());
        info.a((EnumBlockMirror) conversionService.convertToMirrorHandle(meta.getMirrorType()));
        info.a((EnumBlockRotation) conversionService.convertToRotationHandle(meta.getRotationType()));

        if (meta.getIntegrity() < 1.0F) {
            info.b();
            float rotation = MathHelper.a(meta.getIntegrity(), 0.0F, 1.0F);
            DefinedStructureProcessorRotation rotationProcessor = new DefinedStructureProcessorRotation(rotation);
            Random random = new Random();

            if (meta.getSeed() != 0L) {
                random = new Random(meta.getSeed());
            }

            info.a(rotationProcessor);
            info.a(random);
        }

        executeProcessors(bukkitWorld, meta, info);
        definedStructure.a(world, cornerBlock, info);
    }

    /**
     * Reads the blocks in the world into an NMS Structure definition.
     *
     * @param meta Meta data to describe the block selection.
     * @return A new NMS Structure definition.
     */
    @Override
    public Object readStructureFromWorld(StructureReadMeta meta) throws Exception {
        World world = ((CraftWorld) Bukkit.getWorld(meta.getLocation().getWorldName())).getHandle();
        BlockPosition cornerBlock = new BlockPosition((int) meta.getLocation().getX(), (int) meta.getLocation().getY(), (int) meta.getLocation().getZ());
        BlockPosition offsetBlock = new BlockPosition((int) meta.getOffset().getX(), (int) meta.getOffset().getY(), (int) meta.getOffset().getZ());
        Block structureVoid = (Block) Blocks.class.getDeclaredField(meta.getStructureVoidTypeName()).get(null);

        DefinedStructure definedStructure = new DefinedStructure();
        definedStructure.a(world, cornerBlock, offsetBlock, meta.isIncludeEntitiesEnabled(), structureVoid);
        definedStructure.a(meta.getAuthor());
        return definedStructure;
    }

    /**
     * Executes attached processors.
     *
     * @param bukkitWorld World.
     * @param meta        Meta.
     * @param info        Info.
     */
    private void executeProcessors(org.bukkit.World bukkitWorld, StructurePlaceMeta meta, DefinedStructureInfo info) {
        info.a(new DefinedStructureProcessor() {
            @Nullable
            @Override
            public DefinedStructure.BlockInfo a(IWorldReader iWorldReader, BlockPosition blockPosition, DefinedStructure.BlockInfo blockInfo, DefinedStructure.BlockInfo blockInfo1, DefinedStructureInfo definedStructureInfo) {
                // Source and target contain the same block state.
                GenericWrapper<IBlockData> targetBlockState = new GenericWrapper<>(blockInfo.b);
                CraftBlock sourceCraftBlock = new CraftBlock(null, blockInfo.a) {
                    @Override
                    public IBlockData getNMS() {
                        return targetBlockState.item;
                    }

                    @Override
                    public void setBlockData(BlockData data, boolean applyPhysics) {
                        targetBlockState.item = ((CraftBlockData) data).getState();
                    }
                };

                org.bukkit.block.Block targetBlock = bukkitWorld.getBlockAt(new Location(bukkitWorld, blockPosition.getX(), blockPosition.getY(), blockPosition.getZ()));
                StructurePlacePart<org.bukkit.block.Block, org.bukkit.World> structurePlacePart = new StructurePlacePart<org.bukkit.block.Block, org.bukkit.World>() {
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
                    public @NotNull org.bukkit.World getWorld() {
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

                return new DefinedStructure.BlockInfo(blockInfo1.a, targetBlockState.item, blockInfo1.c);
            }

            @Override
            protected DefinedStructureStructureProcessorType a() {
                return null;
            }

            @Override
            protected <T> Dynamic<T> a(DynamicOps<T> dynamicOps) {
                return null;
            }
        });
    }
}

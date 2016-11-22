package com.shynixn.structureblocklib.api.entity;

/**
 * Created by Shynixn
 */
public interface StructureBlockSave extends StructureBlockConstruction {
    void showInvisibleBlocks(boolean showInvisibleBlocks);

    boolean isShowingInvisibleBlocks();

    void save();
}

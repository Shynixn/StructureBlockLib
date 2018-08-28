package com.github.shynixn.structureblocklib.bukkit.api.business.proxy;

public interface StructureBlockCorner extends StructureBlock {
    /**
     * Sets the name of the save.
     *
     * @param name name
     */
    void setSaveName(String name);

    /**
     * Returns the name of the save.
     *
     * @return name
     */
    String getSaveName();
}

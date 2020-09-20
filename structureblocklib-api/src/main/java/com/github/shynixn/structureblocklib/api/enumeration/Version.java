package com.github.shynixn.structureblocklib.api.enumeration;

public enum Version {
    /**
     * Unknown version.
     */
    VERSION_UNKNOWN("", "", 0.0),

    /**
     * Version 1.9.2 - 1.9.4
     */
    VERSION_1_9_R2("v1_9_R2", "1.9.4", 1.092),

    /**
     * Version 1.10.0 - 1.10.2.
     */
    VERSION_1_10_R1("v1_10_R1", "1.10.2", 1.10),

    /**
     * Version 1.11.0 - 1.11.2.
     */
    VERSION_1_11_R1("v1_11_R1", "1.11.2", 1.11),

    /**
     * Version 1.12.0 - 1.12.2.
     */
    VERSION_1_12_R1("v1_12_R1", "1.12.2", 1.12),

    /**
     * Version 1.13.1 - 1.13.2.
     */
    VERSION_1_13_R2("v1_13_R2", "1.13.2", 1.131),

    /**
     * Version 1.14.0 - 1.14.4.
     */
    VERSION_1_14_R1("v1_14_R1", "1.14.4", 1.144),

    /**
     * Version 1.15.0 - 1.15.2.
     */
    VERSION_1_15_R1("v1_15_R1", "1.15.2", 1.150),

    /**
     * Version 1.16.0 - 1.16.1.
     */
    VERSION_1_16_R1("v1_16_R1", "1.16.1", 1.160),

    /**
     * Version 1.16.2 - 1.16.2.
     */
    VERSION_1_16_R2("v1_16_R2", "1.16.2", 1.161);

    private final String bukkitId;
    private final String id;
    private final double numericId;

    /**
     * Version instance.
     *
     * @param bukkitId bukkit id.
     * @param id       id.
     * @param numberId Numeric id.
     */
    Version(String bukkitId, String id, double numberId) {
        this.bukkitId = bukkitId;
        this.id = id;
        this.numericId = numberId;
    }

    /**
     * Gets the bukkit id.
     *
     * @return id.
     */
    public String getBukkitId() {
        return bukkitId;
    }

    /**
     * Gets easy id.
     *
     * @return id.
     */
    public String getId() {
        return id;
    }

    /**
     * Gets the numeric id.
     *
     * @return id.
     */
    public double getNumericId() {
        return numericId;
    }

    /**
     * Gets if this version is same or greater than the given version by parameter.
     */
    public boolean isVersionSameOrGreaterThan(Version version) {
        int result = Double.compare(this.numericId, version.numericId);
        return result == 0 || result == 1;
    }
}

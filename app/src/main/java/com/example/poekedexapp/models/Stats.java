package com.example.poekedexapp.models;
import java.util.Map;

/**
 * The type Stats.
 */
public class Stats {
    /**
     * Gets stat.
     *
     * @return the stat
     */
    public Map<String, Object> getStat() {
        return stat;
    }

    /**
     * Sets stat.
     *
     * @param stat the stat
     */
    public void setStat(Map<String, Object> stat) {
        this.stat = stat;
    }

    /**
     * Gets base stat.
     *
     * @return the base stat
     */
    public Double getBase_stat() {
        return base_stat;
    }

    /**
     * Sets base stat.
     *
     * @param base_stat the base stat
     */
    public void setBase_stat(Double base_stat) {
        this.base_stat = base_stat;
    }

    /**
     * Gets effort.
     *
     * @return the effort
     */
    public Double getEffort() {
        return effort;
    }

    /**
     * Sets effort.
     *
     * @param effort the effort
     */
    public void setEffort(Double effort) {
        this.effort = effort;
    }

    private Map<String, Object> stat;
    private Double base_stat;
    private Double effort;

}

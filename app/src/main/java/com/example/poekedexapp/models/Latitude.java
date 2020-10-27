package com.example.poekedexapp.models;

import java.io.Serializable;

/**
 * The type Latitude.
 */
public class Latitude implements Serializable {
    private String location;
    private Double posX;
    private Double posY;

    /**
     * Instantiates a new Latitude.
     *
     * @param location the location
     * @param posX     the pos x
     * @param posY     the pos y
     */
    public Latitude(String location, Double posX, Double posY) {
        this.location = location;
        this.posX = posX;
        this.posY = posY;
    }

    /**
     * Gets location.
     *
     * @return the location
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets location.
     *
     * @param location the location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Gets pos x.
     *
     * @return the pos x
     */
    public Double getPosX() {
        return posX;
    }

    /**
     * Sets pos x.
     *
     * @param posX the pos x
     */
    public void setPosX(Double posX) {
        this.posX = posX;
    }

    /**
     * Gets pos y.
     *
     * @return the pos y
     */
    public Double getPosY() {
        return posY;
    }

    /**
     * Sets pos y.
     *
     * @param posY the pos y
     */
    public void setPosY(Double posY) {
        this.posY = posY;
    }
}

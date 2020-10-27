package com.example.poekedexapp.models;

/**
 * The type Type color.
 */
public class TypeColor {
    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets color.
     *
     * @return the color
     */
    public String getColor() {
        return color;
    }

    /**
     * Sets color.
     *
     * @param color the color
     */
    public void setColor(String color) {
        this.color = color;
    }

    private String name;
    private String color;

    /**
     * Instantiates a new Type color.
     *
     * @param name  the name
     * @param color the color
     */
    public TypeColor(String name, String color) {
        this.name = name;
        this.color = color;
    }
}

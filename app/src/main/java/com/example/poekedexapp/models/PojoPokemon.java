package com.example.poekedexapp.models;

import java.io.Serializable;

/**
 * The type Pojo pokemon.
 */
public class PojoPokemon implements Serializable {

    private long id;
    private String name;
    private String url;
    private byte[] sprite;
    private String color;

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

    /**
     * Gets id.
     *
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(long id) {
        this.id = id;
    }

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
     * Gets url.
     *
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * Sets url.
     *
     * @param url the url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Get sprite byte [ ].
     *
     * @return the byte [ ]
     */
    public byte[] getSprite() {
        return sprite;
    }

    /**
     * Sets sprite.
     *
     * @param sprite the sprite
     */
    public void setSprite(byte[] sprite) {
        this.sprite = sprite;
    }
}

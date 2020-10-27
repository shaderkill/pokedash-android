package com.example.poekedexapp.models;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * The type Pokemon.
 */
public class Pokemon implements Serializable {

    private int id;
    private String name;
    private String url;
    private List<Ability> abilities;
    private Integer base_experience;
    private Double height;
    private Double weight;
    private Map<String, Object> species;
    private Map<String, Object> sprites;
    private List<Moves> moves;
    private List<Stats> stats;
    private List<Type> types;
    private String colorHex;
    private Bitmap spriteBitMap;
    private boolean isFav;

    @Override
    public String toString() {
        return name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
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
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets abilities.
     *
     * @return the abilities
     */
    public List<Ability> getAbilities() {
        return abilities;
    }

    /**
     * Sets abilities.
     *
     * @param abilities the abilities
     */
    public void setAbilities(List<Ability> abilities) {
        this.abilities = abilities;
    }

    /**
     * Gets base experience.
     *
     * @return the base experience
     */
    public Integer getBase_experience() {
        return base_experience;
    }

    /**
     * Sets base experience.
     *
     * @param base_experience the base experience
     */
    public void setBase_experience(Integer base_experience) {
        this.base_experience = base_experience;
    }

    /**
     * Gets height.
     *
     * @return the height
     */
    public Double getHeight() {
        return height;
    }

    /**
     * Sets height.
     *
     * @param height the height
     */
    public void setHeight(Double height) {
        this.height = height;
    }

    /**
     * Gets species.
     *
     * @return the species
     */
    public Map<String, Object> getSpecies() {
        return species;
    }

    /**
     * Sets species.
     *
     * @param species the species
     */
    public void setSpecies(Map<String, Object> species) {
        this.species = species;
    }

    /**
     * Gets sprites.
     *
     * @return the sprites
     */
    public Map<String, Object> getSprites() {
        return sprites;
    }

    /**
     * Sets sprites.
     *
     * @param sprites the sprites
     */
    public void setSprites(Map<String, Object> sprites) {
        this.sprites = sprites;
    }

    /**
     * Gets moves.
     *
     * @return the moves
     */
    public List<Moves> getMoves() {
        return moves;
    }

    /**
     * Sets moves.
     *
     * @param moves the moves
     */
    public void setMoves(List<Moves> moves) {
        this.moves = moves;
    }

    /**
     * Gets stats.
     *
     * @return the stats
     */
    public List<Stats> getStats() {
        return stats;
    }

    /**
     * Sets stats.
     *
     * @param stats the stats
     */
    public void setStats(List<Stats> stats) {
        this.stats = stats;
    }

    /**
     * Gets types.
     *
     * @return the types
     */
    public List<Type> getTypes() {
        return types;
    }

    /**
     * Sets types.
     *
     * @param types the types
     */
    public void setTypes(List<Type> types) {
        this.types = types;
    }

    /**
     * Gets weight.
     *
     * @return the weight
     */
    public Double getWeight() {
        return weight;
    }

    /**
     * Sets weight.
     *
     * @param weight the weight
     */
    public void setWeight(Double weight) {
        this.weight = weight;
    }

    /**
     * Gets color hex.
     *
     * @return the color hex
     */
    public String getColorHex() {
        return colorHex;
    }

    /**
     * Sets color hex.
     *
     * @param colorHex the color hex
     */
    public void setColorHex(String colorHex) {
        this.colorHex = colorHex;
    }

    /**
     * Gets sprite bit map.
     *
     * @return the sprite bit map
     */
    public Bitmap getSpriteBitMap() {
        return spriteBitMap;
    }

    /**
     * Sets sprite bit map.
     *
     * @param spriteBitMap the sprite bit map
     */
    public void setSpriteBitMap(Bitmap spriteBitMap) {
        this.spriteBitMap = spriteBitMap;
    }

    /**
     * Is fav boolean.
     *
     * @return the boolean
     */
    public boolean isFav() {
        return isFav;
    }

    /**
     * Sets fav.
     *
     * @param fav the fav
     */
    public void setFav(boolean fav) {
        isFav = fav;
    }

}

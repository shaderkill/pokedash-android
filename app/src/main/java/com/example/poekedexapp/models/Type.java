package com.example.poekedexapp.models;
import java.util.Map;

/**
 * The type Type.
 */
public class Type {
    /**
     * Gets type.
     *
     * @return the type
     */
    public Map<String, Object> getType() {
        return type;
    }

    /**
     * Gets slot.
     *
     * @return the slot
     */
    public String getSlot() {
        return slot;
    }

    private Map<String, Object> type;
    private String slot;

}

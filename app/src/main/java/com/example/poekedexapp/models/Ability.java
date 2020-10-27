package com.example.poekedexapp.models;
import java.util.Map;

/**
 * The type Ability.
 */
public class Ability {
    /**
     * Gets ability.
     *
     * @return the ability
     */
    public Map<String, Object> getAbility() {
        return ability;
    }

    /**
     * Gets slot.
     *
     * @return the slot
     */
    public String getSlot() {
        return slot;
    }

    private Map<String, Object> ability;
    private String slot;

}

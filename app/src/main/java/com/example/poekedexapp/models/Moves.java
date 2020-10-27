package com.example.poekedexapp.models;
import java.util.List;
import java.util.Map;

/**
 * The type Moves.
 */
public class Moves {
    /**
     * Gets move.
     *
     * @return the move
     */
    public Map<String, Object> getMove() {
        return move;
    }

    /**
     * Sets move.
     *
     * @param move the move
     */
    public void setMove(Map<String, Object> move) {
        this.move = move;
    }

    /**
     * Gets version group details.
     *
     * @return the version group details
     */
    public List<Map<String, Object>> getVersion_group_details() {
        return version_group_details;
    }

    /**
     * Sets version group details.
     *
     * @param version_group_details the version group details
     */
    public void setVersion_group_details(List<Map<String, Object>> version_group_details) {
        this.version_group_details = version_group_details;
    }

    private Map<String, Object> move;
    private List<Map<String, Object>> version_group_details;

}

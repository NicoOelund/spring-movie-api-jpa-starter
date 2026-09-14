package ek.osnb.starter.model;

import jakarta.persistence.Embeddable;

@Embeddable
public class Rating {
    private Double score;      // e.g., 8.5
    private Integer voteCount; // e.g., 15000

    // TODO: Add default constructor
    public Rating(){}

    // TODO: Add constructor with parameters
    public Rating(Double score, Integer voteCount) {
        this.score = score;
        this.voteCount = voteCount;
    }

    // TODO: Add getters and setters
    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Integer getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }
}

package ek.osnb.starter.DTO;

public record CreateMovieRequest(
        String title,
        Integer releaseYear,
        String genre,
        Double ratingScore,
        Integer ratingVoteCount
) {}

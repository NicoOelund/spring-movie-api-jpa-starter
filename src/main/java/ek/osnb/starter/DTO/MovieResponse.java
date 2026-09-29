package ek.osnb.starter.DTO;

import java.util.List;

public record MovieResponse(
        Long id,
        String title,
        Integer releaseYear,
        String genre,
        Double ratingScore,
        Integer ratingVoteCount,
        MovieDetailsResponse details,
        List<ActorResponse> actors
) {}

package ek.osnb.starter.DTO;

public record MovieDetailsResponse(
        String plot,
        Integer budget,
        Integer runtime,
        String productionCompany
) {}

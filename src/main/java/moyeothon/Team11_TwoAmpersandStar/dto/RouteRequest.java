package moyeothon.Team11_TwoAmpersandStar.dto;

import moyeothon.Team11_TwoAmpersandStar.entity.Route;

public record RouteRequest(String pathData) {

    public Route toEntity() {
        return new Route(this.pathData);
    }
}

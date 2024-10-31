package moyeothon.Team11_TwoAmpersandStar.route.api.dto.request;

import moyeothon.Team11_TwoAmpersandStar.route.domain.Route;

public record RouteRequest(String pathData) {

    public Route toEntity() {
        return new Route(this.pathData);
    }
}

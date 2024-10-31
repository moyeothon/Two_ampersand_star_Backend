package moyeothon.Team11_TwoAmpersandStar.route.api.dto.request;

import moyeothon.Team11_TwoAmpersandStar.route.domain.Route;

public record RouteRequest(String date, String time, String title, String district, String speed, String pathData) {

    public Route toEntity() {
        return new Route(this.date, this.time, this.title, this.district, this.speed, this.pathData);
    }
}

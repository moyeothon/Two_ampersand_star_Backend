package moyeothon.Team11_TwoAmpersandStar.route.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import moyeothon.Team11_TwoAmpersandStar.route.api.dto.response.RouteResponse;

@Entity
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private String pathData;

    protected Route() {
    }

    public Route(String pathData) {
        this.pathData = pathData;
    }

    public Long getId() {
        return id;
    }

    public String getPathData() {
        return pathData;
    }

    public void setPathData(String pathData) {
        this.pathData = pathData;
    }

    public RouteResponse toDto() {
        return new RouteResponse(id, pathData);
    }
}

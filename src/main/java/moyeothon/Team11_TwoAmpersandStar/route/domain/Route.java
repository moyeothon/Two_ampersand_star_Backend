package moyeothon.Team11_TwoAmpersandStar.route.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import java.time.LocalDate;
import java.time.LocalTime;
import moyeothon.Team11_TwoAmpersandStar.route.api.dto.response.RouteResponse;

@Entity
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String date;
    private String time;
    private String title;
    private String district;
    private String speed;

    @Lob
    private String pathData;

    protected Route() {
    }

    public Route(String date, String time, String title, String district, String speed, String pathData) {
        this.date = date;
        this.time = time;
        this.title = title;
        this.district = district;
        this.speed = speed;
        this.pathData = pathData;
    }

    public RouteResponse toDto() {
        return new RouteResponse(id, date, time, title, district, speed, pathData);
    }
}

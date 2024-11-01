package moyeothon.Team11_TwoAmpersandStar.route.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
import java.time.LocalTime;
import moyeothon.Team11_TwoAmpersandStar.main.api.dto.response.NearbyRouteResponse;
import moyeothon.Team11_TwoAmpersandStar.member.domain.Member;
import moyeothon.Team11_TwoAmpersandStar.route.api.dto.response.RouteResponse;
import moyeothon.Team11_TwoAmpersandStar.runningInfo.api.dto.response.RunningInfoResponse;

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

    @ManyToOne
    private Member member;

    public void setMember(Member member){
        this.member = member;
    }

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

    public NearbyRouteResponse toNearbyRouteResponse() {
        return new NearbyRouteResponse(district, pathData);
    }

    public RunningInfoResponse toRunningInfoResponse(){
        return new RunningInfoResponse(date, time, title, district, speed, pathData);
    }

    public RouteResponse toDto() {
        return new RouteResponse(id, date, time, title, district, speed, pathData);
    }
}

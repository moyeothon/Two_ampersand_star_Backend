package moyeothon.Team11_TwoAmpersandStar.route.domain;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import moyeothon.Team11_TwoAmpersandStar.main.api.dto.response.NearbyRouteResponse;
import moyeothon.Team11_TwoAmpersandStar.main.api.dto.response.ScheduleResponse;
import moyeothon.Team11_TwoAmpersandStar.member.api.dto.response.CompleteRouteResponse;
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

    @OneToMany(mappedBy = "currentRoute")
    private List<Member> participants = new ArrayList<>();

    public void addParticipant(Member member) {
        participants.add(member);
    }

    protected Route() {}

    public Route(String date, String time, String title, String district, String speed, String pathData) {
        this.date = date;
        this.time = time;
        this.title = title;
        this.district = district;
        this.speed = speed;
        this.pathData = pathData;
    }

    public String getTitle(){ return title; }

    public String getPathData(){ return pathData; }

    public List<Member> getParticipants(){ return participants; }

    public ScheduleResponse toScheduleDto() {
        return new ScheduleResponse(date, time, title, district, speed, pathData);
    }

    public NearbyRouteResponse toNearbyRouteResponse() {
        return new NearbyRouteResponse(title, pathData);
    }

    public CompleteRouteResponse toCompleteRouteResponse() {
        return new CompleteRouteResponse(title, pathData);
    }

    public RunningInfoResponse toRunningInfoResponse(){
        return new RunningInfoResponse(date, time, title, district, speed, pathData);
    }

    public RouteResponse toDto() {
        return new RouteResponse(id, date, time, title, district, speed, pathData);
    }
}

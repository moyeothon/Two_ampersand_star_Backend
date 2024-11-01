package moyeothon.Team11_TwoAmpersandStar.main.api;

import java.util.List;
import moyeothon.Team11_TwoAmpersandStar.main.api.dto.response.NearbyRouteResponse;
import moyeothon.Team11_TwoAmpersandStar.main.api.dto.response.ScheduleResponse;
import moyeothon.Team11_TwoAmpersandStar.main.api.dto.response.ScheduleRouteResponse;
import moyeothon.Team11_TwoAmpersandStar.main.application.MainService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/mains")
public class MainController {

    private final MainService mainService;

    public MainController(MainService mainService) {
        this.mainService = mainService;
    }

    @GetMapping("/schedule")
    public ResponseEntity<ScheduleResponse> getSchedule(
        @RequestHeader("Authorization") String authorization) {
        ScheduleResponse schedule = mainService.getSchedule(authorization);
        return ResponseEntity.status(HttpStatus.OK).body(schedule);
    }

    @GetMapping("/location")
    public ResponseEntity<ScheduleRouteResponse> getScheduleRoute(
        @RequestHeader("Authorization") String authorization) {
        ScheduleRouteResponse ScheduleRoute = mainService.getScheduleRoute(authorization);
        return ResponseEntity.status(HttpStatus.OK).body(ScheduleRoute);
    }

    @GetMapping("/routes")
    public ResponseEntity<List<NearbyRouteResponse>> getNearbyRoutes(
        @RequestHeader("Authorization") String authorization) {
        List<NearbyRouteResponse> nearbyRoutes = mainService.getNearbyRoutes(authorization);
        return ResponseEntity.status(HttpStatus.OK).body(nearbyRoutes);
    }
}

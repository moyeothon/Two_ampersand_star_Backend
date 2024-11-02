package moyeothon.Team11_TwoAmpersandStar.runningInfo.api;

import moyeothon.Team11_TwoAmpersandStar.runningInfo.api.dto.response.RunningInfoResponse;
import moyeothon.Team11_TwoAmpersandStar.runningInfo.application.RunningInfoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/running/info")
public class RunningInfoController {

    private final RunningInfoService runningInfoService;

    public RunningInfoController(RunningInfoService runningInfoService) {
        this.runningInfoService = runningInfoService;
    }

    @GetMapping("/{routeId}")
    ResponseEntity<RunningInfoResponse> getRunningInfo(@PathVariable("routeId") Long routeId) {
        RunningInfoResponse RunningInfo = runningInfoService.getRunningInfo(routeId);
        return ResponseEntity.status(HttpStatus.OK).body(RunningInfo);
    }

    @PostMapping("/{routeId}")
    ResponseEntity<Void> tryRunning(
        @RequestHeader("Authorization") String authorization,
        @PathVariable("routeId") Long routeId) {
        runningInfoService.tryRunning(authorization, routeId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}

package moyeothon.Team11_TwoAmpersandStar.runningInfo.application;

import moyeothon.Team11_TwoAmpersandStar.global.jwt.TokenProvider;
import moyeothon.Team11_TwoAmpersandStar.member.domain.Member;
import moyeothon.Team11_TwoAmpersandStar.route.domain.Route;
import moyeothon.Team11_TwoAmpersandStar.route.domain.repository.RouteRepository;
import moyeothon.Team11_TwoAmpersandStar.runningInfo.api.dto.response.RunningInfoResponse;
import org.springframework.stereotype.Service;

@Service
public class RunningInfoService {

    private final RouteRepository routeRepository;
    private final TokenProvider tokenProvider;

    public RunningInfoService(RouteRepository routeRepository, TokenProvider tokenProvider) {
        this.routeRepository = routeRepository;
        this.tokenProvider = tokenProvider;
    }

    public RunningInfoResponse getRunningInfo(Long routeId) {
        Route route = routeRepository.findById(routeId)
            .orElseThrow(() -> new RuntimeException("경로를 찾을 수 없습니다."));
        RunningInfoResponse runningInfo = route.toRunningInfoResponse();
        return runningInfo;
    }

    public void tryRunning(String authorization, Long routeId) {
        Member member = tokenProvider.getMemberFromToken(authorization);
        Route route = routeRepository.findById(routeId)
            .orElseThrow(() -> new RuntimeException("경로를 찾을 수 없습니다."));
        route.setMember(member);
    }
}

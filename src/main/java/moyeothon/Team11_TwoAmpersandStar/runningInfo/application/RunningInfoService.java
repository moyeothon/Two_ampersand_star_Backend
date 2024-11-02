package moyeothon.Team11_TwoAmpersandStar.runningInfo.application;

import moyeothon.Team11_TwoAmpersandStar.global.jwt.TokenProvider;
import moyeothon.Team11_TwoAmpersandStar.member.domain.Member;
import moyeothon.Team11_TwoAmpersandStar.member.domain.repository.MemberRepository;
import moyeothon.Team11_TwoAmpersandStar.route.domain.Route;
import moyeothon.Team11_TwoAmpersandStar.route.domain.repository.RouteRepository;
import moyeothon.Team11_TwoAmpersandStar.runningInfo.api.dto.response.RunningInfoResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RunningInfoService {

    private final MemberRepository memberRepository;
    private final RouteRepository routeRepository;
    private final TokenProvider tokenProvider;

    public RunningInfoService(RouteRepository routeRepository,
        TokenProvider tokenProvider, MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
        this.routeRepository = routeRepository;
        this.tokenProvider = tokenProvider;
    }

    public RunningInfoResponse getRunningInfo(Long routeId) {
        Route route = routeRepository.findById(routeId)
            .orElseThrow(() -> new RuntimeException("경로를 찾을 수 없습니다."));
        return route.toRunningInfoResponse();
    }

    @Transactional
    public void tryRunning(String authorization, Long routeId) {
        Member member = tokenProvider.getMemberFromToken(authorization);

        if (member.getCurrentRoute() != null) {
            throw new RuntimeException("이미 다른 경로에 참여 중입니다.");
        }

        Route route = routeRepository.findById(routeId)
            .orElseThrow(() -> new RuntimeException("경로를 찾을 수 없습니다."));

        member.setCurrentRoute(route);
        route.addParticipant(member);
        memberRepository.save(member);
        routeRepository.save(route);
    }
}

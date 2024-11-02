package moyeothon.Team11_TwoAmpersandStar.main.application;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import moyeothon.Team11_TwoAmpersandStar.global.jwt.TokenProvider;
import moyeothon.Team11_TwoAmpersandStar.main.api.dto.response.NearbyRouteResponse;
import moyeothon.Team11_TwoAmpersandStar.main.api.dto.response.ScheduleResponse;
import moyeothon.Team11_TwoAmpersandStar.main.api.dto.response.ScheduleRouteResponse;
import moyeothon.Team11_TwoAmpersandStar.member.domain.Member;
import moyeothon.Team11_TwoAmpersandStar.member.domain.repository.MemberRepository;
import moyeothon.Team11_TwoAmpersandStar.route.domain.CompleteRoute;
import moyeothon.Team11_TwoAmpersandStar.route.domain.Route;
import moyeothon.Team11_TwoAmpersandStar.route.domain.repository.CompleteRouteRepository;
import moyeothon.Team11_TwoAmpersandStar.route.domain.repository.RouteRepository;
import org.springframework.stereotype.Service;

@Service
public class MainService {

    private final RouteRepository routeRepository;
    private final TokenProvider tokenProvider;
    private final MemberRepository memberRepository;
    private final CompleteRouteRepository completeRouteRepository;

    public MainService(RouteRepository routeRepository,
        TokenProvider tokenProvider, CompleteRouteRepository completeRouteRepository,
        MemberRepository memberRepository) {
        this.routeRepository = routeRepository;
        this.tokenProvider = tokenProvider;
        this.completeRouteRepository = completeRouteRepository;
        this.memberRepository = memberRepository;
    }

    public List<NearbyRouteResponse> getNearbyRoutes(String authorization) {
        Member member = tokenProvider.getMemberFromToken(authorization);
        String district = member.getDistrict();
        List<Route> routes = routeRepository.findByDistrict(district);
        return routes.stream()
            .map(Route::toNearbyRouteResponse)
            .collect(Collectors.toList());
    }

    @Transactional
    public void completeCurrentRoute(String authorization) {
        Member member = tokenProvider.getMemberFromToken(authorization);
        Route currentRoute = member.getCurrentRoute();
        if (currentRoute == null) {
            throw new RuntimeException("현재 참여 했던 경로가 없습니다.");
        }

        CompleteRoute completeRoute = new CompleteRoute(member, currentRoute, currentRoute.getTitle());
        completeRouteRepository.save(completeRoute);

        currentRoute.getParticipants().remove(member);
        member.setCurrentRoute(null);
        memberRepository.save(member);
    }

    public ScheduleResponse getSchedule(String authorization) {
        Member member = tokenProvider.getMemberFromToken(authorization);
        Route currentRoute = member.getCurrentRoute();
        if (currentRoute == null) {
            throw new RuntimeException("현재 참여 중인 코스가 없습니다.");
        }

        return currentRoute.toScheduleDto();
    }

    public ScheduleRouteResponse getScheduleRoute(String authorization) {
        Member member = tokenProvider.getMemberFromToken(authorization);
        Route currentRoute = member.getCurrentRoute();
        if (currentRoute == null) {
            throw new RuntimeException("현재 참여 중인 경로가 없습니다.");
        }

        return new ScheduleRouteResponse(currentRoute.getPathData());
    }
}

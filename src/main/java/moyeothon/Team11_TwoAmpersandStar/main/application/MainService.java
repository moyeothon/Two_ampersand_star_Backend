package moyeothon.Team11_TwoAmpersandStar.main.application;

import java.util.List;
import java.util.stream.Collectors;
import moyeothon.Team11_TwoAmpersandStar.global.jwt.TokenProvider;
import moyeothon.Team11_TwoAmpersandStar.main.api.dto.response.NearbyRouteResponse;
import moyeothon.Team11_TwoAmpersandStar.member.domain.Member;
import moyeothon.Team11_TwoAmpersandStar.member.domain.repository.UserRepository;
import moyeothon.Team11_TwoAmpersandStar.route.api.dto.response.RouteResponse;
import moyeothon.Team11_TwoAmpersandStar.route.domain.Route;
import moyeothon.Team11_TwoAmpersandStar.route.domain.repository.RouteRepository;
import org.springframework.stereotype.Service;

@Service
public class MainService {

    private final UserRepository userRepository;
    private final RouteRepository routeRepository;
    private final TokenProvider tokenProvider;

    public MainService(UserRepository userRepository, RouteRepository routeRepository,
        TokenProvider tokenProvider){
        this.userRepository = userRepository;
        this.routeRepository = routeRepository;
        this.tokenProvider = tokenProvider;
    }

    public List<NearbyRouteResponse> getNearbyRoutes(String authorization){
        Member member = tokenProvider.getMemberFromToken(authorization);
        String district  = member.getDistrict();
        List<Route> routes = routeRepository.findByDistrict(district);
        return routes.stream()
            .map(Route::toNearbyRouteResponse)
            .collect(Collectors.toList());
    }


}

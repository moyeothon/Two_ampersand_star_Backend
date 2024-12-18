package moyeothon.Team11_TwoAmpersandStar.route.application;


import java.util.List;
import java.util.stream.Collectors;
import moyeothon.Team11_TwoAmpersandStar.route.api.dto.request.RouteRequest;
import moyeothon.Team11_TwoAmpersandStar.route.api.dto.response.RouteResponse;
import moyeothon.Team11_TwoAmpersandStar.route.domain.Route;
import moyeothon.Team11_TwoAmpersandStar.route.domain.repository.RouteRepository;
import org.springframework.stereotype.Service;

@Service
public class RouteService {

    private final RouteRepository routeRepository;

    public RouteService(RouteRepository routeRepository){
        this.routeRepository = routeRepository;
    }

    public void saveRoute(RouteRequest request) {
        Route route = request.toEntity();
        routeRepository.save(route);
    }

    public List<RouteResponse> getAllRoutes() {
        return routeRepository.findAll().stream()
            .map(Route::toDto)
            .collect(Collectors.toList());
    }

    public RouteResponse getRouteById(Long id) {
        Route route = routeRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("해당 경로를 찾지 못했습니다."));
        return route.toDto();
    }
}

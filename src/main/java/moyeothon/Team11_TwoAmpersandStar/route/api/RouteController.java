package moyeothon.Team11_TwoAmpersandStar.route.api;


import java.util.List;
import moyeothon.Team11_TwoAmpersandStar.route.api.dto.request.RouteRequest;
import moyeothon.Team11_TwoAmpersandStar.route.api.dto.response.RouteResponse;
import moyeothon.Team11_TwoAmpersandStar.route.application.RouteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/routes")
public class RouteController {

    private final RouteService routeService;

    public RouteController(RouteService routeService) {
        this.routeService = routeService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> saveRoute(@RequestBody RouteRequest request) {
        routeService.saveRoute(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<RouteResponse>> getAllRoutes() {
        List<RouteResponse> routes = routeService.getAllRoutes();
        return ResponseEntity.status(HttpStatus.OK).body(routes);
    }

    @GetMapping("/{routeId}")
    public ResponseEntity<RouteResponse> getRouteById(@PathVariable("routeId") Long id) {
        RouteResponse routeResponse = routeService.getRouteById(id);
        return ResponseEntity.status(HttpStatus.OK).body(routeResponse);
    }
}

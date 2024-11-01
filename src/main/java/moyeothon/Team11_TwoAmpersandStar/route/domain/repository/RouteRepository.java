package moyeothon.Team11_TwoAmpersandStar.route.domain.repository;

import java.util.List;
import moyeothon.Team11_TwoAmpersandStar.route.domain.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RouteRepository extends JpaRepository<Route, Long> {
    List<Route> findByDistrict(String district);
}

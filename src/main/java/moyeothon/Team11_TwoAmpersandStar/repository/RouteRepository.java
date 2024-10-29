package moyeothon.Team11_TwoAmpersandStar.repository;

import moyeothon.Team11_TwoAmpersandStar.entity.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RouteRepository extends JpaRepository<Route, Long> {


}

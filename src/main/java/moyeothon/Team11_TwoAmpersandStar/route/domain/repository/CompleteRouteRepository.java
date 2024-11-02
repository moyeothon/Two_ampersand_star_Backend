package moyeothon.Team11_TwoAmpersandStar.route.domain.repository;

import java.util.List;
import moyeothon.Team11_TwoAmpersandStar.member.domain.Member;
import moyeothon.Team11_TwoAmpersandStar.route.domain.CompleteRoute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompleteRouteRepository extends JpaRepository<CompleteRoute, Long> {
    List<CompleteRoute> findByMember(Member member);
}

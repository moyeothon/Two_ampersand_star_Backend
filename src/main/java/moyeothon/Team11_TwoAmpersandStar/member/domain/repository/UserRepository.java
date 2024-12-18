package moyeothon.Team11_TwoAmpersandStar.member.domain.repository;

import java.util.Optional;
import moyeothon.Team11_TwoAmpersandStar.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);
}

package moyeothon.Team11_TwoAmpersandStar.member.application;

import java.util.List;
import java.util.stream.Collectors;
import moyeothon.Team11_TwoAmpersandStar.global.jwt.TokenProvider;
import moyeothon.Team11_TwoAmpersandStar.member.api.dto.response.CompleteRouteResponse;
import moyeothon.Team11_TwoAmpersandStar.member.api.dto.response.MemberInfoResDto;
import moyeothon.Team11_TwoAmpersandStar.member.domain.Member;
import moyeothon.Team11_TwoAmpersandStar.member.domain.repository.MemberRepository;
import moyeothon.Team11_TwoAmpersandStar.member.exception.NotFoundMemberException;
import moyeothon.Team11_TwoAmpersandStar.route.domain.CompleteRoute;
import moyeothon.Team11_TwoAmpersandStar.route.domain.repository.CompleteRouteRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final CompleteRouteRepository completeRouteRepository;

    public UserService(MemberRepository memberRepository, PasswordEncoder passwordEncoder,
        TokenProvider tokenProvider, CompleteRouteRepository completeRouteRepository) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
        this.completeRouteRepository = completeRouteRepository;
    }

    public MemberInfoResDto getUser(String email) {
        Member member = memberRepository.findByEmail(email)
            .orElseThrow(NotFoundMemberException::new);
        return MemberInfoResDto.from(member);
    }

    @Transactional
    public void updateUser(String email, String nickName, String password) {
        Member member = memberRepository.findByEmail(email)
            .orElseThrow(NotFoundMemberException::new);
        String encodePassword = passwordEncoder.encode(password);
        member.update(nickName, encodePassword);
    }

    public List<CompleteRouteResponse> getComplete(String authorization) {
        Member member = tokenProvider.getMemberFromToken(authorization);
        List<CompleteRoute> completeRoutes = completeRouteRepository.findByMember(member);
        return completeRoutes.stream()
            .map(completeRoute -> completeRoute.getRoute().toCompleteRouteResponse())
            .collect(Collectors.toList());
    }
}

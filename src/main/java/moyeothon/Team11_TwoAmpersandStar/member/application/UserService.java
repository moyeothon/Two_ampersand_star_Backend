package moyeothon.Team11_TwoAmpersandStar.member.application;

import java.util.List;
import moyeothon.Team11_TwoAmpersandStar.global.jwt.TokenProvider;
import moyeothon.Team11_TwoAmpersandStar.member.api.dto.response.CompleteRouteResponse;
import moyeothon.Team11_TwoAmpersandStar.member.api.dto.response.MemberInfoResDto;
import moyeothon.Team11_TwoAmpersandStar.member.domain.Member;
import moyeothon.Team11_TwoAmpersandStar.member.domain.repository.MemberRepository;
import moyeothon.Team11_TwoAmpersandStar.member.exception.NotFoundMemberException;
import moyeothon.Team11_TwoAmpersandStar.route.domain.repository.RouteRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final RouteRepository routeRepository;

    public UserService(MemberRepository memberRepository, PasswordEncoder passwordEncoder,
        TokenProvider tokenProvider, RouteRepository routeRepository) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
        this.routeRepository = routeRepository;
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
/*
    public List<CompleteRouteResponse> getComplete(String authorization) {
        Member member = tokenProvider.getMemberFromToken(authorization);

    }

 */
}

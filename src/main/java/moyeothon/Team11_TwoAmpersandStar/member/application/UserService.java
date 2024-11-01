package moyeothon.Team11_TwoAmpersandStar.member.application;

import moyeothon.Team11_TwoAmpersandStar.member.api.dto.response.MemberInfoResDto;
import moyeothon.Team11_TwoAmpersandStar.member.domain.Member;
import moyeothon.Team11_TwoAmpersandStar.member.domain.repository.MemberRepository;
import moyeothon.Team11_TwoAmpersandStar.member.exception.NotFoundMemberException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
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
}

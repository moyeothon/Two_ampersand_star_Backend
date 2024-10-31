package moyeothon.Team11_TwoAmpersandStar.member.application;

import moyeothon.Team11_TwoAmpersandStar.member.domain.Member;
import moyeothon.Team11_TwoAmpersandStar.member.domain.repository.MemberRepository;
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

    @Transactional
    public void updateUser(String email, String nickName, String password) {
        Member member = memberRepository.findByEmail(email)
            .orElseThrow(IllegalAccessError::new);
        String encodePassword = passwordEncoder.encode(password);
        member.update(nickName, encodePassword);
    }
}

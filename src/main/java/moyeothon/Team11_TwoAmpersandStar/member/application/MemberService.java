package moyeothon.Team11_TwoAmpersandStar.member.application;

import moyeothon.Team11_TwoAmpersandStar.global.jwt.TokenProvider;
import moyeothon.Team11_TwoAmpersandStar.member.api.dto.request.MemberCreateReqDto;
import moyeothon.Team11_TwoAmpersandStar.member.api.dto.request.MemberLoginReqDto;
import moyeothon.Team11_TwoAmpersandStar.member.api.dto.response.MemberLoginResDto;
import moyeothon.Team11_TwoAmpersandStar.member.domain.Member;
import moyeothon.Team11_TwoAmpersandStar.member.domain.repository.MemberRepository;
import moyeothon.Team11_TwoAmpersandStar.member.exception.InvalidMemberException;
import moyeothon.Team11_TwoAmpersandStar.member.exception.NotFoundMemberException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    public MemberService(MemberRepository memberRepository, PasswordEncoder passwordEncoder,
        TokenProvider tokenProvider) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
    }

    public void join(MemberCreateReqDto memberCreateReqDto) {
        if (memberRepository.existsByEmail(memberCreateReqDto.email())) {
            throw new InvalidMemberException("이미 존재하는 이메일입니다.");
        }

        Member member = new Member(
            memberCreateReqDto.nickName(),
            memberCreateReqDto.email(),
            memberCreateReqDto.city(),
            memberCreateReqDto.district(),
            passwordEncoder.encode(memberCreateReqDto.pwd())
        );
        memberRepository.save(member);
    }

    public MemberLoginResDto login(MemberLoginReqDto memberLoginReqDto) {
        Member member = memberRepository.findByEmail(memberLoginReqDto.email())
            .orElseThrow(NotFoundMemberException::new);

        if (!passwordEncoder.matches(memberLoginReqDto.pwd(), member.getPwd())) {
            throw new InvalidMemberException("패스워드가 일치하지 않습니다.");
        }

        String token = tokenProvider.generateToken(member.getEmail());
        return MemberLoginResDto.of(member, token);
    }
}

package moyeothon.Team11_TwoAmpersandStar.member.api.dto.response;

import moyeothon.Team11_TwoAmpersandStar.member.domain.Member;

public record MemberLoginResDto(
        String token
) {
    public static MemberLoginResDto of(Member member, String token) {
        return new MemberLoginResDto(
                token
        );
    }
}

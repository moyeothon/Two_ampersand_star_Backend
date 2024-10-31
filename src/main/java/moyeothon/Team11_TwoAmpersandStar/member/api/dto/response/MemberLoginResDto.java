package moyeothon.Team11_TwoAmpersandStar.member.api.dto.response;

import moyeothon.Team11_TwoAmpersandStar.member.domain.Member;

public record MemberLoginResDto(
        String assessToken
) {
    public static MemberLoginResDto of(Member member, String assessToken) {
        return new MemberLoginResDto(
            assessToken
        );
    }
}

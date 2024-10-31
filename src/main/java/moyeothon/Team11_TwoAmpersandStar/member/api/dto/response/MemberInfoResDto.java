package moyeothon.Team11_TwoAmpersandStar.member.api.dto.response;

import moyeothon.Team11_TwoAmpersandStar.member.domain.Member;

public record MemberInfoResDto(
    String nickName,
    String password
) {

    public static MemberInfoResDto from(Member member) {
        return new MemberInfoResDto(
            member.getNickName(),
            member.getPassword()
        );
    }

}

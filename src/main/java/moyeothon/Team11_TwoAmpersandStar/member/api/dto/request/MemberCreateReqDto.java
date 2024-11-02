package moyeothon.Team11_TwoAmpersandStar.member.api.dto.request;

public record MemberCreateReqDto(
    String email,
    String password,
    String passwordCheck,
    String nickName,
    String district,
    String city
) {

}

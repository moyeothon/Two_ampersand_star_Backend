package moyeothon.Team11_TwoAmpersandStar.member.api.dto.request;

public record MemberCreateReqDto(
    String nickName,
    String email,
    String city,
    String district,
    String pwd,
    String checkPwd
) {

}

package moyeothon.Team11_TwoAmpersandStar.member.api.dto.request;

public record MemberCreateReqDto(
        String name,
        String email,
        String pwd,
        String checkPwd
) {
}

package moyeothon.Team11_TwoAmpersandStar.member.api;

import moyeothon.Team11_TwoAmpersandStar.member.api.dto.request.MemberUpdateReqDto;
import moyeothon.Team11_TwoAmpersandStar.member.application.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping("info")
    public ResponseEntity<Void> updateUser(@AuthenticationPrincipal String email,
        @RequestBody MemberUpdateReqDto memberUpdateReqDto) {
        userService.updateUser(email, memberUpdateReqDto.nickName(), memberUpdateReqDto.password());
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}

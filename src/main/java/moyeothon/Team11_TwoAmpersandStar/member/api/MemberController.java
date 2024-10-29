package moyeothon.Team11_TwoAmpersandStar.member.api;

import moyeothon.Team11_TwoAmpersandStar.member.api.dto.request.MemberCreateReqDto;
import moyeothon.Team11_TwoAmpersandStar.member.api.dto.request.MemberLoginReqDto;
import moyeothon.Team11_TwoAmpersandStar.member.api.dto.response.MemberLoginResDto;
import moyeothon.Team11_TwoAmpersandStar.member.application.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/members")
public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping()
    public ResponseEntity<String> join(@RequestBody MemberCreateReqDto memberCreateReqDto) {
        memberService.join(memberCreateReqDto);
        return new ResponseEntity<>("회원가입", HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<MemberLoginResDto> login(@RequestBody MemberLoginReqDto memberLoginReqDto) {
        MemberLoginResDto memberLoginResDto = memberService.login(memberLoginReqDto);
        return new ResponseEntity<>(memberLoginResDto, HttpStatus.OK);
    }
}

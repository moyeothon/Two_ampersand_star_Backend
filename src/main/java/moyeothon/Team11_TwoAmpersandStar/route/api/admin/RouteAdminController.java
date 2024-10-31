package moyeothon.Team11_TwoAmpersandStar.route.api.admin;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RouteAdminController {

    @Value("${kakao.map.api-key}")
    private String kakaoMapApiKey;

    @GetMapping("/admin/route")
    public String routeDrawingPage(Model model) {
        model.addAttribute("kakaoMapApiKey", kakaoMapApiKey);
        return "admin/routeDrawingAdmin";
    }
}

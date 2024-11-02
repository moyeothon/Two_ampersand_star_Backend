package moyeothon.Team11_TwoAmpersandStar.route.domain;

import jakarta.persistence.*;
import moyeothon.Team11_TwoAmpersandStar.member.domain.Member;

@Entity
@Table(name = "completeRoutes")
public class CompleteRoute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Member member;

    @ManyToOne
    private Route route;

    private String title;

    protected CompleteRoute() {}

    public CompleteRoute(Member member, Route route, String title) {
        this.member = member;
        this.route = route;
        this.title = title;
    }

    public Member getMember() { return member; }
    public Route getRoute() { return route; }
    public String getTitle() { return title; }
}

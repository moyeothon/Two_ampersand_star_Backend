package moyeothon.Team11_TwoAmpersandStar.member.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;
import moyeothon.Team11_TwoAmpersandStar.route.domain.Route;

@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(name = "nickName")
    private String nickName;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "city")
    private String city;

    @Column(name = "district")
    private String district;

    @Lob
    private String pathDate;

    @Transient
    private String passwordCheck;

    @ManyToOne
    private Route currentRoute;

    public Member() {
    }

    public Member(String nickName, String email, String city, String district, String password) {
        this.nickName = nickName;
        this.email = email;
        this.city = city;
        this.district = district;
        this.password = password;
    }

    public Long getId() { return id; }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getNickName() {
        return nickName;
    }

    public String getDistrict() { return district; }

    public Route getCurrentRoute() {
        return currentRoute;
    }

    public void setCurrentRoute(Route route) {
        this.currentRoute = route;
    }

    public void update(String nickName, String password) {
        this.nickName = nickName;
        this.password = password;
    }
}

package moyeothon.Team11_TwoAmpersandStar.member.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Transient;
//import moyeothon.Team11_TwoAmpersandStar.member.api.dto.request.MemberUpdateReqDto;

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
    private String pwd;

    @Column(name = "city")
    private String city;

    @Column(name = "district")
    private String district;

    @Lob
    private String pathDate;

    @Transient
    private String checkPwd;

    public Member() {
    }

    public Member(String nickName, String email, String city, String district, String pwd) {
        this.nickName = nickName;
        this.email = email;
        this.city = city;
        this.district = district;
        this.pwd = pwd;
    }

    public String getEmail() {
        return email;
    }

    public String getPwd() {
        return pwd;
    }

    private boolean isPasswordMatching(String checkPwd) {
        return this.pwd.equals(checkPwd);
    }
/*
    public void update(MemberUpdateReqDto memberUpdateReqDto) {
        this.nickName = nickName;
        this.pwd = pwd;
        this.city = city;
        this.district = district;
    }
    */
}

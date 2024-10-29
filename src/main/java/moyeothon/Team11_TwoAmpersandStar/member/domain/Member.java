package moyeothon.Team11_TwoAmpersandStar.member.domain;

import jakarta.persistence.*;

@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String pwd;

    @Lob
    private String pathDate;

    @Transient
    private String checkPwd;

    public Member() { }

    public Member(String name, String email, String pwd) {
        this.name = name;
        this.email = email;
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

}

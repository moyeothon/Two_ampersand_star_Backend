package moyeothon.Team11_TwoAmpersandStar.global.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;
import moyeothon.Team11_TwoAmpersandStar.global.jwt.exception.CustomAuthenticationException;
import moyeothon.Team11_TwoAmpersandStar.member.domain.Member;
import moyeothon.Team11_TwoAmpersandStar.member.domain.repository.MemberRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Component
public class TokenProvider {
    private final MemberRepository memberRepository;
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Value("${token.expire.time}")
    private String tokenExpireTime;

    @Value("${jwt.secret}")
    private String secret;
    private Key key;

    public TokenProvider(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @PostConstruct
    public void init() {
        byte[] key = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(key);
    }

    public String generateToken(String email) {
        Date date = new Date();
        Date expiryDate = new Date(date.getTime() + Long.parseLong(tokenExpireTime));

        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(date)
                .setExpiration(expiryDate)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (UnsupportedJwtException | MalformedJwtException exception) {
            log.error("JWT가 유효하지 않습니다."); // 로그를 통해 예외 메시지 출력
            throw new CustomAuthenticationException("JWT가 유효하지 않습니다."); // 커스텀 예외 발생
        } catch (SignatureException exception) {
            log.error("JWT 서명 검증에 실패했습니다."); // 로그를 통해 예외 메시지 출력
            throw new CustomAuthenticationException("JWT 서명 검증에 실패했습니다."); // 커스텀 예외 발생
        } catch (ExpiredJwtException exception) {
            log.error("JWT가 만료되었습니다."); // 로그를 통해 예외 메시지 출력
            throw new CustomAuthenticationException("JWT가 만료되었습니다."); // 커스텀 예외 발생
        } catch (IllegalArgumentException exception) {
            log.error("JWT가 null이거나 비어 있거나 공백만 있습니다."); // 로그를 통해 예외 메시지 출력
            throw new CustomAuthenticationException("JWT가 null이거나 비어 있거나 공백만 있습니다."); // 커스텀 예외 발생
        } catch (Exception exception) {
            log.error("JWT 검증에 실패했습니다.", exception); // 로그를 통해 예외 메시지 출력
            throw new CustomAuthenticationException("JWT 검증에 실패했습니다."); // 커스텀 예외 발생
        }
    }

    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        Member member = memberRepository.findByEmail(claims.getSubject()).orElseThrow();

        return new UsernamePasswordAuthenticationToken(member.getEmail(), "", Collections.emptyList());
    }

}

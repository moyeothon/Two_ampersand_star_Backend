package moyeothon.Team11_TwoAmpersandStar.global.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;
import java.security.Key;
import java.util.Collections;
import java.util.Date;
import moyeothon.Team11_TwoAmpersandStar.global.jwt.exception.CustomAuthenticationException;
import moyeothon.Team11_TwoAmpersandStar.member.domain.Member;
import moyeothon.Team11_TwoAmpersandStar.member.domain.repository.MemberRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class TokenProvider {

    private final MemberRepository memberRepository;
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Value("${token.expire.time}")
    private String tokenExpireTime;

    private Key key;

    public TokenProvider(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @PostConstruct
    public void init() {
        this.key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
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

    private String resolveToken(String token) {
        if (StringUtils.hasText(token) && token.startsWith("Bearer ")) {
            return token.substring(7).trim();
        }
        return token != null ? token.trim() : null;
    }

    public boolean validateToken(String token) {
        try {
            String resolvedToken = resolveToken(token);
            Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(resolvedToken);
            return true;
        } catch (UnsupportedJwtException | MalformedJwtException exception) {
            log.error("유효하지 않은 JWT입니다: {}", exception.getMessage());
            throw new CustomAuthenticationException("JWT가 유효하지 않습니다.");
        } catch (SignatureException exception) {
            log.error("JWT 서명 검증 실패: {}", exception.getMessage());
            throw new CustomAuthenticationException("JWT 서명 검증에 실패했습니다.");
        } catch (ExpiredJwtException exception) {
            log.error("만료된 JWT입니다: {}", exception.getMessage());
            throw new CustomAuthenticationException("JWT가 만료되었습니다.");
        } catch (IllegalArgumentException exception) {
            log.error("JWT가 null이거나 공백만 있습니다: {}", exception.getMessage());
            throw new CustomAuthenticationException("JWT가 null이거나 비어 있습니다.");
        } catch (Exception exception) {
            log.error("JWT 검증 실패", exception);
            throw new CustomAuthenticationException("JWT 검증에 실패했습니다.");
        }
    }

    public Authentication getAuthentication(String token) {
        String resolvedToken = resolveToken(token);
        Claims claims = Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(resolvedToken)
            .getBody();
        Member member = memberRepository.findByEmail(claims.getSubject()).orElseThrow();
        return new UsernamePasswordAuthenticationToken(member.getEmail(), "", Collections.emptyList());
    }

    public Member getMemberFromToken(String token) {
        Authentication authentication = getAuthentication(token);
        String email = (String) authentication.getPrincipal();
        return memberRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
    }
}

package by.mk_jd2_92_22.reportService.security;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Base64;

@Component
public class JwtProvider {

    @Value("${jwt.secret}")
    private String secretKey;
    @Value("${jwt.expiration}")
    private long validityToMilliseconds;
//    @Value("${jwt.issuer}")
//    private String jwtIssuer;

    @PostConstruct
    protected void init(){
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }


//    public String createToken(String username){
//
////        Claims claims = Jwts.claims().setSubject(username);
////        claims.put("role", role);
//        Date now = new Date();
//        Date validity = new Date(now.getTime()+ TimeUnit.MINUTES.toMillis(validityToMilliseconds ));

//        return Jwts.builder()
////                .setClaims(claims)
//                .setSubject(username)
////                .setIssuer(jwtIssuer)
//                .setIssuedAt(now)
//                .setExpiration(validity)
//                .signWith(SignatureAlgorithm.HS512, secretKey)
//                .compact();
//    }

    public String getUsername(String token){
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

//    public Date getExpirationDate(String token) {
//        return Jwts.parser()
//                .setSigningKey(secretKey)
//                .parseClaimsJws(token)
//                .getBody().getExpiration();
//    }
    public boolean validate(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (SignatureException ex) {
            //logger.error("Invalid JWT signature - {}", ex.getMessage());
        } catch (MalformedJwtException ex) {
            //logger.error("Invalid JWT token - {}", ex.getMessage());
        } catch (ExpiredJwtException ex) {
            //logger.error("Expired JWT token - {}", ex.getMessage());
        } catch (UnsupportedJwtException ex) {
            //logger.error("Unsupported JWT token - {}", ex.getMessage());
        } catch (IllegalArgumentException ex) {
            //logger.error("JWT claims string is empty - {}", ex.getMessage());
        }
        return false;
    }
//    public boolean validateToken(String token){
//        try {
//            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
//            return !claimsJws.getBody().getExpiration().before(new Date());
//        } catch (JwtException | IllegalArgumentException e) {
//            throw new JwtAuthenticationException("JWT is expired or invalid", HttpStatus.UNAUTHORIZED);
//        }
//
//    }

//    public Authentication getAuthentication(String token){
//        UserDetails userDetails = this.userDetailsService.loadUserByUsername(getUsername(token));
//        return new UsernamePasswordAuthenticationToken(userDetails,"", userDetails.getAuthorities());
//    }

//    public String resolveToken(HttpServletRequest request){
//        return request.getHeader(HttpHeaders.AUTHORIZATION);
//    }


}

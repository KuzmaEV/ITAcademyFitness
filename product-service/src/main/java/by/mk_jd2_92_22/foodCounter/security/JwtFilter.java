package by.mk_jd2_92_22.foodCounter.security;

import by.mk_jd2_92_22.foodCounter.security.customDatail.CustomUserDetailsService;
import by.mk_jd2_92_22.foodCounter.security.customDatail.entity.CustomUserDetails;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


import static org.apache.logging.log4j.util.Strings.isEmpty;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final CustomUserDetailsService detailsService;

    public JwtFilter(JwtProvider jwtProvider, CustomUserDetailsService detailsService) {
        this.jwtProvider = jwtProvider;
        this.detailsService = detailsService;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (isEmpty(header) || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String token = header.split(" ")[1].trim();
        if (!jwtProvider.validate(token)) {
            filterChain.doFilter(request, response);
            return;
        }

//        String token = this.jwtProvider.resolveToken(request);
//        try {
//            if (token != null && jwtProvider.validate(token)) {
//                Authentication authentication = jwtProvider.getAuthentication(token);
//                if (authentication != null) {
//                    SecurityContextHolder.getContext().setAuthentication(authentication);
//                }
//            }
//        } catch (JwtAuthenticationException e) {
//            SecurityContextHolder.clearContext();
//            ((HttpServletResponse) response).sendError(e.getHttpStatus().value());
//            throw new JwtAuthenticationException("JWT expired or invalid");
//        }

        CustomUserDetails userDetails = detailsService
                .loadUserByUsername(token);

        UsernamePasswordAuthenticationToken
                authentication = new UsernamePasswordAuthenticationToken(
                userDetails, null,
                userDetails.getAuthorities()
        );

        authentication.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(request)
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }
}

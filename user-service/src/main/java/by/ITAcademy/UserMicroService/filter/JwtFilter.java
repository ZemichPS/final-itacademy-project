package by.ITAcademy.UserMicroService.filter;

import by.ITAcademy.UserMicroService.services.utils.AccountStatusUserDetailsChecker;
import by.ITAcademy.UserMicroService.services.utils.JwtTokenHandler;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

import static org.apache.logging.log4j.util.Strings.isEmpty;
import static org.apache.logging.log4j.util.Strings.trimToNull;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final UserDetailsService detailsService;
    private final JwtTokenHandler jwtHandler;

    public JwtFilter(UserDetailsService userDetailsService, JwtTokenHandler jwtHandler) {
        this.detailsService = userDetailsService;
        this.jwtHandler = jwtHandler;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain)
            throws ServletException, IOException {

        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (isEmpty(header) || !header.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        // Get jwt token and validate
        final String token = header.split(" ")[1].trim();

        if (!jwtHandler.validate(token)) {
            chain.doFilter(request, response);
            return;
        }

        UserDetails userDetails = null;


            userDetails = detailsService.loadUserByUsername(jwtHandler.getUsername(token));

    /*
    } else {
            userDetails = User.builder()
                    .username("")
                    .roles("GUEST")
                    .accountLocked(true)
                    .authorities("GUEST")
                    .build();
        }

*/
        UsernamePasswordAuthenticationToken
                authentication = new UsernamePasswordAuthenticationToken(
                userDetails, null,
                userDetails == null ?
                        List.of() : userDetails.getAuthorities()
        );

        authentication.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(request)
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }
}
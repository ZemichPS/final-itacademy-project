package by.itacademy.auditservice.endpoints.filter;

import by.itacademy.auditservice.core.dto.UserDto;
import by.itacademy.auditservice.endpoints.utils.JwtTokenHandler;
import by.itacademy.auditservice.service.api.IUserServiceAccessor;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

import static org.apache.logging.log4j.util.Strings.isEmpty;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtTokenHandler jwtHandler;
    private final IUserServiceAccessor userServiceAccessor;

    private final ConversionService conversionService;


    public JwtFilter(JwtTokenHandler jwtHandler, IUserServiceAccessor userServiceAccessor, ConversionService conversionService) {
        this.jwtHandler = jwtHandler;
        this.userServiceAccessor = userServiceAccessor;
        this.conversionService = conversionService;
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

        final String token = header.split(" ")[1].trim();

        if (!jwtHandler.validate(token)) {
            chain.doFilter(request, response);
            return;
        }

        UserDto user = userServiceAccessor.loadUserByUuid(token);

        boolean isLocked = switch (user.getStatus()) {
            case WAITING_ACTIVATION, DEACTIVATED -> true;
            case ACTIVATED -> false;
        };

        UserDetails userDetails = User.builder()
                .username(user.getUuid().toString())
                .roles(user.getRole().name())
                .password(token)
                .accountLocked(isLocked)
                .disabled(false)
                .accountExpired(false)
                .credentialsExpired(false)
                .authorities(user.getRole().name())
                .build();



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
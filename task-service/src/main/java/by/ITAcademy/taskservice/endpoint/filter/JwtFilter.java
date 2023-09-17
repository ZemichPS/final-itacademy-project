package by.ITAcademy.taskservice.endpoint.filter;


import by.ITAcademy.taskservice.core.dto.UserActor;
import by.ITAcademy.taskservice.core.dto.UserDto;
import by.ITAcademy.taskservice.sevice.api.IUserServiceAccessor;
import by.ITAcademy.taskservice.sevice.utils.JwtTokenHandler;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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

        // Get jwt token and validate
        final String token = header.split(" ")[1].trim();

        if (!jwtHandler.validate(token)) {
            chain.doFilter(request, response);
            return;
        }

        // Get user identity and set it on the spring security context
        UserDto user = userServiceAccessor.loadUserByToken(token);
        UserActor userActor = new UserActor();

        userActor.setUuid(user.getUuid());
        userActor.setMail(user.getMail());
        userActor.setFullName(user.getFullName());
        userActor.setRole(user.getRole());
        userActor.setStatus(user.getStatus());
        userActor.setToken(token);
        userActor.setAuthorities(new SimpleGrantedAuthority(user
                .getRole()
                .name()));




        UsernamePasswordAuthenticationToken
                authentication = new UsernamePasswordAuthenticationToken(
                userActor, null,
                userActor == null ?
                        List.of() : userActor.getAuthorities()
        );

        authentication.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(request)
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }
}
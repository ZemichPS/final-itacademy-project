package by.ITAcademy.taskservice.endpoint.converters;

import by.ITAcademy.taskservice.core.dto.UserDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

public class UserDtoToUserDetailsConverter implements Converter<UserDto, UserDetails> {
    @Override
    public UserDetails convert(UserDto user) {

        boolean isLocked = switch (user.getStatus()) {
            case WAITING_ACTIVATION, DEACTIVATED -> true;
            case ACTIVATED -> false;
        };

        UserDetails userDetail = User.builder()
                .username(user.getUuid().toString())
                .roles(user.getRole().name())
                .accountLocked(isLocked)
                .disabled(false)
                .accountExpired(false)
                .credentialsExpired(false)
                .authorities(user.getRole().name())
                .build();

        return userDetail;

    }
}

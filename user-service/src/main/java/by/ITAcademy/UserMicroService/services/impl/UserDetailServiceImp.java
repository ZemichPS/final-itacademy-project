package by.ITAcademy.UserMicroService.services.impl;

import by.ITAcademy.UserMicroService.core.DTO.UserDto;
import by.ITAcademy.UserMicroService.dao.entity.UserEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserDetailServiceImp implements UserDetailsService {
    private final UserService userService;

    public UserDetailServiceImp(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        final UserEntity userEntity = userService.getByUuid(UUID.fromString(username));

        boolean isLocked = switch (userEntity.getStatus()) {
            case WAITING_ACTIVATION, DEACTIVATED -> true;
            case ACTIVATED -> false;
        };

        UserDetails userDetail = User.builder()
                .username(userEntity.getUuid().toString())
                .roles(userEntity.getRole().name())
                .accountLocked(isLocked)
                .disabled(false)
                .accountExpired(false)
                .credentialsExpired(false)
                .authorities(userEntity.getRole().name())
                .password(userEntity.getPassword())
                .build();

        return userDetail;
    }
}

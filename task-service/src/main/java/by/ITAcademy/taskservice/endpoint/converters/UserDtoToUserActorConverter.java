package by.ITAcademy.taskservice.endpoint.converters;

import by.ITAcademy.taskservice.core.dto.UserActor;
import by.ITAcademy.taskservice.core.dto.UserDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class UserDtoToUserActorConverter implements Converter<UserDto, UserActor> {
    @Override
    public UserActor convert(UserDto source) {
        UserActor actor = new UserActor();
        actor.setUuid(source.getUuid());
        actor.setMail(source.getMail());
        actor.setFullName(source.getFullName());
        actor.setRole(source.getRole());
        actor.setAuthorities(new SimpleGrantedAuthority(source.getRole().toString()));
        actor.setStatus(source.getStatus());
        actor.setAccountNonExpired(false);
        actor.setAccountNonLocked(false);
        actor.setCredentialsNonExpired(false);
        actor.setEnabled(false);
        return actor;
    }
}

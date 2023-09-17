package by.ITAcademy.taskservice.sevice.impl;

import by.ITAcademy.taskservice.core.dto.UserActor;
import by.ITAcademy.taskservice.sevice.api.IUserHolder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class UserHolder implements IUserHolder {

    @Override
    public UserActor getUser() {
        return (UserActor) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}

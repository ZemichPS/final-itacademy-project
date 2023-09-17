package by.ITAcademy.taskservice.sevice.api;

import by.ITAcademy.taskservice.core.dto.UserActor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public interface IUserHolder {
    UserActor getUser();



}

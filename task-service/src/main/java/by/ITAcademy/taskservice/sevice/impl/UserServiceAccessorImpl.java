package by.ITAcademy.taskservice.sevice.impl;

import by.ITAcademy.taskservice.core.dto.UserDto;
import by.ITAcademy.taskservice.core.dto.UserRefDto;
import by.ITAcademy.taskservice.sevice.api.IUserHolder;
import by.ITAcademy.taskservice.sevice.api.IUserServiceAccessor;
import by.ITAcademy.taskservice.sevice.api.IUserServiceFeignClient;
import org.springframework.http.HttpStatus;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class UserServiceAccessorImpl implements IUserServiceAccessor {
    IUserServiceFeignClient feignClient;
    private final IUserHolder userHolder;

    public UserServiceAccessorImpl(IUserHolder userHolder) {
        this.userHolder = userHolder;
    }


    @Override
    public boolean validateStaff(List<UserRefDto> stafflist) {
        String token = userHolder.getUser().getPassword();
        HttpStatus status = (HttpStatus) feignClient.validateStaff(token, stafflist).getStatusCode();
        if (!HttpStatus.OK.equals(status)) {
            return false;
        }
        return true;
    }

    @Override
    public UserDto loadUserByToken(String token) {
        final UserDto user = feignClient.getCurrentUser(token).getBody();
        return user;
    }

    @Override
    public UserDto loadUserByUuid(UUID uuid) {
        String token = userHolder.getUser().getPassword();
        return feignClient.getUserByUuid(token, uuid).getBody();
    }
}

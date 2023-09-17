package by.itacademy.auditservice.service.impl;


import by.itacademy.auditservice.core.dto.UserDto;
import by.itacademy.auditservice.service.api.IUserHolder;
import by.itacademy.auditservice.service.api.IUserServiceAccessor;
import by.itacademy.auditservice.service.api.IUserServiceFeignClient;
import org.springframework.stereotype.Component;

@Component
public class UserServiceAccessorImpl implements IUserServiceAccessor {
    private final IUserServiceFeignClient feignClient;
    private final IUserHolder userHolder;

    public UserServiceAccessorImpl(IUserServiceFeignClient feignClient, IUserHolder userHolder) {
        this.feignClient = feignClient;
        this.userHolder = userHolder;
    }

    @Override
    public UserDto loadUserByUuid(String token) {
        return feignClient.getCurrentUser(token).getBody();
    }
}

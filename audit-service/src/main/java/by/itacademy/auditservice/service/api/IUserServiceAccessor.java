package by.itacademy.auditservice.service.api;


import by.itacademy.auditservice.core.dto.UserDto;

public interface IUserServiceAccessor {
    UserDto loadUserByUuid(String token);
}

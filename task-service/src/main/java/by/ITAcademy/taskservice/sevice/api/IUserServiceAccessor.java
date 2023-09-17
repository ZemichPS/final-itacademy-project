package by.ITAcademy.taskservice.sevice.api;

import by.ITAcademy.taskservice.core.dto.UserDto;
import by.ITAcademy.taskservice.core.dto.UserRefDto;

import java.util.List;
import java.util.UUID;

public interface IUserServiceAccessor {
    boolean validateStaff(List<UserRefDto> userRefList);

    UserDto loadUserByToken(String token);
    UserDto loadUserByUuid(UUID uuid);


}

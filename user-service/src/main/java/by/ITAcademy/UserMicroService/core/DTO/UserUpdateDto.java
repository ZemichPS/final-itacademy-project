package by.ITAcademy.UserMicroService.core.DTO;


import by.itacademy.sharedresource.core.enums.UserRole;
import by.itacademy.sharedresource.core.enums.UserStatus;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.Objects;


public record UserUpdateDto(
        String mail,
        @JsonProperty("fio")
        String fullName,
        UserRole role,
        UserStatus status,
        String password){};

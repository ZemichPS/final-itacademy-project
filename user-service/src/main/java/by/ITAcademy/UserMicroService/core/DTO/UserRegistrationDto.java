package by.ITAcademy.UserMicroService.core.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public record UserRegistrationDto(
        String mail,
        @JsonProperty("fio")
        String fullName,
        String password) {
};

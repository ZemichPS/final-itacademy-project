package by.ITAcademy.UserMicroService.services.api;

import by.ITAcademy.UserMicroService.dao.entity.UserEntity;
import by.ITAcademy.UserMicroService.core.DTO.LoginDto;
import by.ITAcademy.UserMicroService.core.DTO.UserRegistrationDto;
import by.ITAcademy.UserMicroService.core.DTO.VerificationDto;

public interface IAuthService {
    void registration(UserRegistrationDto item);
    boolean verification(VerificationDto item);
    String logIn(LoginDto item);

    UserEntity accessToAccount();





}

package by.ITAcademy.UserMicroService.services.api;

import by.ITAcademy.UserMicroService.dao.entity.UserEntity;
import by.ITAcademy.UserMicroService.core.DTO.*;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.UUID;

public interface IUserService {
    UserEntity createNewAdmin(UserCreateDto userCreateDTO);

    UserEntity getByUuid(UUID uuid);

    UserEntity getByMail(String mail);
    Page<UserEntity> getPage(int offset, int limit);
    UserEntity update(UserUpdateDto item, LocalDateTime dtUpdate, UUID uuid);

    boolean isExistsByMail(String email);

    boolean existsByMailAndActivationCode(VerificationDto item);


}

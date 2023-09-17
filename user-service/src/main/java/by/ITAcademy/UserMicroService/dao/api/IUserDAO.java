package by.ITAcademy.UserMicroService.dao.api;

import by.ITAcademy.UserMicroService.dao.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IUserDAO extends JpaRepository<UserEntity, UUID>, PagingAndSortingRepository<UserEntity, UUID>{
    /*
    @Lock(LockModeType.OPTIMISTIC_FORCE_INCREMENT)
    public Optional<Role> findByUuid(UUID uuid);
    */

    Optional<UserEntity> findByMail(String mail);
    Boolean existsByMail(String mail);

    Boolean existsByMailAndActivationCode(String mail, String activationCode);




}

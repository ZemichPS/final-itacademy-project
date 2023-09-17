package by.ITAcademy.taskservice.dao.api;

import by.ITAcademy.taskservice.dao.entity.UserRefEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IUserDao extends JpaRepository<UserRefEntity, UUID> {
}

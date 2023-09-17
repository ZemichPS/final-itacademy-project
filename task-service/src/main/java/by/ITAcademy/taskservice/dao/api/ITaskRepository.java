package by.ITAcademy.taskservice.dao.api;

import by.ITAcademy.taskservice.core.enums.TaskStatus;
import by.ITAcademy.taskservice.dao.entity.TaskEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
public interface ITaskRepository extends CrudRepository<TaskEntity, UUID>, PagingAndSortingRepository<TaskEntity, UUID> {
    Optional<TaskEntity> findByUuidAndProjectIn(
            UUID uuid, List<UUID> projects
    );
    Page<TaskEntity> findByProjectInAndStatusInAndImplementerIn(
            List<UUID> projects,
            List<TaskStatus> statuses,
            List<UUID> implementers,
            Pageable pageable
    );
    Page<TaskEntity> findByProjectIn(
            List<UUID> projects,
            Pageable pageable
    );

    Page<TaskEntity> findByImplementerIn(
            List<UUID> implementers,
            Pageable pageable
    );
}

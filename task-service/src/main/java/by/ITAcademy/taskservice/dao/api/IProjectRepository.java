package by.ITAcademy.taskservice.dao.api;

import by.ITAcademy.taskservice.core.enums.ProjectStatus;
import by.ITAcademy.taskservice.dao.entity.ProjectEntity;
import by.ITAcademy.taskservice.sevice.impl.TaskService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
public interface IProjectRepository extends CrudRepository<ProjectEntity, UUID>, PagingAndSortingRepository<ProjectEntity, UUID> {
    Page<ProjectEntity> findByStatus(ProjectStatus status, Pageable pageable);
    boolean existsByUuidAndManagerUuidOrUuidAndStaffUuid(
            UUID project, UUID manager,
            UUID project2, UUID staff);
    Optional<ProjectEntity> findByUuidAndManagerUuidOrUuidAndStaffUuid(
            UUID project, UUID manager,
            UUID project2, UUID staff);
    List<ProjectEntity> findByUuidInAndManagerUuidOrUuidInAndStaffUuid(
            List<UUID> projects, UUID manager,
            List<UUID> project2, UUID staff);
    Page<ProjectEntity> findByManagerUuidOrStaffUuid(
            UUID manager, UUID staff,
            Pageable pageable);
    List<ProjectEntity> findByManagerUuidOrStaffUuid(
            UUID manager, UUID staff);
    Page<ProjectEntity> findByStatusAndManagerUuidOrStatusOrStaffUuid(
            ProjectStatus status, UUID manager,
            ProjectStatus statusN, UUID staff,
            Pageable pageable
    );



}

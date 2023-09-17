package by.ITAcademy.taskservice.sevice.impl;

import by.ITAcademy.taskservice.core.dto.*;
import by.ITAcademy.taskservice.core.enums.Role;
import by.ITAcademy.taskservice.dao.api.IProjectRepository;
import by.ITAcademy.taskservice.dao.entity.ProjectEntity;
import by.ITAcademy.taskservice.dao.entity.UserRefEntity;
import by.ITAcademy.taskservice.sevice.api.IAuditService;
import by.ITAcademy.taskservice.sevice.api.IEventCreator;
import by.ITAcademy.taskservice.sevice.api.IProjectService;
import by.ITAcademy.taskservice.sevice.api.IUserServiceAccessor;
import by.itacademy.sharedresource.core.dto.AuditEventRecord;
import by.itacademy.sharedresource.core.enums.EssenceType;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class ProjectService implements IProjectService {
    private final IProjectRepository projectRepository;
    private final IAuditService auditService;
    private final UserHolder userHolder;
    private final ConversionService conversionService;

    private final IUserServiceAccessor userServiceAccessor;
    private final IEventCreator eventCreator;


    public ProjectService(IProjectRepository projectRepository,
                          IAuditService auditService,
                          UserHolder userHolder,
                          ConversionService conversionService,
                          IUserServiceAccessor userServiceAccessor,
                          IEventCreator eventCreator) {
        this.projectRepository = projectRepository;
        this.auditService = auditService;
        this.userHolder = userHolder;
        this.conversionService = conversionService;
        this.userServiceAccessor = userServiceAccessor;
        this.eventCreator = eventCreator;
    }

    @Transactional
    @Override
    public ProjectEntity create(ProjectCreateDto projectCreate) {
        // TODO validation
        checkOnManagerRole(projectCreate.manager().uuid());

        ProjectEntity projectEntity = conversionService.convert(projectCreate, ProjectEntity.class);
        projectEntity.setUuid(UUID.randomUUID());
        projectRepository.save(projectEntity);

        AuditEventRecord event = eventCreator.create(
                EssenceType.PROJECT,
                "project was created",
                projectEntity.getUuid().toString()
        );

        auditService.saveEvent(event);
        return projectEntity;

    }


    @Override
    public Page<ProjectEntity> getPage(ProjectPageParams params) {
        return null;
    }

    @Override
    public ProjectEntity getProject(ProjectRefDto projectRef) {

        ProjectEntity projectEntity = projectRepository
                .findById(projectRef.uuid())
                .orElseThrow(() -> new IllegalArgumentException("Project doesn't exist"));

        return projectEntity;
    }

    @Transactional
    @Override
    public void update(ProjectUpdateParams params, ProjectCreateDto projectCreate) {
        checkOnManagerRole(projectCreate.manager().uuid());
        ProjectEntity projectEntity = projectRepository
                .findById(params.uuid())
                .orElseThrow(() -> new IllegalArgumentException("Project doesn't exist"));
        if (!params.dtUpdate().equals(projectEntity.getUpdatedAt())) {
            throw new RuntimeException("Failed to update project. Try again");
        }
        updateProjectEntity(projectEntity, projectCreate);
        projectRepository.save(projectEntity);

    }

    private void updateProjectEntity(ProjectEntity updatingProjectEntity, ProjectCreateDto from) {
        updatingProjectEntity.setName(from.name());
        updatingProjectEntity.setDescription(from.description());
        updatingProjectEntity.setManager(new UserRefEntity(from.manager().uuid()));
        updatingProjectEntity.getStaff().clear();
        from.staff().stream().forEach(userRef -> {
            UserRefEntity refEntity = new UserRefEntity(userRef.uuid());
            updatingProjectEntity.getStaff().add(refEntity);
        });
        updatingProjectEntity.setStatus(from.status());

    }

    private boolean checkOnManagerRole(UUID uuid) {
        UserDto checkingUser = userServiceAccessor.loadUserByUuid(uuid);

        if (!checkingUser.getRole().equals(Role.MANAGER)) {
            throw new IllegalArgumentException("Person assigned on manager role doesn't have needed authority");
        }

        return true;
    }
}

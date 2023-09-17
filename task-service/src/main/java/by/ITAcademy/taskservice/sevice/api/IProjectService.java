package by.ITAcademy.taskservice.sevice.api;

import by.ITAcademy.taskservice.core.dto.*;
import by.ITAcademy.taskservice.dao.entity.ProjectEntity;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;

public interface IProjectService {

     ProjectEntity create(ProjectCreateDto projectCreate);

    Page<ProjectEntity> getPage(ProjectPageParams params);

    ProjectEntity getProject(ProjectRefDto projectRef);

    void update(ProjectUpdateParams params, ProjectCreateDto projectCreate);


}

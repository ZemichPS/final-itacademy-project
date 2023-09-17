package by.ITAcademy.taskservice.endpoint.converters;

import by.ITAcademy.taskservice.core.dto.ProjectCreateDto;
import by.ITAcademy.taskservice.dao.entity.ProjectEntity;
import by.ITAcademy.taskservice.dao.entity.UserRefEntity;
import org.springframework.core.convert.converter.Converter;

public class ProjectCreateDtoToProjectEntityConverter implements Converter<ProjectCreateDto, ProjectEntity> {
    @Override
    public ProjectEntity convert(ProjectCreateDto source) {
        ProjectEntity project = new ProjectEntity();
        project.setName(source.name());
        project.setDescription(source.description());
        project.setManager(new UserRefEntity(source.manager().uuid()));
        source.staff().stream().forEach(userRef -> {
            project.getStaff().add(new UserRefEntity(userRef.uuid()));
        });
        project.setStatus(source.status());
        return project;
    }
}

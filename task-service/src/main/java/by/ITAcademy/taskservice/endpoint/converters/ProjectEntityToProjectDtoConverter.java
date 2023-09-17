package by.ITAcademy.taskservice.endpoint.converters;

import by.ITAcademy.taskservice.core.dto.ProjectDto;
import by.ITAcademy.taskservice.core.dto.ProjectRefDto;
import by.ITAcademy.taskservice.core.dto.UserRefDto;
import by.ITAcademy.taskservice.dao.entity.ProjectEntity;
import org.springframework.core.convert.converter.Converter;

public class ProjectEntityToProjectDtoConverter implements Converter<ProjectEntity, ProjectDto> {
    @Override
    public ProjectDto convert(ProjectEntity source) {
        ProjectDto project = new ProjectDto();
        project.setUuid(source.getUuid());
        project.setDtCreate(source.getCreatedAt().toLocalDateTime());
        project.setDtUpdate(source.getUpdatedAt().toLocalDateTime());
        project.setName(source.getName());
        project.setDescription(source.getDescription());
        project.setManager(new UserRefDto(source.getManager().getUuid()));
        source.getStaff().stream().forEach(ref-> project
                .getStaff()
                .add(new UserRefDto(ref.getUuid())));
        project.setStatus(source.getStatus());

        return project;
    }
}

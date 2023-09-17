package by.ITAcademy.taskservice.endpoint.converters;

import by.ITAcademy.taskservice.core.dto.ProjectRefDto;
import by.ITAcademy.taskservice.core.dto.TaskDto;
import by.ITAcademy.taskservice.core.dto.UserRefDto;
import by.ITAcademy.taskservice.dao.entity.TaskEntity;
import org.springframework.core.convert.converter.Converter;

public class TaskEntityToTaskDtoConverter implements Converter<TaskEntity, TaskDto> {
    @Override
    public TaskDto convert(TaskEntity source) {
        TaskDto taskDto = new TaskDto();
        taskDto.setUuid(source.getUuid());
        taskDto.setDtCreate(source.getCreatedAt().toLocalDateTime());
        taskDto.setDtUpdate(source.getUpdatedAt().toLocalDateTime());
        taskDto.setProject(new ProjectRefDto(source.getProject()));
        taskDto.setTitle(source.getTitle());
        taskDto.setDescription(source.getDescription());
        taskDto.setStatus(source.getStatus());
        taskDto.setImplementer(new UserRefDto(source.getImplementer()));
        return taskDto;
    }
}

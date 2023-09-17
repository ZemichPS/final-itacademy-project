package by.ITAcademy.taskservice.core.dto;

import by.ITAcademy.taskservice.core.enums.ProjectStatus;

import java.util.List;

public record ProjectCreateDto(
        String name,
        String description,
        UserRefDto manager,
        List<UserRefDto> staff,
        ProjectStatus status
) {


}

package by.ITAcademy.taskservice.core.dto;

import by.ITAcademy.taskservice.core.enums.ProjectStatus;

import java.util.List;

public record TaskPageParam(Integer page,
                            Integer size,
                            List<ProjectRefDto> projectRefList,
                            List<UserRefDto> userRefList,
                            List<ProjectStatus> projectStatusList
) {
}

package by.ITAcademy.taskservice.sevice.api;

import by.ITAcademy.taskservice.core.dto.*;

public interface ITaskService {
    void assign(TaskCreateDto taskCreate);
    PageOfDto<TaskDto> getPage(TaskPageParam param);

    TaskDto getTask(TaskRefDto taskRef);

    void update(TaskUpdateParams params);

    void updateStatus(TaskStatusUpdateParams params);


}

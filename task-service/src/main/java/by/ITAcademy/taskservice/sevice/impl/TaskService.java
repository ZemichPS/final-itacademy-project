package by.ITAcademy.taskservice.sevice.impl;

import by.ITAcademy.taskservice.core.dto.*;
import by.ITAcademy.taskservice.dao.api.ITaskRepository;
import by.ITAcademy.taskservice.sevice.api.IAuditService;
import by.ITAcademy.taskservice.sevice.api.ITaskService;
import org.springframework.stereotype.Service;

@Service
public class TaskService implements ITaskService {


    private final ITaskRepository repository;
    private final IAuditService auditService;
    private final UserHolder userHolder;

    public TaskService(ITaskRepository repository, IAuditService auditService, UserHolder userHolder) {
        this.repository = repository;
        this.auditService = auditService;
        this.userHolder = userHolder;
    }

    @Override
    public void assign(TaskCreateDto taskCreate) {


    }

    @Override
    public PageOfDto<TaskDto> getPage(TaskPageParam param) {
        return null;
    }

    @Override
    public TaskDto getTask(TaskRefDto taskRef) {
        return null;
    }

    @Override
    public void update(TaskUpdateParams params) {

    }

    @Override
    public void updateStatus(TaskStatusUpdateParams params) {

    }
}

package by.ITAcademy.taskservice.endpoint.controller;

import by.ITAcademy.taskservice.core.dto.*;
import by.ITAcademy.taskservice.core.enums.ProjectStatus;
import by.ITAcademy.taskservice.core.enums.TaskStatus;
import by.ITAcademy.taskservice.sevice.api.ITaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/task")
public class TaskController {
    private final ITaskService taskService;

    public TaskController(ITaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<Void> assign(TaskCreateDto taskCreate) {
        // TODO add controller logic
        taskService.assign(taskCreate);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<PageOfDto<TaskDto>> getPage(@RequestParam(defaultValue = "0") Integer page,
                                                      @RequestParam(defaultValue = "20") Integer size,
                                                      @RequestParam(required = false) List<ProjectRefDto> projectRefList,
                                                      @RequestParam(required = false) List<UserRefDto> userRefList,
                                                      @RequestParam(required = false) List<ProjectStatus> projectStatusList
    ) {
        // TODO add controller logic
        TaskPageParam params = new TaskPageParam(page, size, projectRefList, userRefList, projectStatusList);
        PageOfDto<TaskDto> pageOfTask = taskService.getPage(params);
        return new ResponseEntity<>(pageOfTask, HttpStatus.OK);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<TaskDto> getTask(@PathVariable(required = true) UUID uuid) {
        TaskDto task = taskService.getTask(new TaskRefDto(uuid));
        // TODO add controller logic
        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @PutMapping("/{uuid}/dt_update/{dt_update}")
    public ResponseEntity<Void> update(@PathVariable(required = true) UUID uuid,
                                       @PathVariable(required = true, name = "dt_update") LocalDateTime dtUpdate,
                                       @RequestBody(required = true) TaskCreateDto taskCreate
    ) {
        // TODO add controller logic
        TaskUpdateParams params = new TaskUpdateParams(uuid, dtUpdate, taskCreate);
        taskService.update(params);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/{uuid}/dt_update/{dt_update}/status/{status}")
    public ResponseEntity<TaskDto> updateStatus(@PathVariable(required = true) UUID uuid,
                                                @PathVariable(required = true, name = "dt_update") LocalDateTime dtUpdate,
                                                @PathVariable(required = true) TaskStatus status) {

        // TODO add controller logic
        TaskStatusUpdateParams params = new TaskStatusUpdateParams(uuid, dtUpdate, status);
        taskService.updateStatus(params);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}

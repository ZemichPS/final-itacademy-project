package by.ITAcademy.taskservice.core.dto;

import by.ITAcademy.taskservice.core.enums.TaskStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class TaskDto {
    private UUID uuid;
    private LocalDateTime dtCreate;
    private LocalDateTime dtUpdate;
    private ProjectRefDto project;
    private String title;

    private String description;

    private TaskStatus status;
    private UserRefDto implementer;

    public TaskDto() {
    }

    public TaskDto(UUID uuid, LocalDateTime dtCreate, LocalDateTime dtUpdate, ProjectRefDto project, String title, String description, TaskStatus status, UserRefDto implementer) {
        this.uuid = uuid;
        this.dtCreate = dtCreate;
        this.dtUpdate = dtUpdate;
        this.project = project;
        this.title = title;
        this.description = description;
        this.status = status;
        this.implementer = implementer;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public LocalDateTime getDtCreate() {
        return dtCreate;
    }

    public void setDtCreate(LocalDateTime dtCreate) {
        this.dtCreate = dtCreate;
    }

    public LocalDateTime getDtUpdate() {
        return dtUpdate;
    }

    public void setDtUpdate(LocalDateTime dtUpdate) {
        this.dtUpdate = dtUpdate;
    }

    public ProjectRefDto getProject() {
        return project;
    }

    public void setProject(ProjectRefDto project) {
        this.project = project;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public UserRefDto getImplementer() {
        return implementer;
    }

    public void setImplementer(UserRefDto implementer) {
        this.implementer = implementer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskDto taskDto = (TaskDto) o;
        return Objects.equals(uuid, taskDto.uuid) && Objects.equals(dtCreate, taskDto.dtCreate) && Objects.equals(dtUpdate, taskDto.dtUpdate) && Objects.equals(project, taskDto.project) && Objects.equals(title, taskDto.title) && Objects.equals(description, taskDto.description) && status == taskDto.status && Objects.equals(implementer, taskDto.implementer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, dtCreate, dtUpdate, project, title, description, status, implementer);
    }
}

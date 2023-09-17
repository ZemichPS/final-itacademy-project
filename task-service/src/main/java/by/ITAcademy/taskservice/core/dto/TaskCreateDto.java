package by.ITAcademy.taskservice.core.dto;

import by.ITAcademy.taskservice.core.enums.TaskStatus;

import java.util.Objects;

public class TaskCreateDto {
    private ProjectRefDto project;
    private String title;
    private String description;
    private TaskStatus status;
    private UserRefDto implementer;

    public TaskCreateDto() {
    }

    public TaskCreateDto(ProjectRefDto project, String title, String description, TaskStatus status, UserRefDto implementer) {
        this.project = project;
        this.title = title;
        this.description = description;
        this.status = status;
        this.implementer = implementer;
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
        TaskCreateDto that = (TaskCreateDto) o;
        return Objects.equals(project, that.project) && Objects.equals(title, that.title) && Objects.equals(description, that.description) && status == that.status && Objects.equals(implementer, that.implementer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(project, title, description, status, implementer);
    }
}

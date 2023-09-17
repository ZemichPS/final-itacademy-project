package by.ITAcademy.taskservice.core.dto;

import by.ITAcademy.taskservice.core.enums.ProjectStatus;
import by.ITAcademy.taskservice.core.enums.TaskStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class ProjectDto {
    private UUID uuid;
    private LocalDateTime dtCreate;
    private LocalDateTime dtUpdate;
    private String name;
    private String description;
    private UserRefDto manager;
    private List<UserRefDto> staff;
    private ProjectStatus status;

    public ProjectDto(UUID uuid, LocalDateTime dtCreate, LocalDateTime dtUpdate, String name, String description, UserRefDto manager, List<UserRefDto> staff, ProjectStatus status) {
        this.uuid = uuid;
        this.dtCreate = dtCreate;
        this.dtUpdate = dtUpdate;
        this.name = name;
        this.description = description;
        this.manager = manager;
        this.staff = staff;
        this.status = status;
    }

    public ProjectDto() {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UserRefDto getManager() {
        return manager;
    }

    public void setManager(UserRefDto manager) {
        this.manager = manager;
    }

    public List<UserRefDto> getStaff() {
        return staff;
    }

    public void setStaff(List<UserRefDto> staff) {
        this.staff = staff;
    }

    public ProjectStatus getStatus() {
        return status;
    }

    public void setStatus(ProjectStatus status) {
        this.status = status;
    }
}

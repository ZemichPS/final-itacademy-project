package by.ITAcademy.taskservice.dao.entity;

import by.ITAcademy.taskservice.core.enums.ProjectStatus;
import jakarta.persistence.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.SourceType;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "projects",
        uniqueConstraints = @UniqueConstraint(name = "projects_name_unique", columnNames = {"name"}))
public class ProjectEntity {
    @Id
    @Column(name = "uuid")
    private UUID uuid;
    @CreationTimestamp(source = SourceType.DB)
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "dt_create", precision = 3)
    private Timestamp createdAt;
    @Version
    @UpdateTimestamp(source = SourceType.DB)
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "dt_update")
    private Timestamp updatedAt;
    @Column(name = "name", unique = true)
    private String name;
    @Column(name = "description")
    private String description;
    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "manager", foreignKey = @ForeignKey(name = "project_users_foreign_key"))
    private UserRefEntity manager;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    @JoinTable(
            name = "projects_staff",
            joinColumns = @JoinColumn(name = "project_uuid"),
            foreignKey = @ForeignKey(name = "projects_staff_project_uuid_fkey"),
            inverseJoinColumns = @JoinColumn(name = "staff_uuid"),
            inverseForeignKey = @ForeignKey(name = "users_staff_uuid_fkey")
    )
    private List<UserRefEntity> staff;
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ProjectStatus status;

    public ProjectEntity() {
    }

    public ProjectEntity(UUID uuid,
                         Timestamp createdAt,
                         Timestamp updatedAt,
                         String name,
                         String description,
                         UserRefEntity manager,
                         List<UserRefEntity> staff,
                         ProjectStatus status) {
        this.uuid = uuid;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.name = name;
        this.description = description;
        this.manager = manager;
        this.staff = staff;
        this.status = status;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
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

    public UserRefEntity getManager() {
        return manager;
    }

    public void setManager(UserRefEntity manager) {
        this.manager = manager;
    }

    public List<UserRefEntity> getStaff() {
        return staff;
    }

    public void setStaff(List<UserRefEntity> staff) {
        this.staff = staff;
    }

    public ProjectStatus getStatus() {
        return status;
    }

    public void setStatus(ProjectStatus status) {
        this.status = status;
    }
}

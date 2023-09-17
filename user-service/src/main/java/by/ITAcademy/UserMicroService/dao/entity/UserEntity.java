package by.ITAcademy.UserMicroService.dao.entity;



import by.itacademy.sharedresource.core.enums.UserRole;
import by.itacademy.sharedresource.core.enums.UserStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.validator.constraints.NotBlank;


import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;


@Entity
@Table(name = "users", schema = "app")
public class UserEntity {

    @Id
    @UuidGenerator
    @Column(name = "user_uuid")
    private UUID uuid;

    @CreationTimestamp(source = SourceType.DB)
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "dt_create")
    private Timestamp dtCreate;

    @Version
    @UpdateTimestamp(source = SourceType.DB)
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "dt_update")
    private Timestamp dtUpdate;

    @Column(name = "mail")
    @NotEmpty(message = "email is required")
    @Email(message = "Email is incorrect")
    private String mail;

    @Column(name = "user_name")
    @NotBlank(message = "full user name is required")
    private String fullName;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_role")
    private UserRole role;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_status")
    private UserStatus status;

    @Column(name = "password")
    @Size(message = "Password must contains as minimum 6 characters", min = 6)
    private String password;

    @Column(name = "activation_code")
    private String activationCode;

    public UserEntity(UUID uuid, String mail, String fullName, UserRole role, UserStatus status, String activationCode) {
        this.uuid = uuid;
        this.mail = mail;
        this.fullName = fullName;
        this.role = role;
        this.status = status;
        this.activationCode = activationCode;
    }

    public UserEntity() {
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public Timestamp getDtCreate() {
        return dtCreate;
    }

    public void setDtCreate(Timestamp createdAt) {
        this.dtCreate = createdAt;
    }

    public Timestamp getDtUpdate() {
        return dtUpdate;
    }

    public void setDtUpdate(Timestamp updatedAt) {
        this.dtUpdate = updatedAt;
    }

    public String getMail() {
        return mail;
    }

    public String getUsername(){
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public UserStatus getStatus() {
        return status;
    }

    public String getActivationCode() {
        return activationCode;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity entity = (UserEntity) o;
        return Objects.equals(uuid, entity.uuid) && Objects.equals(dtCreate, entity.dtCreate) && Objects.equals(dtUpdate, entity.dtUpdate) && Objects.equals(mail, entity.mail) && Objects.equals(fullName, entity.fullName) && role == entity.role && status == entity.status && Objects.equals(password, entity.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, dtCreate, dtUpdate, mail, fullName, role, status, password);
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "uuid=" + uuid +
                ", createdAt=" + dtCreate +
                ", updatedAt=" + dtUpdate +
                ", mail='" + mail + '\'' +
                ", fullName='" + fullName + '\'' +
                ", role=" + role +
                ", status=" + status +
                ", password='" + password + '\'' +
                '}';
    }
}

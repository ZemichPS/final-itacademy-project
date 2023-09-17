package by.itacademy.auditservice.dao.entity;

import by.itacademy.sharedresource.core.enums.UserRole;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "user")
public class UserEntity {
    @Id
    private UUID uuid;
    private String mail;
    private String fio;
    @Enumerated(EnumType.STRING)
    private UserRole role;

    public UserEntity() {
    }

    public UserEntity(UUID uuid, String mail, String fio, UserRole role) {
        this.uuid = uuid;
        this.mail = mail;
        this.fio = fio;
        this.role = role;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
}

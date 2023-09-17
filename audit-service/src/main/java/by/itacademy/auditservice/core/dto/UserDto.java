package by.itacademy.auditservice.core.dto;


import by.itacademy.sharedresource.core.enums.UserRole;
import by.itacademy.sharedresource.core.enums.UserStatus;

import javax.management.relation.Role;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;


public class UserDto {

    private UUID uuid;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String mail;
    private String fullName;
    private UserRole role;
    private UserStatus status;

    public UserDto(UUID uuid, LocalDateTime createdAt, LocalDateTime updatedAt, String mail, String fullName, UserRole role, UserStatus status) {
        this.uuid = uuid;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.mail = mail;
        this.fullName = fullName;
        this.role = role;
        this.status = status;
    }

    public UserDto() {
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getMail() {
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

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto userDto = (UserDto) o;
        return Objects.equals(uuid, userDto.uuid) && Objects.equals(createdAt, userDto.createdAt) && Objects.equals(updatedAt, userDto.updatedAt) && Objects.equals(mail, userDto.mail) && Objects.equals(fullName, userDto.fullName) && role == userDto.role && status == userDto.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, createdAt, updatedAt, mail, fullName, role, status);
    }
}

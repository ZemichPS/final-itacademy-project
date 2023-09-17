package by.ITAcademy.taskservice.core.dto;

import by.ITAcademy.taskservice.core.enums.TaskStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record TaskStatusUpdateParams(UUID uuid, LocalDateTime dtUpdate, TaskStatus status) {
}

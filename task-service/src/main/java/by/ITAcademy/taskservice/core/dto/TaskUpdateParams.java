package by.ITAcademy.taskservice.core.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record TaskUpdateParams(UUID uuid, LocalDateTime dateTime, TaskCreateDto taskCreate) {
}

package by.ITAcademy.taskservice.core.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record ProjectUpdateParams(UUID uuid, LocalDateTime dtUpdate) {
}

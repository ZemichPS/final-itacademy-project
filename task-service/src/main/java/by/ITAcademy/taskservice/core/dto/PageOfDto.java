package by.ITAcademy.taskservice.core.dto;

import java.util.List;

public record PageOfDto<T>(
        Integer number,
        Integer size,
        Integer totalPages,
        Long totalElements,
        boolean first,
        Integer numberOfElements,
        boolean last,
        List<T> content) {
}

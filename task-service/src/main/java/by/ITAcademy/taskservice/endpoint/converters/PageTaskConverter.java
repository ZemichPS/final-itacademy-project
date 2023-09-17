package by.ITAcademy.taskservice.endpoint.converters;

import by.ITAcademy.taskservice.core.dto.PageOfDto;
import by.ITAcademy.taskservice.core.dto.ProjectDto;
import by.ITAcademy.taskservice.core.dto.UserRefDto;
import by.ITAcademy.taskservice.dao.entity.ProjectEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;

import java.util.stream.Collectors;


public class PageTaskConverter implements Converter<Page<ProjectEntity>, PageOfDto<ProjectDto>>  {


    @Override
    public PageOfDto<ProjectDto> convert(Page<ProjectEntity> source) {


        PageOfDto<ProjectDto> page = new PageOfDto<>(
                source.getNumber(),
                source.getSize(),
                source.getTotalPages(),
                source.getTotalElements(),
                source.isFirst(),
                source.getNumberOfElements(),
                source.isLast(),
                source.getContent().stream().map(e -> new ProjectDto(
                        e.getUuid(),
                        e.getCreatedAt().toLocalDateTime(),
                        e.getUpdatedAt().toLocalDateTime(),
                        e.getName(),
                        e.getDescription(),
                        new UserRefDto(e.getManager().getUuid()),
                        e.getStaff()
                                .stream()
                                .map(u-> new UserRefDto(u.getUuid()))
                                .collect(Collectors.toList()),
                        e.getStatus())).toList()
        );
        return page;
    }
}

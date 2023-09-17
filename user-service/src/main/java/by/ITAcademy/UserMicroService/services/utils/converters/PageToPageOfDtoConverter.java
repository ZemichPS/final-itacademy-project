package by.ITAcademy.UserMicroService.services.utils.converters;

import by.ITAcademy.UserMicroService.dao.entity.UserEntity;
import by.ITAcademy.UserMicroService.core.DTO.PageOfDto;
import by.ITAcademy.UserMicroService.core.DTO.UserDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;

import java.util.ArrayList;

public class PageToPageOfDtoConverter implements Converter<Page<UserEntity>, PageOfDto<UserDto>> {
    @Override
    public PageOfDto<UserDto> convert(Page<UserEntity> source) {
        PageOfDto<UserDto> page = new PageOfDto<>();
        page.setNumber(source.getNumber());
        page.setSize(source.getSize());
        page.setTotalPages(source.getTotalPages());
        page.setTotalElements(source.getTotalElements());
        page.setFirst(source.isFirst());
        page.setNumberOfElements(source.getNumberOfElements());
        page.setLast(source.isLast());
        page.setContent(new ArrayList<>());

        source.getContent().stream().forEach(entity -> page.getContent().add(
                new UserDto(
                        entity.getUuid(),
                        entity.getDtCreate().toLocalDateTime(),
                        entity.getDtUpdate().toLocalDateTime(),
                        entity.getMail(),
                        entity.getFullName(),
                        entity.getRole(),
                        entity.getStatus()
                )
        ));

        return page;
    }
}

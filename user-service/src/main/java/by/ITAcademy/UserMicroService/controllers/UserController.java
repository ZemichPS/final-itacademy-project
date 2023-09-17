package by.ITAcademy.UserMicroService.controllers;

import by.ITAcademy.UserMicroService.core.DTO.PageOfDto;
import by.ITAcademy.UserMicroService.core.DTO.UserCreateDto;
import by.ITAcademy.UserMicroService.core.DTO.UserDto;
import by.ITAcademy.UserMicroService.core.DTO.UserUpdateDto;
import by.ITAcademy.UserMicroService.services.api.IUserService;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {
    private final IUserService userService;
    private final ConversionService conversionService;


    public UserController(IUserService userService, ConversionService conversionService) {
        this.userService = userService;
        this.conversionService = conversionService;
    }

    // Добавление нового пользователя
    @PostMapping
    private ResponseEntity<UserDto> createNewAdmin(@RequestBody UserCreateDto userCreateDTO) {
        UserDto createdUserDto = conversionService.convert(userService.createNewAdmin(userCreateDTO), UserDto.class);
        return new ResponseEntity<>(createdUserDto, HttpStatus.CREATED);
    }

    // Получить страницу пользователей
    @GetMapping
    private ResponseEntity<PageOfDto<UserDto>> getPages(@RequestParam(value = "offset", defaultValue = "0") Integer offset,
                                                        @RequestParam(value = "limit", defaultValue = "20") Integer limit
    ) {
        PageOfDto<UserDto> page = conversionService.convert(userService.getPage(offset, limit), PageOfDto.class);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    // Получить информацию о пользователе
    @GetMapping("/{uuid}")
    private ResponseEntity<UserDto> getByUuid(@PathVariable UUID uuid) {
        UserDto userDto = conversionService.convert(userService.getByUuid(uuid), UserDto.class);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }


    // Редактировать информацию о пользователе
    @PutMapping("/{uuid}/dt_update/{dt_update}")
    private ResponseEntity<UserDto> updateByUuid(@PathVariable(name = "uuid") UUID uuid,
                                                 @PathVariable(name = "dt_update") LocalDateTime dtUpdate,
                                                 @RequestBody UserUpdateDto userUpdateDto) {

        UserDto userDto = conversionService.convert(userService.update(userUpdateDto, dtUpdate, uuid), UserDto.class);
        return new ResponseEntity<>(userDto, HttpStatus.OK);

    }


}

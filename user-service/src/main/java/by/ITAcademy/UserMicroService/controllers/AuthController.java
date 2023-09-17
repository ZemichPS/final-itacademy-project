package by.ITAcademy.UserMicroService.controllers;

import by.ITAcademy.UserMicroService.core.DTO.UserDto;
import by.ITAcademy.UserMicroService.core.DTO.LoginDto;
import by.ITAcademy.UserMicroService.core.DTO.UserRegistrationDto;
import by.ITAcademy.UserMicroService.core.DTO.VerificationDto;
import by.ITAcademy.UserMicroService.services.api.IAuthService;
import by.ITAcademy.UserMicroService.services.impl.AuthService;

import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class AuthController {

    private final IAuthService authService;
    private final ConversionService conversionService;


    public AuthController(AuthService authService, ConversionService conversionService) {
        this.authService = authService;
        this.conversionService = conversionService;

    }

    @PostMapping("/registration")
    ResponseEntity<Void> registration(@RequestBody UserRegistrationDto registrationDto) {
        authService.registration(registrationDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/verification")
    ResponseEntity<Void> verification(@RequestParam(required = true) String code,
                                      @RequestParam(required = true) String mail){


        VerificationDto verificationDto = new VerificationDto(code, mail);

        authService.verification(verificationDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/login")
    ResponseEntity<String> login(@RequestBody LoginDto loginDto) {

        String jwtToken = authService.logIn(loginDto);

        return new ResponseEntity<>(jwtToken, HttpStatus.OK);
    }

    @GetMapping("/me")
    ResponseEntity<UserDto> accessToAccount(){
        UserDto userDto = conversionService.convert(authService.accessToAccount(), UserDto.class);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

}

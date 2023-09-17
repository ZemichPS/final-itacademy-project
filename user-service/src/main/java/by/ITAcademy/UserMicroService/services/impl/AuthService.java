package by.ITAcademy.UserMicroService.services.impl;

import by.ITAcademy.UserMicroService.core.exception.UserNotFoundException;
import by.ITAcademy.UserMicroService.dao.api.IUserDAO;
import by.ITAcademy.UserMicroService.dao.entity.UserEntity;
import by.ITAcademy.UserMicroService.core.DTO.*;

import by.ITAcademy.UserMicroService.core.exception.IncorrectPasswordException;
import by.ITAcademy.UserMicroService.core.exception.LoginException;
import by.ITAcademy.UserMicroService.core.exception.VerificationException;
import by.ITAcademy.UserMicroService.services.api.IAuditService;
import by.ITAcademy.UserMicroService.services.api.IAuthService;
import by.ITAcademy.UserMicroService.services.api.IEventCreator;
import by.ITAcademy.UserMicroService.services.api.INotifier;
import by.ITAcademy.UserMicroService.services.utils.AccountStatusUserDetailsChecker;
import by.ITAcademy.UserMicroService.services.utils.JwtTokenHandler;

import by.itacademy.sharedresource.core.dto.AuditEventRecord;
import by.itacademy.sharedresource.core.enums.EssenceType;
import by.itacademy.sharedresource.core.enums.UserRole;
import by.itacademy.sharedresource.core.enums.UserStatus;
import com.google.common.base.VerifyException;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.random.RandomGenerator;
import java.util.random.RandomGeneratorFactory;

@Service
public class AuthService implements IAuthService {

    public final UserService userService;
    public final ConversionService conversionService;
    private final IUserDAO userRepo;
    private final IAuditService auditService;
    private final INotifier notifier;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenHandler jwtTokenHandler;
    private final UserHolder userHolder;
    private final AccountStatusUserDetailsChecker accountChecker;
    private final IEventCreator eventCreator;


    public AuthService(UserService userService,
                       ConversionService conversionService,
                       IUserDAO userRepo,
                       IAuditService auditService,
                       INotifier notifier, UserDetailsService userDetailsService, PasswordEncoder passwordEncoder, JwtTokenHandler jwtTokenHandler, UserHolder userHolder, AccountStatusUserDetailsChecker accountChecker, IEventCreator eventCreator) {
        this.userService = userService;
        this.conversionService = conversionService;
        this.userRepo = userRepo;
        this.auditService = auditService;
        this.notifier = notifier;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenHandler = jwtTokenHandler;
        this.userHolder = userHolder;
        this.accountChecker = accountChecker;
        this.eventCreator = eventCreator;
    }


    @Override
    @Transactional
    @Modifying(flushAutomatically = true)
    public void registration(UserRegistrationDto item) {
        if (userService.isExistsByMail(item.mail())) {
            throw new IllegalArgumentException("Account with such email already exists. You should use other email address");
        }

        UserEntity registrationEntity = conversionService.convert(item, UserEntity.class);
        String activationCode = userService.generateVerificationCode();

        registrationEntity.setUuid(UUID.randomUUID());
        registrationEntity.setRole(UserRole.USER.USER);
        registrationEntity.setStatus(UserStatus.WAITING_ACTIVATION);
        registrationEntity.setActivationCode(activationCode);
        registrationEntity.setPassword(passwordEncoder.encode(item.password()));
        userRepo.save(registrationEntity);

        MailInfo mailInfo = new MailInfo();
        mailInfo.setMail(item.mail());
        mailInfo.setActivationCode(activationCode);
        mailInfo.setRole(UserRole.USER.USER);
        mailInfo.setStatus(UserStatus.WAITING_ACTIVATION);
        mailInfo.setFullName(item.fullName());
        mailInfo.setDestination("verification");
        notifier.send(mailInfo);
    }

    @Override
    @Transactional
    public boolean verification(VerificationDto item) {
        if (!userService.existsByMailAndActivationCode(item)) {
            throw new VerificationException("Failed to verify account. Check your user's data, verification code  and try again.");
        }
        UserEntity verifyUserEntity = userRepo
                .findByMail(item.mail())
                .orElseThrow(() -> new VerifyException("Failed to verify account. Account with such email doesn't exist. Please be sure that in user data was inputted correctly."));

        if (verifyUserEntity.getStatus().equals(UserStatus.ACTIVATED)) {
            throw new VerificationException("Redundant action. Account is verified  by system already");
        }

        try {
            verifyUserEntity.setStatus(UserStatus.ACTIVATED);
            userRepo.save(verifyUserEntity);

            MailInfo mailInfo = new MailInfo();
            mailInfo.setMail(item.mail());
            mailInfo.setRole(verifyUserEntity.getRole());
            mailInfo.setStatus(verifyUserEntity.getStatus());
            mailInfo.setFullName(verifyUserEntity.getFullName());
            mailInfo.setDestination("congratulation");
            notifier.send(mailInfo);

            return true;
        } catch (Exception ex) {
            throw new VerificationException("Failed to verify account. Internal server error", ex.getCause());
        }

    }

    @Override
    public String logIn(LoginDto loginDto) {
        UserEntity userEntity = userService.getByMail(loginDto.mail());
        UserDto userDto = conversionService.convert(userEntity, UserDto.class);
        if(userEntity == null){
            throw new UserNotFoundException("User with such email doesn't exist");
        }

        if (!passwordEncoder.matches(loginDto.password(), userEntity.getPassword())) {
            throw new IncorrectPasswordException("Inputted password is incorrect. Try again.");
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(userEntity.getUuid().toString());
        accountChecker.check(userDetails);

        String token;
        try {
            token = jwtTokenHandler.generateAccessToken(userDetails);
            AuditEventRecord event = eventCreator.create(
                    userDto,
                    EssenceType.USER,
                    "user was logged in",
                    userEntity.getUuid().toString()
            );
            auditService.saveEvent(event);

            return token;
        } catch (Exception ex) {
            throw new LoginException(ex.getMessage());
            //throw new LoginException("Failed to login.", ex.getCause());
        }
    }

    @Override
    public UserEntity accessToAccount() {
        return userService.getByUuid(UUID.fromString(userHolder
                .getUser()
                .getUsername())
        );
    }






}

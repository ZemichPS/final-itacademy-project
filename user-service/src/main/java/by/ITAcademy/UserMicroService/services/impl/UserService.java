package by.ITAcademy.UserMicroService.services.impl;

import by.ITAcademy.UserMicroService.dao.api.IUserDAO;
import by.ITAcademy.UserMicroService.dao.entity.UserEntity;
import by.ITAcademy.UserMicroService.core.DTO.*;

import by.ITAcademy.UserMicroService.core.exception.InvalidDtoException;
import by.ITAcademy.UserMicroService.core.exception.UserNotFoundException;
import by.ITAcademy.UserMicroService.services.api.IAuditService;
import by.ITAcademy.UserMicroService.services.api.IEventCreator;
import by.ITAcademy.UserMicroService.services.api.INotifier;
import by.ITAcademy.UserMicroService.services.api.IUserService;


import by.itacademy.sharedresource.core.dto.AuditEventRecord;
import by.itacademy.sharedresource.core.enums.EssenceType;
import by.itacademy.sharedresource.core.enums.UserRole;
import by.itacademy.sharedresource.core.enums.UserStatus;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.DataBinder;
import org.springframework.validation.Validator;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import java.util.random.RandomGenerator;
import java.util.random.RandomGeneratorFactory;

@Service
public class UserService implements IUserService {

    private final IUserDAO userRepo;
    private final Validator userCreateDtoValidator;
    private final Validator userDtoValidator;
    private final ConversionService conversionService;
    private final KafkaProducerService producerService;
    private final IAuditService auditService;
    private final PasswordEncoder passwordEncoder;
    private final UserHolder userHolder;
    private final IEventCreator eventCreator;
    private final INotifier notifier;
    private final KafkaProducerService kafkaProducerService;

    public UserService(IUserDAO userRepo, Validator userCreateDtoValidator, Validator userDtoValidator, ConversionService conversionService, KafkaProducerService producerService, IAuditService auditService, PasswordEncoder passwordEncoder, UserHolder userHolder, IEventCreator eventCreator, INotifier notifier, KafkaProducerService kafkaProducerService) {
        this.userRepo = userRepo;
        this.userCreateDtoValidator = userCreateDtoValidator;
        this.userDtoValidator = userDtoValidator;
        this.conversionService = conversionService;
        this.producerService = producerService;
        this.auditService = auditService;
        this.passwordEncoder = passwordEncoder;
        this.userHolder = userHolder;
        this.eventCreator = eventCreator;
        this.notifier = notifier;
        this.kafkaProducerService = kafkaProducerService;
    }


    @Override
    @Transactional

    public UserEntity createNewAdmin(UserCreateDto userCreateDTO) {

        validation(userCreateDTO, userCreateDtoValidator);

        UserEntity entity = conversionService.convert(userCreateDTO, UserEntity.class);
        entity.setPassword(passwordEncoder.encode(userCreateDTO.password()));
        String code = generateVerificationCode();
        entity.setActivationCode(code);
        UserEntity savedUserEntity = userRepo.saveAndFlush(entity);

        MailInfo mailInfo = new MailInfo();
        mailInfo.setMail(userCreateDTO.mail());
        mailInfo.setActivationCode(code);
        mailInfo.setRole(UserRole.ADMIN);
        mailInfo.setStatus(UserStatus.WAITING_ACTIVATION);
        mailInfo.setFullName(userCreateDTO.fullName());
        mailInfo.setDestination("verification");
        notifier.send(mailInfo);


        AuditEventRecord event = eventCreator.create(
                EssenceType.USER,
                "admin was created",
                entity.getUuid().toString()
        );
        auditService.saveEvent(event);
        return savedUserEntity;
    }

    @Override
    @Transactional(readOnly = true)
    public UserEntity getByUuid(UUID uuid) {
        Optional<UserEntity> entityOptional = userRepo.findById(uuid);
        return entityOptional.orElseThrow(() -> new UserNotFoundException("User with that uuid is nowhere to be found."));
    }

    @Override
    @Transactional(readOnly = true)
    public UserEntity getByMail(String mail) {
        Optional<UserEntity> entityOptional = userRepo.findByMail(mail);
        return entityOptional.orElseThrow(() -> new UserNotFoundException("User with that mail is nowhere to be found."));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserEntity> getPage(int offset, int limit) {
        Page<UserEntity> page = userRepo.findAll(PageRequest.of(offset, limit));
        return page;
    }

    @Override
    @Transactional
    public UserEntity update(UserUpdateDto item, LocalDateTime dtUpdate, UUID uuid) {

        UserEntity entity = userRepo.findById(uuid)
                .orElseThrow(() -> new UserNotFoundException("User with such uuid was not found"));


        entity.setMail(item.mail());
        entity.setFullName(item.fullName());
        entity.setRole(item.role());
        entity.setStatus(item.status());
        entity.setPassword(passwordEncoder.encode(item.password()));
        userRepo.saveAndFlush(entity);


//        AuditEventRecord event = eventCreator.create(
//                EssenceType.USER,
//                "user was updated",
//                entity.getUuid().toString()
//        );

        AuditEventRecord auditEventRecord = new AuditEventRecord(
                LocalDateTime.now(),
                "user was updated",
                EssenceType.USER,
                entity.getUuid().toString(),
                UUID.randomUUID(),
                "somemail@mail.ru",
                "full name",
                UserRole.ADMIN
        );

        kafkaProducerService.saveEvent(auditEventRecord);
        //auditService.saveEvent(auditEventRecord);
        return entity;
    }

    private void validation(Object target, Validator validator) {
        DataBinder dataBinder = new DataBinder(target);
        dataBinder.addValidators(validator);
        dataBinder.validate();
        if (dataBinder.getBindingResult().hasErrors()) {
            throw new InvalidDtoException("Invalid dto", dataBinder.getBindingResult().getAllErrors());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isExistsByMail(String email) {
        return userRepo.existsByMail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByMailAndActivationCode(VerificationDto item) {
        return userRepo.existsByMailAndActivationCode(item.mail(), item.code());
    }

    protected String generateVerificationCode() {

        RandomGenerator randomGenerator = RandomGeneratorFactory
                .of("L128X1024MixRandom")
                .create();
        return String.valueOf(randomGenerator.nextInt(1001, 9999));
    }

}

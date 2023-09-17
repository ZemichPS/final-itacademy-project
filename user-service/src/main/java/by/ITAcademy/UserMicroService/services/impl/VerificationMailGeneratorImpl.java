package by.ITAcademy.UserMicroService.services.impl;

import by.ITAcademy.UserMicroService.core.DTO.MailInfo;
import by.ITAcademy.UserMicroService.services.api.IMailGenerator;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component("verification")
public class VerificationMailGeneratorImpl implements IMailGenerator {
    @Override
    public MailInfo generate(MailInfo mailInfo) {
        Map<String, Object> templateModel = new HashMap<>();
        templateModel.put("fullName", mailInfo.getFullName());
        templateModel.put("role", mailInfo.getRole().toString());
        templateModel.put("status", mailInfo.getStatus().toString());
        templateModel.put("activationCode", mailInfo.getActivationCode());
        templateModel.put("email", mailInfo.getMail());
        templateModel.put("url", "http://localhost:8081/users/verification");

        mailInfo.setTemplateModel(templateModel);

        mailInfo.setSubject("Welcome to Task Manager!");
        mailInfo.setTemplateName("registration-template.html");

        return mailInfo;
    }
}

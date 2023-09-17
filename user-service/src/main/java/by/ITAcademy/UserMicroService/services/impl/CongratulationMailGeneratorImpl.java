package by.ITAcademy.UserMicroService.services.impl;

import by.ITAcademy.UserMicroService.core.DTO.MailInfo;
import by.ITAcademy.UserMicroService.services.api.IMailGenerator;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component("congratulation")
public class CongratulationMailGeneratorImpl implements IMailGenerator {
    @Override
    public MailInfo generate(MailInfo mailInfo) {
        Map<String, Object> templateModel = new HashMap<>();
        templateModel.put("email", mailInfo.getMail());
        templateModel.put("role", mailInfo.getRole().toString());
        templateModel.put("status", mailInfo.getStatus().toString());
        templateModel.put("fullName", mailInfo.getFullName());

        mailInfo.setTemplateModel(templateModel);

        mailInfo.setSubject("Congratulations!");
        mailInfo.setTemplateName("congratulations-on-registration.html");

        return mailInfo;
    }
}

package by.ITAcademy.UserMicroService.services.impl;

import by.ITAcademy.UserMicroService.config.property.SmtpProperties;
import by.ITAcademy.UserMicroService.core.DTO.MailInfo;
import by.ITAcademy.UserMicroService.core.exception.FailedInActivatingException;
import by.ITAcademy.UserMicroService.services.api.INotifier;
import by.ITAcademy.UserMicroService.services.api.IMailGenerator;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.Map;

@Service
public class MailSender implements INotifier {
    private final Map<String, IMailGenerator> mailGeneratorMap;

    private final JavaMailSender javaMailSender;

    private final SpringTemplateEngine thymeleafTemplateEngine;
    private final SmtpProperties smtpProperties;

    public MailSender(Map<String, IMailGenerator> mailGeneratorMap,
                      JavaMailSender javaMailSender,
                      SpringTemplateEngine thymeleafTemplateEngine, SmtpProperties smtpProperties) {
        this.mailGeneratorMap = mailGeneratorMap;
        this.javaMailSender = javaMailSender;
        this.thymeleafTemplateEngine = thymeleafTemplateEngine;
        this.smtpProperties = smtpProperties;
    }

    public void send(MailInfo mailInfo) {
        String code = mailInfo.getDestination();
        IMailGenerator mailGenerator = mailGeneratorMap.get(code);
        Context thymeleafContext = new Context();
        MimeMessage message = javaMailSender.createMimeMessage();

        if (mailGenerator == null) {
            throw new UnsupportedOperationException(code + " is not supported yet.");
        }

        mailInfo = mailGenerator.generate(mailInfo);

        thymeleafContext.setVariables(mailInfo.getTemplateModel());

        String htmlBody = thymeleafTemplateEngine.process(mailInfo.getTemplateName(), thymeleafContext);

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom(smtpProperties.getUsername());
            helper.setTo(mailInfo.getMail());
            helper.setSubject(mailInfo.getSubject());
            helper.setText(htmlBody, true);
            javaMailSender.send(message);
        } catch (MessagingException e) {
            throw new FailedInActivatingException("Failed in sending email", e.getCause());
        }


    }
}

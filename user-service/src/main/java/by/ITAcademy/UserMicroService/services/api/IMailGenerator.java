package by.ITAcademy.UserMicroService.services.api;

import by.ITAcademy.UserMicroService.core.DTO.MailInfo;

public interface IMailGenerator {
    MailInfo generate(MailInfo mailInfo);

}

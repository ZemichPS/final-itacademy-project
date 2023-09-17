package by.ITAcademy.UserMicroService.services.api;

import by.ITAcademy.UserMicroService.core.DTO.MailInfo;

public interface INotifier {
    void send(MailInfo mailInfo);
}

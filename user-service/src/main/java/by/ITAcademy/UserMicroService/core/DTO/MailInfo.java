package by.ITAcademy.UserMicroService.core.DTO;


import by.itacademy.sharedresource.core.enums.UserRole;
import by.itacademy.sharedresource.core.enums.UserStatus;

import java.util.Map;

public class MailInfo {

    private String mail;
    private String fullName;
    private UserRole role;
    private UserStatus status;
    private String activationCode;
    private String destination;
    private String subject;
    private String templateName;
    private Map<String, Object> templateModel;

    public MailInfo(String mail, String fullName, UserRole role, UserStatus status, String activationCode, String destination, String subject, String templateName, Map<String, Object> templateModel) {
        this.mail = mail;
        this.fullName = fullName;
        this.role = role;
        this.status = status;
        this.activationCode = activationCode;
        this.destination = destination;
        this.subject = subject;
        this.templateName = templateName;
        this.templateModel = templateModel;
    }

    public MailInfo() {
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public String getActivationCode() {
        return activationCode;
    }

    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public Map<String, Object> getTemplateModel() {
        return templateModel;
    }

    public void setTemplateModel(Map<String, Object> templateModel) {
        this.templateModel = templateModel;
    }

}

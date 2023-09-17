package by.itacademy.auditservice.service.api;

public interface IEventProcessor<T> {
    void process(T record);
}

package by.ITAcademy.UserMicroService.services.utils.JsonComponents;


import by.itacademy.sharedresource.core.dto.ErrorResponse;
import by.itacademy.sharedresource.core.dto.StructuredErrorResponse;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.boot.jackson.JsonObjectSerializer;

import java.io.IOException;

@JsonComponent
public class ErrorResponseJsonComponent {
    public static class StructuredErrorResponseSerializer extends JsonObjectSerializer<StructuredErrorResponse> {
        @Override
        protected void serializeObject(StructuredErrorResponse response, JsonGenerator jgen, SerializerProvider provider) throws IOException {

            jgen.writeStringField("logref", response.getErrorType().name().toLowerCase());
            jgen.writeArrayFieldStart("errors");
            response.getErrorMap().entrySet().stream().forEach(error -> {
                try {
                    jgen.writeStartObject();
                    jgen.writeStringField("field", error.getKey());
                    jgen.writeStringField("message", error.getValue());
                    jgen.writeEndObject();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            jgen.writeEndArray();
        }
    }


    public static class ErrorResponseSerializer extends JsonObjectSerializer<ErrorResponse>{
        @Override
        protected void serializeObject(ErrorResponse value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
            jgen.writeStringField("logref", value.getErrorType().name().toLowerCase());
            jgen.writeStringField("message", value.getMessage());
        }
    }

}

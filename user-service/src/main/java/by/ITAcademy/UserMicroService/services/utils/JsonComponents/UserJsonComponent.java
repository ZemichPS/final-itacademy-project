package by.ITAcademy.UserMicroService.services.utils.JsonComponents;


import by.ITAcademy.UserMicroService.core.DTO.*;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.boot.jackson.JsonObjectDeserializer;
import org.springframework.boot.jackson.JsonObjectSerializer;

import java.io.IOException;
import java.sql.Timestamp;

@JsonComponent
public class UserJsonComponent {
    public static class UserDtoSerializer extends JsonObjectSerializer<UserDto> {
        @Override
        public void serializeObject(UserDto userDTO, JsonGenerator jgen, SerializerProvider provider) throws IOException {
            jgen.writeStringField("uuid", userDTO.getUuid().toString());
            jgen.writeNumberField("dt_create", Timestamp.valueOf(userDTO.getCreatedAt()).getTime());
            jgen.writeNumberField("dt_update", Timestamp.valueOf(userDTO.getUpdatedAt()).getTime());
            jgen.writeStringField("mail", userDTO.getMail());
            jgen.writeStringField("fio", userDTO.getFullName());
            jgen.writeStringField("role", userDTO.getRole().name());
            jgen.writeStringField("status", userDTO.getStatus().name());
        }
    }
}


//
//    public static class UserCreateDtoDeserializer extends JsonObjectDeserializer<UserCreateDto> {
//
//        @Override
//        protected UserCreateDto deserializeObject(JsonParser jsonParser, DeserializationContext context, ObjectCodec codec, JsonNode tree) throws IOException {
//            UserCreateDto createDTO = new UserCreateDto();
//            createDTO.setMail(tree.get("mail").textValue());
//            createDTO.setFullName(tree.get("fio").textValue());
//            createDTO.setRole(Role.valueOf(tree.get("role").textValue()));
//            createDTO.setStatus(UserStatus.valueOf(tree.get("status").textValue()));
//            createDTO.setPassword(tree.get("password").textValue());
//
//            UserCreateDto createDTO = new UserCreateDto()
//
//            return createDTO;
//        }
//    }
//
//    public static class UserRegistrationDtoDeserializer extends JsonObjectDeserializer<UserRegistrationDto> {
//        @Override
//        protected UserRegistrationDto deserializeObject(JsonParser jsonParser, DeserializationContext context, ObjectCodec codec, JsonNode tree) throws IOException {
//            UserRegistrationDto dto = new UserRegistrationDto();
//            dto.setMail(tree.get("mail").textValue());
//            dto.setFullName(tree.get("fio").textValue());
//            dto.setPassword(tree.get("password").textValue());
//            return dto;
//        }
//    }
//
//    public static class loginDtoDeserializer extends JsonObjectDeserializer<LoginDto> {
//
//        @Override
//        protected LoginDto deserializeObject(JsonParser jsonParser, DeserializationContext context, ObjectCodec codec, JsonNode tree) throws IOException {
//            LoginDto loginDto = new LoginDto(
//                    tree.get("mail").textValue(),
//                    tree.get("password").textValue()
//            );
//
//            return loginDto;
//        }
//    }
//
//    public static class PageOfDtoSerializer extends JsonObjectSerializer<PageOfDto<UserDto>> {
//        @Override
//        protected void serializeObject(PageOfDto<UserDto> value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
//            jgen.writeNumberField("number", value.getNumber());
//            jgen.writeNumberField("size", value.getSize());
//            jgen.writeNumberField("total_pages", value.getTotalPages());
//            jgen.writeNumberField("total_elements", value.getTotalElements());
//            jgen.writeBooleanField("first", value.isFirst());
//            jgen.writeNumberField("number_of_elements", value.getNumberOfElements());
//            jgen.writeBooleanField("last", value.isLast());
//            jgen.writeArrayFieldStart("content");
//
//            value.getContent().stream().forEach(userDto -> {
//                        try {
//                            jgen.writeStartObject();
//                            jgen.writeStringField("uuid", userDto.getUuid().toString());
//                            jgen.writeNumberField("dt_create", Timestamp.valueOf(userDto.getCreatedAt()).getTime());
//                            jgen.writeNumberField("dt_update", Timestamp.valueOf(userDto.getCreatedAt()).getTime());
//                            jgen.writeStringField("mail", userDto.getMail());
//                            jgen.writeStringField("fio", userDto.getFullName());
//                            jgen.writeStringField("role", userDto.getRole().name());
//                            jgen.writeStringField("status", userDto.getStatus().name());
//                            jgen.writeEndObject();
//                        } catch (IOException e) {
//                            throw new RuntimeException(e);
//                        }
//                    }
//            );
//            jgen.writeEndArray();
//        }
//    }



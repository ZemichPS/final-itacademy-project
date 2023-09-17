package by.ITAcademy.UserMicroService.services.utils.JsonComponents;

import by.ITAcademy.UserMicroService.core.DTO.UserDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;
import org.springframework.stereotype.Component;


public class UserDtoKafkaSerializer implements Serializer<UserDto> {

    @Override
    public byte[] serialize(String s, UserDto dto) {
        ObjectMapper mapper = new ObjectMapper();

        if(dto == null){
            return null;
        }
        try {
            return mapper.writeValueAsString(dto).getBytes();
        } catch (JsonProcessingException e) {
            throw new SerializationException("Error serializing UserDto to bytes[] ", e);
        }
    }
}

//package by.ITAcademy.taskservice.endpoint.jsoncomponents;
//
//
//import by.ITAcademy.taskservice.core.dto.*;
//import by.ITAcademy.taskservice.core.enums.TaskStatus;
//import com.fasterxml.jackson.core.JsonGenerator;
//import com.fasterxml.jackson.core.JsonParser;
//import com.fasterxml.jackson.core.ObjectCodec;
//import com.fasterxml.jackson.databind.DeserializationContext;
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.SerializerProvider;
//import org.springframework.boot.jackson.JsonComponent;
//import org.springframework.boot.jackson.JsonObjectDeserializer;
//import org.springframework.boot.jackson.JsonObjectSerializer;
//
//import java.io.IOException;
//import java.sql.Timestamp;
//import java.util.UUID;
//
//@JsonComponent
//public class TaskJsonComponent {
//
//    public static class TaskCreateSerializer extends JsonObjectDeserializer<TaskCreateDto> {
//
//        @Override
//        protected TaskCreateDto deserializeObject(JsonParser jsonParser, DeserializationContext context, ObjectCodec codec, JsonNode tree) throws IOException {
//            TaskCreateDto taskCreate = new TaskCreateDto();
//
//            ProjectRefDto projectRef = new ProjectRefDto(UUID.fromString(tree.get("project").get("uuid").textValue()));
//            taskCreate.setProject(projectRef);
//            taskCreate.setTitle(tree.get("title").textValue());
//            taskCreate.setDescription(tree.get("description").textValue());
//            taskCreate.setStatus(TaskStatus.valueOf(tree.get("status").textValue()));
//            UserRefDto userRef = new UserRefDto(UUID.fromString(tree.get("implementer").get("uuid").textValue()));
//            taskCreate.setImplementer(userRef);
//
//            return taskCreate;
//        }
//    }
//
//    public static class PageOfTaskSerializer extends JsonObjectSerializer<PageOfDto<TaskDto>> {
//
//        @Override
//        protected void serializeObject(PageOfDto<TaskDto> value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
//
//            jgen.writeNumberField("number", value.number());
//            jgen.writeNumberField("size", value.size());
//            jgen.writeNumberField("total_pages", value.totalPages());
//            jgen.writeNumberField("total_elements", value.totalElements());
//            jgen.writeBooleanField("first", value.first());
//            jgen.writeNumberField("number_of_elements", value.numberOfElements());
//            jgen.writeBooleanField("last", value.last());
//            jgen.writeArrayFieldStart("content");
//            value.content().stream().forEach(task -> {
//                try {
//                    jgen.writeStartObject();
//                    jgen.writeStringField("uuid", task.getUuid().toString());
//                    jgen.writeNumberField("dt_create", Timestamp.valueOf(task.getDtCreate()).getTime());
//                    jgen.writeNumberField("dt_update", Timestamp.valueOf(task.getDtUpdate()).getTime());
//                    jgen.writeStringField("project", "{" + task.getProject().uuid().toString() + "}");
//                    jgen.writeStringField("title", task.getTitle());
//                    jgen.writeStringField("description", task.getDescription());
//                    jgen.writeStringField("status", task.getStatus().name());
//                    jgen.writeStringField("implementer", "{" + task.getImplementer().uuid().toString() + "}");
//                    jgen.writeEndObject();
//                } catch (IOException e) {
//                    throw new RuntimeException("Failed to serialize page", e);
//                }
//            });
//            jgen.writeEndArray();
//        }
//    }
//    public static class TaskSerializer extends JsonObjectSerializer<TaskDto> {
//        @Override
//        protected void serializeObject(TaskDto value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
//            jgen.writeStringField("uuid", value.getProject().uuid().toString());
//            jgen.writeNumberField("dt_create", Timestamp.valueOf(value.getDtCreate()).getTime());
//            jgen.writeNumberField("dt_update", Timestamp.valueOf(value.getDtUpdate()).getTime());
//            jgen.writeStringField("project", "{" + value.getProject().uuid().toString() + "}");
//            jgen.writeStringField("title", value.getTitle());
//            jgen.writeStringField("description", value.getDescription());
//            jgen.writeStringField("status", value.getStatus().name());
//            jgen.writeStringField("implementer", "{" + value.getImplementer().uuid().toString() + "}");
//
//        }
//
//    }
//}

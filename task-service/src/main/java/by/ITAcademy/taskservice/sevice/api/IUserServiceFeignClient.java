package by.ITAcademy.taskservice.sevice.api;

import by.ITAcademy.taskservice.config.FeignClientConfig;
import by.ITAcademy.taskservice.core.dto.UserDto;
import by.ITAcademy.taskservice.core.dto.UserRefDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;
import java.util.UUID;


@FeignClient(value = "userServiceClient",
        url = "USER-SERVICE/users",
        configuration = FeignClientConfig.class
)
public interface IUserServiceFeignClient {
    @GetMapping("/me")
    public ResponseEntity<UserDto> getCurrentUser(@RequestHeader(HttpHeaders.AUTHORIZATION) String token);

    @GetMapping("validateStaff")
    public ResponseEntity<Void> validateStaff(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                              @RequestBody List<UserRefDto> stafflist);

    @GetMapping("/{uuid}")
    public ResponseEntity<UserDto> getUserByUuid(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                              @PathVariable UUID uuid);

}

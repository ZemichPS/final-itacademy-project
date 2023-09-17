package by.itacademy.auditservice.service.api;

import by.itacademy.auditservice.core.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;


@FeignClient(value = "userServiceClient",
        url = "USER-SERVICE/users"
)
public interface IUserServiceFeignClient {
    @GetMapping("/me")
    public ResponseEntity<UserDto> getCurrentUser(@RequestHeader(HttpHeaders.AUTHORIZATION) String token);

}

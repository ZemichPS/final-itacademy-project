package by.itacademy.auditservice.endpoints.web;


import by.itacademy.auditservice.dao.entity.AuditEntity;
import by.itacademy.auditservice.service.api.IAuditService;
import by.itacademy.sharedresource.core.dto.AuditRecord;
import by.itacademy.sharedresource.core.dto.PageDTO;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Validated
@RestController
@RequestMapping("/audit")
public class AuditController {
    private final IAuditService auditService;
    private final ConversionService conversionService;


    public AuditController(
            IAuditService auditService,
            ConversionService conversionService
    ) {
        this.auditService = auditService;
        this.conversionService = conversionService;
    }

    @GetMapping
    public ResponseEntity<?> getPages(
            @RequestParam(required = false, defaultValue = "0") @PositiveOrZero Integer page,
            @RequestParam(required = false, defaultValue = "20") @PositiveOrZero Integer size
    ) {
        Page<AuditEntity> pageOfAudit =  auditService.get(PageRequest.of(page, size));

        return new ResponseEntity<>(
                conversionService.convert(pageOfAudit, PageDTO.class),
                HttpStatus.OK
        );
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<?> getCard(@PathVariable UUID uuid) {
        AuditRecord auditDTO = conversionService.convert(auditService.get(uuid), AuditRecord.class);
        return new ResponseEntity<>(auditDTO, HttpStatus.OK);
    }


}

package by.ITAcademy.taskservice.endpoint.controller;

import by.ITAcademy.taskservice.core.dto.*;
import by.ITAcademy.taskservice.sevice.api.IProjectService;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/project")
public class ProjectController {
    private final ConversionService conversionService;
    private final IProjectService projectService;

    public ProjectController(ConversionService conversionService, IProjectService projectService) {
        this.conversionService = conversionService;
        this.projectService = projectService;
    }

    @GetMapping
    public ResponseEntity<Void> save(@RequestBody(required = true) ProjectCreateDto projectCreate) {
        projectService.create(projectCreate);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<PageOfDto<ProjectDto>> getPage(@RequestParam(defaultValue = "0") Integer page,
                                                         @RequestParam(defaultValue = "20") Integer size,
                                                         @RequestParam(defaultValue = "false") boolean archived
    ) {
        ProjectPageParams params = new ProjectPageParams(page, size, archived);
        PageOfDto<ProjectDto> projectPage = conversionService.convert(projectService.getPage(params), PageOfDto.class);
        return new ResponseEntity<>(projectPage, HttpStatus.OK);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<ProjectDto> getProject(@PathVariable(required = true) UUID uuid) {
        ProjectRefDto ref = new ProjectRefDto(uuid);
        ProjectDto projectDto = conversionService.convert(projectService.getProject(ref), ProjectDto.class);
        // TODO add controller logic
        return new ResponseEntity<>(projectDto, HttpStatus.CREATED);
    }

    @PutMapping("/{uuid}/dt_update/{dt_update}")
    public ResponseEntity<Void> update(@PathVariable(required = true) UUID uuid,
                                       @PathVariable(required = true, name = "dt_update") LocalDateTime dtUpdate,
                                       @RequestBody(required = true) ProjectCreateDto projectCreate
    ) {
        // TODO add controller logic
        projectService.update(new ProjectUpdateParams(uuid, dtUpdate), projectCreate);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}

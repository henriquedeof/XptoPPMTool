package com.xpto.ppmtool.controllers;

import com.xpto.ppmtool.domain.ProjectTask;
import com.xpto.ppmtool.services.ProjectTaskService;
import com.xpto.ppmtool.utils.validations.MapValidationError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/api/backlog")
public class BacklogController {

    private ProjectTaskService projectTaskService;

    public BacklogController(ProjectTaskService projectTaskService) {
        this.projectTaskService = projectTaskService;
    }

    @PostMapping("/{backlog_id}")
    public ResponseEntity<?> addPTToBacklog(@Valid @RequestBody ProjectTask projectTask, BindingResult bindingResult,
                                            @PathVariable String backlog_id, Principal principal){

        ResponseEntity<?> errorMap = MapValidationError.getMapValidation(bindingResult);
        if(errorMap != null){
            return errorMap;
        }

        ProjectTask addedProjectTask = this.projectTaskService.addProjectTask(backlog_id, projectTask, principal.getName());

        return new ResponseEntity<ProjectTask>(addedProjectTask, HttpStatus.CREATED);
    }

    @GetMapping("/{backlog_id}")
    public Iterable<ProjectTask> getProjectBacklog(@PathVariable String backlog_id, Principal principal){
        return projectTaskService.findBacklogById(backlog_id, principal.getName());
    }

    @GetMapping("/{backlog_id}/{pt_id}")
    public ResponseEntity<?> getProjectTask(@PathVariable String backlog_id, @PathVariable String pt_id, Principal principal){
        ProjectTask projectTask =   this.projectTaskService.findPTByProjectSequence(backlog_id, pt_id, principal.getName());
        return new ResponseEntity<ProjectTask>(projectTask, HttpStatus.OK);
    }

    @PatchMapping("/{backlog_id}/{pt_id}")
    public ResponseEntity<?> updateProjectTask(@Valid @RequestBody ProjectTask projectTask, BindingResult bindingResult,
                                            @PathVariable String backlog_id, @PathVariable String pt_id, Principal principal){

        ResponseEntity<?> mapError = MapValidationError.getMapValidation(bindingResult);
        if(mapError != null){
            return mapError;
        }

        ProjectTask updatedTask = this.projectTaskService.updateByProjectSequence(projectTask, backlog_id, pt_id, principal.getName());

        return new ResponseEntity<ProjectTask>(updatedTask, HttpStatus.OK);
    }

    @DeleteMapping("/{backlog_id}/{pt_id}")
    public ResponseEntity<?> deleteProjectTask(@PathVariable String backlog_id, @PathVariable String pt_id, Principal principal){
        this.projectTaskService.deletePTByProjectSequence(backlog_id, pt_id, principal.getName());

        return new ResponseEntity<String>("Project Task " + pt_id + " was deleted successfully.", HttpStatus.OK);
    }


}

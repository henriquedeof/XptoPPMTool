package com.xpto.ppmtool.controllers;

import com.xpto.ppmtool.domain.Project;
import com.xpto.ppmtool.services.ProjectService;
import com.xpto.ppmtool.utils.validations.MapValidationError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/project")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    /*
    BindingResult is used in conjunction with @Valid annotation (which validates the validations created on my Entity objects)

    NOTE: On Dougllas' course, he creates a method to validate each field and if any problem happens, the application throws an exception.
          However, in this course, Carlos creates field validations directly on Entities through javax.validation.constraints, without the necessity of validating them "manually".
    */

    //NOTE: In this course, Carlos is using this method to create or update a Project. This is considered a BAD practice as update methods should use PUT but not POST HTTP method.
    //      I am leaving as it is just to follow the course, but when the course is finished, I must correct it and use a different method.
    @PostMapping
    public ResponseEntity<?> createNewProject(@Valid @RequestBody Project project, BindingResult bindingResult){
        //Without the @Valid I was getting a 500 server error. With it, my response is a 400 bad request

        ResponseEntity<?> errorMap = MapValidationError.getMapValidation(bindingResult);//validating Inputs of project param.
        if (errorMap != null){
            return errorMap;
        }

        Project savedProject = this.projectService.saveOrUpdateProject(project);
        return new ResponseEntity<Project>(savedProject, HttpStatus.CREATED);


        /*
        Just for a matter of curiosity, the ProjectServiceImpl is handling the exception the same way as here. However, the exception shown is this one, not the one on the Service layer.
        It means that although they have the same implementation the first invoker method (this one) has the preference in launching the exception.
        */
//        try {
//            Project savedProject = this.projectService.saveOrUpdateProject(project);
//            return new ResponseEntity<Project>(savedProject, HttpStatus.CREATED);
//        }catch (ProjectIdException e){
//            throw new ProjectIdException("Project ID '" + project.getProjectIdentifier().toUpperCase() + "' already exists1987329873");
//        }
    }

    @GetMapping("/{projectIdentifier}")
    public ResponseEntity<?> findProjectByIdentifier(@PathVariable String projectIdentifier){
        Project project = this.projectService.findProjectByIdentifier(projectIdentifier);
        return new ResponseEntity<>(project, HttpStatus.OK);
    }

    @GetMapping("/all")
    public List<Project> getAllProjects(){
        /*
        Apparently, the way this list is structured is not right. The PROBABLE correct way may be found on Jonh's project.
        */

        return this.projectService.findAllProjects();//Do not need any kind of validation as if the there is no Projects available, the list will be empty.
    }

    @DeleteMapping("/{projectIndetifier}")
    public ResponseEntity<?> deleteProject(@PathVariable String projectIndetifier){
        this.projectService.deleteProjectByIdentifier(projectIndetifier);

        return new ResponseEntity<String>("Project with ID: '" + projectIndetifier + "' was deleted", HttpStatus.OK);
    }

}

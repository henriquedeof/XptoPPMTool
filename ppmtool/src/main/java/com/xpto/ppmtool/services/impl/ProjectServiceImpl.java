package com.xpto.ppmtool.services.impl;

import com.xpto.ppmtool.domain.Project;
import com.xpto.ppmtool.exceptions.ProjectIdException;
import com.xpto.ppmtool.repositories.ProjectRepository;
import com.xpto.ppmtool.services.ProjectService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectServiceImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public Project saveOrUpdateProject(Project project){

        try{
            project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
            return projectRepository.save(project);
        }catch (Exception e){
            throw new ProjectIdException("Project ID '" + project.getProjectIdentifier().toUpperCase() + "' already exists");
        }
    }

    @Override
    public Project findProjectByIdentifier(String projectIdentifier) {
        Project project = this.projectRepository.findByProjectIdentifier(projectIdentifier.toUpperCase());
        if(project == null){
            throw new ProjectIdException("Project ID '" + projectIdentifier + "' does not exist");
        }

        return project;
    }

    @Override
    public List<Project> findAllProjects() {
        return this.projectRepository.findAll();
    }

    @Override
    public void deleteProjectByIdentifier(String identifier) {
        Project project = this.findProjectByIdentifier(identifier);

        //I believe I do not need this IF code as the findProjectByIdentifier() method already validates whether Project exists or not
//        if(project == null){
//            throw new ProjectIdException("Project ID '" + identifier + "' does not exist1111111");
//        }

        this.projectRepository.delete(project);
    }


}

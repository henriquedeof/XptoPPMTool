package com.xpto.ppmtool.services.impl;

import com.xpto.ppmtool.domain.Backlog;
import com.xpto.ppmtool.domain.Project;
import com.xpto.ppmtool.exceptions.ProjectIdException;
import com.xpto.ppmtool.repositories.BacklogRepository;
import com.xpto.ppmtool.repositories.ProjectRepository;
import com.xpto.ppmtool.services.ProjectService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final BacklogRepository backlogRepository;

    public ProjectServiceImpl(ProjectRepository projectRepository, BacklogRepository backlogRepository) {
        this.projectRepository = projectRepository;
        this.backlogRepository = backlogRepository;
    }

    @Override
    public Project saveOrUpdateProject(Project project){

        String projectIdentifier = project.getProjectIdentifier().toUpperCase();

        try{
            project.setProjectIdentifier(projectIdentifier);

            //If it is the creation of a new Project, create a new Backlog associated with it
            if(project.getId() == null){
                Backlog backlog = new Backlog();
                project.setBacklog(backlog);
                backlog.setProject(project);
                backlog.setProjectIdentifier(projectIdentifier);
            }

            //If it is the update of an existent Project then set the correspondent Backlog
            if(project.getId() != null){
                project.setBacklog(this.backlogRepository.findByProjectIdentifier(projectIdentifier));
            }

            return projectRepository.save(project);
        }catch (Exception e){
            throw new ProjectIdException("Project ID '" + projectIdentifier + "' already exists");
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

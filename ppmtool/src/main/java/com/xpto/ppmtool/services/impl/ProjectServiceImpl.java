package com.xpto.ppmtool.services.impl;

import com.xpto.ppmtool.domain.Backlog;
import com.xpto.ppmtool.domain.Project;
import com.xpto.ppmtool.domain.User;
import com.xpto.ppmtool.exceptions.ProjectIdException;
import com.xpto.ppmtool.exceptions.ProjectNotFoundException;
import com.xpto.ppmtool.repositories.BacklogRepository;
import com.xpto.ppmtool.repositories.ProjectRepository;
import com.xpto.ppmtool.repositories.UserRepository;
import com.xpto.ppmtool.services.ProjectService;
import org.springframework.stereotype.Service;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final BacklogRepository backlogRepository;
    private final UserRepository userRepository;

    public ProjectServiceImpl(ProjectRepository projectRepository, BacklogRepository backlogRepository, UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.backlogRepository = backlogRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Project saveOrUpdateProject(Project project, String username){

        String projectIdentifier = project.getProjectIdentifier().toUpperCase();

        if(project.getId() != null){//verifying if it is an update
            Project existingProject = this.projectRepository.findByProjectIdentifier(projectIdentifier);

            if(existingProject != null && (!project.getProjectLeader().equals(username))){
                throw new ProjectNotFoundException("Project not found in your account");
            }else if(existingProject == null){
                throw new ProjectNotFoundException("Project with ID '" + projectIdentifier + "' cannot be updated because it does not exist");
            }
        }

        try{

            User user = this.userRepository.findByUsername(username);

            project.setProjectIdentifier(projectIdentifier);
            project.setProjectLeader(user.getUsername());
            project.setUser(user);

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
    public Project findProjectByIdentifier(String projectIdentifier, String username) {
        Project project = this.projectRepository.findByProjectIdentifier(projectIdentifier.toUpperCase());
        if(project == null){
            throw new ProjectIdException("Project ID '" + projectIdentifier + "' does not exist");
        }

        if(!project.getProjectLeader().equals(username)){
            throw new ProjectNotFoundException("Project not found in your account");
        }

        return project;
    }

    @Override
    public Iterable<Project> findAllProjects(String username) {
        return this.projectRepository.findAllByProjectLeader(username);
    }

    @Override
    public void deleteProjectByIdentifier(String identifier, String username) {
        Project project = this.findProjectByIdentifier(identifier, username);
        this.projectRepository.delete(project);
    }


}

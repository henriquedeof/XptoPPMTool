package com.xpto.ppmtool.services.impl;

import com.xpto.ppmtool.domain.Backlog;
import com.xpto.ppmtool.domain.ProjectTask;
import com.xpto.ppmtool.exceptions.ProjectNotFoundException;
import com.xpto.ppmtool.repositories.BacklogRepository;
import com.xpto.ppmtool.repositories.ProjectRepository;
import com.xpto.ppmtool.repositories.ProjectTaskRepository;
import com.xpto.ppmtool.services.ProjectTaskService;
import org.springframework.stereotype.Service;

@Service
public class ProjectTaskServiceImpl implements ProjectTaskService {

    private final BacklogRepository backlogRepository;
    private final ProjectTaskRepository projectTaskRepository;
    private final ProjectRepository projectRepository;


    public ProjectTaskServiceImpl(BacklogRepository backlogRepository, ProjectTaskRepository projectTaskRepository, ProjectRepository projectRepository) {
        this.backlogRepository = backlogRepository;
        this.projectTaskRepository = projectTaskRepository;
        this.projectRepository = projectRepository;
    }


    @Override
    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask) {
        try {
            Backlog backlog = this.backlogRepository.findByProjectIdentifier(projectIdentifier);

            projectTask.setBacklog(backlog);
            Integer ptSequence = backlog.getPTSequence();
            ptSequence++;

            backlog.setPTSequence(ptSequence);

            projectTask.setProjectSequence(projectIdentifier + "-" + ptSequence);
            projectTask.setProjectIdentifier(projectIdentifier);

            //If priority is not set, the default is 0.
            if(projectTask.getPriority() == null){
                projectTask.setPriority(3);
            }

            //If priority is not set, the default is 0.
            if(projectTask.getStatus() == "" || projectTask.getStatus() == null){
                projectTask.setStatus("TO_DO");
            }

            return this.projectTaskRepository.save(projectTask);

        }catch (Exception e){
            throw new ProjectNotFoundException("Project not Found");
        }

    }

    @Override
    public Iterable<ProjectTask> findBacklogById(String projectIdentifier) {
        if(this.projectRepository.findByProjectIdentifier(projectIdentifier) == null){
            throw new ProjectNotFoundException("Project with ID: '" + projectIdentifier + "' does not exist");
        }

        return projectTaskRepository.findByProjectIdentifierOrderByPriority(projectIdentifier);
    }

    @Override
    public ProjectTask findPTByProjectSequence(String backlogIdentifier, String projectSequence) {
        Backlog backlog = this.backlogRepository.findByProjectIdentifier(backlogIdentifier);
        if(backlog == null){
            throw new ProjectNotFoundException("Project with ID: '" + backlogIdentifier + "' does not exist");
        }

        ProjectTask projectTask = this.projectTaskRepository.findByProjectSequence(projectSequence);
        if(projectTask == null){
            throw new ProjectNotFoundException("Project Task: '" + projectSequence + "' not found");
        }

        //Validating if the Project Task belongs to the right project
        if(!projectTask.getProjectIdentifier().equals(backlogIdentifier)){
            throw new ProjectNotFoundException("Project Task: '" + projectSequence + "' does not exist in project: " + backlogIdentifier);
        }

        return projectTask;
    }

    @Override
    public ProjectTask updateByProjectSequence(ProjectTask projectTask, String backlogIdentifier, String projectSequence) {
        ProjectTask ptByProjectSequence = this.findPTByProjectSequence(backlogIdentifier, projectSequence);

        ptByProjectSequence = projectTask;

        return this.projectTaskRepository.save(ptByProjectSequence);
    }

    @Override
    public void deletePTByProjectSequence(String backlogIdentifier, String projectSequence) {
        ProjectTask projectTask = this.findPTByProjectSequence(backlogIdentifier, projectSequence);
        this.projectTaskRepository.delete(projectTask);
    }


}

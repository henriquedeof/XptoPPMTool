package com.xpto.ppmtool.services;

import com.xpto.ppmtool.domain.ProjectTask;

public interface ProjectTaskService {

    ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask, String username);

    Iterable<ProjectTask> findBacklogById(String projectIdentifier, String username);

    ProjectTask findPTByProjectSequence(String backlogIdentifier, String projectSequence, String username);

    ProjectTask updateByProjectSequence(ProjectTask projectTask, String backlogIdentifier, String projectSequence, String username);

    void deletePTByProjectSequence(String backlogIdentifier, String projectSequence, String username);

}

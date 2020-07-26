package com.xpto.ppmtool.services;

import com.xpto.ppmtool.domain.ProjectTask;

public interface ProjectTaskService {

    ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask);

    Iterable<ProjectTask> findBacklogById(String projectIdentifier);

    ProjectTask findPTByProjectSequence(String backlogIdentifier, String projectSequence);

    ProjectTask updateByProjectSequence(ProjectTask projectTask, String backlogIdentifier, String projectSequence);

    void deletePTByProjectSequence(String backlogIdentifier, String projectSequence);

}

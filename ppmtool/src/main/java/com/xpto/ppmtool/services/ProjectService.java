package com.xpto.ppmtool.services;

import com.xpto.ppmtool.domain.Project;

import java.util.List;

public interface ProjectService {

    Project saveOrUpdateProject(Project project);

    Project findProjectByIdentifier(String projectIdentifier);

    List<Project> findAllProjects();

    void deleteProjectByIdentifier(String identifier);

}

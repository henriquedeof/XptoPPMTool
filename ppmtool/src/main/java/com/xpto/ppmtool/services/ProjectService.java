package com.xpto.ppmtool.services;

import com.xpto.ppmtool.domain.Project;

public interface ProjectService {

    Project saveOrUpdateProject(Project project, String username);

    public Iterable<Project> findAllProjects(String username);

    Project findProjectByIdentifier(String projectIdentifier, String username);

    void deleteProjectByIdentifier(String identifier, String username);

}

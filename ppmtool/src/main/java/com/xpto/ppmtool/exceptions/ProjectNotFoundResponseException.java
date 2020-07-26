package com.xpto.ppmtool.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectNotFoundResponseException {

    private String projectNotFound;

    public ProjectNotFoundResponseException(String projectNotFound) {
        this.projectNotFound = projectNotFound;
    }
}

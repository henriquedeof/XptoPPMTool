package com.xpto.ppmtool.exceptions;

import lombok.Data;

@Data
public class ProjectIdResponseException {

    private String projectIdentifier;

    public ProjectIdResponseException(String projectIdentifier) {
        this.projectIdentifier = projectIdentifier;
    }



}

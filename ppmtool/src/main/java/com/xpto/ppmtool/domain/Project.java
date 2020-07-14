package com.xpto.ppmtool.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Project Name is required")
    private String projectName;

    @NotBlank(message = "Project Identifier is required")
    @Size(min = 4, max = 5, message = "Project Name must have between 4 to 5 characters")
    @Column(updatable = false, unique = true)
    private String projectIdentifier;//This field is unique and once created, I cannot change anymore (according to the project specification).

    @NotBlank(message = "Project Description is required")
    private String description;

    @JsonFormat(pattern = "yyyy-mm-dd")
    private Date startDate; //Currently, the best way to use Date is using the java.time.LocalDate

    @JsonFormat(pattern = "yyyy-mm-dd")
    private Date endDate;

    //-----------

    @JsonFormat(pattern = "yyyy-mm-dd")
    private Date createdAt;

    @JsonFormat(pattern = "yyyy-mm-dd")
    private Date updatedAt;


    //---------- https://www.baeldung.com/jpa-entity-lifecycle-events ------------
    //Every time a object is created, when the system calls the save() method, this.createdAt will receive a new Date() with the current date
    @PrePersist
    protected void onCreate(){
        this.createdAt = new Date();
    }

    //Every time a object is updated, when the system calls the update() method, this.updatedAt will receive a new Date() with the current date
    @PreUpdate
    protected void onUpdate(){
        this.updatedAt = new Date();
    }


}

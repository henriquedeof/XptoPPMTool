package com.xpto.ppmtool.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
//@Data
@Getter
@Setter
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

    //EAGER: When a Project object is loaded the Backlog information is available for using.
    //CascadeType.ALL: Indicates that Project is the owning side of the relationship. It means that if I delete a Project object, all children must be deleted as well.
            //However, if a delete a son of Project, this Project object will not be affected at all.
    //mappedBy = "project": Indicates that Backlog has an attribute named as 'project' and also it will be the FK.
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "project")
    @JsonIgnore //Adding this annotation as, according to the course, it is not necessary to show the tasks here, only the Project information.
    private Backlog backlog;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User user;

    private String projectLeader;

    //-----------

    @JsonFormat(pattern = "yyyy-mm-dd")
    @Column(updatable = false)
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

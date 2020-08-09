package com.xpto.ppmtool.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class ProjectTask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(updatable = false, unique = true)
    private String projectSequence;

    @NotBlank(message = "Please include a project summary")
    private String summary;

    private String acceptanceCriteria;
    private String status;
    private Integer priority;

    @JsonFormat(pattern = "yyyy-mm-dd")
    private Date dueDate;

    @Column(updatable = false)
    private String projectIdentifier;

    @JsonFormat(pattern = "yyyy-mm-dd")
    private Date createdAt;

    @JsonFormat(pattern = "yyyy-mm-dd")
    private Date updatedAt;

    //I believe JPA already maps the property 'name = "backlog_id"'. Therefore, I can delete it and the behaviour will be the same.
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "backlog_id", updatable = false, nullable = false)
    @JsonIgnore
    private Backlog backlog;

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

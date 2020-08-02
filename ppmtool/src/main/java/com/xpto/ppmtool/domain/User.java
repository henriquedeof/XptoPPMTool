package com.xpto.ppmtool.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Collection;
import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email(message = "Username needs to be an email")
    @NotBlank(message = "Username is required")//verify if it is not null and has at least one character.
    //@Column(updatable = false, unique = true, nullable = false)
    @Column(unique = true)//Maybe the way above might be better
    private String username;

    @NotBlank(message = "Full name is required")
    private String fullName;

    @NotBlank(message = "Password is required")
    private String password;

    @Transient //This field will not be persisted on the database
    private String confirmPassword;

    //--------

    //@JsonFormat(pattern = "yyyy-mm-dd")
    @Column(updatable = false) // Maybe I do not need this annotation.
    private Date createdAt;

    //@JsonFormat(pattern = "yyyy-mm-dd")
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


    //UserDetails methods below


    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }

}

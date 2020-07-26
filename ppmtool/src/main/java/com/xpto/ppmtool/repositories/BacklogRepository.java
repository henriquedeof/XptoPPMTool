package com.xpto.ppmtool.repositories;

import com.xpto.ppmtool.domain.Backlog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BacklogRepository extends JpaRepository<Backlog, Long> {

    Backlog findByProjectIdentifier(String projectIdentifier);

}

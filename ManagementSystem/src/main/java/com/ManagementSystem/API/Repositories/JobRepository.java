package com.ManagementSystem.API.Repositories;

import com.ManagementSystem.API.Entities.Job;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JobRepository extends JpaRepository<Job, Long> {

}

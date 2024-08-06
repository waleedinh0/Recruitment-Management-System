package com.ManagementSystem.API.Controller;

import com.ManagementSystem.API.Entities.Job;
import com.ManagementSystem.API.Repositories.JobRepository;
import com.ManagementSystem.API.Service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class JobController {
    @Autowired
    private JobService jobService;

    @Autowired
    private JobRepository jobRepository;

    @GetMapping("/jobs")
    public ResponseEntity<List<Job>> getJobs() {
        List<Job> jobs=  jobRepository.findAll();
        return ResponseEntity.ok(jobs);
    }

    @PreAuthorize("hasRole('APPLICANT')")
    @GetMapping("/jobs/apply?job_id={job_id}")
    public Job jobs(@PathVariable Long id) {
        return jobService.findJobById(id);
    }
}

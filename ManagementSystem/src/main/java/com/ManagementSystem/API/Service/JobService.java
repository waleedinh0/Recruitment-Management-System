package com.ManagementSystem.API.Service;

import com.ManagementSystem.API.Entities.Job;
import com.ManagementSystem.API.Repositories.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JobService {
    @Autowired
    private JobRepository jobRepository;

    public Job saveJob(Job job) {
        return jobRepository.save(job);
    }

    public Job findJobById(Long id) {
        return jobRepository.findById(id).orElse(null);
    }
}

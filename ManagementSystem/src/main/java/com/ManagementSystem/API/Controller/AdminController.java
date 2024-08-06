package com.ManagementSystem.API.Controller;

import com.ManagementSystem.API.Entities.Job;
import com.ManagementSystem.API.Entities.User;
import com.ManagementSystem.API.Repositories.UserRepository;
import com.ManagementSystem.API.Service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class AdminController {
    @Autowired
    private JobService jobService;
    @Autowired
    private UserRepository userRepository;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin/job")
    public ResponseEntity<?> createJob(@RequestBody Job job) {
        Job createdJob = jobService.saveJob(job);
        return ResponseEntity.ok(createdJob);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/job/{id}")
    public ResponseEntity<?> getJobById(@PathVariable Long id) {
        Job job = jobService.findJobById(id);
        if (job == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(job);
    }

    @GetMapping("/admin/applicants")
    public ResponseEntity<List<User>> getAllApplicants() {
        List<User> applicants = userRepository.findAll();
        return ResponseEntity.ok(applicants);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/applicant/{id}")
    public ResponseEntity<?> applicantId(@PathVariable Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            Long userId = user.getUserId();
            return ResponseEntity.ok().body(userId);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

package com.ManagementSystem.API.Controller;

import com.ManagementSystem.API.Entities.Profile;
import com.ManagementSystem.API.Repositories.ProfileRepository;
import com.ManagementSystem.API.Service.ProfileService;
import com.ManagementSystem.API.Service.ResumeParserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Map;

@RestController
public class ResumeController {
    @Autowired
    private ProfileService profileService;
    @Autowired
    private ResumeParserService resumeParserService;
    @Autowired
    private ProfileRepository profileRepository;


    @PostMapping("/uploadResume")
    public ResponseEntity<?> uploadResume(@RequestParam("file") MultipartFile file) throws Exception{
        File convFile = new File(System.getProperty("java.io.tmpdir") + "/" + file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convFile)) {
            fos.write(file.getBytes());
        }
        Map<String, Object> parsedData = resumeParserService.parseResume(convFile);

        Profile profile = profileService.createProfileFromParsedData(parsedData);

        return ResponseEntity.ok("Resume uploaded and parsed successfully!");
    }
}

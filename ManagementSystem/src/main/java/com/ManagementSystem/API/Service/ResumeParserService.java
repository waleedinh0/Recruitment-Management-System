package com.ManagementSystem.API.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.nio.file.Files;
import java.util.Map;

@Service
public class ResumeParserService {
    @Value("${resume.parser.api.url}")
    private String apiUrl;

    @Value("${resume.parser.api.key}")
    private String apiKey;

    public Map<String, Object> parseResume(File resumeFile) throws Exception {

        RestTemplate restTemplate = new RestTemplate();
        byte[] fileContent = Files.readAllBytes(resumeFile.toPath());
        Resource fileResource = new ByteArrayResource(fileContent);

        HttpEntity<Resource> requestEntity = new HttpEntity<>(fileResource, httpHeaders());
        ResponseEntity<Map> response = restTemplate.exchange(apiUrl, HttpMethod.POST, requestEntity, Map.class);

        return response.getBody();
    }

    public HttpHeaders httpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.set("apikey", apiKey);
        return headers;
    }
}

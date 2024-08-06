package com.ManagementSystem.API.Service;

import com.ManagementSystem.API.Entities.Profile;
import com.ManagementSystem.API.Repositories.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProfileService {
    @Autowired
    private ProfileRepository profileRepository;

    public Profile createProfileFromParsedData(Map<String, Object> parsedData) {
        Profile profile = new Profile();
        profile.setProfileName((String) parsedData.get("name"));
        profile.setEmail((String) parsedData.get("email"));
        profile.setPhone((String) parsedData.get("phone"));


        List<Map<String, Object>> experienceList = (List<Map<String, Object>>) parsedData.get("experience");
        if (experienceList != null && !experienceList.isEmpty()) {
            String experience = formatExperience(experienceList);
            profile.setExperience(experience);
            System.out.println("Formatted Experience: " + experience);
        } else {
            System.out.println("Experience list is null or empty");
        }


        List<Map<String, Object>> educationList = (List<Map<String, Object>>) parsedData.get("education");
        if (educationList != null && !educationList.isEmpty()) {
            String education = formatEducation(educationList);
            profile.setEducation(education);
            System.out.println("Formatted Education: " + education);
        } else {
            System.out.println("Education list is null or empty");
        }


        List<String> skillsList = (List<String>) parsedData.get("skills");
        if (skillsList != null && !skillsList.isEmpty()) {
            String skills = formatSkills(skillsList);
            profile.setSkills(skills);
            System.out.println("Formatted Skills: " + skills);
        } else {
            System.out.println("Skills list is null or empty");
        }

        System.out.println("Profile before saving: " + profile);
        return profileRepository.save(profile);
    }

    private String formatExperience(List<Map<String, Object>> experienceList) {
        return experienceList.stream()
                .map(exp -> {
                    System.out.println("Processing experience entry: " + exp);
                    String company = (String) exp.get("organization");
                    String title = (String) exp.get("title");
                    List<String> dates = (List<String>) exp.get("dates");

                    if (company == null) {
                        company = (String) exp.get("name");
                    }
                    if (company == null) {
                        company = "Unknown Company";
                    }

                    String dateRange = dates != null && !dates.isEmpty() ? String.join(" - ", dates) : "No dates";

                    String formattedExp = String.format("%s at %s (%s)",
                            title != null ? title : "Unknown Title",
                            company,
                            dateRange);
                    System.out.println("Formatted experience entry: " + formattedExp);
                    return formattedExp;
                })
                .collect(Collectors.joining("; "));
    }

    private String formatEducation(List<Map<String, Object>> educationList) {
        return educationList.stream()
                .map(edu -> {
                    System.out.println("Processing education entry: " + edu);
                    String institution = (String) edu.get("name");
                    String degree = (String) edu.get("degree");
                    List<String> dates = (List<String>) edu.get("dates");

                    if (institution == null) {
                        institution = "Unknown Institution";
                    }

                    String dateRange = dates != null && !dates.isEmpty() ? String.join(" - ", dates) : "No dates";

                    String formattedEdu = String.format("%s, %s (%s)",
                            institution,
                            degree != null ? degree : "Degree not specified",
                            dateRange);
                    System.out.println("Formatted education entry: " + formattedEdu);
                    return formattedEdu;
                })
                .collect(Collectors.joining("; "));
    }

    private String formatSkills(List<String> skillsList) {
        System.out.println("Processing skills: " + skillsList);
        String formattedSkills = skillsList.stream()
                .filter(skill -> skill != null && !skill.trim().isEmpty())
                .collect(Collectors.joining(", "));
        System.out.println("Formatted skills: " + formattedSkills);
        return formattedSkills;
    }
}

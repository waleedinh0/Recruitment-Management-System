package com.ManagementSystem.API.Repositories;

import com.ManagementSystem.API.Entities.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
    Optional<Profile> findById(Long id);

    Optional<Profile> findByEmail(String email);

    Optional<Profile> findByProfileName(String userName);
}

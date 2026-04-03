package com.project.urlshortner.repository;

import com.project.urlshortner.entities.Urls;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UrlRepository extends JpaRepository<Urls, Long> {
   Optional<Urls> findByShortCode(String shortCode);
}

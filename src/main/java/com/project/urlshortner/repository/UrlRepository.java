package com.project.urlshortner.repository;

import com.project.urlshortner.entities.Urls;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlRepository extends JpaRepository<Urls, Long> {
}

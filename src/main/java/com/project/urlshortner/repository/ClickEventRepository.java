package com.project.urlshortner.repository;

import com.project.urlshortner.entities.ClickEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClickEventRepository extends JpaRepository<ClickEvent, Long> {
}

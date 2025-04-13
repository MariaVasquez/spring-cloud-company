package com.debugideas.co.company.repositories;

import com.debugideas.co.company.entities.Website;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WebsiteRepository extends JpaRepository<Website, Long> {
}

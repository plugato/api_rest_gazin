package com.plugato.api_rest_gazin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DeveloperRepository extends JpaRepository<Developer, Long>{}
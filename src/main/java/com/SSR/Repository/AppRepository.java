package com.SSR.Repository;

import com.SSR.Entity.App;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppRepository extends JpaRepository<App, Long> {
        Optional<App> findByUsername(String username);
        Optional<App> findByEmail(String email);
  }
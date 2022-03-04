package com.example.springbootbackend.repository;

import com.example.springbootbackend.model.Commentary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentaryRepository extends JpaRepository<Commentary, Long> {
}

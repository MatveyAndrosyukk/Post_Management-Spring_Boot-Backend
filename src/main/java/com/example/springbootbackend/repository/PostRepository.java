package com.example.springbootbackend.repository;

import com.example.springbootbackend.model.Commentary;
import com.example.springbootbackend.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

}

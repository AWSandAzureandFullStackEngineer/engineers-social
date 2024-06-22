package com.engineers.core.engineerssocial.repository;

import com.engineers.core.engineerssocial.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PostRepository extends JpaRepository<Post, UUID> {
}

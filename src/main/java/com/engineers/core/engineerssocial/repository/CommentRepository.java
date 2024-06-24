package com.engineers.core.engineerssocial.repository;

import com.engineers.core.engineerssocial.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

}

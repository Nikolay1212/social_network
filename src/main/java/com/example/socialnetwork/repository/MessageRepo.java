package com.example.socialnetwork.repository;

import com.example.socialnetwork.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepo extends JpaRepository<Message, Long> {
}

package com.userservice.Repositories;

import com.userservice.Models.Session;
import com.userservice.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SessionRepo extends JpaRepository<Session, Long> {

    public Optional<Session> findByTokenAndUser_Id(String token, Long id);
}

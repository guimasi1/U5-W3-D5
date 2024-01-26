package com.example.U5W3D5.event;

import com.example.U5W3D5.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EventsDAO extends JpaRepository<Event, UUID> {
     Optional<Event> findByTitle(String title);
     Page<Event> findByUsers(User user, Pageable pageable);
     Optional<Event> findById(UUID id);

}

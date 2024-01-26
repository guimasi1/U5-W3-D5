package com.example.U5W3D5.event;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EventsDAO extends JpaRepository<Event, UUID> {
     Optional<Event> findByTitle(String title);
     Optional<Event> findById(UUID id);

}

package com.example.webflux_assignment.repository;

import com.example.webflux_assignment.entity.BoardEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends ReactiveCrudRepository<BoardEntity, Long> {
}

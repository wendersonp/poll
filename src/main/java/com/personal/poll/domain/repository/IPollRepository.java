package com.personal.poll.domain.repository;

import com.personal.poll.domain.models.PollEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPollRepository extends JpaRepository<PollEntity, Long> {
}

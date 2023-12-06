package com.personal.poll.domain.repository;

import com.personal.poll.domain.models.VoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IVoteRepository extends JpaRepository<VoteEntity, Long> {
}

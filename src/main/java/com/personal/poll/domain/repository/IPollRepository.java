package com.personal.poll.domain.repository;

import com.personal.poll.domain.enums.PollStatusEnum;
import com.personal.poll.domain.models.PollEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IPollRepository extends JpaRepository<PollEntity, Long> {

    List<PollEntity> findByStatus(PollStatusEnum status);

    @EntityGraph(attributePaths = {"votes"})
    @Query("Select p From PollEntity p Where p.id = ?1")
    Optional<PollEntity> findPollWithVotesById(Long id);
}

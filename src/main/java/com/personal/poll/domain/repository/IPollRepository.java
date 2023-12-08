package com.personal.poll.domain.repository;

import com.personal.poll.domain.enums.PollStatusEnum;
import com.personal.poll.domain.models.PollEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPollRepository extends JpaRepository<PollEntity, Long> {

    List<PollEntity> findByStatus(PollStatusEnum status);
}

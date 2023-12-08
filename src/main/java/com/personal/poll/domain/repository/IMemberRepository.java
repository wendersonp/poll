package com.personal.poll.domain.repository;

import com.personal.poll.domain.models.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMemberRepository extends JpaRepository<MemberEntity, Long> {
}

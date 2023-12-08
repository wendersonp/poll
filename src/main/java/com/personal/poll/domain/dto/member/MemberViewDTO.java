package com.personal.poll.domain.dto.member;


import com.personal.poll.domain.models.MemberEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MemberViewDTO {

    private Long id;

    private String name;

    public MemberViewDTO(MemberEntity memberEntity) {
        this.id = memberEntity.getId();
        this.name = memberEntity.getName();
    }
}

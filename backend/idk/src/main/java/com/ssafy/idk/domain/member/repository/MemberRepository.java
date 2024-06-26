package com.ssafy.idk.domain.member.repository;

import com.ssafy.idk.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByPhoneNumber(String phoneNumber);
    Optional<Member> findByConnectionInformation(String connectionInformation);

    boolean existsByPhoneNumber(String phoneNumber);
}

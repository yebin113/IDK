package com.ssafy.idk.domain.pocket.repository;

import com.ssafy.idk.domain.autotransfer.entity.AutoTransfer;
import com.ssafy.idk.domain.member.entity.Member;
import com.ssafy.idk.domain.mydata.entity.Mydata;
import com.ssafy.idk.domain.pocket.entity.Pocket;
import com.ssafy.idk.domain.targetsaving.entity.TargetSaving;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PocketRepository extends JpaRepository<Pocket, Long> {

    Optional<Pocket> findByAutoTransfer(AutoTransfer autoTransfer);
    Optional<Pocket> findByMydata(Mydata mydata);

    List<Pocket> findByExpectedDate(Integer date);

    List<Pocket> findByMemberOrderByOrderNumber(Member member);

    Pocket findByTargetSaving(TargetSaving targetSaving);
}

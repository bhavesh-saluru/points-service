package com.kudospoints.pointsservice.repository;

import com.kudospoints.pointsservice.domain.PointsLedger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PointsLedgerRepository extends JpaRepository<PointsLedger, UUID> {

    @Query("SELECT SUM(pl.points) FROM PointsLedger pl WHERE pl.member.id = :memberId")
    Integer getBalanceForMember(@Param("memberId") UUID memberId);

    List<PointsLedger> findByMemberIdOrderByCreatedAtDesc(UUID memberId);
}

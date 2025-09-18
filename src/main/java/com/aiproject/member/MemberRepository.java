package com.aiproject.member;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberRepository extends JpaRepository<Member, Integer> {
    // 이메일 찾기
    @Query(value = "SELECT * FROM member m WHERE m.member_email = :email AND ROWNUM = 1", nativeQuery = true)
    Optional<Member> findByMemberEmail(@Param("email") String email);

    // 멤버가 가진 토큰 가져오기
    @Query(value = "SELECT * FROM member m WHERE m.token = :token AND ROWNUM = 1", nativeQuery = true)
    Optional<Member> findByToken(@Param("token") String token);

    // 중복확인 → boolean 대신 int로
    @Query(value = "SELECT COUNT(*) FROM member m WHERE m.member_email = :email", nativeQuery = true)
    int countByMemberEmail(@Param("email") String email);
}

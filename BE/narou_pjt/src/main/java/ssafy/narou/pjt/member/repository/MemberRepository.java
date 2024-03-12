package ssafy.narou.pjt.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ssafy.narou.pjt.member.entity.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {

    Optional<Member> findMemberByEmail(String email);
    Optional<Member> findMemberByNickname(String nickname);

    @Modifying
    @Query(value = "update Member m set m.followers=m.followers+:count where m.userId=:id")
    void hitFollwersCount(@Param("id") Long userId, @Param("count") Integer count);

    @Modifying
    @Query(value = "update Member m set m.followings=m.followings+:count where m.userId=:id")
    void hitFollwingCount(@Param("id") Long userId, @Param("count") Integer count);

    @Modifying
    @Query(value = "update Member m set m.followers=m.followers-:count where m.userId=:id")
    void hitunFollwersCount(@Param("id") Long userId, @Param("count") Integer count);

    @Modifying
    @Query(value = "update Member m set m.followings=m.followings-:count where m.userId=:id")
    void hitunFollwingCount(@Param("id") Long userId, @Param("count") Integer count);

}

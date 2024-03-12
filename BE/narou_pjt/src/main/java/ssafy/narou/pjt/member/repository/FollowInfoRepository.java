package ssafy.narou.pjt.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ssafy.narou.pjt.member.entity.Follow;

public interface FollowInfoRepository extends JpaRepository<Follow, Integer>, FollowInfoRepositoryCustom {

//    @Query("UPDATE Follow f set f.activated=:isActivated where f.followedMember.userId=:followedId and f.followingMember.userId=:followingId")
//    void update(@Param("followedId") Long followedId, @Param("followingId") Long followingId, @Param("isActivated") boolean isActivated);
}

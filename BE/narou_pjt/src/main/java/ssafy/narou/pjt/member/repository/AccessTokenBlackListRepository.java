package ssafy.narou.pjt.member.repository;

public interface AccessTokenBlackListRepository {

    void save(String accessToken);


}

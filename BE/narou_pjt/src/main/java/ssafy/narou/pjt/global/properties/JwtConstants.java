package ssafy.narou.pjt.global.properties;

public interface JwtConstants {

    String JWT_HEADER_NAME = "Authorization";
    String JWT_PREFIX = "Bearer ";
    String JWT_ISSUER = "Narou";
    String JWT_TOKEN_TYPE = "token-type";
    String JWT_AUTHORITIES = "authorities";
    String JWT_ACCESS_TOKEN = "access-token";
    String JWT_REFRESH_TOKEN = "refresh-token";
}

package ssafy.narou.pjt.article.dto.response;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

@Builder
@AllArgsConstructor
@ToString
@Setter
@Getter
@JsonSerialize
public class LocationInfo {

    private Integer nthDay;
    //일차별 순서
    private Integer order;
    //위도
    private Double latitude;
    //경도
    private Double longitude;
    //시구(서울시 강남구)
    private String address;
    //장소명
    private String location;


}

package ssafy.narou.pjt.global.auth.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseMessage<T> {

    private Boolean success;
    private T data;
    private String message;

    // Exception 설정용 or 정상 처리 반환 static factory method
    public static ResponseMessage<?> of(boolean success, String message){
        ResponseMessage<?> responseMessage = new ResponseMessage<>();
        responseMessage.setSuccess(success);
        responseMessage.setData(null);
        responseMessage.setMessage(message);
        return responseMessage;
    }

    // DTO 반환용 static factory method
    public static <T> ResponseMessage<T> of(boolean success, T data, String message){
        ResponseMessage<T> responseMessage = new ResponseMessage<>();
        responseMessage.setSuccess(success);
        responseMessage.setData(data);
        responseMessage.setMessage(message);
        return responseMessage;
    }
}

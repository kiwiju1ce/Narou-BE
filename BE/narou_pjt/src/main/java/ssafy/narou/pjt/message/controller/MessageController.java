package ssafy.narou.pjt.message.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ssafy.narou.pjt.global.auth.dto.response.ResponseMessage;
import ssafy.narou.pjt.global.validation.NarouUserId;
import ssafy.narou.pjt.message.dto.response.ChatMessageResponse;
import ssafy.narou.pjt.message.dto.request.ChatMessageRequest;
import ssafy.narou.pjt.message.dto.response.ChatRoomResponse;
import ssafy.narou.pjt.message.service.ChatService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MessageController {

    private final ChatService chatService;

    @ResponseBody
    @GetMapping("/api/chatRoom")
    public ResponseEntity<?> chatRoomList(@NarouUserId Long userId){
        List<ChatRoomResponse> list = chatService.findAllChatRoom(userId);
        return ResponseEntity.ok(ResponseMessage.of(true, list, "Success"));
    }

    /**
     * 채팅방의 메시지를 읽는 요청
     * @param userId
     * @param roomName
     * @param separator : page 역할의 index -> 현재까지 보여진 메시지의 입력시간
     * @return
     */
    @ResponseBody
    @GetMapping("/api/chatRoom/{roomName}")
    public ResponseEntity<?> chatMessageList(@NarouUserId Long userId, @PathVariable String roomName,
//                                             @PageableDefault(size = 20) Pageable pageable,
                                             @RequestParam(required = false) LocalDateTime separator) {
        List<ChatMessageResponse> messageList = chatService.getMessageList(roomName, userId, separator);
        return ResponseEntity.ok(ResponseMessage.of(true, messageList, "Success"));
    }

    @MessageMapping("/chat.send/{roomName}")    // 앞에 app 붙여서 사용
    @SendTo("/queue/chat/{roomName}")
    public ChatMessageResponse sendMessage(@DestinationVariable String roomName,
                                           @Header("simpSessionAttributes") Map<String, Object> simpSessionAttributes,
                                           @Payload ChatMessageRequest chatMessageRequest){
        log.info("chat send : {}", roomName);
        return chatService.saveMessage(chatMessageRequest);
    }

    @SubscribeMapping("/chat.register/{roomName}")    // 앞에 app 붙여서 사용
    @SendTo("/queue/chat/{roomName}")
    public ChatMessageResponse registerChatRoom(@DestinationVariable String roomName,
                                 @Header("simpSessionAttributes") Map<String, Object> simpSessionAttributes,
                                 @Payload ChatMessageRequest chatMessageRequest,
                                 @NarouUserId Long userId){
        log.info("chat register : {}", roomName);
        chatService.updateChatRoomInfo(roomName, userId);
        return chatService.saveMessage(chatMessageRequest);
    }
}
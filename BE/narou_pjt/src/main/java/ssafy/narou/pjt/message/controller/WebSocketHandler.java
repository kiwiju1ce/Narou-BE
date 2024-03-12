//package ssafy.narou.pjt.message.controller;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Component;
//import org.springframework.util.StringUtils;
//import org.springframework.web.socket.CloseStatus;
//import org.springframework.web.socket.TextMessage;
//import org.springframework.web.socket.WebSocketMessage;
//import org.springframework.web.socket.WebSocketSession;
//import org.springframework.web.socket.handler.TextWebSocketHandler;
//import ssafy.narou.pjt.message.entity.ChatRoom;
//import ssafy.narou.pjt.message.repository.ChatRoomRepository;
//
//import java.util.Objects;
//
//@Component
//@RequiredArgsConstructor
//public class WebSocketHandler extends TextWebSocketHandler {
//
//    private final ChatRoomRepository chatRoomRepository;
////    private final MemberRepository memberRepository;
//
//    @Override
//    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
//        String roomId = getRoomId(session);
//        chatRoomRepository.createChatRoom(roomId);
//
//        String nickname = getNickname();
//        TextMessage textMessage = new TextMessage(nickname + "님이 입장하였습니다.");
//
//        session.sendMessage(textMessage);
//    }
//
//    @Override
//    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
//        String roomId = getRoomId(session);
//        ChatRoom chatRoom = chatRoomRepository.loadChatRoom(roomId);
//
////        for (WebSocketSession userConnection : chatRoom.getUserList()) {
////            userConnection.sendMessage(message);
////        }
//    }
//
//    @Override
//    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
//        String payload = message.getPayload();
//
//        if (StringUtils.hasText(payload)){
//            String roomId = getRoomId(session);
//            ChatRoom chatRoom = chatRoomRepository.loadChatRoom(roomId);
//
////            for (WebSocketSession userConnection : chatRoom.getUserList()) {
////                userConnection.sendMessage(message);
////            }
//        }
//
//    }
//
//
//    // 실시간 Connection만 관리하는 메서드 -> 방 나가기는 ChatRoom에서,,
//    @Override
//    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
//        String nickname = getNickname();
//        TextMessage textMessage = new TextMessage(nickname + "님이 퇴장하였습니다.");
//        session.sendMessage(textMessage);
//
//        String roomId = getRoomId(session);
//        chatRoomRepository.closeMemberConnection(roomId, session);
//    }
//
//    private String getNickname() {
//        return "test user";
//    }
//
//    private String getRoomId(WebSocketSession session){
//        String uri = Objects.requireNonNull(session.getUri()).toString();
//        String[] split = uri.split("/");
//        return split[split.length-1];
//    }
//}

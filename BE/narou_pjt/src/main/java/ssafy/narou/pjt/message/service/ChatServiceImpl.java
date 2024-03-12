package ssafy.narou.pjt.message.service;

import io.jsonwebtoken.lang.Assert;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssafy.narou.pjt.member.entity.Member;
import ssafy.narou.pjt.message.dto.request.ChatMessageRequest;
import ssafy.narou.pjt.message.dto.response.ChatMessageResponse;
import ssafy.narou.pjt.message.dto.response.ChatRoomResponse;
import ssafy.narou.pjt.message.entity.ChatMessage;
import ssafy.narou.pjt.message.entity.ChatRoom;
import ssafy.narou.pjt.message.entity.MemberToChatRoom;
import ssafy.narou.pjt.message.repository.ChatMessageRepository;
import ssafy.narou.pjt.message.repository.ChatRoomRepository;
import ssafy.narou.pjt.message.repository.MemberChatRoomRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService{

    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final MemberChatRoomRepository memberChatRoomRepository;
//    private final ChatMessageRepositoryCustom chatMessageRepositoryCustom;

    @Override
    public ChatMessageResponse saveMessage(ChatMessageRequest chatMessageRequest) {
        ChatMessage entity = chatMessageRequest.toEntity();
        ChatMessage saved = chatMessageRepository.save(entity);

        return ChatMessageResponse.toDto(saved);
    }

    @Override
    public List<ChatMessageResponse> getMessageList(String chatRoomName, Long userId, LocalDateTime separator) {
        return chatMessageRepository.findChatRoomMessageList(chatRoomName, userId, separator);
    }

    @Override
    @Transactional
    public ChatRoom createChatRoom(String chatRoomName) {
        Optional<ChatRoom> chatRoom = chatRoomRepository.findByRoomName(chatRoomName);
        if (chatRoom.isEmpty()){
            ChatRoom room = ChatRoom.builder()
                    .roomName(chatRoomName).build();
            return chatRoomRepository.save(room);
        }
        return chatRoom.get();
    }

    @Override
    public List<ChatRoomResponse> findAllChatRoom(Long userId) {
        return memberChatRoomRepository.findAllChatRoomById(userId);
    }

    /**
     * roomName이
     * @param roomName
     * @param userId
     */
    @Override
    @Transactional
    public void updateChatRoomInfo(String roomName, Long userId) {
        Assert.notNull(roomName, "roomName should not be null to update room info");
        Assert.notNull(userId, "userId should not be null to update room info");

        ChatRoom room = chatRoomRepository.findByRoomName(roomName).orElseGet(() -> {
            String[] userIds = roomName.split("\\|");
            String revertRoomName = userIds[1] + "|" + userIds[0];
            return chatRoomRepository.findByRoomName(revertRoomName).orElseGet(() -> createChatRoom(roomName));
        });
        Member member = Member.builder()
                .userId(userId).build();

        MemberToChatRoom registration = MemberToChatRoom.builder()
                .chatRoom(room)
                .member(member)
                .build();
        memberChatRoomRepository.save(registration);
    }

}

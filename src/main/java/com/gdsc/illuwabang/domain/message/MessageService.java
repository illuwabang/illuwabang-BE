package com.gdsc.illuwabang.domain.message;

import com.gdsc.illuwabang.domain.room.Room;
import com.gdsc.illuwabang.domain.room.RoomRepository;
import com.gdsc.illuwabang.domain.user.User;
import com.gdsc.illuwabang.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@RequiredArgsConstructor
@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;

    public List<MessageRoomDto> viewAllByMessageRoom(User user){
        List<MessageRoomDto> messageRooms = messageRepository.findAllMessageRoom(user);
        System.out.println(messageRooms);
        return messageRooms;
    }

    public void sendRoomMessage(User sender, String content, Long roomId){
        Optional<Room> existRoom = roomRepository.findById(roomId);
        if(existRoom.isPresent()){
            Room room = existRoom.get();
            Message message = new Message(sender, content, room, room.getUserId(), LocalDateTime.now());
            System.out.println(message.getContent());
            messageRepository.save(message);
        }
    }
    public void sendMessage(User user, String content, Long roomId, Long receiverId){
        Optional<Room> existRoom = roomRepository.findById(roomId);
        Optional<User> existReceiver = userRepository.findById(receiverId);
        if(existRoom.isPresent() && existReceiver.isPresent()){
            Room room = existRoom.get();
            User receiver = existReceiver.get();
            Message message = new Message(user, content, room,  receiver, LocalDateTime.now());
            System.out.println(message.getContent());
            messageRepository.save(message);
        }
    }

    public OneMessageRoomDto viewMessageRoom(User user, Long roomId, Long otherUserId){
        Optional<Room> existRoom = roomRepository.findById(roomId);
        Optional<User> existOtherUser = userRepository.findById(otherUserId);
        if(existRoom.isPresent() && existOtherUser.isPresent()){
            Room room = existRoom.get();
            User otherUser = existOtherUser.get();
            String thumbnail = (room.getImageUrl() != null) ? room.getImageUrl().getThumbnail() : "no_image";
            OneMessageRoomDto.OpponentUser opponentUser = new OneMessageRoomDto.OpponentUser(user.getId(),user.getName());
            OneMessageRoomDto.RoomInfo roomInfo = new OneMessageRoomDto.RoomInfo(room.getId(),thumbnail,
                    room.getType(),room.getDeposit(),room.getRent(), room.getRoadAddress(), room.getFloor(),
                    room.getStartDate(), room.getEndDate());
            messageRepository.updateAllByMessagesStatus(user, room, otherUser);
            List<MessageDetailDto> message = messageRepository.findAllMessagesBetweenUsersInRoom(user, room, otherUser);
            return new OneMessageRoomDto(opponentUser, roomInfo, message);
        }
        else {
            return null;
        }
    }

    public void deleteMessage(User user, Long messageId){
        messageRepository.deleteMessageBySenderIdAndId(user, messageId);
    }
}

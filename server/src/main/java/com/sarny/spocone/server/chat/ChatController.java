package com.sarny.spocone.server.chat;

import com.sarny.spocone.publicclasses.chat.ChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Wojciech Makiela
 */
@RestController
class ChatController {

    private ChatService chatService;

    @Autowired
    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping(path = "/chat/{gameId}/{playerId}/{language}")
    private ResponseEntity<?> getChatMessages(@PathVariable int playerId, @PathVariable int gameId, @PathVariable String language) {
        // TODO messages are only in English. Add Multiple languages support

        List<String> chatMessagesAsStrings = chatService.getChatMessagesAsStrings(playerId, gameId);
        return new ResponseEntity<>(chatMessagesAsStrings, HttpStatus.OK);

    }

    @PostMapping(path = "/chat")
    private ResponseEntity<?> postNewChatMessage(@RequestBody ChatMessage message) {
        // TODO messages are only in English. Add Multiple languages support
        try {
            List<String> chatMessagesAsStrings = chatService.putNewMessage(message);
            return new ResponseEntity<>(chatMessagesAsStrings, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}

package com.example.service;

import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private AccountRepository accountRepository;
    
    public Message createMessage(Message message) {
        if (message.getMessageText() == null || message.getMessageText().isBlank()
                || message.getMessageText().length() > 255
                || message.getPostedBy() == null) {
            return null;
        }
        if (!accountRepository.existsById(message.getPostedBy())) {
        return null;
    }
        return messageRepository.save(message);
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public Message getMessageById(int id) {
        return messageRepository.findById(id).orElse(null);
    }

    public boolean deleteMessageById(int id) {
        if (messageRepository.existsById(id)) {
            messageRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Message updateMessage(int id, String newText) {
        Optional<Message> optionalMessage = messageRepository.findById(id);
        if (optionalMessage.isPresent()) {
            Message message = optionalMessage.get();
            message.setMessageText(newText);
            return messageRepository.save(message);
        }
        return null;
    }

    public List<Message> getMessagesByAccountId(Integer accountId) {
        return messageRepository.findByPostedBy(accountId);
    }

    public int deleteMessageReturningCount(int id) {
    if (messageRepository.existsById(id)) {
        messageRepository.deleteById(id);
        return 1;
    }
    return 0;
}

    public int updateMessageText(int id, String newText) {
    Optional<Message> optionalMessage = messageRepository.findById(id);
    if (optionalMessage.isPresent()) {
        Message message = optionalMessage.get();
        message.setMessageText(newText);
        messageRepository.save(message);
        return 1;
    }
    return 0;
}
}
package Service;

import Model.Message;
import DAO.MessageDAO;
import java.util.*;

public class MessageService {
    MessageDAO messageDAO;

    public MessageService(){
        messageDAO = new MessageDAO();
    }

    public MessageService(MessageDAO messageDAO){
        this.messageDAO = messageDAO;
    }

    public Message addMessage(Message message, Boolean exists){
        if(message.getMessage_text() != "" && message.getMessage_text().length() < 255 && exists){
            return messageDAO.insertMessage(message);
        }
        return null;
    }

    public List<Message> getMessage(){
        return messageDAO.getMessages();
    }

    public Message getMessageById(int id){
        return messageDAO.getMessage(id);
    }

    public Message deleteMessageById(int id){
        return messageDAO.deleteMessage(id);
    }

    public Message updateMessage(String newMessageText, int messageId){
        if(newMessageText != "" && newMessageText.length() < 255){
            return messageDAO.updateMessageDAO(messageId, newMessageText);
        }
        return null;
    }
}

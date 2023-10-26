package Service;

import Model.Message;
import DAO.MessageDAO;

public class MessageService {
    MessageDAO messageDAO;

    public MessageService(){
        messageDAO = new MessageDAO();
    }

    public MessageService(MessageDAO messageDAO){
        this.messageDAO = messageDAO;
    }

    public Message addMessage(Message message){
        if(message.getMessage_text() != "" && message.getMessage_text().length() < 255){
            return messageDAO.insertMessage(message);
        }
        return null;
    }
}

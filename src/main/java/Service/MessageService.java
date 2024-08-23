package Service;

import java.util.List;

import DAO.AccountDAO;
import DAO.MessageDAO;
import Model.Message;

public class MessageService {
    private MessageDAO messageDAO;
    private AccountDAO accountDAO = new AccountDAO();


    public MessageService(){
        this.messageDAO = new MessageDAO();
    }

    public MessageService(MessageDAO messageDAO){
        this.messageDAO = messageDAO;
    }


    public Message createMessage(Message message){
    
        if (
            !message.getMessage_text().isBlank() && 
            message.getMessage_text().length() <= 255 && 
            accountDAO.getAccountByUserId(message.getPosted_by()) != null 
            ) {
            return messageDAO.createMessage(message);
        }
        return null;
    }

    public List<Message> getAllMessages(){
        return messageDAO.getAllMessages();
    }

    public Message getMessageById(int messageId){
        if (messageId == 0) {
            return null;
        }

        return messageDAO.getMessageById(messageId);
    }


    public Message updateMessage(Message message){
        if (
            !message.getMessage_text().isBlank() && 
            message.getMessage_text().length() < 256 && 
            accountDAO.getAccountByUserId(message.getPosted_by()) != null
            ) {
            
            return messageDAO.updateMessage(message);
        }
        return null;
    }

    public Message deleteMessage(Message message){
        if (messageDAO.getMessageById(message.getMessage_id()) != null) {
            messageDAO.deleteMessage(message);
        }

        return null;
    }
    
}

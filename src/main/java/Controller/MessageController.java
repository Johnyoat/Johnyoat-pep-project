package Controller;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Message;
import Service.MessageService;
import io.javalin.http.Context;

public class MessageController {

    private MessageService messageService;
    private ObjectMapper mapper = new ObjectMapper();


    public MessageController(){
        this.messageService = new MessageService();
    }

    public void createMessage(Context context) throws JsonProcessingException  {
        Message message = mapper.readValue(context.body(), Message.class);
        Message createdMessage = messageService.createMessage(message);

        if (createdMessage == null) {
            context.status(400);
        }else{
            context.status(200).json(createdMessage);
        }

       
    }


    public void updateMessage(Context context) throws JsonProcessingException  {
        Message messageBody = mapper.readValue(context.body(), Message.class);
       
        int messageID = Integer.parseInt(context.pathParam("message_id"));
        Message message = messageService.getMessageById(messageID);
        if (message == null) {
            context.status(400);
            return;
        }
       
        message.setMessage_text(messageBody.getMessage_text());
        Message updatedMessage = messageService.updateMessage(message);

        if (updatedMessage == null) {
            context.status(400);
        }else{
            context.status(200).json(updatedMessage);
        }

        
    }
    

    public void getAllMessages(Context context)   {
        List<Message> messages = messageService.getAllMessages();

        if (messages == null) {
            context.status(200);
        }else{
            context.status(200).json(messages);
        }

       
    }


    public void getMessageById(Context context){
        int messageId = Integer.parseInt(context.pathParam("message_id"));
        Message message = messageService.getMessageById(messageId);
        if (message == null) {
            context.status(200);
        }else{
            context.status(200).json(message);
        }
    }


    public void deleteMessageById(Context context){
        int messageId = Integer.parseInt(context.pathParam("message_id"));
        
        Message message = messageService.getMessageById(messageId);

        if (message == null) {
            context.status(200);
        }else{
            messageService.deleteMessage(message);
            context.status(200).json(message);
        }

       
    }
}

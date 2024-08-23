package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Model.Message;
import Util.ConnectionUtil;

public class MessageDAO {

    Connection connection = ConnectionUtil.getConnection();


    public Message createMessage(Message message){

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("insert into message (posted_by,message_text,time_posted_epoch) values(?,?,?)",Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, message.posted_by);
            preparedStatement.setString(2, message.message_text);
            preparedStatement.setLong(3, message.time_posted_epoch);
            preparedStatement.executeUpdate();

            ResultSet generatedKey = preparedStatement.getGeneratedKeys();

            if (generatedKey.next()) {
                return new Message(
                (int) generatedKey.getLong(1),
                message.getPosted_by(),
                message.getMessage_text(),
                message.getTime_posted_epoch()
                );
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public Message getMessageById(int messageID){

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from message where message_id = ?");
            preparedStatement.setInt(1,messageID );

            ResultSet results = preparedStatement.executeQuery();
            
            
            if (results.next()) {
                return new Message(
                    results.getInt("message_id"),
                    results.getInt("posted_by"),
                    results.getString("message_text"),
                    results.getLong("time_posted_epoch")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }



        return null;
    }

    public List<Message> getAllMessages(){

        List<Message> messages = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from message");

            ResultSet results = preparedStatement.executeQuery();
            
            
            if (results.next()) {
                messages.add(new Message(
                    results.getInt("message_id"),
                    results.getInt("posted_by"),
                    results.getString("message_text"),
                    results.getLong("time_posted_epoch")
                ));
            }

            return messages;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    public List<Message> getAllMessagesByUserId(int userId){

        List<Message> messages = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from message where posted_by = ?");
            preparedStatement.setInt(1, userId);
            ResultSet results = preparedStatement.executeQuery();
            
            
            if (results.next()) {
                messages.add(new Message(
                    results.getInt("message_id"),
                    results.getInt("posted_by"),
                    results.getString("message_text"),
                    results.getLong("time_posted_epoch")
                ));
            }

            return messages;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public Message updateMessage(Message message){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("update message set posted_by = ?,message_text = ?,time_posted_epoch = ? where message_id = ?");
            preparedStatement.setInt(1, message.getPosted_by());
            preparedStatement.setString(2, message.message_text);
            preparedStatement.setLong(3, message.getTime_posted_epoch());
            preparedStatement.setLong(4, message.getMessage_id());
            
            System.out.println(preparedStatement);
             int update = preparedStatement.executeUpdate();

            if (update > 0) {
              return message;
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public Message deleteMessage(Message message){

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("delect from message where message_id = ?");
            preparedStatement.setInt(1, message.message_id);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.rowDeleted()) {
                return message;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
    
}

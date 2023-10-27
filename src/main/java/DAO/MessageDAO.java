package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;


import Model.Message;
import Util.ConnectionUtil;

public class MessageDAO {
    public Message insertMessage(Message message){
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "Insert into message(posted_by, message_text, time_posted_epoch) values (?, ?, ?);";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setInt(1, message.getPosted_by());
            preparedStatement.setString(2, message.getMessage_text());
            preparedStatement.setLong(3, message.getTime_posted_epoch());

            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();

            if(rs.next()){
                int generated_message_id = (int) rs.getLong(1);
                return new Message(generated_message_id, message.getPosted_by(), message.getMessage_text(), message.getTime_posted_epoch());
            }
            
        }
        catch(SQLException e){
                System.out.println(e.getMessage());
            }


        return null;
    }

    public List<Message> getMessages(){
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messageList = new ArrayList<Message>();
        try{
            String sql = "Select * from message;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            
            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){
                messageList.add(new Message(rs.getInt("message_id"), 
                rs.getInt("posted_by"), rs.getString("message_text"), 
                rs.getLong("time_posted_epoch")));
            }
            return messageList;
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Message getMessage(int id){
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "Select * from message where message_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()){
                return new Message(rs.getInt("message_id"), 
                rs.getInt("posted_by"), rs.getString("message_text"), 
                rs.getLong("time_posted_epoch"));
            }
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Message deleteMessage(int id){
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "delete from message where message_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            Message messageExists = getMessage(id);

            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            if(messageExists != null){
                return messageExists;
            }
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Message updateMessageDAO(int messageId, String messageText){
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "update message set message_text = ? where message_id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, messageText);
            preparedStatement.setInt(2, messageId);
            preparedStatement.executeUpdate();
            Message messageExists = getMessage(messageId);
            if(messageExists != null){
                return messageExists;
            }
        }catch(SQLException e){
                System.out.println(e.getMessage());
            }
        return null;
    }

    public List<Message> getMessageByAccountID(int accountID){
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messageList = new ArrayList<Message>();
        try{
            String sql = "Select * from message where posted_by = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, accountID);
            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){
                messageList.add(new Message(rs.getInt("message_id"), 
                rs.getInt("posted_by"), rs.getString("message_text"), 
                rs.getLong("time_posted_epoch")));
            }
            return messageList;
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }

        return null;
    }
}

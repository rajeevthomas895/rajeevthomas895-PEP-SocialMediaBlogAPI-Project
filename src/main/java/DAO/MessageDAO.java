package DAO;
import Model.Message;
import Util.ConnectionUtil;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MessageDAO {
  public Message insertMessage(Message message){
    try {
      Connection conn=ConnectionUtil.getConnection();
      String sql="insert into message (posted_by,message_text,time_posted_epoch) values (?,?,?);";
      PreparedStatement ps= conn.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);
      ps.setInt(1,message.getPosted_by());
      ps.setString(2, message.getMessage_text());
      ps.setLong(3,message.getTime_posted_epoch());
      ps.executeUpdate();
      ResultSet rs=ps.getGeneratedKeys();
      if(rs.next()){
        int id=rs.getInt(1);
        return new Message(id,message.getPosted_by(),message.getMessage_text(),message.getTime_posted_epoch());
      }
    } 
    catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }
  public List<Message> getAllMessages(){
    List<Message> messages=new ArrayList<>();
    try
    {
      Connection conn=ConnectionUtil.getConnection();
      String sql="select * from message;";
      PreparedStatement s=conn.prepareStatement(sql);
      ResultSet rs=s.executeQuery();
      while (rs.next()) {
        Message message=new Message(rs.getInt("message_id"),rs.getInt("posted_by"),rs.getString("message_text"),rs.getLong("time_posted_epoch"));
        messages.add(message);
      }
    }
      catch(Exception e){
        e.printStackTrace();
      }
      return messages;
  }
  public Message getMessageById(int id){
    try {
      Connection conn=ConnectionUtil.getConnection();
      String sql="select * from message where message_id = ?;";
      PreparedStatement ps=conn.prepareStatement(sql);
      ps.setInt(1,id);
      ResultSet rs= ps.executeQuery();
      if(rs.next()){
        return new Message(rs.getInt("message_id"),rs.getInt("posted_by"),rs.getString("message_text"),rs.getLong("time_posted_epoch"));
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }
  public Message deleteMessageById(int id){
    try{
      Connection conn=ConnectionUtil.getConnection();
      Message message=getMessageById(id);
      if(message==null){
        return null;
      }
      String sql="delete from message where message_id = ?;";
      PreparedStatement ps=conn.prepareStatement(sql);
      ps.setInt(1,id);
      ps.executeUpdate();
      return message;
    }catch(Exception e){
      e.printStackTrace();
    }
    return null;
  }
  public Message updateMessageById(int id,String newText){
    try{
      Connection conn=ConnectionUtil.getConnection();
      String sql="update message set message_text = ? where message_id = ?;";
      PreparedStatement ps=conn.prepareStatement(sql);
      ps.setString(1, newText);
      ps.setInt(2, id);
      int rowsUpdated=ps.executeUpdate();
      if(rowsUpdated>0){
        return getMessageById(id);
      }
    }catch(Exception e){
      e.printStackTrace();
    }
    return null;
  }
public List<Message> getMessagesByUser(int account_id){
  List<Message> messages=new ArrayList<>();
  try{
    Connection conn=ConnectionUtil.getConnection();
    String sql="select * from message where posted_by = ?;";
    PreparedStatement ps=conn.prepareStatement(sql);
    ps.setInt(1, account_id);
    ResultSet rs=ps.executeQuery();
    while(rs.next()){
      Message message=new Message(rs.getInt("message_id"),rs.getInt("posted_by"),rs.getString("message_text"),rs.getLong("time_posted_epoch"));
      messages.add(message);
    }
    
  }catch(Exception e){
    e.printStackTrace();
  }
  return messages;
  }
}


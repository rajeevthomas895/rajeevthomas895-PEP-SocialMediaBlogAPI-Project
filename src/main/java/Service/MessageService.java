package Service;
import java.util.List;

import DAO.MessageDAO;
import Model.Message;
public class MessageService {
  private MessageDAO messageDAO;
  public MessageService(){
    messageDAO=new MessageDAO();
  }
  public MessageService(MessageDAO messageDAO){
    this.messageDAO=messageDAO;
  }
  public Message createMessage(Message message){
    return messageDAO.insertMessage(message);
  }
  public List<Message> getAllMessages(){
    return messageDAO.getAllMessages();
  }
  public Message getMessageById(int id){
    return messageDAO.getMessageById(id);
  }
  public Message deleteMessageById(int id){
    return messageDAO.deleteMessageById(id);
  }
  public Message updateMessageById(int id,String newText){
    return messageDAO.updateMessageById(id, newText);
  }
  public List<Message> getMessagesByUser(int account_id){
    return messageDAO.getMessagesByUser(account_id);
  }
}

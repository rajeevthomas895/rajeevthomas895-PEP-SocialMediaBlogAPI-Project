package Controller;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.List;


/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    AccountService accountService=new AccountService();
    MessageService messageService=new MessageService();
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register",this::createAccount); 
        app.post("/login",this::userLogin); 
        app.post("/messages",this::createMessage); 
        app.get("/messages",this::getAllMessages); 
        app.get("/messages/{message_id}",this::getMessageById); 
        app.delete("/messages/{message_id}", this::deleteMessageById); 
        app.patch("/messages/{message_id}",this::updateMessageById); 
        app.get("/accounts/{account_id}/messages",this::getMessagesByUser);
        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void createAccount(Context ctx) {
        Account account = ctx.bodyAsClass(Account.class);
        //username check
        if(account.getUsername()==null||account.getUsername().isBlank()){
            ctx.status(400);
            return;
        }
        //password check
        if(account.getPassword()==null||account.getPassword().length()<4){
            ctx.status(400);
            return;
        }
        //check if username exists
        if(accountService.getAccountByUsername(account.getUsername())!=null){
            ctx.status(400);
            return;
        }
        //Insert into DB
        Account savedAccount=accountService.register(account);
        ctx.json(savedAccount);
    }
    private void userLogin(Context ctx){
        Account account=ctx.bodyAsClass(Account.class);
        Account dbAccount=accountService.getAccountByUsername(account.getUsername());

        //check if user exits
        if(dbAccount==null){
            ctx.status(401);
            return;
        }
        //check password
        if(!dbAccount.getPassword().equals(account.getPassword())){
            ctx.status(401);
            return;
        }
        ctx.json(dbAccount);
    }
    private void createMessage(Context ctx){
        Message message=ctx.bodyAsClass(Message.class);
        //check msg_text if null or chars>255
        if(message.getMessage_text() == null 
            || message.getMessage_text().isBlank() 
            || message.getMessage_text().length() > 255) 
        {
            ctx.status(400);
            return;
        }
        //check posted_by refers to a real, existing user
        if(accountService.getAccountById(message.getPosted_by())==null){
            ctx.status(400);
            return;
        }
        Message savedMessage=messageService.createMessage(message);
        ctx.json(savedMessage);
    }
    private void getAllMessages(Context ctx){
        List<Message> messages=messageService.getAllMessages();
        ctx.json(messages);
    }
    private void getMessageById(Context ctx){
        int id=Integer.parseInt(ctx.pathParam("message_id"));
        Message message=messageService.getMessageById(id);
        if(message==null){
            ctx.status(200);
            return;
        }
        ctx.json(message);
    }
    private void deleteMessageById(Context ctx){
        int id=Integer.parseInt(ctx.pathParam("message_id"));
        Message deletedMessage=messageService.deleteMessageById(id);
        if(deletedMessage==null){
            ctx.status(200);
            return;
        }
        ctx.json(deletedMessage);
    }
    private void updateMessageById(Context ctx){
        int id=Integer.parseInt(ctx.pathParam("message_id"));
        Message message=ctx.bodyAsClass(Message.class);
        if(message.getMessage_text()==null||message.getMessage_text().isBlank()||message.getMessage_text().length()>255){
            ctx.status(400);
            return;
        }
        Message updatedMessage=messageService.updateMessageById(id, message.getMessage_text());
        if(updatedMessage == null){
            ctx.status(400);
            return;
        }
        ctx.json(updatedMessage);
    }
    private void getMessagesByUser(Context ctx){
        int account_id=Integer.parseInt(ctx.pathParam("account_id"));
        List<Message> messages=messageService.getMessagesByUser(account_id);
        ctx.json(messages);
    }
}

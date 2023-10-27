package Controller;

import io.javalin.Javalin;
import io.javalin.http.Context;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Service.AccountService;

import Model.Message;
import Service.MessageService;
import java.util.*;

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
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.get("example-endpoint", this::exampleHandler);
        app.post("/register", this::registerHandler);
        app.post("/login", this::loginHandler);
        app.post("/messages", this::messagesHandler);
        app.get("/messages", this::getMessagesHandler);
        app.get("/messages/{message_id}", this::getMessageByIdHandler);
        app.delete("/messages/{message_id}", this::deleteMessageByIdHAndler);
        app.patch("/messages/{message_id}", this::updateMessageHandler);


        return app;
    }
    AccountService accountService = new AccountService();
    MessageService messageService = new MessageService();

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }

    private void registerHandler(Context context) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(context.body(), Account.class);
        Account addedAccount = accountService.addAccount(account);
        if(addedAccount == null){
            context.status(400);
        }
        else{
            context.json(mapper.writeValueAsString(addedAccount));
            context.status();
        }
    }

    private void loginHandler(Context context) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(context.body(), Account.class);
        Account addedAccount = accountService.login(account);
        if(addedAccount != null){
            context.json(mapper.writeValueAsString(addedAccount));
            context.status(200);
        }
        else{
            context.status(401);
        }
    }

    private void messagesHandler(Context context) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(context.body(), Message.class);

        Boolean exists = accountService.checkIfAccountExists(message.getPosted_by());

        Message addedMessage = messageService.addMessage(message, exists);

        if(addedMessage != null){
            context.json(mapper.writeValueAsString(addedMessage));
            context.status(200);
        }
        else{
            context.status(400);
        }
    }

    private void getMessagesHandler(Context context)throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        List<Message> messageList = new ArrayList<Message>();
        messageList = messageService.getMessage();
        
        context.json(mapper.writeValueAsString(messageList));
        context.status();
    }

    private void getMessageByIdHandler(Context context) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        int messageId = Integer.parseInt(context.pathParam("message_id"));
        Message message = messageService.getMessageById(messageId);
        if(message != null){
            context.json(mapper.writeValueAsString(message));
            
        }
        context.status();
    }

    private void deleteMessageByIdHAndler(Context context)throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        int messageId = Integer.parseInt(context.pathParam("message_id"));
        Message message = messageService.deleteMessageById(messageId);
        if(message != null){
            context.json(mapper.writeValueAsString(message));
            
        }
        context.status(200);
    }

    private void updateMessageHandler(Context context)throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        int messageId = Integer.parseInt(context.pathParam("message_id"));
        Message newMessageText = mapper.readValue(context.body(), Message.class);
        Message message = messageService.updateMessage(newMessageText.getMessage_text(), messageId);
        if(message != null){
            context.json(mapper.writeValueAsString(message));
            context.status(200);
        }
        else{
            context.status(400);
        }
    }
}
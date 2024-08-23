package Controller;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;
import Service.AccountService;
import io.javalin.http.Context;

public class AccountController {

  AccountService accountService;
  ObjectMapper mapper = new ObjectMapper();

    public AccountController(){
        this.accountService = new AccountService();
    }

    public void registerUser(Context context) throws  JsonProcessingException {

        Account account = mapper.readValue(context.body(), Account.class);
        Account createAccount = accountService.createUser(account);
       
        if (createAccount == null) {
            context.status(400);
        }else{
            context.status(200).json(createAccount);
        }
       
    }

    public void login(Context context) throws  JsonProcessingException {

        Account account = mapper.readValue(context.body(), Account.class);
        Account authenticatedUser = accountService.logIn(account);
       
        if (authenticatedUser == null) {
            context.status(401);
        }else{
            context.status(200).json(authenticatedUser);
        }

        
    }

    public void getUserMessages(Context context) throws  JsonProcessingException{
        int accountId = Integer.parseInt(context.pathParam("account_id"));
         List<Message> messages = accountService.getUserMessages(accountId);
         if (messages == null) {
            context.status(200);
         }else{
            context.status(200).json(messages);
         }
        
        
    }
}

package Service;

import java.util.List;

import DAO.AccountDAO;
import DAO.MessageDAO;
import Model.Account;
import Model.Message;

public class AccountService {
    AccountDAO acccountDAO;
    MessageDAO messageDAO;


    public AccountService(){
        this.acccountDAO = new AccountDAO();
        this.messageDAO = new MessageDAO();
    }


    public AccountService(AccountDAO accountDao){
        this.acccountDAO = accountDao;
    }


    public Account createUser(Account account){ 
        if (
            !account.getUsername().isBlank() && 
            account.getPassword().length() > 4 && 
            acccountDAO.getAccountByUserName(account.getUsername()) == null
        ) {
           return acccountDAO.createAccount(account);
          
        }
        return null;
    }


    public Account logIn(Account account){
        return this.acccountDAO.logIn(account.getUsername(), account.getPassword());
    }

    public List<Message> getUserMessages(int userId){
        return this.messageDAO.getAllMessagesByUserId(userId);
    }
    
}

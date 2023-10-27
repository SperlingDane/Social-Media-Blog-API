package Service;

import Model.Account;
import DAO.AccountDAO;

public class AccountService {
    AccountDAO accountDAO;

    public AccountService(){
        accountDAO = new AccountDAO();
    }

    public AccountService(AccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }

    public Account addAccount(Account account){
        boolean exists = accountDAO.accountExists(account.getUsername());
        if(account.getUsername() != "" && account.getPassword().length() > 4 && !exists){
            return accountDAO.insertAccount(account);
        }
        return null;
    }

    public Account login(Account account){
        return accountDAO.loginAccount(account);
    }

    public Boolean checkIfAccountExists(int accountId){
        return accountDAO.accountExists(accountId);
    }

    public Boolean checkIfAccountExists(String username){
        return accountDAO.accountExists(username);
    }
}

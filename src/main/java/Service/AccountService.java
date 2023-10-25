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
        if(account.getUsername() != "" && account.getPassword().length() > 4){
            return accountDAO.insertAccount(account);
        }
        return null;
    }

    public Account login(Account account){
        return accountDAO.loginAccount(account);
    }
}

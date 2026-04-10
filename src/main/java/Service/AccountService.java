package Service;
import Model.Account;
import DAO.AccountDAO;

public class AccountService {
  private AccountDAO accountDAO;
  public AccountService(){
    accountDAO=new AccountDAO();
  }
  public AccountService(AccountDAO accountDAO){
    this.accountDAO=accountDAO;
  }
  public Account register(Account account){
    return accountDAO.insertAccount(account);
  }
  public Account getAccountByUsername(String username){
    return accountDAO.getAccountByUsername(username);
  }
  public Account getAccountById(int id){
    return accountDAO.getAccountById(id);
  }
}

package models;
import java.util.ArrayList;

public class Customer extends User {

    private ArrayList<Account> accounts = new ArrayList<>();

    public Customer(String id, String firstName, String lastName, String password, String role){
        super(id, firstName, lastName, password, role);
    }

    public void addAccount(Account newAccount){
        accounts.add(newAccount);
    }

    public ArrayList<Account> getAccounts(){
        return accounts;
    }

}

package sample;

public class Account {
    private String email = "";
    private String password ="";
    private String name = "";
    private String phoneNr = "";
    private int accountID = 0;
    public access accessType;

    public enum access {
        Admin, Employee, Customer
    }

    private static Account single_instance = null;

    private Account(){}

    public static Account getInstance(){
        if (single_instance==null){
            single_instance=new Account();
        }
        return single_instance;
    }


    public String getEmail() {
        return email;
    }


    public String getName() {
        return name;
    }

    public String getPhone() {
        return phoneNr;
    }

    public int getAccountID() {
        return accountID;
    }

    public access getAccessType() {
        return accessType;
    }

    public void setAccessType(access a) {
        this.accessType = a;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword(){
        return this.password;
    }

    public void setName(String name) {
        this.name = name;

    }

    public void setPhoneNr(String phoneNr) {
        this.phoneNr = phoneNr;
    }

    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }

    public Account(String email, String password, String name, String phoneNr, int accountID, access accessType) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phoneNr = phoneNr;
        this.accountID = accountID;
        this.accessType = accessType;

    }
}

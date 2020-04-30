package sample;

public class Account {
    private String email;
    private String password;
    private String name;
    private String phoneNr;
    private int accountID = 0;
    private int accessType;
    private boolean loginStatus;


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

    public int getAccessType() {
        return accessType;
    }

    public void setAccessType(int a) {
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

    public Account(int accountID, String email, String password, String name, String phoneNr,boolean loginStatus, int accessType) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phoneNr = phoneNr;
        this.accountID = accountID;
        this.accessType = accessType;
        this.loginStatus = loginStatus;

    }

    @Override
    public String toString() {
        return this.email + ", " + this.password + ", " + this.name;
    }
}

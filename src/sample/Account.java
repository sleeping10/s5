package sample;

public class Account {
    private String email;
    private String password;
    private String name;
    private String phoneNr;
    private int accountID;
    public access accessType;

    enum access {
        Admin, Employee, Customer
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

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
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

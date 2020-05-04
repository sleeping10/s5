package sample;

import java.io.Serializable;

public class Account implements Serializable {
    private String email;
    private String password;
    private String name;
    private String phoneNr;
    private int accountID;
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
    public void setPassword(String password) {
        this.password = password;
    }
    public String getPassword(){
        return this.password;
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
}

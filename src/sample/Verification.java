package sample;

public abstract class Verification {

    public boolean verifyAccount(String email, String pass, String phone){
        boolean status = false;

        if(email.contentEquals("")){


        }

        if(DBC.getInstance().verifyAccount(email, pass, phone)){
            status = true;
        }
        return status;
    }
}

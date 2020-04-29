package sample;

public abstract class Verification {

    public boolean verifyAccount(String email, String pass, String phone){
        boolean status = false;

        if(email.contentEquals("") || pass.contentEquals("")){
            System.out.println("please type in something");
            status = false;
        }else{
            if(DBC.getInstance().verifyAccount(email, pass, phone)){
                status = true;
            }
        }
        return status;
    }
}

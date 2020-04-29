package sample;

public abstract class Verification {

    public int verifyAccount(String email, String pass, String phone){
        int status;

        if (pass.length() > 8 || pass.length() < 4){
            status = 2;
        }else if(email.contentEquals("") || pass.contentEquals("")){
            status = 3;
        }else{
            if(DBC.getInstance().verifyAccount(email, pass, phone)){
                status = 1;
            }else{
                status = 0;
            }
        }
        System.out.println("DEBUG: Verification status: " + status);
        return status;
    }
}

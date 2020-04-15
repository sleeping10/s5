package sample;

public class Verification {
    private String email, pass;

    public boolean verifyUser(String email, String pass){
        boolean verified = false;
        this.email = email;
        this.pass = pass;

        if (verifyInput()){
            verified = true;
        }
        else{
            verified = false;
        }

        return verified;
        // Check if email already exists in DB (or array for testing purpose)

        //DB.checkUser
    }

    private boolean verifyInput() {
        boolean ver = true;
        //Verify input, check that inputs arent empty

        if (email.contains("") || pass.contains("")){
            ver = false;
        }

        return ver;
    }
}

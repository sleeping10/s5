package sample;

public class Verification {
    private String email, pass;

    public boolean verifyUser(String email, String pass){
        boolean verified = true;
        this.email = email;
        this.pass = pass;

        if (verifyInput()){
            return verified;
        }
        else{
            return verified = false;
        }

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

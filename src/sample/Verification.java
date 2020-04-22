package sample;

public abstract class Verification {
    private String email, pass;

    public boolean verifyEmail(String email){
        boolean verified = true;
        this.email = email;

        return verified;
        // Check if email already exists in DB (or array for testing purpose)

        //DB.checkUser
    }
}

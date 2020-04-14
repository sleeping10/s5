package sample;

public class Verification {
    private String email, pass;

    public boolean verifyUser(String email, String pass){
        this.email = email;
        this.pass = pass;

        verifyInput();

        // Check if email already exists in DB (or array for testing purpose)

        return true;
    }

    private boolean verifyInput() {
        boolean ver = true;
        //Verify input, check that textfields arent empty

        return ver;
    }
}

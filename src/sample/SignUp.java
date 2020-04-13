package sample;

public class SignUp extends Verification {

    public void signup(String email, String pass, String phoneNr, String name){

        if (verifyUser(email, pass) && verifyDetails(phoneNr, name)){
            //create new account here
        }

    }

    private boolean verifyDetails(String phoneNr, String name){
        return true;
    }
    //sign up class
}

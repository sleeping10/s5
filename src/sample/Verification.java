package sample;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Verification {

    public static final Pattern EMAIL_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static int verifyAccount(String email, String pass, String phone){
        int status;

        if(email.contentEquals("") || pass.contentEquals("")){
            status = 3;
        }
        else if (pass.length() > 8 || pass.length() < 4){
            status = 2;
        }
        else if (!validate(email)){
            status = 4;
        }
        else{
            if(DBC.getInstance().verifyAccount(email, pass, phone)){
                status = 1;
            }else{
                status = 0;
            }
        }
        return status;
    }

    public static boolean validate(String emailStr) {
        Matcher matcher = EMAIL_REGEX.matcher(emailStr);
        return matcher.find();
    }
}

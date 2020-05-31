package sample;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Verification {

    private final static Pattern EMAIL_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    private final static Pattern PHONE_REGEX = Pattern.compile("^[0-9]{9,10}$");
    private final static Pattern LICENSE_REGEX = Pattern.compile("(?=.{6}$)[A-öa-ö]{3}[0-9]{2}[A-öa-ö0-9]");

    public static int verifyAccount(String email, String pass, String phone){
        int status;

        if(email.contentEquals("") || pass.contentEquals("")){
            status = 3;
        } else if (pass.length() > 15 || pass.length() < 4){
            status = 2;
        } else if (!validateEmail(email)){
                status = 4;
        } else if (!validatePhone(phone)){
                status = 5;
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

    public static boolean validateEmail(String emailStr) {
        Matcher matcher = EMAIL_REGEX.matcher(emailStr);
        return matcher.find();
    }

    public static boolean validatePhone(String phone){
        if (phone == null){
            return true;
        }else{
            Matcher matcher = PHONE_REGEX.matcher(phone);
            return matcher.find();
        }
    }

    public static boolean validateLicensePlate(String licensePlate){
        Matcher matcher = LICENSE_REGEX.matcher(licensePlate);
        return matcher.find();
    }
}

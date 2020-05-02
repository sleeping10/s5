package sample;

public class PasswordEncryption {

    public static String encryptPassword(String pass){

        String hash = "";
        try {
            hash = byteArrayToHexString(computeHash(pass));
        }catch (Exception e){
            e.printStackTrace();
        }
        return hash;
    }

    public static String checkPassword(String hash){
        String out = "";
        try {
            out = byteArrayToHexString(computeHash(hash));
        }catch (Exception e){
            e.printStackTrace();
        }
        return out;
    }
    public static void main(String arg[]) {
        try {
            // quick way to do input from the keyboard, now deprecated...
            String input = "hej123";
            //
            String hash = byteArrayToHexString(PasswordEncryption.computeHash(input));
            System.out.println("the computed hash (hex string) : " + hash);
            boolean ok = true;
            String inputHash = "";
            while (ok) {
                System.out.print("Now try to enter a password : " );
                inputHash = byteArrayToHexString(PasswordEncryption.computeHash(input));
                if (hash.equals(inputHash)){
                    System.out.println("You got it!");
                    ok = false;
                }
                else
                    System.out.println("Wrong, try again...!");
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public static byte[] computeHash(String x) throws Exception
    {
        java.security.MessageDigest d =null;
        d = java.security.MessageDigest.getInstance("SHA-1");
        d.reset();
        d.update(x.getBytes());
        return  d.digest();
    }

    public static String byteArrayToHexString(byte[] b){
        StringBuffer sb = new StringBuffer(b.length * 2);
        for (int i = 0; i < b.length; i++){
            int v = b[i] & 0xff;
            if (v < 16) {
                sb.append('0');
            }
            sb.append(Integer.toHexString(v));
        }
        return sb.toString().toUpperCase();
    }
}
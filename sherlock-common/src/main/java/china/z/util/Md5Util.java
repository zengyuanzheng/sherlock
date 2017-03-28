package china.z.util;

import java.security.MessageDigest;

public class Md5Util {

    /**
     * MD5加密
     *
     * @param passwd
     * @return
     */
    public static String getMd5(String passwd) {
        StringBuffer md5 = new StringBuffer();
        try {
            MessageDigest instance = MessageDigest.getInstance("MD5");
            byte[] digest = instance.digest(passwd.getBytes());
            for (byte b : digest) {
                String hexString = Integer.toHexString(b & 0xff);
                if (hexString.length()<2){
                    hexString="0"+hexString;
                }
                md5=md5.append(b);
            }
        } catch (Exception e) {
            return md5.toString();
        }
        return md5.toString();
    }

    public static void main(String[] args) {
        String md5 = getMd5("admin");
        //3335474112287-91-8967-119741474-12831-61
        System.out.println(md5);
    }
}

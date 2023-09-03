package dng.vku.yu.uniform_vku;

import dng.vku.yu.uniform_vku.util.AES256;

public class test {
    public static void main(String[] args) {
        String originalString = "123";

        String encryptedString = AES256.encrypt(originalString);
        String decryptedString = AES256.decrypt(encryptedString);

//        System.out.println(originalString);
//        System.out.println(encryptedString);
//        System.out.println(decryptedString);
        System.out.println(AES256.decrypt("qvz/sFdM06gcoKucMac2Gg=="));
    }
}

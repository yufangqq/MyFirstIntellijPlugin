package yufang;

import java.security.*;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * Created by yufang on 4/3/17.
 */
public class EncryptAesCryptor {
  private static final String ALGO = "AES/CBC/PKCS5Padding";
  private static final byte[] keyValue =
      new byte[] { 'T', 'h', 'e', 'B', 'e', 's', 't',
          'S', 'e', 'c', 'r','e', 't', 'K', 'e', 'y' };
  // private static String key = "ea6fe02d3e440baeff785cbbb463f65d";
  // private static String iv = "30303030303030303030303030303031";

  public static byte[] hexStringToByteArray(String s) {
    int len = s.length();
    byte[] data = new byte[len / 2];
    for (int i = 0; i < len; i += 2) {
      data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
          + Character.digit(s.charAt(i+1), 16));
    }
    return data;
  }

  public static String encrypt(String data, String key, String iv) throws Exception {
    byte[] encVal = encrypt(data.getBytes(), key, iv);
    String encryptedValue = new BASE64Encoder().encode(encVal);
    return encryptedValue;
  }

  public static String decrypt(String encryptedData, String key, String iv) throws Exception {
    byte[] decordedValue = new BASE64Decoder().decodeBuffer(encryptedData);
    byte[] decValue = decrypt(decordedValue, key, iv);
    String decryptedValue = new String(decValue);
    return decryptedValue;
  }

  public static byte[] encrypt(byte[] data, String key, String iv) throws Exception {
    javax.crypto.spec.SecretKeySpec keyspec = new javax.crypto.spec.SecretKeySpec(hexStringToByteArray(key), "AES");
    javax.crypto.spec.IvParameterSpec ivspec = new javax.crypto.spec.IvParameterSpec(hexStringToByteArray(iv));

    Cipher c = Cipher.getInstance(ALGO);
    c.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);
    return c.doFinal(data);
  }

  public static byte[] decrypt(byte[] encryptedData, String key, String iv) throws Exception {
    javax.crypto.spec.SecretKeySpec keyspec = new javax.crypto.spec.SecretKeySpec(hexStringToByteArray(key), "AES");
    javax.crypto.spec.IvParameterSpec ivspec = new javax.crypto.spec.IvParameterSpec(hexStringToByteArray(iv));
    Cipher c = Cipher.getInstance(ALGO);
    c.init(Cipher.DECRYPT_MODE, keyspec, ivspec);
    return c.doFinal(encryptedData);
  }

  private static Key generateKey() throws Exception {
    Key key = new SecretKeySpec(keyValue, ALGO);
    return key;
  }

}

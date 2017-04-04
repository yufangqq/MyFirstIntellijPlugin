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
  private static final String ALGO = "AES";
  private static final byte[] keyValue =
      new byte[] { 'T', 'h', 'e', 'B', 'e', 's', 't',
          'S', 'e', 'c', 'r','e', 't', 'K', 'e', 'y' };

  public static String encrypt(String data) throws Exception {
    byte[] encVal = encrypt(data.getBytes());
    String encryptedValue = new BASE64Encoder().encode(encVal);
    return encryptedValue;
  }

  public static String decrypt(String encryptedData) throws Exception {
    byte[] decordedValue = new BASE64Decoder().decodeBuffer(encryptedData);
    byte[] decValue = decrypt(decordedValue);
    String decryptedValue = new String(decValue);
    return decryptedValue;
  }

  public static byte[] encrypt(byte[] data) throws Exception {
    Key key = generateKey();
    Cipher c = Cipher.getInstance(ALGO);
    c.init(Cipher.ENCRYPT_MODE, key);
    return c.doFinal(data);
  }

  public static byte[] decrypt(byte[] encryptedData) throws Exception {
    Key key = generateKey();
    Cipher c = Cipher.getInstance(ALGO);
    c.init(Cipher.DECRYPT_MODE, key);
    return c.doFinal(encryptedData);
  }

  private static Key generateKey() throws Exception {
    Key key = new SecretKeySpec(keyValue, ALGO);
    return key;
  }

}

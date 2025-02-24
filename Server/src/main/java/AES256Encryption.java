import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;

public class AES256Encryption {

    /**
     * Encrypts the given plaintext using AES-256 with CBC mode and PKCS5 padding.
     *
     * @param plainText The text to encrypt.
     * @param key       The secret key (password) used to derive the AES key.
     * @return A Base64-encoded string containing the IV and the ciphertext.
     * @throws Exception if encryption fails.
     */
    public static String encrypt(String plainText, String key) throws Exception {
        // Derive a 256-bit AES key from the provided key using SHA-256
        MessageDigest sha = MessageDigest.getInstance("SHA-256");
        byte[] keyBytes = sha.digest(key.getBytes("UTF-8"));
        SecretKeySpec secretKey = new SecretKeySpec(keyBytes, "AES");

        // Create cipher instance for AES/CBC/PKCS5Padding
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

        // Generate a random Initialization Vector (IV)
        byte[] iv = new byte[cipher.getBlockSize()];
        SecureRandom random = new SecureRandom();
        random.nextBytes(iv);
        IvParameterSpec ivParams = new IvParameterSpec(iv);

        // Initialize the cipher in encryption mode
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParams);

        // Encrypt the plaintext
        byte[] encrypted = cipher.doFinal(plainText.getBytes("UTF-8"));

        // Prepend IV to the encrypted message so it can be used for decryption
        ByteBuffer byteBuffer = ByteBuffer.allocate(iv.length + encrypted.length);
        byteBuffer.put(iv);
        byteBuffer.put(encrypted);
        byte[] cipherMessage = byteBuffer.array();

        // Return the complete cipher message (IV + ciphertext) as a Base64-encoded string
        return Base64.getEncoder().encodeToString(cipherMessage);
    } // end encrypt

    /**
     * Decrypts the given Base64-encoded ciphertext using AES-256 with CBC mode and PKCS5 padding.
     *
     * @param cipherText The Base64-encoded string containing the IV and the ciphertext.
     * @param key        The secret key (password) used to derive the AES key.
     * @return The decrypted plaintext.
     * @throws Exception if decryption fails.
     */
    public static String decrypt(String cipherText, String key) throws Exception {
        // Derive the AES key from the provided key using SHA-256
        MessageDigest sha = MessageDigest.getInstance("SHA-256");
        byte[] keyBytes = sha.digest(key.getBytes("UTF-8"));
        SecretKeySpec secretKey = new SecretKeySpec(keyBytes, "AES");

        // Decode the Base64-encoded cipher text
        byte[] cipherMessage = Base64.getDecoder().decode(cipherText);

        // Create cipher instance for AES/CBC/PKCS5Padding
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        int ivLength = cipher.getBlockSize();

        // Extract the IV from the beginning of the cipher message
        byte[] iv = new byte[ivLength];
        System.arraycopy(cipherMessage, 0, iv, 0, ivLength);
        IvParameterSpec ivParams = new IvParameterSpec(iv);

        // Extract the actual ciphertext
        int encryptedSize = cipherMessage.length - ivLength;
        byte[] encryptedBytes = new byte[encryptedSize];
        System.arraycopy(cipherMessage, ivLength, encryptedBytes, 0, encryptedSize);

        // Initialize the cipher in decryption mode with the extracted IV
        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParams);

        // Decrypt the ciphertext
        byte[] decrypted = cipher.doFinal(encryptedBytes);

        return new String(decrypted, "UTF-8");
    } // end decrypt
} // end AES256Encryption
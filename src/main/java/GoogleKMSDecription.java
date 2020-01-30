
import com.google.cloud.kms.v1.CryptoKeyName;
import com.google.cloud.kms.v1.DecryptResponse;
import com.google.cloud.kms.v1.KeyManagementServiceClient;
import com.google.protobuf.ByteString;

import java.io.*;
import java.util.Properties;

public class GoogleKMSDecription {

    static String FULLAUTH_CLIENT_ID;
    static String FULLAUTH_DEVCONSOLE_APIKEY;
    static String FULLAUTH_APP_ID;
    static String FULLSTORAGE_SIGNKEY;
    static String FULLSTORAGE_API_KEY;

    public static void main(String[] args) throws IOException {


        String projectId = "secretsmanagement-staging";
        String locationId = "global";
        String keyring = "anywhereworks";
        String cryptoKeyId = "anywhereworks-default";
        String cipherTextLocation = "D:/staging.properties.encrypted";

        File file = new File(cipherTextLocation);
        //init array with file length
        byte[] cipherText = new byte[(int) file.length()];
//        InputStream input = new FileInputStream(file);
//
//        BufferedReader br = new BufferedReader(new FileReader(file));
//        String line;
//        while ((line = br.readLine()) != null) {
//            System.out.println(line);
//        }

//        System.out.println(input);
        byte[] decryptedFile = decrypt(projectId, locationId,keyring,cryptoKeyId,cipherText);
        String decryptFileString = new String(decryptedFile);
        try (InputStream input = new FileInputStream(decryptFileString)) {

            Properties prop = new Properties();

            prop.load(input);

            prop.keySet().forEach(x -> x = prop.get(x));

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public static byte[] decrypt(
            String projectId, String locationId, String keyRingId, String cryptoKeyId, byte[] ciphertext)
            throws IOException {

        // Create the KeyManagementServiceClient using try-with-resources to manage client cleanup.
        try (KeyManagementServiceClient client = KeyManagementServiceClient.create()) {

            // The resource name of the cryptoKey
            String resourceName = CryptoKeyName.format(projectId, locationId, keyRingId, cryptoKeyId);

            // Decrypt the ciphertext with Cloud KMS.
            DecryptResponse response = client.decrypt(resourceName, ByteString.copyFrom(ciphertext));

            // Extract the plaintext from the response.
            return response.getPlaintext().toByteArray();
        }
    }
}

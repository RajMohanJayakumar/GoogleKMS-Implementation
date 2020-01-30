//
//import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
//import com.google.api.client.http.HttpTransport;
//import com.google.api.client.http.javanet.NetHttpTransport;
//import com.google.api.client.json.JsonFactory;
//import com.google.api.client.json.jackson2.JacksonFactory;
//import com.google.api.services.cloudkms.v1.CloudKMS;
//import com.google.api.services.cloudkms.v1.CloudKMSScopes;
//import com.google.api.services.cloudkms.v1.model.DecryptRequest;
//import com.google.api.services.cloudkms.v1.model.DecryptResponse;
//import com.google.common.io.ByteStreams;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.StringReader;
//import java.util.Properties;
//
//public class DecryptKM {
//
//    public Properties loadCredentials() {
//        try {
//
//            String filename = "credentials.properties.enc";
//
//            InputStream is = SampleCredentialLoader.class.getClassLoader().getResourceAsStream(filename);
//            byte[] content = ByteStreams.toByteArray(is);
//
//            String kmsResourceID = "projects/kms-demo/locations/global/keyRings/secrets-key-ring/cryptoKeys/secrets-enc-key";
//
//            String decryptedContent = decrypt(kmsResourceID, content);
//
//            Properties props = new Properties();
//            props.load(new StringReader(decryptedContent));
//
//            // set it to constants ...
//            String oauthClientID = props.getProperty("oauth_client_id");
//            String oauthClientSecret = props.getProperty("oauth_client_secret");
//
//            return props;
//
//        } catch (Exception e) {
//            throw new RuntimeException("error loading credential from file", e);
//        }
//    }
//
//    public String decrypt(String kmsResourceID, byte[] content) throws IOException {
//
//        CloudKMS kms = createAuthorizedClient();
//
//        DecryptRequest request = new DecryptRequest().encodeCiphertext(content);
//        DecryptResponse response = kms.projects().locations().keyRings().cryptoKeys().decrypt(kmsResourceID, request).execute();
//
//        return new String(response.decodePlaintext());
//    }
//
//    private CloudKMS createAuthorizedClient() throws IOException {
//
//        HttpTransport transport = new NetHttpTransport();
//        JsonFactory jsonFactory = new JacksonFactory();
//        GoogleCredential credential = GoogleCredential.getApplicationDefault(transport, jsonFactory);
//        if (credential.createScopedRequired()) {
//            credential = credential.createScoped(CloudKMSScopes.all());
//        }
//
//        return new CloudKMS.Builder(transport, jsonFactory, credential)
//                .setApplicationName("kms-demo")
//                .build();
//    }
//
//    public String kmsResouceName(String projectId, String locationId, String keyRingId, String keyId) {
//        return String.format(
//                "projects/%s/locations/%s/keyRings/%s/cryptoKeys/%s",
//                projectId, locationId, keyRingId, keyId);
//    }
//}
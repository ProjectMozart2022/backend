package api.security;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class FirebaseConfig {
  public static void initialize(String serviceAccountKey) throws IOException {
    FirebaseOptions options =
        FirebaseOptions.builder()
            .setCredentials(
                GoogleCredentials.fromStream(
                    new ByteArrayInputStream(serviceAccountKey.getBytes(StandardCharsets.UTF_8))))
            .build();
    FirebaseApp.initializeApp(options);
  }
}

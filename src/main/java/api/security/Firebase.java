package api.security;

import static org.slf4j.LoggerFactory.getLogger;
import static spark.Spark.*;

import api.TeacherApi;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.typesafe.config.ConfigFactory;
import java.io.ByteArrayInputStream;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;
import model.Teacher;
import org.slf4j.Logger;
import persistence.TeacherPersistence;
import spark.Request;

public class Firebase {
  private static final Logger log = getLogger(TeacherApi.class);

  public static void enable(String firebaseKey) {
    try {
      FirebaseApp.initializeApp(
          FirebaseOptions.builder()
              .setCredentials(
                  GoogleCredentials.fromStream(
                      new ByteArrayInputStream(firebaseKey.getBytes(StandardCharsets.UTF_8))))
              .build());
      haltUnauthorized();
    } catch (Exception e) {
      log.error("cannot initialize firebase", e);
    }
  }

  private static void haltUnauthorized() {
    before(
        "/api/admin/*",
        (request, response) -> {
          try {
            if (!request.requestMethod().equals("OPTIONS") && isNotAdmin(request))
              halt(HttpURLConnection.HTTP_UNAUTHORIZED);
          } catch (Exception e) {
            halt(HttpURLConnection.HTTP_UNAUTHORIZED);
          }
        });
    before(
        "/api/teacher/*",
        (request, response) -> {
          try {
            if (!request.requestMethod().equals("OPTIONS") && isNotTeacher(request))
              halt(HttpURLConnection.HTTP_UNAUTHORIZED);
          } catch (Exception e) {
            halt(HttpURLConnection.HTTP_UNAUTHORIZED);
          }
        });
  }

  private static boolean isNotAdmin(Request request) throws FirebaseAuthException {
    return !ConfigFactory.load().getString("mozart.security.adminId").equals(firebaseId(request));
  }

  private static boolean isNotTeacher(Request request) throws FirebaseAuthException {
    return !new TeacherPersistence()
        .getTeachers().stream()
            .map(Teacher::getFirebaseId)
            .collect(Collectors.toList())
            .contains(firebaseId(request));
  }

  private static String firebaseId(Request request) throws FirebaseAuthException {
    return FirebaseAuth.getInstance()
        .verifyIdToken(request.headers("Authorization").substring(7))
        .getUid();
  }
}

package api.security;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import spark.Request;

public class SecurityService {
  public static String getBearerToken(Request request) {
    String authorization = request.headers("Authorization");
    if (authorization != null && authorization.startsWith("Bearer ")) {
      return authorization.substring(7);
    }
    return null;
  }

  public static String decodeAndGetEmail(Request request) throws FirebaseAuthException {
    String jwt = SecurityService.getBearerToken(request);
    return FirebaseAuth.getInstance().verifyIdToken(jwt).getEmail();
  }
}

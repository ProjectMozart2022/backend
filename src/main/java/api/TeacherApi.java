package api;

import static java.net.HttpURLConnection.HTTP_OK;
import static org.slf4j.LoggerFactory.getLogger;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.google.gson.Gson;
import java.util.List;
import model.Teacher;
import org.slf4j.Logger;
import persistence.TeacherPersistence;
import spark.Request;
import spark.Response;

public class TeacherApi {
  private static final Gson gson = new Gson();
  private static final TeacherPersistence persistence = new TeacherPersistence();
  private static final Logger log = getLogger(TeacherApi.class);

  public List<Teacher> getAll(Request request, Response response) {
    return persistence.getAll();
  }

  public List<Teacher> getAllFilteredByStudent(Request request, Response response) {
    String studentId = request.queryParams("studentId");
    return persistence.getAll();
  }

  public String add(Request request, Response response) {
    Teacher teacher = gson.fromJson(request.body(), Teacher.class);
    UserRecord.CreateRequest firebaseRequest =
        new UserRecord.CreateRequest()
            .setDisplayName(teacher.getFirstName() + " " + teacher.getLastName())
            .setEmail(teacher.getEmail())
            .setEmailVerified(false)
            .setPassword(teacher.getPassword())
            .setDisabled(false);
    try {
      teacher.setFirebaseId(FirebaseAuth.getInstance().createUser(firebaseRequest).getUid());
      persistence.add(teacher);
      return "Successfully created teacher";
    } catch (FirebaseAuthException e) {
      log.error("firebase exception", e);
      return "error in creating teacher";
    }
  }

  public String update(Request request, Response response) {
    persistence.update(gson.fromJson(request.body(), Teacher.class));
    response.status(HTTP_OK);
    return "Successfully updated teacher";
  }

  public String delete(Request request, Response response) {
    persistence.delete(request.queryParams("firebaseId"));
    try {
      FirebaseAuth.getInstance().deleteUser(request.queryParams("firebaseId"));
    } catch (FirebaseAuthException e) {
      e.printStackTrace();
    }
    return "Successfully deleted teacher";
  }
}

package api;

import static java.net.HttpURLConnection.HTTP_CREATED;
import static java.net.HttpURLConnection.HTTP_OK;

import com.google.gson.Gson;
import java.util.List;
import model.Profile;
import persistence.ProfilePersistence;
import spark.Request;
import spark.Response;

public class ProfileApi {
  private static final Gson gson = new Gson();
  private static final ProfilePersistence persistence = new ProfilePersistence();

  public List<Profile> getProfiles(Request request, Response response) {
    return persistence.getProfiles();
  }

  public String addProfile(Request request, Response response) {
    persistence.addProfile(gson.fromJson(request.body(), Profile.class));
    response.status(HTTP_CREATED);
    return "Successfully created profile";
  }

  public String updateProfile(Request request, Response response) {
    persistence.updateProfile(gson.fromJson(request.body(), Profile.class));
    response.status(HTTP_OK);
    return "Successfully updated profile";
  }

  public String deleteProfile(Request request, Response response) {
    persistence.deleteProfile(Long.parseLong(request.queryParams("id")));
    response.status(HTTP_OK);
    return "Successfully deleted profile";
  }
}

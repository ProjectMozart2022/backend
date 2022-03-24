package api;

import static java.lang.Long.parseLong;
import static java.net.HttpURLConnection.HTTP_CREATED;
import static java.net.HttpURLConnection.HTTP_OK;

import com.google.gson.Gson;
import java.util.List;
import model.Profile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.ProfilePersistence;
import spark.Request;
import spark.Response;

public class ProfileApi {
  private static final Logger log = LoggerFactory.getLogger(ProfileApi.class);
  private static final Gson gson = new Gson();
  private static final ProfilePersistence persistence = new ProfilePersistence();

  public List<Profile> getProfiles(Request request, Response response) {
    return persistence.getProfiles();
  }

  public long addProfile(Request request, Response response) {
    Profile newProfile = gson.fromJson(request.body(), Profile.class);
    long id = persistence.addProfile(newProfile);
    response.status(HTTP_CREATED);
    response.body("Successfully created profile with id [" + id + "]");
    return id;
  }

  public long updateProfile(Request request, Response response) {
    Profile updatedProfile = gson.fromJson(request.body(), Profile.class);
    long id = persistence.addProfile(updatedProfile);
    response.status(HTTP_OK);
    response.body("Successfully updated profile with id [" + id + "]");
    return id;
  }

  public long deleteProfile(Request request, Response response) {
    long id = parseLong(request.queryParams("id"));
    persistence.deleteProfile(id);
    response.status(HTTP_OK);
    response.body("Successfully created profile with id [" + id + "]");
    return id;
  }
}

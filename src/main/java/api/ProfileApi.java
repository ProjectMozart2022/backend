package api;

import static java.lang.Long.parseLong;
import static java.net.HttpURLConnection.HTTP_CREATED;
import static java.net.HttpURLConnection.HTTP_OK;

import com.google.gson.Gson;

import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
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

    public Response addProfile(Request request, Response response) {
        JsonObject requestBody = gson.fromJson(request.body(), JsonObject.class);
        //this should be list of ints but this wonderful framework doesnt have such method xD
        int classRange = requestBody.get("classRange").getAsInt();
        persistence.addProfile(
                requestBody.get("name").getAsString(),
                requestBody.get("lessonLength").getAsInt(),
                List.of(classRange),
                requestBody.get("itn").getAsBoolean()
        );
        response.status(HTTP_CREATED);
        response.body("Successfully created profile");
        return response;
    }

    public Response updateProfile(Request request, Response response) {
        JsonObject requestBody = gson.fromJson(request.body(), JsonObject.class);
        //this should be list of ints but this wonderful framework doesnt have such method xD
        int classRange = requestBody.get("classRange").getAsInt();
        persistence.updateProfile(new Profile(requestBody.get("id").getAsLong(), requestBody.get("name").getAsString(),
                requestBody.get("lessonLength").getAsInt(),
                List.of(classRange),
                requestBody.get("itn").getAsBoolean()));
        response.status(HTTP_OK);
        response.body("Successfully updated profile");
        return response;
    }

    public Response deleteProfile(Request request, Response response) {
        JsonObject requestBody = gson.fromJson(request.body(), JsonObject.class);
        persistence.deleteProfile(requestBody.get("id").getAsLong());
        response.status(HTTP_OK);
        response.body("Successfully deleted profile");
        return response;
    }
}

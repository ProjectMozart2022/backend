package api;

import static java.lang.Long.parseLong;
import static java.net.HttpURLConnection.HTTP_CREATED;
import static java.net.HttpURLConnection.HTTP_OK;

import com.google.gson.Gson;

import java.util.List;

import com.google.gson.JsonObject;
import model.Teacher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.TeacherPersistence;
import spark.Request;
import spark.Response;

public class TeacherApi {
    private static final Logger log = LoggerFactory.getLogger(TeacherApi.class);
    private static final Gson gson = new Gson();
    private static final TeacherPersistence persistence = new TeacherPersistence();

    public List<Teacher> getTeachers(Request request, Response response) {
        return persistence.getTeachers();
    }

    public Response addTeacher(Request request, Response response) {
        JsonObject requestBody = gson.fromJson(request.body(), JsonObject.class);
        persistence.addTeacher(requestBody.get("firstName").getAsString(), requestBody.get("lastName").getAsString());
        response.status(HTTP_CREATED);
        response.body("Successfully created teacher");
        return response;
    }

    public Response updateTeacher(Request request, Response response) {
        JsonObject requestBody = gson.fromJson(request.body(), JsonObject.class);
        persistence.updateTeacher(new Teacher(requestBody.get("id").getAsLong(), requestBody.get("firstName").getAsString(), requestBody.get("lastName").getAsString()));
        response.status(HTTP_OK);
        response.body("Successfully updated teacher");
        return response;
    }

    public Response deleteTeacher(Request request, Response response) {
        JsonObject requestBody = gson.fromJson(request.body(), JsonObject.class);
        persistence.deleteTeacher(requestBody.get("id").getAsLong());
        response.status(HTTP_OK);
        response.body("Successfully deleted teacher");
        return response;
    }
}

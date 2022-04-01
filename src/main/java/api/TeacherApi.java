package api;

import static java.net.HttpURLConnection.HTTP_CREATED;
import static java.net.HttpURLConnection.HTTP_OK;

import com.google.gson.Gson;

import java.util.List;

import model.Teacher;
import persistence.TeacherPersistence;
import spark.Request;
import spark.Response;

public class TeacherApi {
    private static final Gson gson = new Gson();
    private static final TeacherPersistence persistence = new TeacherPersistence();

    public List<Teacher> getTeachers(Request request, Response response) {
        return persistence.getTeachers();
    }

    public Teacher getTeacher(Request request, Response response) {
        return persistence.getTeacher(Long.parseLong(request.queryParams("id")));
    }

    public String addTeacher(Request request, Response response) {
        persistence.addTeacher(gson.fromJson(request.body(), Teacher.class));
        response.status(HTTP_CREATED);
        return "Successfully created teacher";
    }

    public String updateTeacher(Request request, Response response) {
        persistence.updateTeacher(gson.fromJson(request.body(), Teacher.class));
        response.status(HTTP_OK);
        return "Successfully updated teacher";
    }

    public String deleteTeacher(Request request, Response response) {
        persistence.deleteTeacher(Long.parseLong(request.queryParams("id")));
        response.status(HTTP_OK);
        return "Successfully deleted teacher";
    }
}

package api;

import static java.lang.Long.parseLong;
import static java.net.HttpURLConnection.HTTP_CREATED;
import static java.net.HttpURLConnection.HTTP_OK;

import com.google.gson.Gson;

import java.util.List;

import com.google.gson.JsonObject;
import model.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.StudentPersistence;
import spark.Request;
import spark.Response;

public class StudentApi {
    private static final Logger log = LoggerFactory.getLogger(StudentApi.class);
    private static final Gson gson = new Gson();
    private static final StudentPersistence persistence = new StudentPersistence();

    public List<Student> getStudents(Request request, Response response) {
        return persistence.getStudents();
    }

    public long addStudent(Request request, Response response) {
        JsonObject requestBody = gson.fromJson(request.body(), JsonObject.class);
        long id = persistence.addStudent(
                requestBody.get("first_name").getAsString(),
                requestBody.get("last_name").getAsString(),
                requestBody.get("class_number").getAsInt()
        );
        response.status(HTTP_CREATED);
        response.body("Successfully created student with id [" + id + "]");
        return id;
    }

    public long updateStudent(Request request, Response response) {
        JsonObject requestBody = gson.fromJson(request.body(), JsonObject.class);
        long id = persistence.updateStudent(
                requestBody.get("first_name").getAsString(),
                requestBody.get("last_name").getAsString(),
                requestBody.get("class_number").getAsInt()
        );
        response.status(HTTP_OK);
        response.body("Successfully updated student with id [" + id + "]");
        return id;
    }

    public long deleteStudent(Request request, Response response) {
        long id = parseLong(request.queryParams("id"));
        persistence.deleteStudent(id);
        response.status(HTTP_OK);
        response.body("Successfully deleted student with id [" + id + "]");
        return id;
    }
}

package api;

import com.google.gson.Gson;
import model.Profile;
import model.Student;
import model.Teacher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.Persistence;
import spark.Request;
import spark.Response;

import java.util.List;

import static java.lang.Long.parseLong;
import static spark.Spark.*;

public class Api {
    private static final Logger log = LoggerFactory.getLogger(Api.class);
    private static final Gson gson = new Gson();
    private static final Persistence persistence = new Persistence(
            "jdbc:postgresql://localhost:5432/postgres",
            "mozart",
            "mozart123",
            10);

    public static void main(String[] args) {
        path("/api", () -> {
            path("/student", () -> {
                get("", Api::getStudents, gson::toJson);
                post("", Api::addStudent, gson::toJson);
                put("", Api::updateStudent, gson::toJson);
                delete("", Api::deleteStudent, gson::toJson);
            });
            path("/teacher", () -> {
                get("", Api::getTeachers, gson::toJson);
                post("", Api::addTeacher, gson::toJson);
                put("", Api::updateTeacher, gson::toJson);
                delete("", Api::deleteTeacher, gson::toJson);
            });
            path("/profile", () -> {
                get("", Api::getProfiles, gson::toJson);
                post("", Api::addProfile, gson::toJson);
                put("", Api::updateProfile, gson::toJson);
                delete("", Api::deleteProfile, gson::toJson);
            });
        });
    }

    private static List<Student> getStudents(Request request, Response response) {
        return persistence.students();
    }

    private static List<Teacher> getTeachers(Request request, Response response) {
        return persistence.teachers();
    }

    private static List<Profile> getProfiles(Request request, Response response) {
        return persistence.profiles();
    }

    //todo scrutinize why request is empty
    private static long addStudent(Request request, Response response) {
        Student newStudent = gson.fromJson(request.body(), Student.class);
        long id = persistence.addStudent(newStudent);
        response.status(HTTP_CREATED);
        response.body("Successfully created student with id [" + id + "]");
        return id;
    }

    private static long addTeacher(Request request, Response response) {
        Teacher newTeacher = gson.fromJson(request.body(), Teacher.class);
        long id = persistence.addTeacher(newTeacher);
        response.status(HTTP_CREATED);
        response.body("Successfully created teacher with id [" + id + "]");
        return id;
    }

    private static long addProfile(Request request, Response response) {
        Profile newProfile = gson.fromJson(request.body(), Profile.class);
        long id = persistence.addProfile(newProfile);
        response.status(HTTP_CREATED);
        response.body("Successfully created profile with id [" + id + "]");
        return id;
    }

    private static long updateStudent(Request request, Response response) {
        Student updatedStudent = gson.fromJson(request.body(), Student.class);
        long id = persistence.updateStudent(updatedStudent);
        response.status(HTTP_OK);
        response.body("Successfully updated student with id [" + id + "]");
        return id;
    }

    private static long updateTeacher(Request request, Response response) {
        Teacher updatedTeacher = gson.fromJson(request.body(), Teacher.class);
        long id = persistence.addTeacher(updatedTeacher);
        response.status(HTTP_OK);
        response.body("Successfully updated teacher with id [" + id + "]");
        return id;
    }

    private static long updateProfile(Request request, Response response) {
        Profile updatedProfile = gson.fromJson(request.body(), Profile.class);
        long id = persistence.addProfile(updatedProfile);
        response.status(HTTP_OK);
        response.body("Successfully updated profile with id [" + id + "]");
        return id;
    }
    //have to scrutinize how to make delete methods void because they're not allowed
    private static long deleteStudent(Request request, Response response) {
        long id = parseLong(request.queryParams("id"));
        persistence.deleteStudent(id);
        response.status(HTTP_OK);
        response.body("Successfully deleted student with id [" + id + "]");
        return id;
    }

    private static long deleteTeacher(Request request, Response response) {
        long id = parseLong(request.queryParams("id"));
        persistence.deleteTeacher(id);
        response.status(HTTP_OK);
        response.body("Successfully created profile with id [" + id + "]");
        return id;
    }

    private static long deleteProfile(Request request, Response response) {
        long id = parseLong(request.queryParams("id"));
        persistence.deleteProfile(id);
        response.status(HTTP_OK);
        response.body("Successfully created profile with id [" + id + "]");
        return id;
    }

    final static int HTTP_OK = 200;
    final static int HTTP_CREATED = 201;
}


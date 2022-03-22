package api;

import model.Profile;
import model.Student;
import model.Teacher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.Persistence;
import spark.Request;
import spark.Response;

import java.util.List;

import static spark.Spark.*;

public class Api {
    private static final Logger log = LoggerFactory.getLogger(Api.class);
    private static final Persistence persistence = new Persistence(
            "jdbc:postgresql://localhost:5432/postgres",
            "mozart",
            "mozart123",
            1);

    public static void main(String[] args) {
        path("/api", () -> {
            before("/*", (q, a) -> log.info("Received api call"));
            path("/student", () -> {
                get("", Api::getStudents);
            });
            path("/teacher", () -> {
                get("", Api::getTeachers);
            });
            path("/profile", () -> {
                get("", Api::getProfiles);
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
}


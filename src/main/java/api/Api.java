package api;

import static api.security.AccountRole.*;
import static spark.Spark.*;

import api.security.Account;
import api.security.AccountRole;
import api.security.FirebaseConfig;
import api.security.SecurityService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.gson.Gson;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import spark.Request;
import spark.Response;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Objects;

public class Api {
    private static final Gson gson = new Gson();
    private static final ProfileApi profileApi = new ProfileApi();
    private static final TeacherApi teacherApi = new TeacherApi();
    private static final StudentApi studentApi = new StudentApi();
    private static final AccountApi accountApi = new AccountApi();

    public static void main(String[] args) throws IOException {
        Config config = ConfigFactory.load();
        port(config.getInt("mozart.api.port"));
        FirebaseConfig.initialize(config.getString("mozart.security.serviceAccountKey"));
//      add preflight and cors
        options("/*",
                (request, response) -> {

                    String accessControlRequestHeaders = request
                            .headers("Access-Control-Request-Headers");
                    if (accessControlRequestHeaders != null) {
                        response.header("Access-Control-Allow-Headers",
                                accessControlRequestHeaders);
                    }

                    String accessControlRequestMethod = request
                            .headers("Access-Control-Request-Method");
                    if (accessControlRequestMethod != null) {
                        response.header("Access-Control-Allow-Methods",
                                accessControlRequestMethod);
                    }

                    return "OK";
                });
        before((request, response) -> response.header("Access-Control-Allow-Origin", "*"));
        path(
                "/api",
                () -> {
                    get("/login", accountApi::getAccount, gson::toJson);
                    path(

                            "/student",
                            () -> {
                                before((request, response) -> {
                                    if (!isAdmin(request, response)) {
                                        halt(HttpURLConnection.HTTP_UNAUTHORIZED, "");
                                    }
                                });
                                get("", studentApi::getStudents, gson::toJson);
                                post("", studentApi::addStudent, gson::toJson);
                                put("", studentApi::updateStudent, gson::toJson);
                                delete("", studentApi::deleteStudent, gson::toJson);
                            });
                    path(
                            "/teacher",
                            () -> {
                                before((request, response) -> {
                                    if (!isAdmin(request, response)) {
                                        halt(HttpURLConnection.HTTP_UNAUTHORIZED, "");
                                    }
                                });
                                get("", teacherApi::getTeachers, gson::toJson);
                                post("", teacherApi::addTeacher, gson::toJson);
                                put("", teacherApi::updateTeacher, gson::toJson);
                                delete("", teacherApi::deleteTeacher, gson::toJson);

                                path("/teacher/ping",
                                        () -> {
                                            before((request, response) -> {
                                                if (!isAuthenticated(request, response)) {
                                                    halt(HttpURLConnection.HTTP_UNAUTHORIZED, "");
                                                }
                                            });
                                            get("", (request, response) -> "pong");
                                        });
                            });
                    path(
                            "/profile",
                            () -> {
                                before((request, response) -> {
                                    if (!isAdmin(request, response)) {
                                        halt(HttpURLConnection.HTTP_UNAUTHORIZED, "");
                                    }
                                });
                                get("", profileApi::getProfiles, gson::toJson);
                                post("", profileApi::addProfile, gson::toJson);
                                put("", profileApi::updateProfile, gson::toJson);
                                delete("", profileApi::deleteProfile, gson::toJson);
                            });
                });
    }

    private static boolean isAdmin(Request request, Response response) throws FirebaseAuthException {
        String email = decodeAndGetEmail(request);
        Account account = accountApi.getAccount(request, response);
        if (!Objects.equals(email, account.getEmail())) {
            return false;
        }
        return account.getRole() == ADMIN;
    }

    private static boolean isAuthenticated(Request request, Response response) throws FirebaseAuthException {
        String email = decodeAndGetEmail(request);
        Account account = accountApi.getAccount(request, response);
        if (!Objects.equals(email, account.getEmail())) {
            return false;
        }
        switch (account.getRole()) {
            case ADMIN:
            case TEACHER:
                return true;
            default: return false;
        }
    }

    private static String decodeAndGetEmail(Request request) throws FirebaseAuthException {
        String jwt = SecurityService.getBearerToken(request);
        return FirebaseAuth.getInstance().verifyIdToken(jwt).getEmail();
    }
}


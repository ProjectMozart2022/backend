package api;

import api.security.Account;
import api.security.FirebaseConfig;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.google.gson.Gson;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import model.Teacher;
import spark.Request;
import spark.Response;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Objects;

import static api.security.AccountRole.ADMIN;
import static api.security.AccountRole.TEACHER;
import static api.security.SecurityService.decodeAndGetUid;
import static java.net.HttpURLConnection.HTTP_CREATED;
import static java.net.HttpURLConnection.HTTP_OK;
import static spark.Spark.*;

public class Api {
    private static final Gson gson = new Gson();
    private static final ProfileApi profileApi = new ProfileApi();
    private static final TeacherApi teacherApi = new TeacherApi();
    private static final StudentApi studentApi = new StudentApi();
    private static final AccountApi accountApi = new AccountApi();
    private static final String UNAUTHORIZED_ADMIN_MESSAGE = "Unauthorized access. ADMIN only";
    private static final String UNAUTHORIZED_ACCOUNT_MESSAGE = "Unauthorized access. Neither TEACHER nor ADMIN";

    public static void main(String[] args) throws IOException {
        Config config = ConfigFactory.load();
        port(config.getInt("mozart.api.port"));
        FirebaseConfig.initialize(config.getString("mozart.security.serviceAccountKey"));
        addCors();
        addFilters();

        path(
                "/api",
                () -> {
                    get("/login", accountApi::getAccountByUid, gson::toJson);
                    path(
                            "/student",
                            () -> {
                                get("", studentApi::getStudents, gson::toJson);
                                post("", studentApi::addStudent, gson::toJson);
                                put("", studentApi::updateStudent, gson::toJson);
                                delete("", studentApi::deleteStudent, gson::toJson);
                            });
                    path(
                            "/teacher",
                            () -> {
                                get("", teacherApi::getTeachers, gson::toJson);
                                post("", Api::createTeacher, gson::toJson);
                                put("", teacherApi::updateTeacher, gson::toJson);
                                delete("", Api::deleteTeacher, gson::toJson);

                                path(
                                        "/ping",
                                        () ->
                                                get("", (request, response) -> "pong")
                                );
                            });
                    path(
                            "/profile",
                            () -> {
                                get("", profileApi::getProfiles, gson::toJson);
                                post("", profileApi::addProfile, gson::toJson);
                                put("", profileApi::updateProfile, gson::toJson);
                                delete("", profileApi::deleteProfile, gson::toJson);
                            });
                });
    }

    private static String createTeacher(Request request, Response response) {
        AccountTeacherRequest accountTeacherRequest =
                gson.fromJson(request.body(), AccountTeacherRequest.class);
        UserRecord.CreateRequest firebaseRequest =
                new UserRecord.CreateRequest()
                        .setEmail(accountTeacherRequest.email)
                        .setEmailVerified(false)
                        .setPassword(accountTeacherRequest.password)
                        .setDisabled(false);
        try {
            String uid = FirebaseAuth.getInstance().createUser(firebaseRequest).getUid();
            Account account =
                    new Account(accountTeacherRequest.email, accountTeacherRequest.password, TEACHER, uid);
            long accountId = accountApi.addAccount(account, uid);
            Teacher teacher =
                    new Teacher(accountTeacherRequest.firstName, accountTeacherRequest.lastName);
            teacherApi.addTeacher(accountId, teacher);
            response.status(HTTP_CREATED);
            return "Successfully created teacher account";
        } catch (FirebaseAuthException e) {
            return "Failed to create FirebaseAccount for " + accountTeacherRequest.email;
        }
    }

    private static String deleteTeacher(Request request, Response response) {
        long id = Long.parseLong(request.queryParams("id"));
        try {
            Account account = accountApi.getAccountByTeacherId(id);
            FirebaseAuth.getInstance().deleteUser(account.getFirebaseUid());
            teacherApi.deleteTeacherById(id);
            accountApi.deleteAccount(account.getId());
            response.status(HTTP_OK);
            return "Successfully deleted teacher account";
        } catch (IllegalStateException e) {
            return "No teacher found with id = " + id;
        } catch (FirebaseAuthException e) {
            return "No account found in Firebase with such uid";
        }
    }

    private static boolean isAdmin(Request request, Response response) throws FirebaseAuthException {
        try {
            String uid = decodeAndGetUid(request);
            Account account = accountApi.getAccountByUid(uid);
            if (!Objects.equals(uid, account.getFirebaseUid())) {
                return false;
            }
            return account.getRole() == ADMIN;
        } catch (IllegalArgumentException e) {
            return false;
        }

    }

    private static boolean isAuthenticated(Request request, Response response)
            throws FirebaseAuthException {
        String uid = decodeAndGetUid(request);
        Account account = accountApi.getAccountByUid(uid);
        if (!Objects.equals(uid, account.getEmail())) {
            return false;
        }
        switch (account.getRole()) {
            case ADMIN:
            case TEACHER:
                return true;
            default:
                return false;
        }
    }

    private static void addFilters() {
        before((request, response) ->
                response.header("Access-Control-Allow-Origin", "*")
        );
        before("/api/student", (request, response) -> {
            if (!isAdmin(request, response)) {
                halt(HttpURLConnection.HTTP_UNAUTHORIZED, UNAUTHORIZED_ADMIN_MESSAGE);
            }
        });
        before("/api/teacher", (request, response) -> {
            if (!isAdmin(request, response)) {
                halt(HttpURLConnection.HTTP_UNAUTHORIZED, UNAUTHORIZED_ADMIN_MESSAGE);
            }
        });
        before("/api/profile", (request, response) -> {
            if (!isAdmin(request, response)) {
                halt(HttpURLConnection.HTTP_UNAUTHORIZED, UNAUTHORIZED_ADMIN_MESSAGE);
            }
        });
        before("/api/teacher/ping", (request, response) -> {
            if (!isAuthenticated(request, response)) {
                halt(HttpURLConnection.HTTP_UNAUTHORIZED, UNAUTHORIZED_ACCOUNT_MESSAGE);
            }
        });
    }

    private static void addCors() {
        options(
                "/*",
                (request, response) -> {
                    String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
                    if (accessControlRequestHeaders != null) {
                        response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
                    }

                    String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
                    if (accessControlRequestMethod != null) {
                        response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
                    }

                    return "OK";
                });
    }

    private class AccountTeacherRequest {
        private String email;
        private String password;
        private String firstName;
        private String lastName;

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }
    }
}
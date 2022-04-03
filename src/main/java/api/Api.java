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
import static api.security.SecurityService.decodeAndGetEmail;
import static java.net.HttpURLConnection.HTTP_CREATED;
import static java.net.HttpURLConnection.HTTP_OK;
import static spark.Spark.*;

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
                    get("/login", accountApi::getAccountById, gson::toJson);
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
                                post("", Api::createTeacher, gson::toJson);
                                put("", teacherApi::updateTeacher, gson::toJson);
                                delete("", Api::deleteTeacher, gson::toJson);

                                path("/teacher/ping",
                                        () -> {
                                            before((request, response) -> {
                                                if (!isAuthenticated(request, response)) {
                                                    halt(HttpURLConnection.HTTP_UNAUTHORIZED, "Unauthorized token");
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

    private static String createTeacher(Request request, Response response) {
        AccountTeacherRequest accountTeacherRequest = gson.fromJson(request.body(), AccountTeacherRequest.class);
        UserRecord.CreateRequest firebaseRequest = new UserRecord.CreateRequest()
                .setEmail(accountTeacherRequest.account.getEmail())
                .setEmailVerified(false)
                .setPassword(accountTeacherRequest.account.getPassword())
                .setDisabled(false);
        try {
            String uid = FirebaseAuth.getInstance().createUser(firebaseRequest).getUid();
            long accountId = accountApi.addAccount(accountTeacherRequest.account, uid);
            teacherApi.addTeacher(accountId, accountTeacherRequest.teacher);
            response.status(HTTP_CREATED);
            return "Successfully created teacher account";
        } catch (FirebaseAuthException e){
            e.printStackTrace();
            return "Failed to create FirebaseAccount for " + accountTeacherRequest.account.getEmail();
        }
    }

    private static String deleteTeacher(Request request, Response response) {
        long id = Long.parseLong(request.queryParams("id"));
        Account account = accountApi.getAccountByTeacherId(id);
        try {
            FirebaseAuth.getInstance().deleteUser(account.getFirebaseUid());
            teacherApi.deleteTeacherById(id);
            accountApi.deleteAccount(account.getId());
            response.status(HTTP_OK);
            return "Successfully deleted teacher account";
        } catch (FirebaseAuthException e) {
            e.printStackTrace();
            return "No account found in Firebase with such uid";
        }
    }



    private static boolean isAdmin(Request request, Response response) throws FirebaseAuthException {
        String email = decodeAndGetEmail(request);
        Account account = accountApi.getAccountById(request, response);
        if (!Objects.equals(email, account.getEmail())) {
            return false;
        }
        return account.getRole() == ADMIN;
    }

    private static boolean isAuthenticated(Request request, Response response) throws FirebaseAuthException {
        String email = decodeAndGetEmail(request);
        Account account = accountApi.getAccountById(request, response);
        if (!Objects.equals(email, account.getEmail())) {
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

    private class AccountTeacherRequest {
        private Account account;
        private Teacher teacher;

        public Account getAccount() {
            return account;
        }

        public void setAccount(Account account) {
            this.account = account;
        }

        public Teacher getTeacher() {
            return teacher;
        }

        public void setTeacher(Teacher teacher) {
            this.teacher = teacher;
        }
    }
}


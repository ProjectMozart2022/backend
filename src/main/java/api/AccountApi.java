package api;

import api.security.Account;
import com.google.gson.Gson;
import persistence.AccountPersistence;
import spark.Request;
import spark.Response;

import java.util.List;

import static java.net.HttpURLConnection.HTTP_CREATED;
import static java.net.HttpURLConnection.HTTP_OK;

public class AccountApi {
    private static final Gson gson = new Gson();
    private static final AccountPersistence persistence = new AccountPersistence();

    public List<Account> getAccounts(Request request, Response response) {
        return persistence.getAccounts();
    }

    public Account getAccount(Request request, Response response){
        return persistence.getAccount(Long.parseLong(request.queryParams("id")));
    }

    public String addAccount(Request request, Response response) {
        persistence.addAccount(gson.fromJson(request.body(), Account.class));
        response.status(HTTP_CREATED);
        return "Successfully created profile";
    }

    public String updatePassword(Request request, Response response) {
        persistence.updatePassword(gson.fromJson(request.body(), Account.class));
        response.status(HTTP_OK);
        return "Successfully updated password";
    }

    public String deleteAccount(Request request, Response response) {
        persistence.deleteAccount(Long.parseLong(request.queryParams("id")));
        response.status(HTTP_OK);
        return "Successfully deleted profile";
    }
}

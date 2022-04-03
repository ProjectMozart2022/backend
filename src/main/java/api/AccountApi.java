package api;

import api.security.Account;
import com.google.gson.Gson;
import persistence.AccountPersistence;
import spark.Request;
import spark.Response;

import java.util.List;
import java.util.logging.Logger;

import static java.net.HttpURLConnection.HTTP_OK;

public class AccountApi {
    private static final Gson gson = new Gson();
    private static final AccountPersistence persistence = new AccountPersistence();
    private static final Logger log = Logger.getLogger(AccountApi.class.getName());

    public List<Account> getAccounts(Request request, Response response) {
        return persistence.getAccounts();
    }

    public Account getAccountById(Request request, Response response) {
        return persistence.getAccountById(Long.parseLong(request.queryParams("id")));
    }

    public Account getAccountByTeacherId(long id) {
        return persistence.getAccountByTeacherId(id);
    }

    public long addAccount(Account account, String uid) {
        persistence.addAccount(account, uid);
        log.info("Successfully created account");
        return persistence.getAccountByFirebaseUid(uid).getId();
    }

    public String updatePassword(Request request, Response response) {
        persistence.updatePassword(gson.fromJson(request.body(), Account.class));
        response.status(HTTP_OK);
        return "Successfully updated password";
    }

    public void deleteAccount(long id) {
        persistence.deleteAccount(id);
        log.info("Successfully deleted account");
    }
}

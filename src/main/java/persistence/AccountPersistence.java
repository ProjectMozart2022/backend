package persistence;

import api.security.Account;

import java.util.List;

import static org.jdbi.v3.core.locator.ClasspathSqlLocator.create;

public class AccountPersistence extends Persistence {
    public AccountPersistence() {
        super();
    }

    public List<Account> getAccounts() {
        return jdbi.inTransaction(
                handle ->
                        handle
                                .createQuery(create().locate("queries/account/select_accounts"))
                                .mapToBean(Account.class)
                                .list());
    }

    public Account getAccountById(long id) {
        return jdbi.inTransaction(
                handle ->
                        handle
                                .createQuery(create().locate("queries/account/select_account_by_id"))
                                .bind("id", id)
                                .mapToBean(Account.class)
                                .one());
    }

    public Account getAccountByFirebaseUid(String uid) {
        return jdbi.inTransaction(
                handle ->
                        handle
                                .createQuery(create().locate("queries/account/select_account_by_firebase_uid"))
                                .bind("firebase_uid", uid)
                                .mapToBean(Account.class)
                                .one());
    }

    public Account getAccountByTeacherId(long id) {
        return jdbi.inTransaction(
                handle ->
                        handle
                                .createQuery(create().locate("queries/account/select_account_by_teacher_id"))
                                .bind("id", id)
                                .mapToBean(Account.class)
                                .one());
    }

    public void addAccount(Account newAccount, String uid) {
        jdbi.inTransaction(
                handle ->
                        handle
                                .createUpdate(create().locate("queries/account/add_account"))
                                .bind("firebase_uid", uid)
                                .bind("email", newAccount.getEmail())
                                .bind("password", newAccount.getPassword())
                                .bind("role", newAccount.getRole().name())
                                .execute());
    }

    public void updatePassword(Account account) {
        jdbi.inTransaction(handle ->
                        handle.createUpdate(create().locate("queries/account/update_password")))
                .bind("id", account.getId())
                .bind("password", account.getPassword())
                .execute();
    }

    public void deleteAccount(long id) {
        jdbi.inTransaction(
                handle ->
                        handle
                                .createUpdate(create().locate("queries/account/delete_account"))
                                .bind("id", id)
                                .execute());
    }
}


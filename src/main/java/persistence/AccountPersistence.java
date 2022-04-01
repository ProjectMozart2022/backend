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

    public Account getAccount(long id) {
        return jdbi.inTransaction(
                handle ->
                        handle
                                .createQuery(create().locate("queries/account/select_account"))
                                .bind("id", id)
                                .mapToBean(Account.class)
                                .one());
    }

    public void addAccount(Account newAccount) {
        jdbi.inTransaction(
                handle ->
                        handle
                                .createUpdate(create().locate("queries/account/add_account"))
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


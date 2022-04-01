package persistence;

import static org.jdbi.v3.core.locator.ClasspathSqlLocator.create;

import java.util.List;
import java.util.stream.Collectors;

import model.Profile;

public class ProfilePersistence extends Persistence {
    public ProfilePersistence() {
        super();
    }

    public List<Profile> getProfiles() {
        return jdbi.inTransaction(
                handle ->
                        handle
                                .createQuery(create().locate("queries/profile/select_profiles"))
                                .mapToBean(Profile.class)
                                .list());
    }

    public void addProfile(Profile profile) {
        jdbi.inTransaction(
                handle ->
                        handle
                                .createUpdate(create().locate("queries/profile/add_profile"))
                                .bind("name", profile.getName())
                                .bind("lesson_length", profile.getLessonLength())
                                .bind(
                                        "class_range",
                                        profile.getClassRange().stream().mapToInt(Integer::intValue).toArray())
                                .bind("is_itn", profile.isItn())
                                .execute());
    }

    public void updateProfile(Profile profile) {
        jdbi.inTransaction(
                handle ->
                        handle
                                .createUpdate(create().locate("queries/profile/update_profile"))
                                .bind("id", profile.getId())
                                .bind("name", profile.getName())
                                .bind("lesson_length", profile.getLessonLength())
                                .bind(
                                        "class_range",
                                        profile
                                                .getClassRange().stream().mapToInt(Integer::intValue).toArray())
                                .bind("is_itn", profile.isItn())
                                .execute());
    }

    public void deleteProfile(long id) {
        jdbi.inTransaction(
                handle ->
                        handle
                                .createUpdate(create().locate("queries/profile/delete_profile"))
                                .bind("id", id)
                                .execute());
    }
}

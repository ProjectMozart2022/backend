package persistence;

import static org.jdbi.v3.core.locator.ClasspathSqlLocator.create;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import model.Profile;

public class ProfilePersistence extends Persistence {
    public ProfilePersistence() {
        super();
    }

    public List<Profile> getProfiles() {
        return jdbi.inTransaction(handle ->
                handle
                        .createQuery(create().locate("queries/profile/select_profile"))
                        .mapTo(Profile.class)
                        .list());
    }

    public Profile getProfile(long id) {
        return jdbi.inTransaction(handle ->
                handle
                        .createQuery(create().locate("queries/profile/select_profile_by_id"))
                        .bind("id", id)
                        .mapTo(Profile.class)
                        .one());
    }

    public void addProfile(String name, int lessonLength, List<Integer> classRange, boolean itn) {
        String stringClassRange = classRange.stream().map(String::valueOf).collect(Collectors.joining());
        jdbi.inTransaction(
                handle ->
                        handle
                                .createUpdate(create().locate("queries/profile/add_profile"))
                                .bind("name", name)
                                .bind("lesson_length", lessonLength)
                                .bind("class_range", stringClassRange)
                                .bind("is_itn", itn)
                                .execute());
    }

    public void updateProfile(Profile requestedProfile) {
        jdbi.inTransaction(
                        handle ->
                                handle
                                        .createUpdate(create().locate("queries/profile/update_profile"))
                                        .bind("name", requestedProfile.name())
                                        .bind("lesson_length", requestedProfile.lessonLength())
                                        .bind("class_range", requestedProfile.classRange().stream().map(String::valueOf).collect(Collectors.joining()))
                                        .bind("is_itn", requestedProfile.itn())
                                        .execute());
    }

    public void deleteProfile(long id) {
        jdbi.inTransaction(
                handle ->
                        handle.createUpdate(create().locate("queries/profile/delete_profile")).bind("id", id).execute());
    }
}

package persistence;

import static org.jdbi.v3.core.locator.ClasspathSqlLocator.create;

import java.util.List;
import model.Profile;

public class ProfilePersistence extends Persistence {
  public ProfilePersistence() {
    super();
  }

  public List<Profile> getProfiles() {
    return jdbi.inTransaction(
        handle ->
            handle
                .createQuery(create().locate("queries/profile/select_profile"))
                .mapTo(Profile.class)
                .list());
  }

  public Profile getProfile(long id) {
    return jdbi.inTransaction(
        handle ->
            handle
                .createQuery(create().locate("queries/profile/select_profile_by_id"))
                .bind("id", id)
                .mapTo(Profile.class)
                .one());
  }

  public long addProfile(Profile requestedProfile) {
    return jdbi.inTransaction(
            handle ->
                handle
                    .createUpdate(create().locate("queries/profile/add_profile"))
                    .bind("name", requestedProfile.name())
                    .bind("lesson_length", requestedProfile.lessonLength())
                    .bind("class_range", requestedProfile.classRange())
                    .bind("is_itn", requestedProfile.itn())
                    .executeAndReturnGeneratedKeys("id"))
        .mapTo(Long.class)
        .one();
  }

  public long updateProfile(Profile requestedProfile) {
    return jdbi.inTransaction(
            handle ->
                handle
                    .createUpdate(create().locate("queries/profile/update_profile"))
                    .bind("name", requestedProfile.name())
                    .bind("lesson_length", requestedProfile.lessonLength())
                    .bind("class_range", requestedProfile.classRange())
                    .bind("is_itn", requestedProfile.itn())
                    .executeAndReturnGeneratedKeys("id"))
        .mapTo(Long.class)
        .one();
  }

  public void deleteProfile(long id) {
    jdbi.inTransaction(
        handle ->
            handle.createQuery(create().locate("queries/profile/delete_profile")).bind("id", id));
  }
}

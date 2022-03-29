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
                .createQuery(create().locate("queries/profile/select_profiles"))
                .mapToBean(Profile.class)
                .list());
  }

  public void addProfile(Profile createdProfile) {
    jdbi.inTransaction(
        handle ->
            handle
                .createUpdate(create().locate("queries/profile/add_profile"))
                .bind("name", createdProfile.getName())
                .bind("lesson_length", createdProfile.getLessonLength())
                .bind(
                    "class_range",
                    createdProfile.getClassRange().stream().mapToInt(Integer::intValue).toArray())
                .bind("is_itn", createdProfile.isItn())
                .execute());
  }

  public void updateProfile(Profile requestedProfile) {
    jdbi.inTransaction(
        handle ->
            handle
                .createUpdate(create().locate("queries/profile/update_profile"))
                .bind("id", requestedProfile.getId())
                .bind("name", requestedProfile.getName())
                .bind("lesson_length", requestedProfile.getLessonLength())
                .bind(
                    "class_range",
                    requestedProfile.getClassRange().stream().mapToInt(Integer::intValue).toArray())
                .bind("is_itn", requestedProfile.isItn())
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

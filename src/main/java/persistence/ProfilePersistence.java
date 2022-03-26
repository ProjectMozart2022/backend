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
                .createQuery(create().locate("queries/profile/select_profile"))
                .mapToBean(Profile.class)
                .list());
  }

  public void addProfile(Profile createdProfile) {
    jdbi.inTransaction(
        handle ->
            handle
                .createUpdate(create().locate("queries/profile/add_profile"))
                .bind("name", createdProfile.getName())
                .bind("lesson_length", createdProfile.getName())
                .bind(
                    "class_range",
                    createdProfile
                        .getClassRange()
                        .stream()
                        .map(String::valueOf)
                        .collect(Collectors.joining()))
                .bind("is_itn", createdProfile.isItn())
                .execute());
  }

  public void updateProfile(Profile requestedProfile) {
    jdbi.inTransaction(
        handle ->
            handle
                .createUpdate(create().locate("queries/profile/update_profile"))
                .bind("name", requestedProfile.getName())
                .bind("lesson_length", requestedProfile.getLessonLength())
                .bind(
                    "class_range",
                    requestedProfile
                        .getClassRange()
                        .stream()
                        .map(String::valueOf)
                        .collect(Collectors.joining()))
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

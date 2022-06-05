import config.testcontainters.PostgreContainerInitializer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class Test {


    @BeforeAll
    private void initDb() {
        PostgreContainerInitializer dbContainer = new PostgreContainerInitializer();
        dbContainer.start();
    }
}

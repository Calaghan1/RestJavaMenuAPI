package conftest;

import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.menu.Settings;
import org.menu.db.ConnectionManager;

public class ConfTest {
    @Test
    public void test() {
        try {
            Dotenv dotenv = Dotenv.configure()
                    .directory(Settings.ENV_PATH.toString())
                    .load();
            String url = dotenv.get("URL");
            String user = dotenv.get("USER");
            String password = dotenv.get("PASSWORD");
            Assertions.assertNotNull(url);
            Assertions.assertNotNull(user);
            Assertions.assertNotNull(password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void test2() {
        try {
            ConnectionManager cm = new ConnectionManager();
            cm.init();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}

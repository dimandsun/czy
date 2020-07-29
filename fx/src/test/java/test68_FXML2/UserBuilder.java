package test68_FXML2;

import test52_binding.User;
import javafx.util.Builder;

/**
 * @author chenzy
 * @since 2020-06-11
 */
public class UserBuilder implements Builder<User> {
    private String name;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public User build() {
        return new User(name);
    }
}

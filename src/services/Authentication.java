package services;
import models.User;
import java.util.Optional;
public interface Authentication {

    public Optional<User> login(String id, String password);

}

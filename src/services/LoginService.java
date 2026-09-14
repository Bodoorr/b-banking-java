package services;
import models.User;
import java.util.*;
public class LoginService implements Authentication{

    @Override
    public Optional<User> login(String id, String password) {
        return Optional.empty();
    }
}

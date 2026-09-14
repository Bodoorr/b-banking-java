package services;
import models.User;
import java.util.*;
import java.io.*;
import utils.FileManager;
public class LoginService implements Authentication{

    @Override
    public Optional<User> login(String id, String password) {
        try {
            List<String> users = FileManager.readAllLines("data.txt");

            for (String user:users){
                String [] data = user.split(",");
                if (data[0].equals(id) && data[3].equals(password)){
                    System.out.println("Login Successfully!");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }

}

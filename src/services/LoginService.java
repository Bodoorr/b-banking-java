package services;
import models.Banker;
import models.Customer;
import models.User;
import java.util.*;
import java.io.*;
import utils.FileManager;
public class LoginService implements Authentication{

    @Override
    public Optional<User> login(String id, String password) {
        try {
            List<String> users = FileManager.readAllLines("src/data");

            for (String user:users){
                String [] data = user.split(",");
                if (data[0].equals(id) && data[3].equals(password)){
                    if(data[4].equals("C")){
                        Customer customer=new Customer(
                                data[0],data[1],data[2],data[3],data[4]
                        );
                        return Optional.of(customer);
                    } else if(data[4].equals("B")){
                        Banker banker=new Banker(
                                data[0],data[1],data[2],data[3],data[4]
                        );
                        return Optional.of(banker);
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }

}

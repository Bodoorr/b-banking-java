package services;
import models.Banker;
import models.Customer;
import models.User;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import utils.FileManager;
import utils.PasswordUtils;

public class LoginService implements Authentication{

    private HashMap<String, Integer> failedAttempts=new HashMap<>();
    private HashMap<String, LocalDateTime> lockedTill= new HashMap<>();

    public LoginService(){
        loadLoginAttempts();
    }

    @Override
    public Optional<User> login(String id, String password) {
        LocalDateTime lockTime = lockedTill.get(id);

        if (lockTime !=null && LocalDateTime.now().isBefore(lockTime)){
            System.out.println("Account is locked. Try again later.");
            return Optional.empty();
        }

        if (lockTime !=null && LocalDateTime.now().isAfter(lockTime)){
            lockedTill.remove(id);
            failedAttempts.remove(id);
            saveLoginAttempt(id, 0, null);
        }

        try {
            List<String> users = FileManager.readAllLines("src/data");
            String hashPassword = PasswordUtils.hashPassword(password);

            for (String user:users){
                String [] data = user.split(",");
                if (data[0].equals(id) && data[3].equals(hashPassword)){
                    failedAttempts.remove(id);
                    lockedTill.remove(id);

                    saveLoginAttempt(id,0,null);

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
        } catch (IOException | NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        Integer attempts= failedAttempts.get(id);

        if (attempts==null){
            attempts=1;
        } else {
            attempts++;
        }

        failedAttempts.put(id,attempts);
        LocalDateTime newLockTime= null;

        if (attempts>=3){
            newLockTime= LocalDateTime.now().plusMinutes(1);
            lockedTill.put(id,newLockTime);
            System.out.println("Account locked. Please try again after 1 minute.");
        }

        saveLoginAttempt(id,attempts,newLockTime);
        return Optional.empty();
    }

    private void saveLoginAttempt(String id, Integer attempts, LocalDateTime lockTime){
        String loginData= id+","+attempts+","+lockTime;

        try {
            List<String> lines= FileManager.readAllLines("src/loginAttempts");
            ArrayList<String> updatedLines=new ArrayList<>();
            boolean userFound= false;
            for(String line:lines){
                String [] data= line.split(",");
                if (data[0].equals(id)){
                    updatedLines.add(loginData);
                    userFound=true;
                }else {
                    updatedLines.add(line);
                }
            }
            if (!userFound){
                updatedLines.add(loginData);
            }
            FileManager.overWriteFile("src/loginAttempts",updatedLines);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadLoginAttempts(){
        try {
            List<String> lines=FileManager.readAllLines("src/loginAttempts");
            for (String line:lines){
                String [] data=line.split(",");
                int attempts= Integer.parseInt(data[1]);
                failedAttempts.put(data[0], attempts);

                if (!data[2].equals("null")){
                    LocalDateTime lockTime= LocalDateTime.parse(data[2]);
                    lockedTill.put(data[0],lockTime);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}

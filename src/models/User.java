package models;

public abstract class User {
    private String firstName;
    private String lastName;
    private String id;
    private String role;
    private String password;

    public User(String id, String firstName, String lastName, String password, String role){
        this.id= id;
        this.firstName= firstName;
        this.lastName= lastName;
        this.password= password;
        this.role= role;
    }

    public String getId(){
        return id;
    }

    public String getFirstName(){
        return firstName;
    }

    public String getLastName(){
        return lastName;
    }

    public String getPassword(){
        return password;
    }

    public String getRole(){
        return role;
    }
}

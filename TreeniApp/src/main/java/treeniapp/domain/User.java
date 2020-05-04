
package treeniapp.domain;

/**
 * Class handles the User objects representing the application's registered users.
 */
public class User {
    
    private String username;
    private String name;

    /**
    * Constructor for <code>User</code> object.
    * 
    * @param username   Username (for login) of the user.
    * @param name       Real name of the user as shown in the application.
    */
    public User(String username, String name) {
        this.username = username;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof User)) {
            return false;
        }
        
        User other = (User) obj;
        return username.equals(other.username);
    }
    
}
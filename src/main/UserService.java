package main;

import java.util.ArrayList;
import java.util.List;

/**
 * An Implementation of User class that provides CRUD services on User objects
 */
public class UserService {
    List<User> users = new ArrayList<User>();
    User user = new User("Ali",10,1);

    /**
     * Adds a new and valid user to List of Users
     * @param user an object of User class along with attributes
     */
    public User addUser(User user) {
        if(user.getAge()<0){
            throw new IllegalArgumentException();
        }
        users.add(user);
        return user;
    }
    /**
     * Deletes a valid user from List of Users
     * @param user an object of User class along with attributes
     */
    public void removeUser(User user) {
        if(!users.contains(user))
        {
            throw new IndexOutOfBoundsException();
        }
        users.remove(user);
    }
    /**
     * Edits an existing user and updates its attributes on validity
     * @param id id of user object passed to the function
     * @param newName new name to be edited and updated
     * @param newAge new age to be edited and updated
     */
    public User editUser(int id, String newName, int newAge) {
            try{
                if(newName == "" || newAge <= 0){
                    throw new IllegalArgumentException();
                }
                User user = users.get(id);
                user.setName(newName);
                user.setAge(newAge);

            }catch (NullPointerException e){
                System.out.println("User with ID "+id+" does not exist, or is empty.");
            }
                catch (IndexOutOfBoundsException e)
            {
                System.out.println("desired user ID is out of Users Array bounds");
            }
        return user;
    }
    /**
     * Function to get the complete list of users
     * @return returns list of users
     */
    public List<User> getUsers()
    {
        return users;
    }
    /**
     * Function to get the total count of all users in List
     * @return returns count of users in List
     */
    public int getUsersCount(){
        return users.size();
    }

}
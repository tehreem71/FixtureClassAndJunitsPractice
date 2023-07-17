package main;

import java.util.ArrayList;
import java.util.List;

/**
 * An Implementation of User class that provides CRUD operations for managing User objects
 */
public class UserService
{
  List<User> users = new ArrayList<User>();

  /**
   * Adds a new user to list of Users
   *
   * @param user the user object to be added
   * @throws IllegalArgumentException If the user's age is negative.
   */
  public User addUser(User user) throws IllegalArgumentException
  {
    if (user.getAge() < 0)
    {
      throw new IllegalArgumentException();
    }
    users.add(user);
    return user;
  }

  /**
   * Removes a user from the list of Users
   *
   * @param user the user object to be removed.
   * @throws IndexOutOfBoundsException If the user is not found in the list
   */
  public void removeUser(User user) throws IndexOutOfBoundsException
  {
    if (!users.contains(user))
    {
      throw new IndexOutOfBoundsException();
    }
    users.remove(user);
  }

  /**
   * Edits attributes of existing user based on ID
   *
   * @param id      id of user object to be edited
   * @param newName new name to be assigned to user object
   * @param newAge  new age to be assigned to user object
   * @throws IllegalArgumentException  If the new name is empty or the new age is less than or equal to 0.
   * @throws NullPointerException      If the user with the given ID does not exist or is null.
   * @throws IndexOutOfBoundsException If the desired user ID is out of the users array bounds.
   */
  public void editUser(int id, String newName, int newAge) throws IllegalArgumentException, NullPointerException, IndexOutOfBoundsException
  {
    try
    {
      if (newName == "" || newAge <= 0)
      {
        throw new IllegalArgumentException();
      }
      User user = users.get(id);
      user.setName(newName);
      user.setAge(newAge);

    }
    catch (NullPointerException e)
    {
      System.out.println("User with ID " + id + " does not exist, or is empty.");
    }
    catch (IndexOutOfBoundsException e)
    {
      System.out.println("desired user ID is out of Users Array bounds");
    }
  }

  /**
   * Returns the complete list of users
   *
   * @return list of users
   */
  public List<User> getUsers()
  {
    return users;
  }
}
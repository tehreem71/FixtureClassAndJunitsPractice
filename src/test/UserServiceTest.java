package test;


import main.User;
import main.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import java.util.Objects;

import static org.mockito.Mockito.when;

/**
 * Test class for UserService.java
 */
public class UserServiceTest {
    private Fixture fixture = new Fixture();

    @Test
    public void testAddUser_addsUser()
    {
        fixture.givenUserIsInitialized("Ahmed",10,0);
        fixture.whenUserIsAddedSuccessfully();
        fixture.thenAssertionResultsInEquality(1);
    }
    @Test(expected = IllegalArgumentException.class)
    public void testAddUser_ageIsNegative_doesNotAddUser_throwsException()
    {
        try{
            fixture.givenUserIsInitialized("Ahmed",-10,0);
            fixture.whenUserIsNotAddedSuccessfully();
        }catch (IllegalArgumentException e)
        {
            fixture.thenAssertionResultsInEquality(0);
            fixture.thenThrowIllegalArgumentException();
        }
    }

    @Test
    public void testRemoveUser_removesUser()
    {
        fixture.givenUserIsInitializedAndAdded("Ahmed",10,1);
        fixture.whenUserIsDeleted();
        fixture.thenAssertionResultsInEquality(0);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testRemoveUser_userDoesNotExist_doesNotRemoveUser_throwsException()
    {
        try{
            fixture.givenUserIsInitializedAndAdded("Ali",10,0); // User count=1
            fixture.givenUserIsReInitializedAfterAddition("Ahmed",11,1); // assigning same object to a new reference
            fixture.whenUserIsDeleted();  // will look for Ahmed in usersArray, won't find it so won't delete, count remains 1
        }catch (IndexOutOfBoundsException e){
            fixture.thenAssertionResultsInEquality(1);
            fixture.thenThrowIndexOutOfBoundException();
        }
    }

    @Test
    public void testEditUser_editsUserSuccessfully()
    {
        fixture.givenUserIsInitializedAndAdded("Ahmed",10,0);
        fixture.whenUserIsEdited("Ali",20);
        fixture.thenAssertEqualityCheckOnEditUser();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEditUser_enteredInvalidAttributes_doesNotEditUser_throwsException()
    {
        try{
            fixture.givenUserIsInitializedAndAdded("Ahmed",10,0);
            fixture.whenUserIsEdited("",0);
        } catch(IllegalArgumentException e)
        {
            fixture.thenAssertEqualityCheckOnEditUser();
            fixture.thenThrowIllegalArgumentException();
        }
    }
}
class Fixture{
    private UserService userService = new UserService();
    private int expectedCount;
    private User userMock=Mockito.mock(User.class);
    public void givenUserIsInitialized(String name, int age, int id) {
        this.userMock.setName(name);
        this.userMock.setAge(age);
        this.userMock.setId(id);
    }
    public void givenUserIsInitializedAndAdded(String name, int age, int id) {
        this.userMock.setName(name);
        this.userMock.setAge(age);
        this.userMock.setId(id);
        this.userService.addUser(userMock);
    }
    public void whenUserIsAddedSuccessfully() {
        this.userService.addUser(userMock);
    }
    public void whenUserIsNotAddedSuccessfully() {
        when(userMock.getAge()).thenReturn(-1);     //specifying Mocked object to get Age=-1
        this.userService.addUser(userMock);
    }
    public void thenAssertionResultsInEquality(int expectedCount) {
        this.expectedCount= expectedCount;
        Assert.assertEquals(expectedCount,userService.getUsersCount());
    }
    public void thenThrowIllegalArgumentException() {
        throw new IllegalArgumentException();
    }
    public void thenThrowIndexOutOfBoundException() {
        throw new IndexOutOfBoundsException();
    }
    public void whenUserIsDeleted() {
        this.userService.removeUser(userMock);
    }

    public void givenUserIsReInitializedAfterAddition(String name, int age, int id) {
        userMock = new User();
        this.userMock.setName(name);
        this.userMock.setAge(age);
        this.userMock.setId(id);
    }
    public void whenUserIsEdited(String newName, int newAge) {
        String updatedName = newName;
        int updatedAge = newAge;
        this.userService.editUser(userMock.getId(),updatedName,updatedAge);
    }
    public void thenAssertEqualityCheckOnEditUser() {
        User updatedUser = userService.getUsers().get(userMock.getId());
        boolean checkEqual = Objects.equals(userMock,updatedUser);
        Assert.assertTrue(checkEqual);
    }
}
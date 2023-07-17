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
        fixture.whenUserIsAdded();
        fixture.thenCountOfUserIs(1);
    }
    @Test(expected = IllegalArgumentException.class)
    public void testAddUser_ageIsNegative_doesNotAddUser_throwsException()
    {
            fixture.givenUserIsInitialized("Ahmed",-10,0);
            fixture.whenUserIsAdded();
            fixture.thenCountOfUserIs(0);
            fixture.thenUserServiceThrewIllegalArgumentException();
    }

    @Test
    public void testRemoveUser_removesUser()
    {
        //GIVEN
        fixture.givenUserIsInitialized("Ahmed",10,1);
        fixture.whenUserIsAdded();  //to reduce redundancy in Fixture class
        //WHEN
        fixture.whenUserIsDeleted();
        //THEN
        fixture.thenCountOfUserIs(0);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testRemoveUser_userDoesNotExist_doesNotRemoveUser_throwsException()
    {
            //GIVEN
            fixture.givenUserIsInitialized("Ali",10,0); // User count=1
            fixture.whenUserIsAdded();
            fixture.givenUserIsReInitializedAfterAddition("Ahmed",11,1); // assigning same object to a new reference
            //WHEN
            fixture.whenUserIsDeleted();  // will look for Ahmed in usersArray, won't find it so won't delete, count remains 1
            //THEN
            fixture.thenCountOfUserIs(1);
            fixture.thenUserServiceThrewIndexOutOfBoundException();
    }

    @Test
    public void testEditUser_editsUserSuccessfully()
    {
        //GIVEN
        fixture.givenUserIsInitialized("Ahmed",10,0);
        fixture.whenUserIsAdded();
        //WHEN
        fixture.whenUserIsEdited("Ali",20);
        //THEN
        fixture.thenCheckIfUserIsEditedSuccessfully();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEditUser_enteredInvalidAttributes_doesNotEditUser_throwsException()
    {
            //GIVEN
            fixture.givenUserIsInitialized("Ahmed",10,0);
            fixture.whenUserIsAdded();
            //WHEN
            fixture.whenUserIsEdited("",0);
            //THEN
            fixture.thenCheckIfUserIsEditedSuccessfully();
            fixture.thenUserServiceThrewIllegalArgumentException();
    }
}
class Fixture{
    private UserService userService = new UserService();
    private int expectedCount;
    private User mockedUser=Mockito.mock(User.class);
    private Exception exception=new Exception();

    public void givenUserIsInitialized(String name, int age, int id) {
        this.mockedUser.setName(name);
        this.mockedUser.setAge(age);
        this.mockedUser.setId(id);
        if(age<0)
        {
            when(mockedUser.getAge()).thenReturn(-1);   //specifying Mocked object to put value in getAge as -1
        }
    }
    public void whenUserIsAdded() {
        try {
            this.userService.addUser(mockedUser);
        }
        catch (IllegalArgumentException e){
            this.exception=e;
        }
    }
    public void thenCountOfUserIs(int expectedCount) {
        this.expectedCount= expectedCount;
        Assert.assertEquals(expectedCount,userService.getUsers().size());
    }
    public void thenUserServiceThrewIllegalArgumentException() {
        Assert.assertEquals(IllegalArgumentException.class,exception.getClass());    //check if exception thrown is same as given
        throw new IllegalArgumentException();
    }
    public void thenUserServiceThrewIndexOutOfBoundException() {
        Assert.assertEquals(IndexOutOfBoundsException.class,exception.getClass());
        throw new IndexOutOfBoundsException();
    }
    public void whenUserIsDeleted() {
        try {
            this.userService.removeUser(mockedUser);
        }
        catch (IndexOutOfBoundsException e){
            this.exception=e;
        }

    }
    public void givenUserIsReInitializedAfterAddition(String name, int age, int id) {
        mockedUser = new User();
        this.mockedUser.setName(name);
        this.mockedUser.setAge(age);
        this.mockedUser.setId(id);
    }
    public void whenUserIsEdited(String newName, int newAge) {
        String updatedName = newName;
        int updatedAge = newAge;
        try {
            this.userService.editUser(mockedUser.getId(),updatedName,updatedAge);
        }
        catch (IllegalArgumentException e){
            this.exception=e;
        }
    }
    public void thenCheckIfUserIsEditedSuccessfully() {
        User updatedUser = userService.getUsers().get(mockedUser.getId());
        boolean checkEqual = Objects.equals(mockedUser,updatedUser);
        Assert.assertTrue(checkEqual);
    }
}
package test;

import main.User;
import org.junit.Test;
import org.junit.Assert;

/**
 * Test class for User.java
 */
public class UserTest
{
  private User user = new User();

  @Test
  public void test_getterSetterForName()
  {
    user.setName("Ali");
    String expectedName = "Ali";
    Assert.assertEquals(expectedName, user.getName());
  }

  @Test
  public void test_getterSetterForAge()
  {
    user.setAge(15);
    int expectedAge = 15;
    Assert.assertEquals(expectedAge, user.getAge());
  }
}

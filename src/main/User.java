package main;

import lombok.*;

/**
 * Represent a User with name, age and ID as attributes.
 */
public @Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor class User
{
  private String name;
  @NonNull
  private int age;
  @NonNull
  private int id;
}

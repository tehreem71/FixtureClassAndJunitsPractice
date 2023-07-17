package main;

import lombok.*;

public @Data @AllArgsConstructor @NoArgsConstructor @RequiredArgsConstructor class User {
    private String name;
    @NonNull
    private int age;
    @NonNull
    private int id;

}

package com.kenBank.pojo;

import lombok.*;

@Builder
@Data
@AllArgsConstructor
//@NoArgsConstructor
//@RequiredArgsConstructor // If we are using the constructor injection
public class Sample {
        String name;
        String address;
        int age;

}

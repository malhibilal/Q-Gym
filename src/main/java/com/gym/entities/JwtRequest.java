package com.gym.entities;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
public class JwtRequest {

   private String email;
   private String password;
}

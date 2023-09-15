package com.gym.entities;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
@Builder
public class JwtResponse {
   private String jwtToken;
   private String username;
}

package com.example.laza.otp;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@RedisHash(timeToLive = 3600 * 7)
public class OTP {

    @Id
    private String email;
    private String firstname;
    private String lastname;
    private int code;
    private LocalDateTime sendTime;
    private int sentCount;
}

package com.ssafy.pjt.dto;

import lombok.Data;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@Data
@RedisHash
@SuppressWarnings("all")
public class Token implements Serializable {

    private static final long serialVersionUID = -7353484588260422449L;
    private String username;
    private String refreshToken;

}

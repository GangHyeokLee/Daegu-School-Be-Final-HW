package com.example.finalhomework.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cid;

    //Security 규격에 맞게 항상 4개는 넣어준다.
    private String username; //회원 아이디, email 주소로 하면 많이 편하다.
    private String password;
    private boolean enabled;
    private String role;

    private String cimage;
    private String caddr;

    private LocalDateTime cdate;
}

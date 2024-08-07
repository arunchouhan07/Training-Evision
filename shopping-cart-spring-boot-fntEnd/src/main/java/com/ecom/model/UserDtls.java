package com.ecom.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@ToString
public class UserDtls {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String mobileNumber;

    private String email;

    private String address;

    private String city;

    private String state;

    private String pincode;

    private String password;

    private String profileImage;

    private String role;

	private Boolean isEnable;

	private Boolean accountNonLocked;

	private Integer failedAttempt;

	private Date lockTime;

	private String resetToken;

}

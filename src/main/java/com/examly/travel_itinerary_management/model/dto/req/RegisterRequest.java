package com.examly.travel_itinerary_management.model.dto.req;

import com.examly.travel_itinerary_management.model.User;
import lombok.Data;

@Data
public class RegisterRequest {

    private String username;

    private String email;

    private String password;

    private String firstName;

    private String lastName;

    private String phoneNumber;

    private User.Role role;

}
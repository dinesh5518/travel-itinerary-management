package com.examly.travel_itinerary_management.model.dto.req;

import lombok.Data;

@Data
public class LoginRequest {

    private String email;

    private String password;

}
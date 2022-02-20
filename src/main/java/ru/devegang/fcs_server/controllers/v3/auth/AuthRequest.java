package ru.devegang.fcs_server.controllers.v3.auth;

import lombok.Data;

@Data
public class AuthRequest {

    private String login;
    private String password;
}

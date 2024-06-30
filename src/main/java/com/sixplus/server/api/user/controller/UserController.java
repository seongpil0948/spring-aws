package com.sixplus.server.api.user.controller;

import com.sixplus.server.api.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestParam String id,
                                           @RequestParam String password,
                                           @RequestParam String gender,
                                           @RequestParam String userName,
                                           @RequestParam String displayName,
                                           @RequestParam String email,
                                           @RequestParam String phone,
                                           @RequestParam String avatar) {
        String result = userService.registerUser(id, password, gender, userName, displayName, email, phone, avatar);
        return ResponseEntity.ok(result);
    }
}
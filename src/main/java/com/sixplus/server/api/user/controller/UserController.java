package com.sixplus.server.api.user.controller;

import com.sixplus.server.api.user.repository.UserRepository;
import com.sixplus.server.api.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
    }

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

    @PostMapping("/getUser/{uid}")
    public ResponseEntity<String> getUser(@PathVariable("uid") String id) {
//        String result = userRepository.findById(id).get().toString();
        return ResponseEntity.ok("User not found");
    }

    @GetMapping("/list")
    public ResponseEntity<String> listUsers() {
//        String result = userRepository.findAll().toString();
        return ResponseEntity.ok("No users found");
    }
}
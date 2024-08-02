package com.sixplus.server.api.user.controller;

import com.sixplus.server.api.core.config.security.JwtTokenProvider;
import com.sixplus.server.api.model.Response;
import com.sixplus.server.api.user.model.AuthRequestDTO;
import com.sixplus.server.api.user.repository.UserRepository;
import com.sixplus.server.api.user.service.MemberService;
import com.sixplus.server.api.user.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;
    private final MemberService memberService;
    @Value("${security.jwt.token.valid-key:valid}")
    private List<String> validKeyList;


    public UserController(UserService userService, UserRepository userRepository, JwtTokenProvider jwtTokenProvider, MemberService memberService) {
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.memberService = memberService;
    }

    /*
     * 사용자 로그인
     */
    @PostMapping(value = "/login")
    public Response<?> userLogin(@Valid @RequestBody AuthRequestDTO vo) {
        return memberService.getUserInfoByUserId(vo);
    }


    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestParam String id,
                                           @RequestParam String password,
                                           @RequestParam String gender,
                                           @RequestParam String username,
                                           @RequestParam String displayName,
                                           @RequestParam String email,
                                           @RequestParam String phone,
                                           @RequestParam String avatar) {
        String result = userService.registerUser(id, password, gender, username, displayName, email, phone, avatar);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/getUser/{uid}")
    public Response<String> getUser(@PathVariable("uid") String id) {
//        String result = userRepository.findById(id).get().toString();
        return Response.ok("User not found");
    }

    @GetMapping("/list")
    public Response<String> listUsers() {
//        String result = userRepository.findAll().toString();
        return Response.ok("No users found");
    }
}
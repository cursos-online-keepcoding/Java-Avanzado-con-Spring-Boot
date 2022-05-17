package dms.study.security.controller;

import dms.study.security.entity.AuthRequest;
import dms.study.security.entity.User;
import dms.study.security.service.UserService;
import dms.study.security.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;


    @GetMapping("/")
    public String welcome() {
        return "Welcome to dms app";
    }

    @PostMapping("/sing-up")
    public User singUp(@RequestBody User user) {return userService.addUser(user); }


    @PostMapping("/authenticate")
    public String generateToken(@RequestBody AuthRequest authRequest) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassword()));
        } catch (Exception ex) {
            throw new Exception("Invalid username/password");
        }
        return jwtUtil.generateToken(authRequest.getUserName());
    }
}

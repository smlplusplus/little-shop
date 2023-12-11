package com.mds.system.littleshop.controller;

import com.mds.system.littleshop.dto.AuthResponseDTO;
import com.mds.system.littleshop.dto.LoginDTO;
import com.mds.system.littleshop.dto.SingUpDTO;
import com.mds.system.littleshop.model.Role;
import com.mds.system.littleshop.model.User;
import com.mds.system.littleshop.repository.IRoleRepository;
import com.mds.system.littleshop.repository.IUserRepository;
import com.mds.system.littleshop.security.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/api/auth/")
public class AuthController {
    private AuthenticationManager authenticationManager;
    private PasswordEncoder passwordEncoder;
    private IRoleRepository roleRepository;
    private IUserRepository userRepository;
    private JwtProvider jwtProvider;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, IRoleRepository roleRepository, IUserRepository userRepository, JwtProvider jwtProvider) {
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.jwtProvider = jwtProvider;
    }

    @PostMapping("singUp")
    public ResponseEntity<String> singUp(@RequestBody SingUpDTO singUpDTO) {
        if (userRepository.existsByUserName(singUpDTO.getUsername())) {
            return new ResponseEntity<>("The user already exist!.", HttpStatus.BAD_REQUEST);
        }
        User user = new User();
        user.setUserName(singUpDTO.getUsername());
        user.setPassword(passwordEncoder.encode(singUpDTO.getPassword()));
        Role role = roleRepository.findByName("USER").get();
        user.setRoles(Collections.singletonList(role));
        userRepository.save(user);
        return new ResponseEntity<>("Successful sing up.", HttpStatus.OK);
    }

    @PostMapping("login")
    public ResponseEntity<AuthResponseDTO> login (@RequestBody LoginDTO loginDTO){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDTO.getUsername(), loginDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtProvider.getToken(authentication);
        return new ResponseEntity<>(new AuthResponseDTO(token),HttpStatus.OK);
    }

}

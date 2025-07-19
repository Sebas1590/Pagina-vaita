package org.example.paginaacademia.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.paginaacademia.dto.LoginRequestDTO;
import org.example.paginaacademia.dto.UserDTO;
import org.example.paginaacademia.service.interfaces.IUserService;
import org.example.paginaacademia.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthController {

    private final IUserService iUserService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    @Value("${jwt.expirationMs}")
    private int expMs;
    private final PasswordEncoder passwordEncoder;


    @GetMapping("/register")
    public String registerForm(Model model){
        model.addAttribute("user", new UserDTO());
        return "register-user";
    }

    @PostMapping("/register")
    public String processRegister(@ModelAttribute("user") UserDTO dto){

        if (dto.getRoles() != null) {
            dto.setRoles(dto.getRoles());
        }
        iUserService.saveUser(dto);
        return "redirect:/login";
    }


    @GetMapping("/login")
    public String login(Model model, LoginRequestDTO loginRequestDTO){
        model.addAttribute("loginRequest",loginRequestDTO);
        return "login";
    }


    @PostMapping("/login")
    public String login(
            @ModelAttribute LoginRequestDTO requestDTO,
            Model model,
            HttpServletResponse response){

        try {
            Authentication authRequest = new UsernamePasswordAuthenticationToken(requestDTO.getUsername(), requestDTO.getPassword());

            Authentication authentication = authenticationManager.authenticate(authRequest);

            String token = jwtUtil.generateToken((UserDetails) authentication.getPrincipal());
            ResponseCookie cookieToken = ResponseCookie
                    .from("token", token)
                    .httpOnly(true)
                    .secure(false)
                    .path("/")
                    .domain("")
                    .maxAge(expMs)
                    .build();

            SecurityContextHolder.getContext().setAuthentication(authentication);

            response.addHeader(HttpHeaders.SET_COOKIE, cookieToken.toString());

            return "redirect:/inicio";



        }catch (BadCredentialsException e) {
            model.addAttribute("error", "Email o contraseña incorrectos");
            return "login";
        } catch (Exception e) {
            model.addAttribute("error", "Error en el servidor");
            return "login";
        }

    }

    @PostMapping("logout")
    public String logout(HttpServletRequest request, HttpServletResponse response){
        ResponseCookie cookie = ResponseCookie
                .from("token", "")
                .maxAge(0)
                .path("/")
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        return "redirect:/login?logout";

    }


}


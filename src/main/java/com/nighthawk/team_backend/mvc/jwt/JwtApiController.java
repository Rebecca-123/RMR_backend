package com.nighthawk.team_backend.mvc.jwt;

import java.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nighthawk.team_backend.mvc.database.team.Team;
import com.nighthawk.team_backend.mvc.database.team.TeamDetailsService;
import com.nighthawk.team_backend.mvc.database.team.TeamJpaRepository;

@RestController
@CrossOrigin
public class JwtApiController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private TeamDetailsService teamDetailsService;

    @Autowired // Inject ClubJpaRepository
    private TeamJpaRepository teamJpaRepository;

    @PostMapping("/authenticate")
    public ResponseEntity<MyResponse> createAuthenticationToken(@RequestBody Team authenticationRequest)
            throws Exception {
        authenticate(authenticationRequest.getEmail(), authenticationRequest.getPassword());
        final UserDetails userDetails = teamDetailsService
                .loadUserByUsername(authenticationRequest.getEmail());
        final String token = jwtTokenUtil.generateToken(userDetails);

        // Split the JWT into its three parts
        String[] jwtParts = token.split("\\.");

        // Decode the base64-encoded header and payload
        String payloadJson = new String(Base64.getUrlDecoder().decode(jwtParts[1]));

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode payload;
        payload = objectMapper.readTree(payloadJson);
        // Extract the value of a specific claim from the payload
        String email = payload.get("sub").asText();

        // find ID corresponding to email
        Team team = teamJpaRepository.findByEmail(email);
        Long id = team.getId();

        final ResponseCookie tokenCookie = ResponseCookie.from("jwt", token)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(3600)
                .sameSite("None; Secure")
                // .domain("example.com") // Set to backend domain
                .build();

        // combine id and cookie as MyResponse object
        MyResponse response = new MyResponse(id, tokenCookie);
        // return with cookie in header and MyRespone object in body
        return (ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, tokenCookie.toString()))
                .body(response);
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }
}

package S05T02DiceGameMongo.Model.Util;

import S05T02DiceGameMongo.Model.Domain.Player;
import S05T02DiceGameMongo.Model.Repository.PlayerRepository;
import S05T02DiceGameMongo.Model.Util.JwtUserDetailsService;
import S05T02DiceGameMongo.Model.Util.JwtTokenUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
@RestController
@RequestMapping(value = "/auth")
public class AuthController {

    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    final PlayerRepository playerRepository;
    final AuthenticationManager authenticationManager;
    final JwtUserDetailsService userDetailsService;
    final JwtTokenUtil jwtTokenUtil;

    public AuthController(PlayerRepository playerRepository, AuthenticationManager authenticationManager,
                          JwtUserDetailsService userDetailsService, JwtTokenUtil jwtTokenUtil) {
        this.playerRepository = playerRepository;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestParam("user_name") String userName,
                                       @RequestParam("password") String password) {
        Map<String, Object> responseMap = new HashMap<>();
        try {
            Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName
                    , password));
            if (auth.isAuthenticated()) {
                logger.info("Logged In");
                UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
                String token = jwtTokenUtil.generateToken(userDetails);
                responseMap.put("error", false);
                responseMap.put("message", "Logged In");
                responseMap.put("token", token);
                return ResponseEntity.ok(responseMap);
            } else {
                responseMap.put("error", true);
                responseMap.put("message", "Invalid Credentials");
                return ResponseEntity.status(401).body(responseMap);
            }
        } catch (DisabledException e) {
            e.printStackTrace();
            responseMap.put("error", true);
            responseMap.put("message", "User is disabled");
            return ResponseEntity.status(500).body(responseMap);
        } catch (BadCredentialsException e) {
            responseMap.put("error", true);
            responseMap.put("message", "Invalid Credentials");
            return ResponseEntity.status(401).body(responseMap);
        } catch (Exception e) {
            e.printStackTrace();
            responseMap.put("error", true);
            responseMap.put("message", "Something went wrong");
            return ResponseEntity.status(500).body(responseMap);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> saveUser(@RequestParam("player_id") String playerId,
                                      @RequestParam("user_name") String playerName,
                                      @RequestParam("password") String password) {
        Map<String, Object> responseMap = new HashMap<>();
        Player player = new Player();
        player.setId(playerId);
        player.setRegDate(new Date());
        player.setPassword(new BCryptPasswordEncoder().encode(password));
        player.setRole("USER");
        player.setUserName(playerName);
        UserDetails userDetails = userDetailsService.createUserDetails(playerName, player.getPassword());
        String token = jwtTokenUtil.generateToken(userDetails);
        playerRepository.save(player);
        responseMap.put("error", false);
        responseMap.put("username", playerName);
        responseMap.put("message", "Account created successfully");
        responseMap.put("token", token);
        return ResponseEntity.ok(responseMap);
    }
    /**@PostMapping("/logout")
    public ResponseEntity<?> logoutUser(@RequestParam("token") String token) {
        Map<String, Object> responseMap = new HashMap<>();
        try {
            String username = jwtTokenUtil.getUsernameFromToken(token);
            if (username!= null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                jwtTokenUtil.removeToken(token);
                responseMap.put("error", false);
                responseMap.put("message", "Logged out successfully");
                return ResponseEntity.ok(responseMap);
            }
    }**/
}

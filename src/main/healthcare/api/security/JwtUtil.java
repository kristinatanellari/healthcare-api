package main.healthcare.api.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import main.healthcare.api.model.Doctors;
import main.healthcare.api.model.Patients;
import main.healthcare.api.model.Pharmacist;
import main.healthcare.api.model.Users;
import main.healthcare.api.repository.DoctorsRepository;
import main.healthcare.api.repository.PatientsRepository;
import main.healthcare.api.repository.PharmacistRepository;
import main.healthcare.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

@Service
public class JwtUtil {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PatientsRepository patientsRepository;

    @Autowired
    private DoctorsRepository doctorsRepository;

    @Autowired
    private PharmacistRepository pharmacistRepository;

    @Value("${app.secret_key}")
    private String secret;


    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }


    public Long extractId(String token) {
        Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        return ((Integer) (claims.get("id"))).longValue();
    }


    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(String username) {
        Users user = userRepository.findByUsername(username);
        Map<String, Object> claims = new HashMap<>();
        if (user.getRole() != null) {
            claims.put("role", user.getRole());
            if (Objects.equals(user.getRole(), "PATIENT")) {
                Patients p = patientsRepository.findByUsername(username);
                claims.put("id", p.getPatientId());
            }

            if (Objects.equals(user.getRole(), "DOCTOR")) {
                Doctors d = doctorsRepository.findByUsername(username);
                claims.put("id", d.getDoctorId());
            }


            if (Objects.equals(user.getRole(), "PHARMACIST")) {
                Pharmacist f = pharmacistRepository.findByUsername(username);
                claims.put("id", f.getPharmacistId());
            }
        }
        return createToken(claims, username);
    }

    private String createToken(Map<String, Object> claims, String subject) {

        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, secret).compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}


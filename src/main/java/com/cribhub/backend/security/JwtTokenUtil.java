package com.cribhub.backend.security;


import com.cribhub.backend.domain.Customer;
import com.cribhub.backend.repositories.CustomerRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Optional;
import java.util.function.Function;

@Component
public class JwtTokenUtil {

    SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256); // Create a secure key

    private final CustomerRepository customerRepository;

    // Inject CustomerRepository
    public JwtTokenUtil(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public String createToken(String username) {
        Optional<Customer> customerOptional = customerRepository.findByEmail(username);
        if (customerOptional.isEmpty()) {
            throw new IllegalArgumentException("User not found with email: " + username);
        }

        Customer customer = customerOptional.get();

        Claims claims = Jwts.claims().setSubject(username);
        claims.put("customerId", customer.getUserId()); // Add customerId as a claim
        claims.put("customerName", customer.getUserName()); // Add customerName as a claim


        Date now = new Date();
        // 1 hour
        long validityInMilliseconds = 3600000;
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public Long extractCustomerId(String token) {
        return extractClaim(token, claims -> claims.get("customerId", Long.class));
    }

    public String extractCustomerName(String token) {
        return extractClaim(token, claims -> claims.get("customerName", String.class));
    }


    Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }

    public Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }


}
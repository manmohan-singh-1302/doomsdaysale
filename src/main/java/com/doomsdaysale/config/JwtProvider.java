// this class is used to create and extract the JWT Token.
// A JWT token consist of three parts i.e. header, payload and signature. header contains the type of token i.e. JWT and the signing algorithm i.e. HS256 with SHA-256.
// the payload consists of claims which are statements about an entity like user (which contains name, email, roles etc).
// signature is used to verify the integrity of the JWT Token and authenticate the sender of JWT. It is created using the encoded header, encoded payload, and a secret key.


package com.doomsdaysale.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority; // returns all the roles or authorities granted to the authenticated user.
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Service
public class JwtProvider {
    // this defines the secret key which is used to sign the JWT Token.
    SecretKey key = Keys.hmacShaKeyFor(JWT_CONSTANT.SECRET_KEY.getBytes()); // hmac - hash-based message authentication code.

    // used to create a JWT token
    public String generateToken(Authentication auth){
        // this indicates that the collection can hold objects of any type which indicates GrantedAuthority
        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();

        // roles string contains all the authorities granted.
        String roles = populateAuthorities(authorities);

        // the Jwts.builder() is used to create a builder object for building the JWT.
        return Jwts.builder().
                issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+86400000))
                .claim("email", auth.getName()) // this adds a claim to the JWT which contains users email. auth.getName() returns the user's email.
                .claim("authorities", roles) // this adds a claim to the JWT which contains roles and authorities comma seperated.
                .signWith(key) // signs the JWT with the secret key to ensure integrity
                .compact(); // Builds the JWT and returns as it a string.
    }

    public String getEmailFromJwtToken(String jwt){
        // the jwt is sent in the form of bearer <jwt_token>
        jwt = jwt.substring(7);
      //  Claims claims = Jwts.parser().setSigningKey(key).build().parseClaimsJws(jwt).getBody();
        Claims claims = Jwts.parser().verifyWith(key).build().parseSignedClaims(jwt).getPayload();
        return String.valueOf(claims.get("email"));
    }

    private String populateAuthorities(Collection<? extends GrantedAuthority> authorities) {
        Set<String> auths = new HashSet<>();
        for(GrantedAuthority authority: authorities){
            auths.add(authority.getAuthority());
        }
        return String.join(",", auths);
    }
}

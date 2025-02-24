// this will verify the JWT token for authentication. We need JWTToken dependency jjwt-api, jjwt-impl, jjwt-jackson.
package com.doomsdaysale.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.List;

public class JwtTokenValidator extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwt = request.getHeader("Authorization");
        if(jwt!=null){
            // because we will get the actual jwt token from the seventh index of the string. It is in the format of BearerJWT.
            jwt = jwt.substring(7);
            try{
                // we need to have the secret key stored in the JWT_CONSTANT class inorder to create a HMAC SHA key to validate the signature of the JWT Token.
                SecretKey key = Keys.hmacShaKeyFor(JWT_CONSTANT.SECRET_KEY.getBytes());

                // using the Jwts.parser().setSigningKey().build.parseClaimsJws() verifies the JWT token using the secret key
                // .getBody() method is used to extract the claims from the requests payload like email, roles etc.
                //Claims claims = Jwts.parser().setSigningKey(key).build().parseClaimsJws(jwt).getBody(); //replace the below line with this if things does not goes right.

                Claims claims = Jwts.parser().verifyWith(key).build().parseSignedClaims(jwt).getPayload();

                // now we can extract the email and authorities from the jwt.
                String email = String.valueOf(claims.get("email"));
                String authorities = String.valueOf(claims.get("authorities"));

                // the authorities string is converted into a list of granted authorities
                List<GrantedAuthority> auths = AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);

                // the email and authorities are used to create an authentication object.
                Authentication authentication = new UsernamePasswordAuthenticationToken(email,null,auths);

                // this sets the authentication in the spring security, which lets the user access protected resources based on the roles.
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (Exception e) {
                throw new BadCredentialsException("Invalid JWT Token....");
            }
        }
        // after validation the JWT Token and setting the authentication context the request is passed along the next filter in chain.
        filterChain.doFilter(request, response);
    }
}

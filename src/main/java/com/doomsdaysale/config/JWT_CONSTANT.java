// this class provide all the parameters used for JWT validation like the secret key which is used to verify the signature of the JWT token.
package com.doomsdaysale.config;

public class JWT_CONSTANT {
    public static final String SECRET_KEY = "dfasdfasrdfavbg34523jdrrjgdsdfgasdfgfhbxfhsf0";
    public static final String JWT_HEADER = "Authorization"; // JWT Token is sent through a HTML Authorization header using the Bearer schema like Authorization: Bearer <jwt_token>
}

package ru.vsu.hb.security;

public class SecurityConstants {

    public static final String SECRET = "ThisIsTheMostSecuritySecretKeyItWasGeneratedByAndrewAndArtyom";
    public static final long EXPIRATION_TIME = Long.MAX_VALUE; // 10 days
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/user/register";
}
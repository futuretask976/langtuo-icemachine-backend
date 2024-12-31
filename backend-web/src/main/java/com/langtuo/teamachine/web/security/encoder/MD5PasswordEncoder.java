package com.langtuo.teamachine.web.security.encoder;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Slf4j
public class MD5PasswordEncoder implements PasswordEncoder {
    private static final String SALT = "TEAMACHINE";

    @Override
    public String encode(CharSequence rawPassword) {
        String encoded = DigestUtils.md5DigestAsHex(
                (rawPassword.toString() + SALT).getBytes(StandardCharsets.UTF_8));
        return encoded;
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        // rawPassword 是需要验证的原密码，encodedPassword 是加密后数据库中保存的密码，
        // 这个方法不需要手动调用，而是由 Spring Security 调用，这里写好规则即可
        if (rawPassword != null && encodedPassword != null) {
            return encode(rawPassword).equals(encodedPassword)
                    || rawPassword.equals(encodedPassword);
        } else {
            return false;
        }
    }
}

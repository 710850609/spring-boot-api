package me.linbo.api.user;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

/**
 * @author LinBo
 * @date 2020-01-06 16:09
 */
public class PasswordEncoderTest {

    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    Pbkdf2PasswordEncoder pbkdf2PasswordEncoder = new Pbkdf2PasswordEncoder("");

    String pwd = "123";

    @Test
    public void testBCrypt() {
        String encode = bCryptPasswordEncoder.encode(pwd);
        System.out.println(encode + " " + bCryptPasswordEncoder.matches(pwd, encode));
        encode = bCryptPasswordEncoder.encode(pwd);
        System.out.println(encode + " " + bCryptPasswordEncoder.matches(pwd, encode));
    }


    @Test
    public void testPbkdf2() {
        String encode = pbkdf2PasswordEncoder.encode(pwd);
        System.out.println(encode + " " + pbkdf2PasswordEncoder.matches(pwd, encode));
        encode = pbkdf2PasswordEncoder.encode(pwd);
        System.out.println(encode + " " + pbkdf2PasswordEncoder.matches(pwd, encode));
    }

}

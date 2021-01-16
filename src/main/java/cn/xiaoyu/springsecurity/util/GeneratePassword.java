package cn.xiaoyu.springsecurity.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * BCryptPasswordEncoder使用哈希算法+随机盐来对字符串加密。
 * 因为哈希是一种不可逆算法，所以密码认证时需要使用相同的算法+盐值来对待校验的明文进行加密，然后比较这两个密文来进行验证。
 */
public class GeneratePassword {
    public static String generatePassword(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }

    public static void main(String[] args) {
        System.out.println(generatePassword("pwd"));
    }
}

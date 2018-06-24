package io.xiaoyaoyou.xmall.user.support;

import org.springframework.security.crypto.bcrypt.BCrypt;

/**
 * Created by xiaoyaoyou on 2018/5/9.
 */
public class AuthenticationUtil {
    private static final int workload = 12;

    public static String encryptPwd(String plainPwd) {
        return BCrypt.hashpw(plainPwd, BCrypt.gensalt(workload));
    }

    public static boolean checkPassword(String plainPwd,String encryptPwd) {
        return BCrypt.checkpw(plainPwd, encryptPwd);
    }
}

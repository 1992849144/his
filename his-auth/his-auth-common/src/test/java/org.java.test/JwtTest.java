package org.java.test;



import org.java.auth.pojo.UserInfo;
import org.java.auth.utils.JwtUtils;
import org.java.auth.utils.RsaUtils;
import org.junit.Before;
import org.junit.Test;

import java.security.PrivateKey;
import java.security.PublicKey;

public class JwtTest {

    //生成公钥的地址
    private static final String pubKeyPath = "D:\\project\\his\\tmp\\rsa\\rsa.pub";

    //生成私钥的地址
    private static final String priKeyPath = "D:\\project\\his\\tmp\\rsa\\rsa.pri";

    private PublicKey publicKey;

    private PrivateKey privateKey;

    /**
     * 生成公钥、私钥
     * 234:它是指定的加密种子，作用，相当于盐
     * @throws Exception
     */
    @Test
    public void testRsa() throws Exception {
        RsaUtils.generateKey(pubKeyPath, priKeyPath, "234");
    }

    @Before
    public void testGetRsa() throws Exception {
        //把公钥的地址赋值给publicKey
        this.publicKey = RsaUtils.getPublicKey(pubKeyPath);
        //把私钥的地址赋值给privateKey
        this.privateKey = RsaUtils.getPrivateKey(priKeyPath);
    }

    /**
     * 生成token，加密
     * @throws Exception
     */
    @Test
    public void testGenerateToken() throws Exception {
        // 生成token
        //new UserInfo：封装的用户信息
        //new UserInfo(99L, "christina"), privateKey, 5)
        // new UserInfo()：第一个参数是：id，第二个参数是私钥，第三个参数是token的有效时间，单位是分钟
        String token = JwtUtils.generateToken(new UserInfo("99L", "christina","库房管理员","111"), privateKey, 5);
        System.out.println("token = " + token);
    }


    /**
     * 解析token，获得用户信息，解密
     * @throws Exception
     */
    @Test
    public void testParseToken() throws Exception {
        String token = "eyJhbGciOiJSUzI1NiJ9.eyJpZCI6ImU0NmI3MWIzMDgyMDQ2ZDliYThjZjk4NjhiMWQ4Zjg3IiwidXNlcm5hbWUiOiJqYWNrIiwibmFtZSI6IuW6k-aIv-euoeeQhuWRmCIsInBpY3R1cmUiOiJodHRwOi8vaW1nLmhpcy5jb20vZ3JvdXAxL00wMC8wMC8wMC93S2hUZ2w3QkVDYUFNOFFEQUFGZjVhdkliZkE2NTIuanBnIiwiZXhwIjoxNTg5NzcyMDk0fQ.fZvuOzeaBpFs_ncfDypbZ96ha3X19YhQk-Y0KolC4oMtTbt-O9ig3Nq_hi8q4hrw7Preg12IzFDj56sMN66fZ31bODDo40aVbeHR-HVbVDtS4mMMWtJyVX-0CjfVqXR2jOmDf3J7vuMU49fBesBeDHrfQm4jhLHSmzqLHaeJStQ";

        // 解析token
        UserInfo user = JwtUtils.getInfoFromToken(token, publicKey);
        System.out.println("id: " + user.getId());
        System.out.println("userName: " + user.getUsername());
        System.out.println("name: " + user.getName());
        System.out.println("picture: " + user.getPicture());
    }
}
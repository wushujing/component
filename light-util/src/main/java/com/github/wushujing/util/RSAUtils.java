package com.github.wushujing.util;

import com.github.wushujing.exception.LightException;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.util.Assert;

import javax.crypto.Cipher;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 *
 * RSA加密解密
 */
public final class RSAUtils {

    /** 安全服务提供者 */
    private static final Provider PROVIDER = new BouncyCastleProvider();

    /** 密钥大小 */
    private static final int KEY_SIZE = 1024;

    private static final String KEY_ALGORITHM = "RSA";
    private static final String SIGNATURE_ALGORITHM = "SHA1withRSA";

    /**
     * 不可实例化
     */
    private RSAUtils() {
    }

    /**
     * 生成密钥对
     *
     * @return 密钥对
     */
    public static KeyPair generateKeyPair() {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA", PROVIDER);
            keyPairGenerator.initialize(KEY_SIZE, new SecureRandom());
            return keyPairGenerator.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            //e.printStackTrace();
            throw new LightException("生成密钥对异常",e);
        }
    }

    /**
     * 加密
     *
     * @param publicKey 公钥
     * @param data 数据
     * @return 加密后的数据
     */
    public static byte[] encrypt(PublicKey publicKey, byte[] data) {
        Assert.notNull(publicKey,"publicKey不能为空");
        Assert.notNull(data,"data不能为空");
        try {
            Cipher cipher = Cipher.getInstance("RSA/None/PKCS1Padding", PROVIDER);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            return cipher.doFinal(data);
        } catch (Exception e) {
            throw new LightException("加密异常",e);
        }
    }

    /**
     * 加密
     *
     * @param publicKey  公钥
     * @param text 字符串
     * @return Base64编码字符串
     */
    public static String encrypt(PublicKey publicKey, String text) {
        Assert.notNull(publicKey,"publicKey不能为空");
        Assert.notNull(text,"text不能为空");
        byte[] data = encrypt(publicKey, text.getBytes());
        return data != null ? new String(Base64.getEncoder().encode(data)) : null;
    }

    /**
     * 解密
     *
     * @param privateKey  私钥
     * @param data 数据
     * @return 解密后的数据
     */
    public static byte[] decrypt(PrivateKey privateKey, byte[] data) {
        Assert.notNull(privateKey,"privateKey不能为空");
        Assert.notNull(data,"data不能为空");
        try {
            Cipher cipher = Cipher.getInstance("RSA/None/PKCS1Padding", PROVIDER);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            return cipher.doFinal(data);
        } catch (Exception e) {
            throw new LightException("解密异常",e);
        }
    }

    /**
     * 解密
     *
     * @param privateKey 私钥
     * @param text Base64编码字符串
     * @return 解密后的数据
     */
    public static String decrypt(PrivateKey privateKey, String text) {
        Assert.notNull(privateKey,"privateKey不能为空");
        Assert.notNull(text,"text不能为空");
        byte[] data = decrypt(privateKey, Base64.getDecoder().decode(text));
        return data != null ? new String(data) : null;
    }

    public static PublicKey getPublicKey(String key) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {

        // 解密由base64编码的公钥
        byte[] keyBytes = Base64.getDecoder().decode(key.getBytes());
        // 构造X509EncodedKeySpec对象
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        // KEY_ALGORITHM 指定的加密算法
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        // 取公钥匙对象

        return keyFactory.generatePublic(keySpec);
    }

    public static boolean verify(byte[] data, String publicKey, String sign) throws Exception {
        // 解密由base64编码的公钥
        byte[] keyBytes = Base64.getDecoder().decode(publicKey.getBytes());
        // 构造X509EncodedKeySpec对象
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        // KEY_ALGORITHM 指定的加密算法
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        // 取公钥匙对象
        PublicKey pubKey = keyFactory.generatePublic(keySpec);
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(pubKey);
        signature.update(data);
        // 验证签名是否正常
        return signature.verify(Base64.getDecoder().decode(sign.getBytes()));
    }

}

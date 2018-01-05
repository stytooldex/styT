package nico.styTool;

/**
 * Created by lum on 2017/10/20.
 */

import android.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


final class AESSecureFactory implements Se_s.SecureFactory {
    private final static String SEEDS = "e2aJioOw23#_d2A";
    private final byte[] iv = new byte[]{'w', 'V', 'X', '9', 'M', '*', '(', '+'};

    @Override
    public String encode(String k) {
        try {
            if (k == null) return null;

            // 实例化IvParameterSpec对象，使用指定的初始化向量
            IvParameterSpec spec = new IvParameterSpec(iv);
            // 实例化SecretKeySpec类,根据字节数组来构造SecretKeySpec
            SecretKeySpec key = new SecretKeySpec(k.getBytes(), "DES");
            // 创建密码器
            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            // 用密码初始化Cipher对象
            cipher.init(Cipher.ENCRYPT_MODE, key, spec);
            // 执行加密操作
            byte[] encryptData = cipher.doFinal(k.getBytes());
            // 返回加密后的数据
            return Base64.encodeToString(encryptData, Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return k;
    }

    @Override
    public String decode(String k) {
        try {
            if (k == null) return null;

            // 先使用Base64解密
            byte[] base64byte = Base64.decode(k, Base64.DEFAULT);
            // 实例化IvParameterSpec对象，使用指定的初始化向量
            IvParameterSpec spec = new IvParameterSpec(iv);
            // 实例化SecretKeySpec类,根据字节数组来构造SecretKeySpec
            SecretKeySpec key = new SecretKeySpec(k.getBytes(), "DES");
            // 创建密码器
            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            // 用密码初始化Cipher对象
            cipher.init(Cipher.DECRYPT_MODE, key, spec);
            // 获取解密后的数据
            byte decryptedData[] = cipher.doFinal(base64byte);
            // 将解密后数据转换为字符串输出
            return new String(decryptedData);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
package nico.styTool;


import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * AESUtil Advanced Encryption Standard
 * 高级加密标准
 */
public class AESUtil {

    /**
     * 加密
     *
     * @param sSrc 加密源
     * @param sKey 加密的key
     * @return 加密后内容
     * @throws Exception
     */
    public static String encrypt(String sSrc, String sKey) throws Exception {

        if (sKey == null) {
            //Log.e("Key为空null", "Key为空null");
            return null;
        }
        // 判断Key是否为16位
        if (sKey.length() != 16) {
            //Log.e("\"Key长度不是16位\"", "Key长度不是16位");
            return null;
        }
        byte[] raw = sKey.getBytes();
        SecretKeySpec sKeySpec = new SecretKeySpec(raw, "AESUtil");
        Cipher cipher = Cipher.getInstance("AESUtil/CBC/PKCS5Padding");//"算法/模式/补码方式"
        IvParameterSpec iv = new IvParameterSpec("1234567890123456".getBytes());//使用CBC模式，需要一个向量iv，可增加加密算法的强度
        cipher.init(Cipher.ENCRYPT_MODE, sKeySpec, iv);
        byte[] encrypted = cipher.doFinal(sSrc.getBytes());

        return Base64_2Util.encode(encrypted);//此处使用BASE64做转码功能，同时能起到2次加密的作用。
    }


    /**
     * 解密
     *
     * @param sSrc 解密源
     * @param sKey 解密的key
     * @return 解密后内容
     * @throws Exception
     */

    public static String decrypt(String sSrc, String sKey) throws Exception {
        try {
            // 判断Key是否正确
            if (sKey == null) {
                //Log.e("Key为空null", "Key为空null");
                return null;
            }
            // 判断Key是否为16位
            if (sKey.length() != 16) {
                //Log.e("\"Key长度不是16位\"", "Key长度不是16位");
                return null;
            }
            byte[] raw = sKey.getBytes("ASCII");
            SecretKeySpec sKeySpec = new SecretKeySpec(raw, "AESUtil");
            Cipher cipher = Cipher.getInstance("AESUtil/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec("1234567890123456".getBytes());
            cipher.init(Cipher.DECRYPT_MODE, sKeySpec, iv);
            byte[] encrypted1 = Base64_2Util.decode(sSrc);//先用base64解密
            try {
                byte[] original = cipher.doFinal(encrypted1);
                return new String(original);
            } catch (Exception e) {
                System.out.println(e.toString());
                return null;
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }
    }
}

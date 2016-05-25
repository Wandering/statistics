package cn.thinkjoy.jx.statistics.util;

import org.apache.commons.lang.math.RandomUtils;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;


/**
 * 随机的验证码生成工具（数字或字母加数字）
 * 
 *
 */
public class RandomCodeUtil {

    private static final String BASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    /**
     * 生成指定长度的数字验证码
     *
     * @param length
     * @return
     */
    public static String generateNumCode(int length) {

        StringBuilder code = new StringBuilder();
        for (int i = 0; i < length; i++) {
            code.append(RandomUtils.nextInt(10));
        }
        return code.toString();
    }

    /**
     * 生成制定长度的字母加数字验证码
     *
     * @param length
     * @return
     */
    public static String generateCharCode(int length) {
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < length; i++) {
            code.append(generateOneChar());
        }
        return code.toString();
    }

    private static char generateOneChar() {
        int index = RandomUtils.nextInt(36);
        return BASE.charAt(index);
    }


    /**
     * 加密数据
     * @param encryptString  注意：这里的数据长度只能为8的倍数
     * @param encryptKey
     * @return
     * @throws Exception
     */
    public static String encryptDES(String encryptString, String encryptKey) throws Exception {
        SecretKeySpec key = new SecretKeySpec(getKey(encryptKey), "DES");
        Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedData = cipher.doFinal(encryptString.getBytes());
        return bytesToHexString(encryptedData);
    }

    /**
     * 自定义一个key
     * @param keyRule
     */
    public static byte[] getKey(String keyRule) {
        Key key = null;
        byte[] keyByte = keyRule.getBytes();
        // 创建一个空的八位数组,默认情况下为0
        byte[] byteTemp = new byte[8];
        // 将用户指定的规则转换成八位数组
        for (int i = 0; i < byteTemp.length && i < keyByte.length; i++) {
            byteTemp[i] = keyByte[i];
        }
        key = new SecretKeySpec(byteTemp, "DES");
        return key.getEncoded();
    }

    /***
     * 解密数据
     * @param decryptString
     * @param decryptKey
     * @return
     * @throws Exception
     */
    public static String decryptDES(String decryptString, String decryptKey) throws Exception {
        SecretKeySpec key = new SecretKeySpec(getKey(decryptKey), "DES");
        Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding");
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte decryptedData[] = cipher.doFinal(hexStringToByte(decryptString));
        return new String(decryptedData);
    }

    /**
     * 把16进制字符串转换成字节数组
     *
     * @param hex
     * @return
     */
    public static byte[] hexStringToByte(String hex) {
        int len = (hex.length() / 2);
        byte[] result = new byte[len];
        char[] achar = hex.toCharArray();
        for (int i = 0; i < len; i++) {
            int pos = i * 2;
            result[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));
        }
        return result;
    }

    private static byte toByte(char c) {
        byte b = (byte) "0123456789abcdef".indexOf(c);
        return b;
    }

    /**
     * 把字节数组转换成16进制字符串
     *
     * @param bArray
     * @return
     */
    public static final String bytesToHexString(byte[] bArray) {
        if(bArray == null )
        {
            return "";
        }
        StringBuffer sb = new StringBuffer(bArray.length);
        String sTemp;
        for (int i = 0; i < bArray.length; i++) {
            sTemp = Integer.toHexString(0xFF & bArray[i]);
            if (sTemp.length() < 2)
                sb.append(0);
            sb.append(sTemp);
        }
        return sb.toString();
    }


    public static void main(String[] args) throws Exception {
//        String clearText = "1910095673|1447134382100";  //这里的数据长度必须为8的倍数
//        String key = "aD2u1(qj";
//        System.out.println("明文："+clearText+"\n密钥："+key);
//        String encryptText = encryptDES(clearText, key);
//        System.out.println("加密后：" + encryptText);
//        String decryptText = decryptDES("30905331bc0f092ca97ddc8c1d9d005134d79c0b5a1fd049", key);
//        System.out.println("解密后："+decryptText);

//        System.out.println(MD5Util.getMD5String("000000"));

//        String date = new java.text.SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date());
//        System.out.println("转换后的时间为：" + date);

        System.out.println(generateCharCode(10).toLowerCase());
    }


}
package com.xiaoshihua.thinkpad.democnode.utils;


import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import okhttp3.internal.http.StreamAllocation;

/**
 * Created by ThinkPad on 2016/8/6.
 */
public class Crypt {

    public static final Crypt AES = new Crypt("AES",16,16);

    private final String algorithm;
    private final int secretLength;
    private final int ivLength;

    public Crypt(String algorithm, int secretLength, int ivLength) {
        this.algorithm = algorithm;
        this.secretLength = secretLength;
        this.ivLength = ivLength;
    }

    public SecretKey generateSecrect(byte[] seed){
        return new SecretKeySpec(Arrays.copyOf(seed,secretLength),algorithm);
    }

    public IvParameterSpec generateTV(byte[] seed){
        return new IvParameterSpec(Arrays.copyOf(seed,ivLength));
    }

    public byte[] encrypt(SecretKey key,IvParameterSpec iv,byte[] data) throws CryptException  {
        try {
            Cipher cipher = Cipher.getInstance(algorithm + "/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE,key,iv);
            return cipher.doFinal(data);
        } catch (NoSuchAlgorithmException | InvalidKeyException | InvalidAlgorithmParameterException | NoSuchPaddingException | BadPaddingException | IllegalBlockSizeException e) {
            throw new CryptException("Decrypt error", e);
        }
    }
    public byte[] encrypt( SecretKey secret,  byte[] data) throws CryptException {
        try {
            Cipher cipher = Cipher.getInstance(algorithm + "/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secret);
            return cipher.doFinal(data);
        } catch (NoSuchAlgorithmException | InvalidKeyException | NoSuchPaddingException | BadPaddingException | IllegalBlockSizeException e) {
            throw new CryptException("Encrypt error", e);
        }
    }

    public byte[] decrypt(SecretKey secret,  IvParameterSpec iv, byte[] data) throws CryptException {
        try {
            Cipher cipher = Cipher.getInstance(algorithm + "/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secret, iv);
            return cipher.doFinal(data);
        } catch (NoSuchAlgorithmException | InvalidKeyException | InvalidAlgorithmParameterException | NoSuchPaddingException | BadPaddingException | IllegalBlockSizeException e) {
            throw new CryptException("Decrypt error", e);
        }
    }

    public byte[] decrypt( SecretKey secret,  byte[] data) throws CryptException {
        try {
            Cipher cipher = Cipher.getInstance(algorithm + "/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secret);
            return cipher.doFinal(data);
        } catch (NoSuchAlgorithmException | InvalidKeyException | NoSuchPaddingException | BadPaddingException | IllegalBlockSizeException e) {
            throw new CryptException("Decrypt error", e);
        }
    }

    public static  class CryptException extends Exception{
        public CryptException(String message, Throwable cause) {
            super(message, cause);
        }
    }

}

package com.xiaoshihua.thinkpad.democnode.utils;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by ThinkPad on 2016/8/6.
 */
public class Digest {

    public static final Digest MD5 = new Digest("MD5");
    public static final Digest SHA256 = new Digest("SHA-256");
    private static final Charset CHARSET_UTF_8 = Charset.forName("UTF-8");

    private final String algorithm;

    private Digest( String algorithm) {
        this.algorithm = algorithm;
    }

    public byte[] getRaw( byte[] data) {
        try {
            return MessageDigest.getInstance(algorithm).digest(data);
        } catch (NoSuchAlgorithmException e) {
            throw new AssertionError(e);
        }
    }

    public byte[] getRaw( String data) {
        return getRaw(data.getBytes(CHARSET_UTF_8));
    }

    public String getHex( byte[] data) {
        StringBuilder sb = new StringBuilder();
        for (byte b : getRaw(data)) {
            sb.append(String.format("%02x", 0xFF & b));
        }
        return sb.toString();
    }

    public String getHex( String data) {
        return getHex(data.getBytes(CHARSET_UTF_8));
    }

}

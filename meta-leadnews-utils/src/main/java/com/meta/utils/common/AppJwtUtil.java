package com.meta.utils.common;

import io.jsonwebtoken.CompressionCodecs;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.*;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.*;

public class AppJwtUtil {

    //TOKEN的有效期1天
    private static final int TOKEN_TIME_OUT = 3_600;
    //加密key
    private static final String TOKEN_ENCRY_KEY = "MDk4ZjZiY2Q0NjIxZDM3M2NhZGU0ZTgzMjYyN2I0ZjY";
    //最小刷新间隔（S）
    private static  final int REFRESH_TIME = 300;

    //getToken生产JWT令牌，并由调用者将其放入Token
    public static String getToken(Long id){
        HashMap<String, Object> claimMaps = new HashMap<>();
        claimMaps.put("id",id);
        //获取当前总毫秒数  从1970年1月1日开始计算
        //new Date() 设置时间格式
        long currentTime = System.currentTimeMillis();
        return Jwts.builder()
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(new Date(currentTime)) //签发时间
                .setSubject("system") //说明
                .setIssuer("meta") //签发者信息
                .setAudience("app") //接收用户
                .compressWith(CompressionCodecs.GZIP) //数据压缩方式
                .signWith(SignatureAlgorithm.HS512,generalKey()) //加密方式
                .setExpiration(new Date(currentTime + TOKEN_TIME_OUT * 1000))  //过期时间戳
                .addClaims(claimMaps) //cla信息
                .compact();
    }

    /**
     * 由字符串生成加密key
     *
     * @return
     */
    public static SecretKey generalKey() {
        //getEncoder().encode()将TOKEN_ENCRY_KEY加密为字节数组
        // Base64将字节(byte)数组编码为字符串的方法，而且编码出的字符串只包含ASCII基础字符。解决各系统以及传输协议中二进制不兼容的问题而生的
        byte[] encodedKey = Base64.getEncoder().encode(TOKEN_ENCRY_KEY.getBytes());
        //SecretKeySpec可根据一个字节数组构造一个SecretKey(是一种对称加密即对加密和解密使用相同密钥的加密算法)
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        return key;
    }

    /**
     * 获取token中的claims信息
     * claims=Jwts.builder().var
     * Jwts.parser().setSigningKey(generalKey())校验令牌,如果generalKey()错误将出现异常
     * @param token
     * @return
     */
    private static Jws<Claims> getJws(String token) {
        return Jwts.parser()
                .setSigningKey(generalKey())
                .parseClaimsJws(token);
    }

    /**
     * 获取payload body信息
     * @param token
     * @return
     */
    public static Claims getClaimsBody(String token) {
        try {
            return getJws(token).getBody();
        }catch (ExpiredJwtException e){
            return null;
        }
    }

    /**
     * 获取hearder body信息
     * @param token
     * @return
     */
    public static JwsHeader getHeaderBody(String token) {
        return getJws(token).getHeader();
    }

    /**
     * 是否过期
     * @param claims
     * @return -1：有效，0：有效，1：过期，2：过期
     */
    public static int verifyToken(Claims claims) {
        if(claims==null){
            return 1;
        }
        try {
            //判断当前日期是否在过期日期之前
            claims.getExpiration()
                    .before(new Date());
            // 判断需要自动刷新TOKEN
            //过期日期-系统日期是否大于自动刷新时间
            //getTime()用于获取日期对象表示的时间戳（以毫秒为单位）
            if((claims.getExpiration().getTime()-System.currentTimeMillis())>REFRESH_TIME*1000){
                return -1;
            }else {
                return 0;
            }
        } catch (ExpiredJwtException ex) {
            return 1;
        }catch (Exception e){
            return 2;
        }
    }
}

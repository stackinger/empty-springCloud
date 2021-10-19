package com.example.properties;

import com.example.utils.RasUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * @author The Phoenix(不死人)@itcast.cn
 * @version 1.0
 * @date 2020/3/27
 */
@Configuration
@PropertySource(value = { "classpath:/application.properties" }, encoding = "UTF-8")
@ConfigurationProperties("sc.jwt") //指明前缀
@Component
public class JwtProperties {

    @Value("${secret}")
    private String secret; // 密钥

    @Value("${pubKeyPath}")
    private String pubKeyPath;// 公钥

    @Value("${priKeyPath}")
    private String priKeyPath;// 私钥

    @Value("${expire}")
    private int expire;// token过期时间

    private PublicKey publicKey; // 公钥

    private PrivateKey privateKey; // 私钥

    private static final Logger logger = LoggerFactory.getLogger(JwtProperties.class);

    @PostConstruct
    public void init(){
        try {
            File pubFile = new File(this.pubKeyPath);
            File priFile = new File(this.priKeyPath);
            if( !pubFile.exists() || !priFile.exists()){
                RasUtils.generateKey( this.pubKeyPath ,this.priKeyPath , this.secret);
            }
            this.publicKey = RasUtils.getPublicKey( this.pubKeyPath );
            this.privateKey = RasUtils.getPrivateKey( this.priKeyPath );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getPubKeyPath() {
        return pubKeyPath;
    }

    public void setPubKeyPath(String pubKeyPath) {
        this.pubKeyPath = pubKeyPath;
    }

    public String getPriKeyPath() {
        return priKeyPath;
    }

    public void setPriKeyPath(String priKeyPath) {
        this.priKeyPath = priKeyPath;
    }

    public int getExpire() {
        return expire;
    }

    public void setExpire(int expire) {
        this.expire = expire;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(PrivateKey privateKey) {
        this.privateKey = privateKey;
    }

    public static Logger getLogger() {
        return logger;
    }
}


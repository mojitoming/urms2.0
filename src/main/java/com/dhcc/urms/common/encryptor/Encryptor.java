package com.dhcc.urms.common.encryptor;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Encryptor {
    private static final int N_THREADS;
    private static final String salt;

    static {
        int nCpus = Runtime.getRuntime().availableProcessors();
        N_THREADS = nCpus + 1;
        salt = "7puXd#!Y6%hUKL";
    }

    @Bean("jasyptStringEncryptor")
    public StringEncryptor stringEncryptor() {
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();

        config.setPassword(salt);
        config.setAlgorithm("PBEWithMD5AndTripleDES");
        config.setPoolSize(N_THREADS);

        encryptor.setConfig(config);

        return encryptor;
    }

    public static void main(String[] args) {
        String password = "Mojitoming2020.";
        StringEncryptor encryptor = new Encryptor().stringEncryptor();
        System.out.println(encryptor.encrypt(password));
    }
}

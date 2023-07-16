package io.pastebin.keygenerationservices.services;

import io.pastebin.keygenerationservices.model.KeyInfo;
import io.pastebin.keygenerationservices.model.KeyInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.Base64;
import java.util.Optional;

@Service
public class KeyInfoService {
    @Autowired
    KeyInfoRepository keyInfoRepository;

    public String generateKey(String clientIP) {
        try {
            long timestamp = Instant.now().toEpochMilli();
            String input = clientIP + timestamp;
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] md5Hash = md5.digest(input.getBytes());

            String base62EncodedKey = Base64.getUrlEncoder().withoutPadding().encodeToString(md5Hash);
            if (this.isKeyValid(base62EncodedKey)) {
                //save entry to db and return the key
                return base62EncodedKey;
            } else {
                generateKey(clientIP);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    private boolean isKeyValid(String base62EncodedKey) {
        Optional<KeyInfo> keyInfo = Optional.ofNullable(keyInfoRepository.findByKey(base62EncodedKey));
        return keyInfo.isEmpty();

    }

    public void saveGeneratedKey(String generatedKey) {
        keyInfoRepository.save(new KeyInfo(generatedKey));
    }
}

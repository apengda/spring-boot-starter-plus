package com.github.apengda.springwebplus.starter.service.impl;

import cn.hutool.core.util.StrUtil;
import com.github.apengda.springwebplus.starter.config.SysConfigProperties;
import com.github.apengda.springwebplus.starter.pojo.CurrentUserInfo;
import com.github.apengda.springwebplus.starter.service.TokenStore;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.Duration;

@Configuration
@ConditionalOnMissingBean(RedisTemplate.class)
public class LocalTokenStore implements TokenStore {
    private final SysConfigProperties sysConfig;
    private final Cache<String, CurrentUserInfo> map;

    public LocalTokenStore(SysConfigProperties sysConfig) {
        this.sysConfig = sysConfig;
        map = Caffeine.newBuilder()
                .maximumSize(20000)
                .expireAfterAccess(Duration.ofMinutes(sysConfig.getAuthExpire()))
                .build();
    }

    @Override
    public String buildToken(CurrentUserInfo userInfo) {
        return null;
    }

    @Override
    public CurrentUserInfo byToken(final String token) {
        return map.getIfPresent(token);
    }

    @Override
    public void removeToken(final String token) {
        map.invalidate(token);
    }

    @Override
    public void storeToken(String token, CurrentUserInfo userInfo) {
        if (StrUtil.isBlank(token) || userInfo == null) {
            return;
        }
        map.put(token, userInfo);
    }
}
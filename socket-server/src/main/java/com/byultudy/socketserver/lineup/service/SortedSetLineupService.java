package com.byultudy.socketserver.lineup.service;

import com.byultudy.socketserver.lineup.dto.LineupRequest;
import com.byultudy.socketserver.lineup.dto.LineupResponse;
import com.byultudy.socketserver.lineup.exception.LineupFinishException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.util.StringUtils;

import java.util.List;

public class SortedSetLineupService implements LineupService {

    private final RedisTemplate<String, String> redisTemplate;
    public static final String ZSET_KEY_PREFIX = "line:zset:";

    public static final String STOCK_KEY_PREFIX = "line:stock:";

    public SortedSetLineupService(final RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public LineupResponse lineup(final LineupRequest request) {
        final long currentNanoTime = System.nanoTime();
        if (isFinish(request)) {
            throw new LineupFinishException();
        }
        this.redisTemplate.opsForZSet()
                .add(this.genZSetKey(request), String.valueOf(request.getUserId()), currentNanoTime);
        return LineupResponse.builder()
                .message("success")
                .build();
    }

    private boolean isFinish(final LineupRequest request) {
        final RedisScript<String> script = RedisScript.of(
                """
                        local currentStock = redis.call('GET',KEYS[1]);
                        local result = 0;
                        if tonumber(currentStock) > 0 then
                            result = redis.call('decr', KEYS[1]);
                        end;
                        return tostring(result);
                        """, String.class
        );
        final String result = this.redisTemplate.execute(script
                , List.of(this.genKey(STOCK_KEY_PREFIX, request)));
        if (!StringUtils.hasLength(result) || !isNumeric(result)) {
            return true;
        }
        return Long.parseLong(result) < 1;
    }

    private boolean isNumeric(final String stock) {
        try {
            Long.valueOf(stock);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    protected String genZSetKey(final LineupRequest request) {
        return genKey(ZSET_KEY_PREFIX, request);
    }

    protected String genKey(final String keyPrefix, final LineupRequest request) {
        return keyPrefix + request.getLineId();
    }

    private double getCurrentTime() {
        return 0d;
    }

}
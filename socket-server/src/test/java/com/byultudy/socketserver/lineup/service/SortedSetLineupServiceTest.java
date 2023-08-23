package com.byultudy.socketserver.lineup.service;

import com.byultudy.socketserver.lineup.dto.LineupRequest;
import com.byultudy.socketserver.lineup.dto.LineupResponse;
import com.byultudy.socketserver.lineup.exception.LineupFinishException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.CollectionUtils;

import java.util.Set;

import static com.byultudy.socketserver.lineup.service.SortedSetLineupService.STOCK_KEY_PREFIX;
import static com.byultudy.socketserver.lineup.service.SortedSetLineupService.ZSET_KEY_PREFIX;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(initializers = {
        ConfigDataApplicationContextInitializer.class
}
        , classes = RedisAutoConfiguration.class
)
@TestPropertySource(
        properties = "spring.data.redis.password=dnwjd123@@"
)
public class SortedSetLineupServiceTest {

    @Autowired
    RedisTemplate<String, String> redisTemplate;

    @Autowired
    ApplicationContext ctx;

    LineupService lineupService;

    final long lineId = 1L;
    final long stock = 5L;
    final long userId = 99L;

    @AfterEach
    void tearDown() {
        clearRedis();
        initStock();
    }

    void initStock() {
        this.initStock(this.stock);
    }

    void initStock(final long amount) {
        this.redisTemplate.opsForValue()
                .set(STOCK_KEY_PREFIX + lineId, String.valueOf(amount));
    }

    void clearRedis() {
        final Set<String> keys = this.redisTemplate.keys("*");
        if (CollectionUtils.isEmpty(keys)) {
            return;
        }
        this.redisTemplate.delete(keys);
    }

    @BeforeEach
    void setUp() {
        this.lineupService = new SortedSetLineupService(this.redisTemplate);
    }

    @DisplayName("[SUCCESS] 줄서기 요청 처리.")
    @Test
    void 줄서기_요청_테스트() {
        final LineupRequest request = getLineupRequest();
        final LineupResponse response = this.lineupService.lineup(request);
        assertThat(response)
                .isNotNull()
                .extracting(LineupResponse::getMessage)
                .isEqualTo("success");

        final String firstUserId = this.redisTemplate.opsForZSet().popMax(ZSET_KEY_PREFIX + this.lineId).getValue();
        assertThat(firstUserId).isNotBlank()
                .isEqualTo(String.valueOf(this.userId));
    }

    @DisplayName("[FAIL] 재고가 없는 줄에 줄서기 요청 시 예외가 발생한다.")
    @Test
    void 재고가_없는_줄서기_테스트() {
        final Class<LineupFinishException> expectedException = LineupFinishException.class;
        final LineupRequest request = getLineupRequest();

        this.initStock(0L);

        assertThatThrownBy(() ->
                this.lineupService.lineup(request))
                .describedAs("재고가 0인 줄서기 요청 시, %s", expectedException.getSimpleName())
                .isExactlyInstanceOf(expectedException);
    }

    private LineupRequest getLineupRequest() {
        final LineupRequest request = LineupRequest.builder()
                .lineId(this.lineId)
                .userId(this.userId)
                .build();
        return request;
    }

}
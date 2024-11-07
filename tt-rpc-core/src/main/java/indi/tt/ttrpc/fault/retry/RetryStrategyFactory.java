package indi.tt.ttrpc.fault.retry;

import indi.tt.ttrpc.spi.SpiLoader;

/**
 * 重试策略工厂（用于获取重试器对象）
 *
 * @author tt
 */
public class RetryStrategyFactory {

    static {
        SpiLoader.load(RetryStrategy.class);
    }

    /**
     * 默认重试器
     */
    public static final RetryStrategy DEFAULT_STRATEGY = new NoRetryStrategy();

    /**
     * 获取实例
     *
     * @param key
     * @return
     */
    public static RetryStrategy getInstance(String key) {
        return SpiLoader.getInstance(RetryStrategy.class, key);
    }
}

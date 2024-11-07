package indi.tt.ttrpc.fault.tolerant;

import indi.tt.ttrpc.spi.SpiLoader;

/**
 * 容错策略模式（工厂模式，用于获取容错策略对象）
 *
 * @author tt
 */
public class TolerantStrategyFactory {

    static {
        SpiLoader.load(TolerantStrategy.class);
    }

    /**
     * 默认容错策略
     */
    private static final TolerantStrategy DEFAULT_RETRY_STRATEGY = new FailFastTolerantStrategy();

    /**
     * 获取实例
     *
     * @param key
     * @return
     */
    public static TolerantStrategy getInstance(String key) {
        return SpiLoader.getInstance(TolerantStrategy.class, key);
    }
}
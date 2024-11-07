package indi.tt.ttrpc.fault.tolerant;

import indi.tt.ttrpc.model.RpcResponse;

import java.util.Map;

/**
 * 降级到其他服务 - 容错策略
 *
 * @author tt
 */
public class FailBackTolerantStrategy implements TolerantStrategy {
    @Override
    public RpcResponse doTolerant(Map<String, Object> context, Exception e) {
        // TODO 自行拓展，获取降级的服务并调用
        return null;
    }
}

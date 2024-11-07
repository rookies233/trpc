package indi.tt.ttrpc.fault.tolerant;

import indi.tt.ttrpc.model.RpcResponse;

import java.util.Map;

/**
 * 转移到其他服务节点 - 容错策略
 *
 * @author tt
 */
public class FailOverTolerantStrategy implements TolerantStrategy {
    @Override
    public RpcResponse doTolerant(Map<String, Object> context, Exception e) {
        // TODO 自行拓展，获取其他服务节点并调用
        return null;
    }
}

package indi.tt.ttrpc.fault.retry;

import indi.tt.ttrpc.model.RpcRequest;
import indi.tt.ttrpc.model.RpcResponse;

import java.util.concurrent.Callable;

/**
 * 重试策略
 * TODO 怎么理解 Callable 接口
 *
 * @author tt
 */
public interface RetryStrategy {

    /**
     * 重试
     *
     * @param callable
     * @return
     * @throws Exception
     */
    RpcResponse doRetry(Callable<RpcResponse> callable) throws Exception;
}

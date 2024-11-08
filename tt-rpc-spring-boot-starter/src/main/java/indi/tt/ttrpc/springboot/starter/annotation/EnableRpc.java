package indi.tt.ttrpc.springboot.starter.annotation;

import indi.tt.ttrpc.springboot.starter.bootstrap.RpcConsumerBootstrap;
import indi.tt.ttrpc.springboot.starter.bootstrap.RpcInitBootstrap;
import indi.tt.ttrpc.springboot.starter.bootstrap.RpcProviderBootstrap;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 启动 RPC 注解
 *
 * @author tt
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import({RpcInitBootstrap.class, RpcProviderBootstrap.class, RpcConsumerBootstrap.class})
public @interface EnableRpc {

    /**
     * 需要启动 server
     *
     * @return
     */
    boolean needServer() default true;
}

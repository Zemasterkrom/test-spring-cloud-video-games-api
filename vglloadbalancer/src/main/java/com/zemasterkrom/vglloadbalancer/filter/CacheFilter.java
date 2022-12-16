package com.zemasterkrom.vglloadbalancer.filter;

import com.zemasterkrom.vglloadbalancer.configuration.CacheInstance;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.NettyWriteResponseFilter;
import org.springframework.cloud.gateway.filter.factory.rewrite.ModifyResponseBodyGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.factory.rewrite.RewriteFunction;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class CacheFilter implements GlobalFilter, Ordered {

    /**
     * Cache manager used to store the response
     */
    private final CacheInstance cache;

    /**
     * Spring Gateway Response modifier which allows to read the response body
     */
    private final ModifyResponseBodyGatewayFilterFactory modifyResponseBodyFilter;

    @Autowired
    public CacheFilter(CacheInstance c, ModifyResponseBodyGatewayFilterFactory mrbf) {
        this.cache = c;
        this.modifyResponseBodyFilter = mrbf;
    }

    /**
     * Filter the response to store GET requests in the cache
     *
     * @param exchange Gateway exchange data
     * @param chain    Gateway filter reaction chain
     *
     * @return Chained HTTP response
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        return this.modifyResponseBodyFilter.apply(
                new ModifyResponseBodyGatewayFilterFactory.Config()
                        .setRewriteFunction(String.class, String.class, new ResponseCacher())
        ).filter(exchange, chain);
    }

    /**
     * Allows to avoid a false positive response body since other filters may be applied to it
     *
     * @return Filter order
     */
    @Override
    public int getOrder() {
        return NettyWriteResponseFilter.WRITE_RESPONSE_FILTER_ORDER-1;
    }

    /**
     * Rewriter modifier used to store the GET requests in the cache
     */
    public class ResponseCacher implements RewriteFunction<String, String> {
        @Override
        public Publisher<String> apply(ServerWebExchange exchange, String body) {
            HttpStatus responseCode = exchange.getResponse().getStatusCode();

            if (responseCode != null && responseCode.equals(HttpStatus.OK)) {
                CacheFilter.this.cache.put(exchange.getRequest().getPath(), body);
            }

            return Mono.just(body);
        }
    }
}

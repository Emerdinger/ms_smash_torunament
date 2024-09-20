package com.emerdinger.smashtorunament;

import co.com.bancolombia.logging.technical.LoggerFactory;
import co.com.bancolombia.logging.technical.logger.TechLogger;
import org.reactivestreams.Publisher;
import org.springframework.core.codec.ByteBufferDecoder;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.regex.Pattern;

@Component
public class WriteInfoLogFilter implements WebFilter {

    private static final TechLogger techLogger = LoggerFactory.getLog(LogsConstantsEnum.SERVICE_NAME.getName());
    private static final Pattern regex = Pattern.compile("(\\r\\n|\\n|\\r)");

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

        exchange.getAttributes().put(LogsConstantsEnum.CACHE_REQUEST_INSTANT.getName(),
                getTimestampFormatted(System.currentTimeMillis()));

        return chain.filter(getModifiedServerWebExchange(exchange))
                .doAfterTerminate(() -> techLogger.info(TechMessage.getInfoTechMessage(exchange)));
    }

    private String getTimestampFormatted(Long currentTimeMillis) {
        var dateFormat = new SimpleDateFormat(LogsConstantsEnum.TIME_PATTERN.getName());
        return dateFormat.format(Date.from(Instant.ofEpochMilli(currentTimeMillis)));
    }

    private ServerWebExchange getModifiedServerWebExchange(ServerWebExchange exchange) {
        return exchange.mutate()
                .request(getServerHttpRequestDecorator(exchange))
                .response(getServerHttpResponseDecorator(exchange))
                .build();
    }

    private ServerHttpRequestDecorator getServerHttpRequestDecorator(ServerWebExchange exchange) {
        return new ServerHttpRequestDecorator(exchange.getRequest()) {
            @Override
            public Flux<DataBuffer> getBody() {
                return super.getBody().doOnNext(dataBuffer -> {
                    var stringBuffer = new String(dataBuffer.toString().getBytes(),StandardCharsets.UTF_8);
                    exchange.getAttributes().put(LogsConstantsEnum.CACHE_REQUEST_BODY.getName(),
                            regex.matcher(stringBuffer).replaceAll(""));
                });
            }
        };
    }

    private ServerHttpResponseDecorator getServerHttpResponseDecorator(ServerWebExchange exchange) {
        return new ServerHttpResponseDecorator(exchange.getResponse()) {

            @Override
            public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                final Flux<DataBuffer> dataBufferFlux = Flux.from(body)
                        .map(dataBuffer -> {
                            var stringBuffer = new String(dataBuffer.toString().getBytes(),StandardCharsets.UTF_8);
                            exchange.getAttributes().put(LogsConstantsEnum.CACHE_RESPONSE_BODY.getName(),
                                    stringBuffer);
                            return dataBuffer;
                        });
                return super.writeWith(dataBufferFlux);
            }
        };
    }

}
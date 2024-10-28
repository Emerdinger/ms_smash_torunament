package com.emerdinger.smashtorunament.infraestructure.Aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Aspect
@Component
public class LogginAspect {

    private static final Logger logger = LoggerFactory.getLogger(LogginAspect.class);

    @Pointcut("execution(* com.emerdinger.smashtorunament.infraestructure.endpoint.handler..*(..))")
    public void handlerMethods() {}

    @Before("handlerMethods() && args(request, ..)")
    public void logRequestDetails(JoinPoint joinPoint, ServerRequest request) {
        logger.info("============ Incoming Request ============");

        String path = request.path();
        logger.info("Path: {}", path);

        String methodName = joinPoint.getSignature().getName();
        logger.info("Method: {}", methodName);

        Map<String, List<String>> headers = request.headers().asHttpHeaders();
        logger.info("Headers: {}", headers);

        Mono<String> bodyMono = request.bodyToMono(String.class);
        bodyMono.subscribe(body -> logger.info("Body: {}", body));

        logger.info("============ Request End ============");
    }


}

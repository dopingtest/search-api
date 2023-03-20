package com.blog.kiwi.infra.config

import com.fasterxml.jackson.databind.ObjectMapper
import io.netty.channel.ChannelOption
import io.netty.handler.timeout.ReadTimeoutHandler
import io.netty.handler.timeout.WriteTimeoutHandler
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Scope
import org.springframework.context.annotation.ScopedProxyMode
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.http.codec.LoggingCodecSupport
import org.springframework.http.codec.json.Jackson2JsonDecoder
import org.springframework.web.reactive.function.client.ExchangeFilterFunction
import org.springframework.web.reactive.function.client.ExchangeStrategies
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import reactor.netty.http.client.HttpClient
import java.time.Duration
import java.util.concurrent.TimeUnit

@Configuration
class WebClientConfig(
    private val objectMapper: ObjectMapper
) {

    private val log = LoggerFactory.getLogger(javaClass)

    @Bean
    @Scope(value = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)
    fun webClient(): WebClient {
        val exchangeStrategies: ExchangeStrategies = ExchangeStrategies.builder()
            .codecs { configurer ->
                configurer.defaultCodecs().maxInMemorySize(1024 * 1024 * 50)
                configurer.defaultCodecs().jackson2JsonDecoder(Jackson2JsonDecoder(objectMapper))
            }
            .build()

        exchangeStrategies
            .messageWriters().stream()
            .filter { obj: Any? -> LoggingCodecSupport::class.java.isInstance(obj) }
            .forEach { writer -> (writer as LoggingCodecSupport).setEnableLoggingRequestDetails(true) }

        val httpClient: HttpClient = HttpClient.create()
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
            .responseTimeout(Duration.ofMillis(5000))
            .doOnConnected { conn ->
                conn.addHandlerLast(ReadTimeoutHandler(5000, TimeUnit.MILLISECONDS))
                    .addHandlerLast(WriteTimeoutHandler(5000, TimeUnit.MILLISECONDS))
            }

        return WebClient.builder()
            .clientConnector(ReactorClientHttpConnector(httpClient))
            .exchangeStrategies(exchangeStrategies)
            .filter(ExchangeFilterFunction.ofRequestProcessor { clientRequest ->
                log.debug("# [WebClientConfig] - Request: {} {}", clientRequest.method(), clientRequest.url())
                clientRequest.headers().forEach { name, values ->
                    values.forEach { value ->
                        log.debug(
                            "{} : {}",
                            name,
                            value
                        )
                    }
                }
                Mono.just(clientRequest)
            })
            .filter(ExchangeFilterFunction.ofResponseProcessor { clientResponse ->
                clientResponse.headers().asHttpHeaders().forEach { name, values ->
                    values.forEach { value ->
                        log.debug(
                            "{} : {}",
                            name,
                            value
                        )
                    }
                }
                Mono.just(clientResponse)
            })
            .build()
    }
}
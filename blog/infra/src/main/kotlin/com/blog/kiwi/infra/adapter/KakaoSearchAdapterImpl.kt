package com.blog.kiwi.infra.adapter

import com.blog.kiwi.core.constant.SearchProvider
import com.blog.kiwi.core.constant.SortCondition
import com.blog.kiwi.core.exception.ClientCallException
import com.blog.kiwi.core.response.ResponseType
import com.blog.kiwi.domain.adapter.SearchAdapter
import com.blog.kiwi.domain.spec.SearchBlogResponseSpec
import com.blog.kiwi.infra.dto.KakaoSearchBlogResponse
import com.blog.kiwi.infra.dto.KakaoSearchResponse
import com.blog.kiwi.infra.mapper.SearchClientMapper
import com.blog.kiwi.infra.properties.SearchProperties
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.commons.lang3.exception.ExceptionUtils
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.ClientResponse
import org.springframework.web.reactive.function.client.WebClient
import java.util.*

@Component(value = SearchProvider.KAKAO)
class KakaoSearchAdapterImpl(
    private val webClient: WebClient,
    private val objectMapper: ObjectMapper,
    private val searchClientMapper: SearchClientMapper,
    private val searchProperties: SearchProperties,
): SearchAdapter {

    private val log = LoggerFactory.getLogger(javaClass)

    override fun searchBlog(query: String, sort: SortCondition, page :Int, size: Int): SearchBlogResponseSpec {
        val queryStrings = "?page=${page + 1}&size=$size&sort=${sort.name.lowercase()}&query=$query"
        val response = get(KakaoSearchBlogResponse::class.java, "/v2/search/blog", queryStrings)
        return searchClientMapper.toSpec(response)
    }

    private operator fun <T> get(clazz: Class<T>, path: String, queryStrings: String): T? {
        var response: Optional<String> = Optional.empty()
        try {
            val uri: String = searchProperties.kakao.endpoint + path + queryStrings
            response = webClient.mutate()
                .build().get().uri(uri)
                .header("Authorization", searchProperties.kakao.apiKey)
                .retrieve()
                .onStatus(
                    { status: HttpStatus -> status.is4xxClientError || status.is5xxServerError }
                ) { clientResponse: ClientResponse ->
                    clientResponse.bodyToMono(String::class.java)
                        .map { ClientCallException(responseType = ResponseType.CLIENT_ERROR) }
                }
                .bodyToMono(String::class.java)
                .blockOptional()
        } catch (e: Exception) {
            log.error("# [KakaoSearchAdapter] - {} send {} = {}", clazz, path, ExceptionUtils.getStackTrace(e))
            throw ClientCallException(responseType = ResponseType.CLIENT_ERROR, "Kakao Search API Error")
        }

        return getResponse(clazz, response)
    }

    private fun <T> getResponse(clazz: Class<T>, responseBody: Optional<String>): T? {
        if (responseBody.isEmpty) {
            return null
        }
        try {
            log.info("# [KakaoSearchAdapter] - Response {}", objectMapper.writeValueAsString(responseBody.get()))
            return objectMapper.readValue(responseBody.get(), clazz)
        } catch (e: JsonProcessingException) {
            log.error("# [KakaoSearchAdapter] - {} getResponse error = {}", clazz.name, e.stackTrace)
        }
        return null
    }
}
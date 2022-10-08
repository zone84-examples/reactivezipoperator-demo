package tech.zone84.examples.zipoperator

import com.rometools.rome.feed.synd.SyndEntry
import com.rometools.rome.io.SyndFeedInput
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.reactor.http.client.ReactorHttpClient
import mu.KotlinLogging
import reactor.core.publisher.Mono
import java.io.StringReader

class BlogCollector(
    private val client: ReactorHttpClient,
    private val uri: String
) {

    fun collect(): Mono<List<SyndEntry>> {
        logger.info { "Will collect entries from '$uri'" }
        return Mono.from(client.exchange(HttpRequest.GET<String>(uri), String::class.java))
            .filter { it.status == HttpStatus.OK }
            .map { parse(it.body()) }
    }

    fun parse(feedBody: String): List<SyndEntry> {
        val feed = SyndFeedInput().build(StringReader(feedBody))
        logger.info { "Processing feed..." }
        return feed.entries
    }

    companion object {
        private val logger = KotlinLogging.logger { }
    }
}

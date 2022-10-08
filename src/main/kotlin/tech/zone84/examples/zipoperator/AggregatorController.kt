package tech.zone84.examples.zipoperator

import com.rometools.rome.feed.synd.SyndFeed
import com.rometools.rome.feed.synd.SyndFeedImpl
import com.rometools.rome.io.SyndFeedOutput
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.server.HttpServerConfiguration
import jakarta.inject.Named
import mu.KotlinLogging
import reactor.core.publisher.Mono
import reactor.kotlin.core.util.function.component1
import reactor.kotlin.core.util.function.component2
import java.io.StringWriter

@Controller("/feed")
class AggregatorController(
    @Named("1")
    private val firstCollector: BlogCollector,
    @Named("2")
    private val secondCollector: BlogCollector,
    private val configuration: HttpServerConfiguration
) {
    @Get("/", produces = [MediaType.APPLICATION_XML])
    fun collectPosts() : Mono<String> {
        val first = firstCollector.collect()
        val second = secondCollector.collect()
        return Mono.zip(first, second)
            .map { (firstEntries, secondEntries) ->
                logger.info {
                    "Results have arrived! ${firstEntries.size} posts from blog 1, " +
                        "${secondEntries.size} posts from blog 2"
                }
                val feed: SyndFeed = SyndFeedImpl()
                feed.feedType = "rss_2.0"
                feed.title = "my favorite blogs"
                feed.description = "my favorite blogs"
                feed.link = "http://${configuration.host}:${configuration.port}/feed"
                feed.entries = firstEntries + secondEntries
                publishFeed(feed)
            }
    }

    private fun publishFeed(feed: SyndFeed): String {
        return StringWriter().use { writer ->
            val syndFeedOutput = SyndFeedOutput()
            syndFeedOutput.output(feed, writer)
            writer.toString()
        }
    }

    companion object {
        private val logger = KotlinLogging.logger { }
    }
}

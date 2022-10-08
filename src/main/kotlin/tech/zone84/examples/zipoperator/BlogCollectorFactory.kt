package tech.zone84.examples.zipoperator

import io.micronaut.context.annotation.Factory
import io.micronaut.http.client.annotation.Client
import io.micronaut.reactor.http.client.ReactorHttpClient
import jakarta.inject.Named
import jakarta.inject.Singleton

@Factory
class BlogCollectorFactory {
    @Singleton
    @Named("1")
    fun createFirstFactory(@Client("https://zone84.tech/") client: ReactorHttpClient) =
        BlogCollector(client, "/feed")

    @Singleton
    @Named("2")
    fun createSecondFactory(@Client("https://micronaut.io/") client: ReactorHttpClient) =
        BlogCollector(client, "/blog/feed")
}

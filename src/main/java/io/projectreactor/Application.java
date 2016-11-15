package io.projectreactor;

import static org.springframework.http.HttpHeaders.*;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.web.reactive.function.RequestPredicates.GET;
import static org.springframework.web.reactive.function.RouterFunctions.route;
import static org.springframework.web.reactive.function.ServerResponse.*;
import reactor.ipc.netty.http.HttpServer;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.codec.BodyInserters;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import org.springframework.web.reactive.function.RouterFunction;
import org.springframework.web.reactive.function.RouterFunctions;

/**
 * Main Application for the Project Reactor home site.
 */
public class Application {

	public static void main(String... args) throws InterruptedException {
		HttpHandler httpHandler = RouterFunctions.toHttpHandler(routes());
		ReactorHttpHandlerAdapter adapter = new ReactorHttpHandlerAdapter(httpHandler);
		HttpServer server = HttpServer.create("localhost", 8080);
		server.startAndAwait(adapter);
	}

	private static RouterFunction<?> routes() {

		return route(GET("/docs/api/**"), request ->
				status(FOUND).header(LOCATION, request.path().replace("/docs/", "/old/")).build()
			).andRoute(GET("/docs/reference/**"), request ->
				status(FOUND).header(LOCATION, request.path().replace("/docs/", "/old/")).build()
			).andRoute(GET("/docs/raw/**"), request ->
				status(FOUND).header(LOCATION, request.path().replace("/docs/", "/old/")).build()
			).andRoute(GET("/core/docs/reference/**"), request ->
				status(FOUND).header(LOCATION, "https://github.com/reactor/reactor-core/blob/master/README.md").build()
			).andRoute(GET("/"), request ->
				ok().body(BodyInserters.fromResource(new ClassPathResource("static/index.html")))
			).andRoute(GET("/docs"), request ->
				ok().body(BodyInserters.fromResource(new ClassPathResource("static/docs/index.html")))
			).andRoute(GET("/{file}"), request ->
				ok().body(BodyInserters.fromResource(new ClassPathResource("static/" + request.pathVariable("file"))))
			).andRoute(GET("/assets/{dir}/{file}"), request -> {
					Resource resource = new ClassPathResource("static/assets/" + request.pathVariable("dir") + "/" + request.pathVariable("file"));
					return ok().body(BodyInserters.fromResource(resource));
				}
			);
	}

}

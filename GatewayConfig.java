public class GatewayConfig {
    private final RateLimiter rateLimiter;

    // @Bean
    // RouteLocator routes(RouteLocatorBuilder builder) {
    //   return builder.routes()
    //     .route("auth-service",
    //       r -> r.path("/auth/**")
    //         .filters(f -> rewriteOpenApiPath(f, "/auth")
    //           .filter(authenticationFilter)
    //           .requestRateLimiter(c -> c
    //             .setRateLimiter(RateLimitConfig.auth())// java.lang.IllegalStateException: RedisRateLimiter is not initialized
    //             .setDenyEmptyKey(false)
    //             .setKeyResolver(RateLimitConfig.ipKeyResolver())
    //           )
    //         )
    //         .uri("lb://auth"))
    //         .build();
    // }


    /***Current solution***/
    @Bean
    RouteLocator routes(RouteLocatorBuilder builder) {
      return builder.routes()
        .route("auth-service",
          r -> r.path("/auth/**")
            .filters(f -> rewriteOpenApiPath(f, "/auth")
              .filter(authenticationFilter)
              .requestRateLimiter(c -> c
                .setRateLimiter(rateLimiter)
                .setDenyEmptyKey(false)
                .setKeyResolver(RateLimitConfig.ipKeyResolver())
              )
            )
            .uri("lb://auth"))
            .build();
    }
}

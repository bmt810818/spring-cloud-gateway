
@Slf4j
@Component
@Configuration
public class RateLimitConfig {

  @Bean
  public static KeyResolver ipKeyResolver() {
    return exchange -> {
      String ip = Optional.ofNullable(exchange.getRequest().getRemoteAddress())
        .map(address -> address.getAddress().getHostAddress())
        .orElse("unknown");
      System.out.println("====IP                :" + ip);
      return Mono.just(ip);
    };
  }

  @Bean
  @Primary
  public static RedisRateLimiter auth() {
    return new RedisRateLimiter(1, 1);
  }

  @Bean
  public static RedisRateLimiter merchant() {
    return new RedisRateLimiter(2, 2);
  }

  @Bean
  public static RedisRateLimiter page() {
    return new RedisRateLimiter(3, 3);
  }
}
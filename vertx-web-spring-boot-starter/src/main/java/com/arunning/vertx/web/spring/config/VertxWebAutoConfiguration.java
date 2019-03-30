package com.arunning.vertx.web.spring.config;

import com.arunning.vertx.web.filter.Filter;
import com.arunning.vertx.web.filter.FilterHandler;
import com.arunning.vertx.web.filter.RouteFilterDefinition;
import com.arunning.vertx.web.spring.*;
import com.arunning.vertx.web.spring.annotation.RouteFilter;
import com.arunning.vertx.web.spring.annotation.RouteMapping;
import com.arunning.vertx.web.utils.VertxHolder;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.eventbus.EventBusOptions;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.http.ServerWebSocket;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.PubSecKeyOptions;
import io.vertx.ext.auth.jwt.JWTAuth;
import io.vertx.ext.auth.jwt.JWTAuthOptions;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.*;
import io.vertx.ext.web.sstore.LocalSessionStore;
import io.vertx.ext.web.sstore.SessionStore;
import io.vertx.ext.web.templ.thymeleaf.ThymeleafTemplateEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author chenliangliang
 * @date 2019/3/22
 */
@Configuration
@ComponentScan(basePackages = {"com.arunning.vertx.web.spring"})
@EnableConfigurationProperties(VertxWebProperties.class)
public class VertxWebAutoConfiguration {

    @Bean
    public VertxWebConfig vertxWebConfig() {
        return new VertxWebConfig();
    }

    @Bean
    public ParameterNameDiscoverer parameterNameDiscoverer() {
        return new DefaultParameterNameDiscoverer();
    }


    private static class VertxWebConfig implements ApplicationListener<ContextRefreshedEvent> {

        private static Logger logger = LoggerFactory.getLogger(VertxWebConfig.class);

        @Autowired
        private VertxWebProperties properties;

        @Autowired
        private RouteHandlerFactory routeHandlerFactory;

        private HttpServer server;

        private Router router;

        private AtomicBoolean configured = new AtomicBoolean(false);


        @Override
        public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
            ApplicationContext ctx = contextRefreshedEvent.getApplicationContext();

            doConfig(ctx);
        }

        private void doConfig(ApplicationContext ctx) {
            if (configured.compareAndSet(false, true)) {
                //配置Vertx
                configVertx(properties);

                //配置argumentResolver
                Map<String, RouterMethodArgumentResolver> argumentResolverMap = ctx.getBeansOfType(RouterMethodArgumentResolver.class);
                configArgumentResolver(argumentResolverMap.values());

                //配置exceptionResolver
                Map<String, RouterHandlerExceptionResolver> exceptionResolverMap = ctx.getBeansOfType(RouterHandlerExceptionResolver.class);
                configExceptionResolver(exceptionResolverMap.values());

                //配置returnValueHandler
                Map<String, RouterMethodReturnValueHandler> returnValueHandlerMap = ctx.getBeansOfType(RouterMethodReturnValueHandler.class);
                configReturnValueHandler(returnValueHandlerMap.values());

                //配置Router
                configRouter();

                //部署vertx
                configHttpServer();
            }
        }

        private Map<String, ServerWebSocket> connectMap = new ConcurrentHashMap<>(256);

        private void configHttpServer() {
            HttpServerOptions options = new HttpServerOptions();
            options.setPort(properties.getPort());

            HttpServer httpServer = VertxHolder.getVertx().createHttpServer(options);

            httpServer.requestHandler(router);
            httpServer.websocketHandler(webSocket -> {

                String id = webSocket.binaryHandlerID();

                if (!connectMap.containsKey(id)) {
                    connectMap.put(id, webSocket);
                }

                webSocket.frameHandler(frame -> {

                    if (frame.isText()) {
                        String textData = frame.textData();

                        logger.info("Server received text data [{}]", textData);

                        connectMap.forEach((k, v) -> {

                            v.writeFinalTextFrame("Server send " + textData + " , time: " + new Date());
                        });

                    }


                }).closeHandler(h -> connectMap.remove(id));


            });

            httpServer.listen();
            logger.info("Http Server started at Port [{}]", properties.getPort());
        }

        private void configRouter() {
            this.router = Router.router(VertxHolder.getVertx());

            //先配置系统routeHandler
            configSystemRouteHandler(router);

            //然后配置过滤器
            configFilter(router);

            //再配置请求
            configRequest(router);
        }

        private void configSystemRouteHandler(Router router) {
            //logger

            if (properties.isLog()) {
                router.route().handler(LoggerHandler.create());
            }

            //favicon.ico
            router.route().handler(FaviconHandler.create());

            //body

            VertxWebProperties.Body body = properties.getBody();

            BodyHandler bodyHandler = BodyHandler.create()
                    .setBodyLimit(body.getLimit())
                    .setDeleteUploadedFilesOnEnd(body.isDeleteUploadedFilesOnEnd())
                    .setPreallocateBodyBuffer(true)
                    .setMergeFormAttributes(body.isMergeFormAttributes());

            if (StringUtils.hasText(body.getUploadsDirectory())) {
                bodyHandler.setUploadsDirectory(body.getUploadsDirectory());
            }

            router.route().handler(bodyHandler);

            if (properties.getSession().isEnable()) {

                //cookie
                router.route().handler(CookieHandler.create());

                //session
                SessionStore sessionStore = LocalSessionStore.create(VertxHolder.getVertx(), "smap",
                        1000 * properties.getSession().getTimeoutSeconds());

                router.route().handler(SessionHandler.create(sessionStore));

            }


//            JWTAuthOptions authOptions = new JWTAuthOptions();
//            authOptions.addPubSecKey(new PubSecKeyOptions()
//                    .setAlgorithm("HS256")
//                    .setPublicKey("my secret")
//                    .setSymmetric(true));
//
//            JWTAuth jwtAuth = JWTAuth.create(VertxHolder.getVertx(), authOptions);
//
//            router.route().handler(UserSessionHandler.create(jwtAuth));
//            //auth
//            AuthHandler authHandler = BasicAuthHandler.create(jwtAuth, "jwt-auth");
//            router.route().handler(authHandler);

            //static resources
            router.route("/static/*").handler(StaticHandler.create("static"));

            //template

            TemplateHandler templateHandler = TemplateHandler.create(ThymeleafTemplateEngine.create(VertxHolder.getVertx()));

            router.get("/dynamic/*").handler(ctx -> {


                ctx.put("ip", ctx.request().host())
                        .put("time", new Date())
                        .next();


            });
            router.get("/dynamic/*").handler(templateHandler);

            logger.info("configSystemRouteHandler finished.");
        }

        public static void main(String[] args) {

            Vertx vertx = Vertx.vertx();

            JWTAuthOptions authOptions = new JWTAuthOptions();
            authOptions.addPubSecKey(new PubSecKeyOptions()
                    .setAlgorithm("HS256")
                    .setPublicKey("my secret")
                    .setSymmetric(true));

            JWTAuth jwtAuth = JWTAuth.create(VertxHolder.getVertx(), authOptions);

            String token = jwtAuth.generateToken(new JsonObject().put("name", "cll"));

            System.out.println("token: " + token);

            jwtAuth.authenticate(new JsonObject().put("jwt", token), res -> {

                if (res.succeeded()) {
                    System.out.println("验证成功");
                    System.out.println(res.result().principal());
                } else {
                    System.out.println("验证失败,error: " + res.cause().getMessage());
                }
            });
        }

        private void configRequest(Router router) {
            Registry<RouteHandlerDefinition> registry = RouteHandlerDefinitionRegistry.INSTANCE;

            ErrorHandler errorHandler = ErrorHandler.create();

            for (RouteHandlerDefinition definition : registry.lookup()) {

                RouteHandler handler = routeHandlerFactory.newRouteHandler(definition);

                RouteMapping routeMapping = definition.getRouteMapping();

                //path
                Route route = router.route(routeMapping.method(), definition.getPath());

                //order
                //route.order(routeMapping.order());

                //consumes
                String[] consumes = routeMapping.consumes();

                if (consumes.length > 0) {
                    for (String consume : consumes) {
                        route.consumes(consume);
                    }
                }

                //produces
                String[] produces = routeMapping.produces();

                if (produces.length > 0) {
                    for (String produce : produces) {
                        route.produces(produce);
                    }
                }

                //handler
                route.blockingHandler(handler);

                route.failureHandler(errorHandler);

                logger.info("Mapping Request: url=[{}], method=[{}] to Handler => {}", definition.getPath(), routeMapping.method(), handler);
            }

            registry.clear();
        }


        private void configFilter(Router router) {
            Registry<RouteFilterDefinition> registry = RouteFilterDefinitionRegistry.INSTANCE;

            for (RouteFilterDefinition definition : registry.lookup()) {

                Filter filter = definition.getFilter();
                RouteFilter routeFilter = definition.getRouteFilter();

                FilterHandler handler = new FilterHandler(filter);
                Route route = router.route(routeFilter.url())/*.order(-1)*/;

                if (filter.blocking()) {
                    route.blockingHandler(handler);
                } else {
                    route.handler(handler);
                }

                logger.info("Mapping Filter: url=[{}] to Handler =>{}", routeFilter.url(), handler);

            }

            registry.clear();
        }

        private void configVertx(VertxWebProperties properties) {
            EventBusOptions eventBusOptions = new EventBusOptions();
            //便于调试 设定超时等时间较长 生产环境建议适当调整
            eventBusOptions.setConnectTimeout(properties.getConnectTimeout());

            VertxOptions options = new VertxOptions();
            options/*.setEventLoopPoolSize(NettyRuntime.availableProcessors())*/
                    .setWorkerPoolSize(properties.getWorkPoolSize())
                    .setMaxWorkerExecuteTime(20)
                    .setMaxWorkerExecuteTimeUnit(TimeUnit.SECONDS);

            Vertx vertx = Vertx.vertx(options);

            VertxHolder.init(vertx);
            logger.info("configVertx finished. Vertx={}", vertx);
        }

        private void configReturnValueHandler(Collection<RouterMethodReturnValueHandler> handlers) {
            Registry<RouterMethodReturnValueHandler> registry = RouterMethodReturnValueHandlerRegistry.INSTANCE;

            for (RouterMethodReturnValueHandler handler : handlers) {
                registry.register(handler);
            }
            logger.info("configReturnValueHandler finished. handlers={}", registry.lookup());
        }

        private void configExceptionResolver(Collection<RouterHandlerExceptionResolver> resolvers) {
            Registry<RouterHandlerExceptionResolver> registry = RouterHandlerExceptionResolverRegistry.INSTANCE;

            for (RouterHandlerExceptionResolver resolver : resolvers) {
                registry.register(resolver);
            }
            logger.info("configExceptionResolver finished. resolvers={}", registry.lookup());
        }


        private void configArgumentResolver(Collection<RouterMethodArgumentResolver> resolvers) {
            Registry<RouterMethodArgumentResolver> registry = RouterMethodArgumentResolverRegistry.INSTANCE;

            for (RouterMethodArgumentResolver resolver : resolvers) {
                registry.register(resolver);
            }
            logger.info("configArgumentResolver finished. resolvers={}", registry.lookup());
        }
    }
}

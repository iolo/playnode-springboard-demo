package kr.iolo.springboard;

import com.facebook.nifty.processor.NiftyProcessor;
import com.facebook.swift.codec.ThriftCodecManager;
import com.facebook.swift.service.ThriftEventHandler;
import com.facebook.swift.service.ThriftServer;
import com.facebook.swift.service.ThriftServerConfig;
import com.facebook.swift.service.ThriftServiceProcessor;
import kr.iolo.springboard.cache.CacheService;
import kr.iolo.springboard.cache.DummyCacheService;
import kr.iolo.springboard.cache.RedisCacheService;
import kr.iolo.springboard.thrift.TSpringboard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class SpringboardApplication {
    private static final Logger L = LoggerFactory.getLogger(SpringboardApplication.class);

    @Bean
    ThriftServer thriftServer(TSpringboard springboard) {
        final ThriftCodecManager codecManager = new ThriftCodecManager();
        final List<ThriftEventHandler> eventHandlers = new ArrayList<>();
        final NiftyProcessor processor = new ThriftServiceProcessor(codecManager, eventHandlers, springboard);
        final ThriftServerConfig config = new ThriftServerConfig().setPort(9876);
        final ThriftServer server = new ThriftServer(processor, config);
        System.out.println("*** STARTUP THRIFT SERVER! ***");
        System.out.println(config);
        server.start();
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                System.out.println("*** SHUTDOWN THRIFT SERVER! ***");
                server.close();
            }
        });
        return server;
    }

    public static void main(String[] args) {
        final ApplicationContext ctx = SpringApplication.run(SpringboardApplication.class, args);
        if (L.isTraceEnabled()) {
            L.trace("**************************");
            for (final String beanName : ctx.getBeanDefinitionNames()) {
                if (L.isTraceEnabled()) {
                    L.trace(beanName);
                }
            }
            L.trace("**************************");
        }

        final ThriftServer server = ctx.getBean(ThriftServer.class);
        while (true) {
            int at = server.getAcceptorThreads();
            int iot = server.getIoThreads();
            int wt = server.getWorkerThreads();
            System.out.println("*** #acceptor=" + at + ",#io=" + iot + ",#worker=" + wt + " ***");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                //ignored
            }
        }
    }
}

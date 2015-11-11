package kr.iolo.springboard;

import com.facebook.nifty.client.FramedClientConnector;
import com.facebook.swift.service.ThriftClientManager;
import com.facebook.swift.service.ThriftServer;
import com.google.common.base.MoreObjects;
import com.google.common.net.HostAndPort;
import kr.iolo.springboard.thrift.*;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.StopWatch;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SpringboardApplication.class)
@IntegrationTest
@ActiveProfiles("test")
public class ThriftServerTest {

    @Autowired
    ThriftServer thriftServer;

    @Test
    public void test() throws Exception {
        try (ThriftClientManager clientManager = new ThriftClientManager()) {
            FramedClientConnector connector = new FramedClientConnector(HostAndPort.fromParts("localhost", 9876));
            TSpringboard springboard = clientManager.createClient(connector, TSpringboard.class).get();

            StopWatch st = new StopWatch();

            st.start("getUser");
            TUser u = springboard.getUser(1);
            assertNotNull(u);
            System.out.println(ToStringBuilder.reflectionToString(u));
            assertEquals(1, u.getId());
            st.stop();

            st.start("getForum");
            TForum f = springboard.getForum(1);
            assertNotNull(f);
            System.out.println(ToStringBuilder.reflectionToString(f));
            assertEquals(1, f.getId());
            st.stop();

            st.start("getPost");
            TPost p = springboard.getPost(1);
            assertNotNull(p);
            System.out.println(ToStringBuilder.reflectionToString(p));
            assertEquals(1, p.getId());
            st.stop();

            st.start("getComment");
            TComment c = springboard.getComment(1);
            assertNotNull(c);
            System.out.println(ToStringBuilder.reflectionToString(c));
            assertEquals(1, c.getId());
            st.stop();

            System.out.println(st.prettyPrint());
        }
    }
}

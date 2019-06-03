package com.httpclient;

import com.alibaba.fastjson.TypeReference;
import com.google.common.collect.ImmutableMap;
import com.util.BeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HeaderElement;
import org.apache.http.HeaderElementIterator;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.pool.PoolStats;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author cheny.huang
 * @date 2019-03-05 16:58.
 */
@Slf4j
public class ApacheHttpClient {
    private CloseableHttpClient httpClient;
    private PoolingHttpClientConnectionManager manager;
    @Before
    public void buildPoolHttpClient() {
        ConnectionKeepAliveStrategy myStrategy = (response, context) -> {
            HeaderElementIterator it = new BasicHeaderElementIterator(
                    response.headerIterator(HTTP.CONN_KEEP_ALIVE));
            while (it.hasNext()) {
                HeaderElement he = it.nextElement();
                String param = he.getName();
                String value = he.getValue();
                // 如果客户端返回值中带有timeout字段，则以这个字段作为keep-alive时长
                if (value != null && "timeout".equalsIgnoreCase(param)) {
                    return Long.parseLong(value) * 1000;
                }
            }
            //如果没有约定，则默认定义时长为90s，90s后会被认为过期的连接，然后由定时任务清理
            return 90000;
        };

        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        // 设置最大的连接数，即整个池都不能超过的连接数
        connectionManager.setMaxTotal(20);
        // 设置每一个路由的连接数(dest ip + dest port)，路由指的是目的路由，而非原路由
        connectionManager.setDefaultMaxPerRoute(10);
        // 间隔多少时间检测下链接是否还存活
        connectionManager.setValidateAfterInactivity(5000);

        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(5000)
                .setSocketTimeout(20000)
                // 连接不够用时等待超时时间，一定要设置，如果不设置的话，如果连接池连接不够用，就会线程阻塞
                .setConnectionRequestTimeout(90000)
                .build();

        this.manager = connectionManager;
        this.httpClient = HttpClients.custom()
                .setConnectionManager(connectionManager)
                .setKeepAliveStrategy(myStrategy)
                .setDefaultRequestConfig(requestConfig)
                .build();
    }

    private CloseableHttpResponse response() throws IOException {
        return httpClient.execute(new HttpGet("http://localhost:8887/dynamic/json/string"));
    }

    @Test
    public void testEntityResponse() throws IOException {
        HttpPost post = new HttpPost("http://localhost:8887/bpm/proc/deploy");
        post.addHeader(HttpHeaders.ACCEPT, "application/json");
        post.addHeader(HttpHeaders.CONTENT_TYPE, "application/json");
        post.setEntity(new StringEntity(BeanUtil.deepPrintObject(new ProcDeploy("test")), Charset.forName("UTF-8")));
        HttpResponse response = httpClient.execute(post);
        ResResult res = BeanUtil.parser(response.getEntity().getContent(), new TypeReference<ResResult>(){}.getType());
        assertNotNull(res);
        assertTrue(res.getStatus() == 0);
    }

    @Test
    public void testNoArgRequest() throws IOException {
        HttpPost post = new HttpPost("http://localhost:8887/no/params");
        post.addHeader(HttpHeaders.ACCEPT, "application/json");
        post.addHeader(HttpHeaders.CONTENT_TYPE, "application/json");
        HttpResponse response = httpClient.execute(post);
        ResResult res = BeanUtil.parser(response.getEntity().getContent(), new TypeReference<ResResult>(){}.getType());
        Assert.assertNotNull(res);
        Assert.assertTrue(res.getStatus() == 0);
    }

    @Test
    public void testGenericTypeResponse() throws IOException {
        HttpPost post = new HttpPost("http://localhost:8887/generic/single/params");
        Map<String, Object> req = ImmutableMap.of("bi",11, "eName", "呵呵");
        post.addHeader(HttpHeaders.ACCEPT, "application/json");
        post.addHeader(HttpHeaders.CONTENT_TYPE, "application/json");
        post.setEntity(new StringEntity(BeanUtil.deepPrintObject(req), Charset.forName("UTF-8")));
        HttpResponse response = httpClient.execute(post);
        ResResult<GenericValue> res = BeanUtil.parser(response.getEntity().getContent(), new TypeReference<ResResult<GenericValue>>(){}.getType());
        log.info("res value:{}", BeanUtil.deepPrintObject(res));
        Assert.assertNotNull(res);
        Assert.assertTrue(res.getStatus() == 0);
        Assert.assertTrue(res.getData().getBi()==11);
        Assert.assertTrue(res.getData().getBl()>0);
        Assert.assertEquals(res.getData().geteName(), "呵呵");
    }

    @Test
    public void testGetRequest() throws IOException {
        HttpGet get = new HttpGet("http://localhost:8887/dynamic/route");
        get.addHeader(HttpHeaders.ACCEPT, "application/json");
        get.addHeader(HttpHeaders.CONTENT_TYPE, "application/json");
        HttpResponse response = httpClient.execute(get);
        ResResult<List<Map<String,Object>>> res = BeanUtil.parser(response.getEntity().getContent(), new TypeReference<ResResult<List<Map<String,Object>>>>(){}.getType());
        log.info("res value:{}", BeanUtil.deepPrintObject(res));
        Assert.assertTrue(res.getStatus() == 0);
        Assert.assertNotNull(res.getData());
        Assert.assertEquals(res.getData().get(0).get("label"), "测试");
    }


    @Test
    public void testStringResponse() throws IOException {
        HttpPost post = new HttpPost("http://localhost:8080/modify/password/v");
        post.addHeader(HttpHeaders.ACCEPT, "application/json");
        post.addHeader(HttpHeaders.CONTENT_TYPE, "application/json");
        HttpResponse response = httpClient.execute(post);
        String res = EntityUtils.toString(response.getEntity());
        log.info("{}", res);
        Assert.assertNotNull(res);
    }


    // 设置setSocketTimeout= 1000，检查超过这个时间后是否抛出超时异常
    @Test
    public void testSocketTimeout() {
        long s = System.currentTimeMillis();
        try {
            EntityUtils.consume(response().getEntity());
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("connection consume : {}", System.currentTimeMillis() - s);
    }

    // 设置setDefaultMaxPerRoute=1，并发发送两个请求，是否存在两个连接，用于控制发往同一个目的连接最大数，如果超过这个连接数
    // 则阻塞线程
    // 设置ConnectionRequestTimeout=2000，如果超时2s后，还未发送请求则返回超时错误，用于防止MaxPerRoute阻塞线程太久导致异常
    @Test
    public void testMaxPerRoute() throws Exception {
        long s = System.currentTimeMillis();
        Thread t1 = new Thread(() -> {
            try {
                EntityUtils.consume(response().getEntity());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        t1.start();
        Thread t2 = new Thread(() -> {
            try {
                EntityUtils.consume(response().getEntity());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        t2.start();
        printStatus();
        t1.join();
        printStatus();
        long s2 = System.currentTimeMillis();
        t2.join();
        printStatus();
        long s3 = System.currentTimeMillis();
        log.info("execute time, s1:{},s2:{}, all:{}", (s2 - s), (s3 - s2), (s3 -s));
    }

    // 设置ConnectionRequestTimeout=-1
    // 设置setDefaultMaxPerRoute=10
    @Test
    public void testCleanConnection() throws Exception {
        new Thread(new IdleConnectionEvictionTask(manager), "httpclient-idle-evict-worker").start();
        // 检测不同的目的地连接是否会被重复利用
        EntityUtils.consume(httpClient.execute(new HttpGet("https://www.baidu.com")).getEntity());
        for (int i=0;i<50;i++) {
            new Thread(() -> {
                try {
                    EntityUtils.consume(response().getEntity());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }
        TimeUnit.HOURS.sleep(1);
    }

    // 设置ConnectionRequestTimeout=-1
    // 设置setDefaultMaxPerRoute=10
    @Test
    public void testMaxConnection() throws Exception {
        new Thread(new IdleConnectionEvictionTask(manager), "httpclient-idle-evict-worker").start();
        for (int i=0;i<12;i++) {
            new Thread(() -> {
                try {
                    EntityUtils.consume(httpClient.execute(new HttpGet("http://localhost:8887/dynamic/json/string")).getEntity());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }
        for (int i=0;i<5;i++) {
            new Thread(() -> {
                try {
                    EntityUtils.consume(httpClient.execute(new HttpGet("http://localhost:8886/dynamic/json/string")).getEntity());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }
        TimeUnit.HOURS.sleep(1);
    }

    private void printStatus() {
        PoolStats st = manager.getTotalStats();
        log.info("current pooling connection status,available:{}, max:{}, lease:{}, pending:{}",
                st.getAvailable(), st.getMax(), st.getLeased(), st.getPending());
    }

    private static class IdleConnectionEvictionTask implements Runnable {

        private PoolingHttpClientConnectionManager manager;

        IdleConnectionEvictionTask(PoolingHttpClientConnectionManager manager) {
            this.manager = manager;
        }

        @Override
        public void run() {
            for (;;) {
                try {
                    // 清理过期的连接，即超过keepalive时间的连接或者短连接
                    manager.closeExpiredConnections();
                    // 清理超过多长时间未通信的连接(会关闭还在keepalive的连接)
                    manager.closeIdleConnections(300, TimeUnit.SECONDS);
                    PoolStats st = manager.getTotalStats();
                    log.info("current pooling connection status,available:{}, max:{}, lease:{}, pending:{}",
                            st.getAvailable(), st.getMax(), st.getLeased(), st.getPending());
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    log.warn("显式的中断线程:", e);
                    return;
                }
            }
        }
    }
}

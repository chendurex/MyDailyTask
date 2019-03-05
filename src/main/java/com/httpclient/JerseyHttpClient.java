package com.httpclient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.common.collect.ImmutableMap;
import com.util.BeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.glassfish.jersey.client.ClientProperties;
import org.junit.Assert;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author cheny.huang
 * @date 2019-03-01 16:58.
 */
@Slf4j
public class JerseyHttpClient {

    @Test
    public void testEntityRequestAndResponse() {
        String url = "http://localhost:8887/bpm/proc/deploy";
        Client clientBuilder = ClientBuilder.newClient();
        clientBuilder.property(ClientProperties.CONNECT_TIMEOUT, 1000);
        clientBuilder.property(ClientProperties.READ_TIMEOUT,    1000);
        Response response = clientBuilder.target(url).request(MediaType.APPLICATION_JSON)
                        .post(Entity.json(new ProcDeploy("test")));
        Assert.assertNotNull(response);
        Assert.assertTrue(response.readEntity(ResResult.class).getStatus() == 0);
    }

    @Test
    public void testEntityAndReturnGeneric() {
        String url = "http://localhost:8887/bpm/proc/deploy";
        Client clientBuilder = ClientBuilder.newClient();
        ResResult res = clientBuilder.target(url).request(MediaType.APPLICATION_JSON)
                .post(Entity.json(new ProcDeploy("test")), ResResult.class);
        Assert.assertNotNull(res);
        Assert.assertTrue(res.getStatus() == 0);
    }

    @Test
    public void testNoArgRequest() {
        String url = "http://localhost:8887/no/params";
        Client clientBuilder = ClientBuilder.newClient();
        ResResult res = clientBuilder.target(url).request(MediaType.APPLICATION_JSON)
                .post(null, ResResult.class);
        Assert.assertNotNull(res);
        Assert.assertTrue(res.getStatus() == 0);
    }

    /**
     * post请求，返回值如下：
     {
     "status" : 0,
     "desc" : "true",
     "data" : {
       "bi" : 11,
       "bl" : 1,
       "eName" : "呵呵"
     }
     }
     * <p>注意，如果返回的参数多余接收的参数(如：返回参数中代码name字段，而接收参数中不需要此字段，所以并未定义name字段)，
     * 那么在定义对象时增加@JsonIgnoreProperties(ignoreUnknown = true)注解，
     * 因为这仅仅是一个简单的工具包，按照标准的方式执行，框架层不提供全局配置来改变默认的配置</p>
     * <p>如果觉得麻烦，那么可以选择使用Map接收</p>
     */
    @Test
    public void testGenericTypeResponse() {
        String url = "http://localhost:8887/generic/single/params";
        Client clientBuilder = ClientBuilder.newClient();
        Map<String, Object> req = ImmutableMap.of("bi",11, "eName", "呵呵");
        ResResult<GenericValue> res = clientBuilder.target(url).request(MediaType.APPLICATION_JSON)
                .post(Entity.json(req), new GenericType<ResResult<GenericValue>>(){});
        log.info("res value:{}", BeanUtil.deepPrintObject(res));
        Assert.assertNotNull(res);
        Assert.assertTrue(res.getStatus() == 0);
        Assert.assertTrue(res.getData().getBi()==11);
        Assert.assertTrue(res.getData().getBl()>0);
        Assert.assertEquals(res.getData().geteName(), "呵呵");
    }

    /**
     * get请求，返回值如下
     {
     "status" : 0,
     "desc" : "true",
     "data" : [ {
       "label" : "测试",
       "id" : 40,
       "pid" : 0,
       "subclass" : null
     } ]
     }
     */
    @Test
    public void testGetRequest() {
        String url = "http://localhost:8887/dynamic/route";
        Client client = ClientBuilder.newClient();
        ResResult res = client.target(url).request(MediaType.APPLICATION_JSON).method("GET", ResResult.class);
        log.info("res value:{}", BeanUtil.deepPrintObject(res));
        Assert.assertTrue(res.getStatus() == 0);
        Assert.assertNotNull(res.getData());
        ResResult<List<Map<String,Object>>> generic = client.target(url).request(MediaType.APPLICATION_JSON)
                .method("GET", new GenericType<ResResult<List<Map<String,Object>>>>(){});
        Assert.assertEquals(generic.getData().get(0).get("label"), "测试");
    }

    /**
     * 返回原始的字符串
     */
    @Test
    public void testStringResponse() {
        String url = "http://localhost:8080/modify/password/v";
        Client clientBuilder = ClientBuilder.newClient();
        Map<String, Object> req = ImmutableMap.of("key","spring.swagger2.enabled", "value", "true");
        String res = clientBuilder.target(url).request(MediaType.APPLICATION_JSON)
                .post(Entity.json(req), String.class);
        log.info("{}", res);
        Assert.assertNotNull(res);
    }

    @Test
    public void testAsyncRequest() throws ExecutionException, InterruptedException {
        Client client = ClientBuilder.newClient();
        String url = "http://localhost:8887/generic/single/params";
        Map<String, Object> req = ImmutableMap.of("bi",11);
        Future<ResResult<GenericValue>> res  = client.target(url).request(MediaType.APPLICATION_JSON)
                .async().post(Entity.json(req), new GenericType<ResResult<GenericValue>>(){});
        log.info("res value:{}", BeanUtil.deepPrintObject(res));
        Assert.assertTrue(res.get().getStatus() == 0);
        Assert.assertNotNull(res.get().getData());
    }



}

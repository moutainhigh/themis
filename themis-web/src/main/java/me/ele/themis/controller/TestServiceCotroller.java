package me.ele.themis.controller;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import me.ele.rpc.json.protocol.JsonRequest;
import me.ele.themis.judgement.api.JudgementService;
import me.ele.themis.ruleengine.api.RuleService;
import me.ele.utils.helper.StringUtil;
import me.ele.utils.json.JacksonHelper;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URI;
import java.util.HashMap;

/**
 * Created by guoxin on 22/8/2016.
 */
@Controller
@RequestMapping("/test")
public class TestServiceCotroller {


    private JudgementService judgementService ;

    private RuleService ruleService ;

    @PostConstruct
    public void init() throws IOException {
        judgementService = ClientUtils.getClient(JudgementService.class);
        ruleService = ClientUtils.getClient(RuleService.class);
    }



    @RequestMapping(value = "/QUERY_J", method = RequestMethod.GET)
    @ResponseBody
    public String queryJudgement(){
        return judgementService.queryJudgement();
    }

    @RequestMapping(value = "/QUERY_R", method = RequestMethod.GET)
    @ResponseBody
    public String query(){
        return ruleService.testRule();
    }

    @RequestMapping(value = "/START", method = RequestMethod.GET)
    @ResponseBody
    public String startTask(){
        return ruleService.startTest();
    }


    @RequestMapping(value = "/SOA", method = RequestMethod.GET)
    @ResponseBody
    public String soaQuery(@RequestParam(value="url", required=false) String url,
                           @RequestParam(value="iface", required=false) String iterface,
                           @RequestParam(value="method", required=false) String method,
                           @RequestParam(value="args", required=false) String args){


        HashMap<String,String> metas = new HashMap<String,String>();

        HashMap<String, String> tempMap = JSON.parseObject(args, new HashMap<String, String>().getClass());

        JsonRequest request = generateJsonRequest(iterface , method , tempMap , metas);

        HttpPost post = new HttpPost();
        post.setURI(URI.create(StringUtil.toString(new Object[]{"http://", url, "/rpc"})));
        post.setEntity(new StringEntity(this.serializeRequest(request), ContentType.APPLICATION_JSON));
        String result = "";
        try {
            result = JacksonHelper.getMapper().writeValueAsString(soaRequest(post));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return result;
    }

    private JsonRequest generateJsonRequest(String iface , String method , HashMap<String,String> args , HashMap<String,String> metas ){
        Method mockMethod = null;
        try {
            mockMethod = Object.class.getMethod("toString",null);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        JsonRequest request = new JsonRequest("1.0", "1.1", "1.1", mockMethod , null, null);
        request.setIface(iface);
        request.setMethod(method);
        request.setArgs(args);
        request.setMetas(metas);
        return request;
    }


    private String soaRequest(HttpPost post){
        String result = "";
        try {
            HttpClient e = new DefaultHttpClient();
            HttpResponse response = e.execute(post);

            try {
                int e1 = response.getStatusLine().getStatusCode();
                result = EntityUtils.toString(response.getEntity());
                if(200 != e1) {
                    result = String.format("Service(%s) receives non-OK status(%d) and content(%s)!", new Object[]{post.getEntity(), Integer.valueOf(e1), result});
                }
            } catch (IOException var5) {
                result = String.format("Service(%s) fails to read HttpResponse!" , "test");
            }
        } catch (IOException var6) {
            result = String.format("Service(%s) fails to execute HttpRequest!"  , "test");
        }

        return result;
    }

    private String serializeRequest(JsonRequest request) {
        try {
            return JacksonHelper.getMapper().writeValueAsString(request);
        } catch (JsonProcessingException var3) {
            printLog("Fail to Serialize Request!" + var3);
        }
        return "";
    }


    private void printLog(String log){
        System.out.print(log);
    }

}

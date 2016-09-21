package me.ele.themis.ruleengine.drools;

import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyObject;
import me.ele.themis.common.biz.RulesBiz;
import org.drools.*;
import org.drools.builder.KnowledgeBuilder;
import org.drools.compiler.DroolsParserException;
import org.drools.compiler.PackageBuilder;
import org.drools.compiler.PackageBuilderConfiguration;
import org.drools.rule.builder.dialect.java.JavaDialectConfiguration;
import org.drools.runtime.StatefulKnowledgeSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.*;

/**
 * Created by admin on 9/9/2016.
 */
@Service
public class RuleClient {

    private GroovyObject ruleEngine;

    @Autowired
    public KnowledgeBase kbaseTemplate;

    @Autowired
    public RulesBiz rulesBiz;

    /** 默认启动初始化规则1 */
    public final static int INI_RULE_INDEX = 1;

    //    public  StatefulKnowledgeSession getNewSession(){
//        return kbaseTemplate.newStatefulKnowledgeSession();
//    }

    private RuleBase ruleBase;

    public StatefulSession getNewStatefulSession(){
        return ruleBase.newStatefulSession();
    }

//    @PostConstruct
//    public void reloadRule(){
//
//        StringBuffer sb=new StringBuffer();
//        sb.append("package me.ele.themis.common.drools \n");
//        sb.append("import me.ele.themis.ruleengine.drools.RuleDomain;\n");
//        sb.append("import me.ele.themis.ruleengine.drools.RuleTestTask;\n");
//
//        sb.append("rule \"drools_11\"\n");
//        sb.append("when\n");
//        sb.append("$rule:RuleDomain(type >= 20)\n");
//        sb.append("then \n");
//        sb.append("$rule.setName(\"random mun > 20\");\n");
//        sb.append("$rule.setDiscription(\"level + 1\");\n");
//        sb.append("$rule.setAward($rule.getAward() +1 );\n");
//
//        sb.append("end\n");
//        System.out.println("更新规则数据: --- " +  sb.toString());
//
//        PackageBuilder builder = new PackageBuilder();
//        try {
//            builder.addPackageFromDrl(
//                    new StringReader(sb.toString()));
//        } catch (DroolsParserException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        if (builder.hasErrors()) {
//            System.err.print(builder.getErrors());
//            return ;
//        }
//        ruleBase = RuleBaseFactory.newRuleBase();
//        ruleBase.addPackage(builder.getPackage());
//    }

    public  String ruleExcute(RuleDomain ruleDomain){
        return (String)ruleEngine.invokeMethod("excute",ruleDomain);
    }

    @PostConstruct
    public String initGroovyRuleEngine(){
        return reloadGroovyRuleEngine(INI_RULE_INDEX);
    }

    public String reloadGroovyRuleEngine(int ruleIndex){
        String result = "success";

        //测试数据库
        String rulesInDb =  rulesBiz.queryRulesById(ruleIndex);

        ClassLoader parent = ClassLoader.getSystemClassLoader();
        GroovyClassLoader loader = new GroovyClassLoader(parent);
        Class< ?> clazz = loader.parseClass(rulesInDb);
        ruleEngine = null;
        try {
            ruleEngine = (GroovyObject)clazz.newInstance();
        } catch (Exception e) {
            result = e.getMessage();
        }
        System.out.println("groovy rule load success :"  +  rulesInDb);
        return result;
    }

    /** 测试加载本地groovy文件     --------------------*/
    private String readScriptLocal(){
        String script=
                "import me.ele.themis.ruleengine.drools.RuleDomain; \n "+
                        "class TestScript {  \n" +
                        "    static String excute(RuleDomain ruleDomain){  \n" +
                        "        println ruleDomain;  \n" +
                        "        if(ruleDomain.getType() > 10){ ruleDomain.setDiscription(\" 该数字大于 10\"); };"+
                        "        else{ ruleDomain.setDiscription(\" 该数字小于 10\"); };"+
                        "        return ruleDomain.toString();  \n" +
                        "    }  \n" +
                        "} ";//groovy script


           		/* 获取系统当前路径 */
        String s = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        File file = new File(s);
		/* 获取系统工程路径 */
        String rootPath = file.getParentFile().getParentFile().getPath();
        String scriptNew = testReadScript(new StringBuffer(rootPath).append("/target/classes/conf/properties/script.groovy").toString() );
        System.out.println(scriptNew);
        return scriptNew;
    }

    private String testReadScript(String path){
        StringBuffer scriptBuf = new StringBuffer();
        File file = new File(path);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = "";
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                scriptBuf.append(tempString);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        return scriptBuf.toString();
    }
    /** 测试加载本地groovy文件     --------------------*/
}

package com.zhang.devkit;


import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class MysqlCodeGenerator  implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(MysqlCodeGenerator.class);


    @Autowired
    private MybatisPlusGeneratorConfig mpConfig;

    private GlobalConfig gbConfig;
    private DataSourceConfig dsConfig;
    private PackageConfig pgConfig;
    private InjectionConfig inConfig;
    private TemplateConfig tpConfig;
    private StrategyConfig stConfig;




    public static void main(String args[]) {
        SpringApplication.run(MysqlCodeGenerator.class, args);
    }

    public void run(String... args) throws Exception {

            log.info("Creating tables");
            log.info("Mysql Code generator....");

             generate(args);


    }


    private GlobalConfig getGlobalConfig(){

        log.info("set global config.");


        log.info(mpConfig.getModuleName());

        GlobalConfig gbConfig = new GlobalConfig();

        gbConfig.setOutputDir(mpConfig.getProjectPath() + "/src/main/java");
        gbConfig.setAuthor(mpConfig.getAuthor());
        gbConfig.setOpen(mpConfig.getOpen());
        // gbConfig.setSwagger2(true);

        return gbConfig;

    }



    private PackageConfig getPackageConfig() {


        // 包配置
        PackageConfig pkConfig = new PackageConfig();
        pkConfig.setModuleName(mpConfig.getModuleName());
        pkConfig.setParent(mpConfig.getParent());

        return pkConfig;

    }



    private DataSourceConfig getDataSourceConfig(){

        // 数据源配置
        DataSourceConfig dsConfig = new DataSourceConfig();
        //dsConfig.setUrl("jdbc:mysql://localhost:3306/mybatisplusdemo?useUnicode=true&useSSL=false&characterEncoding=utf8");
        //dsConfig.setSchemaName("mybatisplusdemo");
        //dsConfig.setDriverName("com.mysql.jdbc.Driver");
        //dsConfig.setUsername("root");
        //dsConfig.setPassword("root123");


        dsConfig.setUrl(mpConfig.getJdbcUrl());
        //dsConfig.setSchemaName(mpConfig.getJdbcSchemaName());
        dsConfig.setDriverName(mpConfig.getJdbcDriverName());
        dsConfig.setUsername(mpConfig.getJdbcUserName());
        dsConfig.setPassword(mpConfig.getJdbcPassword());


        return dsConfig;
    }


    private InjectionConfig getInjectionConfig() {
        // 自定义配置
        InjectionConfig inConfig = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };

        // 如果模板引擎是 freemarker
        // String templatePath = "/templates/mapper.xml.ftl";

        String templatePath = mpConfig.getTemplatePath();

        // 如果模板引擎是 velocity
        // String templatePath = "/templates/mapper.xml.vm";

        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return mpConfig.getProjectPath() + "/src/main/resources/mapper/" + mpConfig.getModuleName()
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }

            //public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
            //    return projectPath + "/src/main/resources/mapper/" + pc.getModuleName()
            //            + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            //}

        });

        inConfig.setFileOutConfigList(focList);

        return inConfig;

    }



    private TemplateConfig getTemplateConfig() {

        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();
        //templateConfig.setEntity("templates/entity.java");

        // 配置自定义输出模板
        //指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
        // templateConfig.setEntity("templates/entity2.java");
        templateConfig.setService(mpConfig.getService());
        templateConfig.setServiceImpl(mpConfig.getServiceImpl());
        templateConfig.setController(mpConfig.getController());

        templateConfig.setXml(null);

        return templateConfig;
    }


    private StrategyConfig getStrategyConfig() {

        // 策略配置
        StrategyConfig stConfig = new StrategyConfig();
        stConfig.setNaming(NamingStrategy.underline_to_camel);
        stConfig.setColumnNaming(NamingStrategy.underline_to_camel);
        //stConfig.setSuperEntityClass("你自己的父类实体,没有就不用设置!");
        stConfig.setEntityLombokModel(true);
        stConfig.setRestControllerStyle(true);
        // 公共父类
        //strategy.setSuperControllerClass("你自己的父类控制器,没有就不用设置!");
        // 写于父类中的公共字段
        //strategy.setSuperEntityColumns("id");
        //stConfig.setInclude(scanner("表名，多个英文逗号分割").split(","));

        stConfig.setInclude(mpConfig.getTables());
        stConfig.setControllerMappingHyphenStyle(true);
        stConfig.setTablePrefix(mpConfig.getModuleName() + "_");


        return stConfig;
    }



    /*
    public String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + "：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.checkValNotNull(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }
    */


    public void generate(String[] args) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();


        mpg.setGlobalConfig(getGlobalConfig());

        mpg.setPackageInfo(getPackageConfig());

        mpg.setDataSource(getDataSourceConfig());



        mpg.setCfg(getInjectionConfig());

        mpg.setTemplate(getTemplateConfig());



        mpg.setStrategy(getStrategyConfig());
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();

    }

}

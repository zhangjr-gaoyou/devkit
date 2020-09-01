package com.zhang.devkit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;



@Configuration
@ConfigurationProperties(prefix = MybatisPlusGeneratorConfig.PREFIX, ignoreInvalidFields = true)

public class MybatisPlusGeneratorConfig {

    public static final String PREFIX = "mybatisplus";

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    private String moduleName;

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    private String parent;

    public String getTemplatePath() {
        return templatePath;
    }

    public void setTemplatePath(String templatePath) {
        this.templatePath = templatePath;
    }

    private String templatePath;

    public String getXml() {
        return xml;
    }

    public String getTables() {
        return tables;
    }

    public void setTables(String tables) {
        this.tables = tables;
    }

    private String tables;


    public void setXml(String xml) {
        this.xml = xml;
    }

    private  String xml;

    public String getProjectPath() {
        return projectPath;
    }

    public void setProjectPath(String projectPath) {
        this.projectPath = projectPath;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Boolean getOpen() {
        return isOpen;
    }

    public void setOpen(Boolean open) {
        isOpen = open;
    }

    private String projectPath;
    @Value("${author:demo}")
    private String author;
    @Value("${open:true}")
    private Boolean isOpen;


    @Value("${mybatisplus.jdbc.url}")
    private String jdbcUrl;

    @Value("${mybatisplus.jdbc.driverName}")
    private String jdbcDriverName;

    public String getJdbcSchemaName() {
        return jdbcSchemaName;
    }

    public void setJdbcSchemaName(String jdbcSchemaName) {
        this.jdbcSchemaName = jdbcSchemaName;
    }

    @Value("${mybatisplus.jdbc.schemaName}")
    private String jdbcSchemaName;


    public String getJdbcUrl() {
        return jdbcUrl;
    }

    public void setJdbcUrl(String jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
    }

    public String getJdbcDriverName() {
        return jdbcDriverName;
    }

    public void setJdbcDriverName(String jdbcDriverName) {
        this.jdbcDriverName = jdbcDriverName;
    }

    public String getJdbcUserName() {
        return jdbcUserName;
    }

    public void setJdbcUserName(String jdbcUserName) {
        this.jdbcUserName = jdbcUserName;
    }

    public String getJdbcPassword() {
        return jdbcPassword;
    }

    public void setJdbcPassword(String jdbcPassword) {
        this.jdbcPassword = jdbcPassword;
    }

    @Value("${mybatisplus.jdbc.userName}")
    private String jdbcUserName;

    @Value("${mybatisplus.jdbc.password}")
    private String jdbcPassword;





}

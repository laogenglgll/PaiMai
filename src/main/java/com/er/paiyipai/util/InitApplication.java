package com.er.paiyipai.util;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

public class InitApplication {
    public static void main(String[] args) {
        //创建生成器工具AutoGenerator
        AutoGenerator mpg = new AutoGenerator();
        //全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setAuthor("");
        //获取到项目所在的绝对路径
        gc.setOutputDir(System.getProperty("user.dir")+"/paiyipai/src/main/java");
        System.out.println(gc.getOutputDir());
        gc.setOpen(false);//是否打开资源管理器(文件夹浏览窗口)
        gc.setFileOverride(true);
        gc.setMapperName("%sDao");//设置Dao名，默认名为Mapper
        gc.setServiceName("%sService");//去掉默认的'I'
        gc.setIdType(IdType.ASSIGN_ID);//设置主键类型，自增会自动调整
        gc.setDateType(DateType.ONLY_DATE);//设置日期类型
        mpg.setGlobalConfig(gc);
        //添加全局、数据源、包配置
        //数据源配置
        DataSourceConfig ds = new DataSourceConfig();
        ds.setDriverName("com.mysql.cj.jdbc.Driver");
        ds.setUrl("jdbc:mysql://localhost:3309/paimai?serverTimezone=UTC");
        ds.setUsername("root");
        ds.setPassword("root");
        ds.setDbType(DbType.MYSQL);
        mpg.setDataSource(ds);
        //包名配置
        PackageConfig pc = new PackageConfig();
        pc.setParent("com.er");
        pc.setModuleName("paiyipai");

        pc.setEntity("model");//默认为entity
        pc.setMapper("dao");//默认为mapper
        pc.setXml("mapper");//默认为mapper/xml
        mpg.setPackageInfo(pc);
        //添加策略配置，执行代码生成
        //策略配置
        StrategyConfig sc = new StrategyConfig();
        //sc.setInclude("sc");//默认生成所有表，"sc"表名
        sc.setNaming(NamingStrategy.underline_to_camel);//类名下划线改驼峰命名
        sc.setColumnNaming(NamingStrategy.underline_to_camel);//属性名下划线改驼峰命名
        sc.setEntityLombokModel(true);//设置lombok注解
        sc.setRestControllerStyle(true);
        sc.setControllerMappingHyphenStyle(true);//支持路径符号连接
        //sc.setEntityTableFieldAnnotationEnable(true);//设置表及字段注解
        mpg.setStrategy(sc);
        mpg.execute();

    }
}


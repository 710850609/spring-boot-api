package me.linbo.api;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

/**
 * @author LinBo
 * @date 2020-01-06 17:41
 */
public class MybatisPlusGenerate {

    public static void main(String[] args) {
        String projectPath = "G:/mybatis";
        make(projectPath);
        System.out.println("生成完成 " + projectPath);
    }


    public static void make(String projectPath) {
        // 全局配置
        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setOutputDir(projectPath + "/src/main/java");
        globalConfig.setAuthor("LinBo");
        globalConfig.setOpen(false);

        // 数据源配置
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setUrl("jdbc:mysql://localhost:3306/api?Unicode=true&characterEncoding=UTF-8&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC");
        dataSourceConfig.setDriverName("com.mysql.cj.jdbc.Driver");
        dataSourceConfig.setUsername("root");
        dataSourceConfig.setPassword("123456");

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setInclude(new String[] { "role", "resource", "role_resource", "user_role" }); // 需要生成的表
        strategy.setTablePrefix("");
        // strategy.setExclude(new String[]{"test"}); // 排除生成的表

        // strategy.setCapitalMode(true);// 全局大写命名 ORACLE 注意
        // strategy.setTablePrefix(new String[] { "tlog_", "tsys_" });// 此处可以修改为您的表前缀
        strategy.setNaming(NamingStrategy.underline_to_camel);// 表名生成策略
        strategy.setEntityLombokModel(true);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent("me.linbo.web");
        pc.setModuleName("security");
        pc.setController("service");
        pc.setService("bll");
        pc.setEntity("model");

        AutoGenerator mpg = new AutoGenerator();
        mpg.setGlobalConfig(globalConfig);
        mpg.setDataSource(dataSourceConfig);
        mpg.setStrategy(strategy);
        mpg.setPackageInfo(pc);

        // 执行生成
        mpg.execute();
    }

}

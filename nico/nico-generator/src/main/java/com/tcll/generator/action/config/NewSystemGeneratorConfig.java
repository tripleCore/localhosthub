package com.tcll.generator.action.config;

import com.baomidou.mybatisplus.generator.config.rules.DbType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

/**
 * 默认的代码生成的配置
 */
public class NewSystemGeneratorConfig extends AbstractGeneratorConfig {

	//全局配置
    protected void globalConfig() {
        globalConfig.setOutputDir("E:\\my\\workspace_bankservice\\xase-parent\\xase-web\\src\\main\\java");//写自己项目的绝对路径,注意具体到java目录
        globalConfig.setFileOverride(true);
        globalConfig.setEnableCache(false);// XML 二级缓存
        globalConfig.setBaseResultMap(true);// XML ResultMap
        globalConfig.setBaseColumnList(true);// XML columList
        globalConfig.setOpen(false);
        globalConfig.setAuthor("caocc");//作者
    }

    //数据库配置
    protected void dataSourceConfig() {
        dataSourceConfig.setDbType(DbType.MYSQL);
        dataSourceConfig.setDriverName("com.mysql.jdbc.Driver");
        dataSourceConfig.setUsername("root");
        dataSourceConfig.setPassword("q1w2e3r4");
        dataSourceConfig.setUrl("jdbc:mysql://127.0.0.1:3306/cpms?characterEncoding=utf8");
    }
    //策略配置
    protected void strategyConfig() {
        //strategyConfig.setTablePrefix(new String[]{"xx_"});// 此处可以修改为您的表前缀
        strategyConfig.setNaming(NamingStrategy.underline_to_camel);//表名生成策略-下划线转驼峰规则
    }
    //包配置
    protected void packageConfig() {
        packageConfig.setParent(null);
        packageConfig.setEntity("org.xase.web.model");
        packageConfig.setMapper("org.xase.web.dao");
        packageConfig.setXml("org.xase.web.dao.mapping");
    }
    //cpms特有全局配置
    protected void contextConfig() {
        contextConfig.setProPackage("org.xase.web");
        contextConfig.setCoreBasePackage("org.xase.utils");
        contextConfig.setBizChName("字典管理");
        contextConfig.setBizEnName("sysDict");
        contextConfig.setModuleName("system");
        contextConfig.setProjectPath("E:\\my\\workspace_bankservice\\xase-parent\\xase-web");//写自己项目的绝对路径
        contextConfig.setEntityName("SysDict");
        sqlConfig.setParentMenuName(null);//这里写已有菜单的名称,当做父节点

        /**
         * mybatis-plus 生成器开关
         */
        contextConfig.setEntitySwitch(true);
        contextConfig.setDaoSwitch(true);
        contextConfig.setServiceSwitch(true);

        /**
         * adi 生成器开关
         */
        contextConfig.setControllerSwitch(true);
        contextConfig.setIndexPageSwitch(true);
        contextConfig.setAddPageSwitch(true);
        contextConfig.setEditPageSwitch(true);
        contextConfig.setJsSwitch(true);
        contextConfig.setInfoJsSwitch(true);
        contextConfig.setSqlSwitch(true);
    }

    @Override
    protected void config() {
        globalConfig();
        dataSourceConfig();
        strategyConfig();
        packageConfig();
        contextConfig();
    }
}

package com.tcll.generator.action.config;

import java.io.File;
import java.util.List;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.tcll.generator.engine.SimpleTemplateEngine;
import com.tcll.generator.engine.base.CpmsTemplateEngine;
import com.tcll.generator.engine.config.ContextConfig;
import com.tcll.generator.engine.config.SqlConfig;
import com.tcll.generator.utils.FileUtil;

/**
 * 代码生成的抽象配置
 */
public abstract class AbstractGeneratorConfig {

	/**
	 * mybatis-plus代码生成器配置
	 */
	// 全局配置
	GlobalConfig globalConfig = new GlobalConfig();
	// 数据源配置
	DataSourceConfig dataSourceConfig = new DataSourceConfig();
	// 策略配置
	StrategyConfig strategyConfig = new StrategyConfig();
	// 包配置
	PackageConfig packageConfig = new PackageConfig();

	TableInfo tableInfo = null;

	/**
	 * 独立 业务代码生成器配置
	 */
	ContextConfig contextConfig = new ContextConfig();

	SqlConfig sqlConfig = new SqlConfig();

	protected abstract void config();

	public void init() {
		config();

		packageConfig
				.setService(contextConfig.getProPackage() + ".modular." + contextConfig.getModuleName() + ".service");
		packageConfig.setServiceImpl(
				contextConfig.getProPackage() + ".modular." + contextConfig.getModuleName() + ".service.impl");

		// controller没用掉,生成之后会自动删掉
		packageConfig.setController("TTT");

		if (!contextConfig.getEntitySwitch()) {
			packageConfig.setEntity("TTT");
		}

		if (!contextConfig.getDaoSwitch()) {
			packageConfig.setMapper("TTT");
			packageConfig.setXml("TTT");
		}

		if (!contextConfig.getServiceSwitch()) {
			packageConfig.setService("TTT");
			packageConfig.setServiceImpl("TTT");
		}

	}

	/**
	 * 删除不必要的代码
	 */
	public void destory() {
		String outputDir = globalConfig.getOutputDir() + "/TTT";
		FileUtil.deleteDir(new File(outputDir));
	}

	public AbstractGeneratorConfig() {
	}

	// 执行MyBatis-Plus生成器
	public void doMpGeneration() {
		init();
		AutoGenerator autoGenerator = new AutoGenerator();
		autoGenerator.setGlobalConfig(globalConfig);
		autoGenerator.setDataSource(dataSourceConfig);
		autoGenerator.setStrategy(strategyConfig);
		autoGenerator.setPackageInfo(packageConfig);
		autoGenerator.execute();
		destory();

		// 获取table信息,用于cpms代码生成
		List<TableInfo> tableInfoList = autoGenerator.getConfig().getTableInfoList();
		if (tableInfoList != null && tableInfoList.size() > 0) {
			this.tableInfo = tableInfoList.get(0);
		}
	}

	// 执行独立业务生成器
	public void doAdiGeneration() {
		CpmsTemplateEngine cpmsTemplateEngine = new SimpleTemplateEngine();
		cpmsTemplateEngine.setContextConfig(contextConfig);
		sqlConfig.setConnection(dataSourceConfig.getConn());
		cpmsTemplateEngine.setSqlConfig(sqlConfig);
		cpmsTemplateEngine.setTableInfo(tableInfo);
		cpmsTemplateEngine.start();
	}
}

package com.tcll.generator.action;


import com.tcll.generator.action.config.NewSystemGeneratorConfig;

/**
 * 代码生成器,可以生成实体,dao,service,controller,html,js
 */
public class NewSystemCodeGenerator {

    public static void main(String[] args) {

        /**
         * Mybatis-Plus的代码生成器:
         *      mp的代码生成器可以生成实体,mapper,mapper对应的xml,service
         */
        NewSystemGeneratorConfig cpmsGeneratorConfig = new NewSystemGeneratorConfig();
        cpmsGeneratorConfig.doMpGeneration();

        /**
         * adi的生成器:
         *      adi的代码生成器可以生成controller,html页面,页面对应的js
         */
        cpmsGeneratorConfig.doAdiGeneration();
    }

}
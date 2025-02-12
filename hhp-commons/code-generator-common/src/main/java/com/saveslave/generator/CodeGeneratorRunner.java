package com.saveslave.generator;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableFill;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CodeGeneratorRunner {

    public static void main(String[] args) {
        try {
            MybatisPlusCodeGenerator generator = new MybatisPlusCodeGenerator();
            generator.DB_URL = "jdbc:mysql://127.0.0.1:3306/sa-token-db?useUnicode=true&characterEncoding=utf-8&serverTimezone=GMT";
            generator.DB_USER_NAME = "root";
            generator.DB_PASSWORD = "root";
            generator.TABLE_PREFIX = new String[]{"sys_"};
            generator.PACKAGE_PARENT = "com.saveslave.hhp";
            generator.SUPER_ENTITY_CLASS = "";
            generator.SUPER_ENTITY_COLUMNS = new String[]{};
            generator.USE_TEMP_OUTPUT = true;
            generator.INCLUDES = new String[]{
                    "sys_user"
            };
            generateDefaultCode(generator);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void generateDefaultCode(MybatisPlusCodeGenerator generator) throws IOException {
        GlobalConfig globalConfig = generator.getDefaultGlobalConfig();
        DataSourceConfig dataSourceConfig = generator.getDefaultDataSourceConfig();
        StrategyConfig strategyConfig = generator.getDefaultStrategyConfig();
        PackageConfig packageConfig = generator.getDefaultPackageConfig();
        TemplateConfig templateConfig = generator.getDefaultTemplateConfig();
        InjectionConfig injectionConfig = generator.getDefaultInjectionConfig(packageConfig);
        strategyConfig.setEntityTableFieldAnnotationEnable(true);
        //==============================自动填充==============================
        List<TableFill> tableFillList = new ArrayList<>();
        TableFill CREATOR_ID = new TableFill("CREATOR_ID", FieldFill.INSERT);
        TableFill CREATE_TIME = new TableFill("CREATE_TIME", FieldFill.INSERT);
        TableFill UPDATER_ID = new TableFill("UPDATER_ID", FieldFill.INSERT_UPDATE);
        TableFill UPDATE_TIME = new TableFill("UPDATE_TIME", FieldFill.INSERT_UPDATE);
        tableFillList.add(CREATOR_ID);
        tableFillList.add(CREATE_TIME);
        tableFillList.add(UPDATER_ID);
        tableFillList.add(UPDATE_TIME);
        strategyConfig.setTableFillList(tableFillList);
        //==============================自动填充==============================
        generator.generateCode(globalConfig, dataSourceConfig, strategyConfig, packageConfig, templateConfig, injectionConfig);
    }
}

package com.saveslave.generator;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.querys.MySqlQuery;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.FileType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.DefaultResourceLoader;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class MybatisPlusCodeGenerator {
    public static String NO_CONTROLLER = "";
    /*public static String CONTROLLER = "com.pd.common.basic.controller.BaseController";
    public static String REST_CONTROLLER = "com.pd.common.basic.controller.BaseRestController";*/

    public String DB_URL = "jdbc:mysql://127.0.0.1:3306/tdb_project?useUnicode=true&characterEncoding=utf-8&serverTimezone=GMT";
    public String DB_USER_NAME = "root";
    public String DB_PASSWORD = "root1234";
    public String[] TABLE_PREFIX = new String[]{};
    public String[] INCLUDES = new String[]{};
    public String[] EXCLUDES = new String[]{};
    public String SUPER_ENTITY_CLASS = "";
    public String[] SUPER_ENTITY_COLUMNS = new String[]{};
    public String SUPER_SERVICE_CLASS;
    public String SUPER_SERVICE_IMPL_CLASS;
    public String SUPER_MAPPER_CLASS;
    public String PACKAGE_PARENT = "com.sp.common.demo";
    public String PACKAGE_ENTITY = "persistent.entity";
    public String PACKAGE_MAPPER = "persistent.mapper";
    public String PACKAGE_XML = "persistent.mapper.xml";
    public String PACKAGE_SERVICE = "service";
    public String PACKAGE_SERVICEIMPL = "service.impl";
    public String PACKAGE_CONTROLLER = "controller";

    public String LOGIC_DELETE_FIELD_NAME = "data_status";
    public Boolean USE_TEMP_OUTPUT = true;
    public Boolean ENABLE_DO_SWAGGER = true;
    public Boolean ENABLE_DTO_SWAGGER = true;
    public String TEMP_DIR = "";
    public IdType ID_TYPE = IdType.AUTO;
    public boolean FILE_OVERRIDE = false;

    private  String  path;
    private  String  projectPath;

    public GlobalConfig getDefaultGlobalConfig() throws IOException {
        //输出路径
        String exportPath = getExportPath();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir(exportPath);
        gc.setFileOverride(FILE_OVERRIDE);
        gc.setActiveRecord(false);
        // XML 二级缓存
        gc.setEnableCache(false);
        // XML ResultMap
        gc.setBaseResultMap(true);
        // XML columList
        gc.setBaseColumnList(true);
        gc.setAuthor("author");
        gc.setOpen(false);
        gc.setDateType(DateType.ONLY_DATE);
        gc.setServiceName("%s");
        gc.setServiceImplName("%sServiceImpl");
        gc.setSwagger2(ENABLE_DO_SWAGGER);
        gc.setIdType(ID_TYPE);
        return gc;
    }

    public DataSourceConfig getDefaultDataSourceConfig() {
        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        if (DB_URL.toLowerCase().contains("mysql")) {
            dsc.setDbType(DbType.MYSQL);
            dsc.setDriverName("com.mysql.jdbc.Driver");
        } else if (DB_URL.toLowerCase().contains("oracle")) {
            dsc.setDbType(DbType.ORACLE);
            dsc.setDriverName("oracle.jdbc.driver.OracleDriver");
        } else if (DB_URL.toLowerCase().contains("oceanbase")) {
            dsc.setDbType(DbType.OCEAN_BASE);
            dsc.setDriverName("com.oceanbase.jdbc.Driver");
            dsc.setDbQuery(new CustomMySqlQuery());
        }
        dsc.setUsername(DB_USER_NAME);
        dsc.setPassword(DB_PASSWORD);
        dsc.setUrl(DB_URL);
        return dsc;
    }

    public StrategyConfig getDefaultStrategyConfig() {
        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        // 表名生成策略
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setTablePrefix(TABLE_PREFIX);
        if(INCLUDES.length > 0) {
            strategy.setInclude(INCLUDES);
        } else {
            strategy.setExclude(EXCLUDES);
        }
        // 自定义实体父类
        if(StringUtils.isNotBlank(SUPER_ENTITY_CLASS)) {
            strategy.setSuperEntityClass(SUPER_ENTITY_CLASS);
            strategy.setSuperEntityColumns(SUPER_ENTITY_COLUMNS);
        }
        /*// 自定义Controller父类
        if(StringUtils.isNotBlank(SUPER_CONTROLLER_CLASS)) {
            strategy.setSuperControllerClass(SUPER_CONTROLLER_CLASS);
        }*/
        // 自定义SERVICE父类
        if(StringUtils.isNotBlank(SUPER_SERVICE_CLASS)) {
            strategy.setSuperServiceClass(SUPER_SERVICE_CLASS);
        }
        // 自定义SERVICE父类
        if(StringUtils.isNotBlank(SUPER_SERVICE_IMPL_CLASS)) {
            strategy.setSuperServiceImplClass(SUPER_SERVICE_IMPL_CLASS);
        }
        // 自定义Mapper父类
        if(StringUtils.isNotBlank(SUPER_MAPPER_CLASS)) {
            strategy.setSuperMapperClass(SUPER_MAPPER_CLASS);
        }

        // 支持lombok 简化代码
        strategy.setEntityLombokModel(true);
        // 支持Restful风格接口
        strategy.setRestControllerStyle(true);
        strategy.setLogicDeleteFieldName(LOGIC_DELETE_FIELD_NAME);

        List<TableFill> tableFillList = new ArrayList<>();
        TableFill CREATOR_ID = new TableFill("CREATED_BY", FieldFill.INSERT);
        TableFill CREATE_TIME = new TableFill("CREATED_TIME", FieldFill.INSERT);
        TableFill UPDATER_ID = new TableFill("UPDATED_BY", FieldFill.INSERT_UPDATE);
        TableFill UPDATE_TIME = new TableFill("UPDATED_TIME", FieldFill.INSERT_UPDATE);

        tableFillList.add(CREATOR_ID);
        tableFillList.add(CREATE_TIME);
        tableFillList.add(UPDATER_ID);
        tableFillList.add(UPDATE_TIME);
        strategy.setTableFillList(tableFillList);

        return strategy;
    }

    public PackageConfig getDefaultPackageConfig() {
        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent(PACKAGE_PARENT);
        pc.setEntity(PACKAGE_ENTITY);
        pc.setMapper(PACKAGE_MAPPER);
        pc.setXml(PACKAGE_XML);
        pc.setController(PACKAGE_CONTROLLER);
        pc.setService(PACKAGE_SERVICE);
        pc.setServiceImpl(PACKAGE_SERVICEIMPL);
        return pc;
    }

    public TemplateConfig getDefaultTemplateConfig() {
        TemplateConfig templateConfig = new TemplateConfig();
        /*templateConfig.setController("/code_templates/controller.java");*/
       /* templateConfig.setService("/code_templates/service.java");*/
        templateConfig.setService(null);
        templateConfig.setServiceImpl("/code_templates/serviceImpl.java");
        templateConfig.setMapper("/code_templates/mapper.java");
        templateConfig.setXml("/code_templates/mapper.xml");
        templateConfig.setEntity("/code_templates/entity.java");
          return templateConfig;
    }

    public String getExportPath() throws IOException{
        if(path==null){
            // 获取工程路径
            String projectPath = getProjectPath();
            String exportPath;

                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
                TEMP_DIR = projectPath + "/temp/" + format.format(new Date());
                exportPath = StringUtils.replaceEach(TEMP_DIR, new String[]{"/", "."},
                        new String[]{File.separator, File.separator});

            projectPath = StringUtils.replaceEach(projectPath + "/src/main/java/", new String[]{"/", "."},
                        new String[]{File.separator, File.separator});

            System.out.println("exportPath="+exportPath);
            System.out.println("projectPath="+projectPath);
            path=exportPath;
        }

        System.out.println(path);
        return path;
    }

    public String getProjectPath() throws IOException {
        // 获取工程路径
        File projectPath = new DefaultResourceLoader().getResource("").getFile();
        while (!new File(projectPath.getPath() + File.separator + "src").exists()) {
            projectPath = projectPath.getParentFile();
        }
        String path = StringUtils.replaceEach(projectPath.toString(), new String[]{"/", "."},
                new String[]{File.separator, File.separator});
        return path;
    }

    public FileOutConfig createXmlFileOutConfig(PackageConfig packageConfig) throws IOException{
        String templatePath = "/code_templates/mapper.xml.vm";
        String projectPath = getProjectPath();
        String format = "%s/src/main/resources/%s/%s/%sMapper";

        return new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名
                return StringUtils.replaceEach(String.format(format, projectPath, packageConfig.getParent()
                        , packageConfig.getXml(), tableInfo.getEntityName())
                        , new String[]{"/", "."}, new String[]{File.separator, File.separator}) + StringPool.DOT_XML;
            }
        };
    }

    public FileOutConfig createServiceFileOutConfig(PackageConfig packageConfig) throws IOException{
        String templatePath = "/code_templates/service.java.vm";
        String exportPath = getExportPath();
        String format = "%s/%s/%s/%sService";

        return new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                String entityName = tableInfo.getEntityName();
                // 自定义输出文件名
                return StringUtils.replaceEach(String.format(format, exportPath, packageConfig.getParent(), packageConfig.getService(), entityName)
                        , new String[]{"/", "."}, new String[]{File.separator, File.separator}) + StringPool.DOT_JAVA;
            }
        };
    }

    public FileOutConfig createDOFileOutConfig(PackageConfig packageConfig) throws IOException{
        String templatePath = "/code_templates/entity.java.vm";
        String exportPath = getExportPath();
        String format = "%s/%s/%s/%s";

        return new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                String entityName = tableInfo.getEntityName();
                // 自定义输出文件名
                return StringUtils.replaceEach(String.format(format, exportPath, packageConfig.getParent(), packageConfig.getEntity(), entityName)
                        , new String[]{"/", "."}, new String[]{File.separator, File.separator}) + StringPool.DOT_JAVA;
            }
        };
    }

    public FileOutConfig createDTOFileOutConfig(PackageConfig packageConfig) throws IOException {
        String templatePath = "/code_templates/dto.java.vm";
        String exportPath = getExportPath();
        String format = "%s/%s/service/dto/%sDTO";

        return new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                String entityName = tableInfo.getEntityName();
                // 自定义输出文件名
                return StringUtils.replaceEach(String.format(format, exportPath, packageConfig.getParent(), entityName)
                        , new String[]{"/", "."}, new String[]{File.separator, File.separator}) + StringPool.DOT_JAVA;
            }
        };
    }

    public InjectionConfig getDefaultInjectionConfig(PackageConfig packageConfig) throws IOException {
        // 注入自定义配置，可以在 VM 中使用 cfg.abc 设置的值
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                Map<String, Object> map = new HashMap<>();
                map.put("abc", this.getConfig().getGlobalConfig().getAuthor() + "-mp");
                map.put("dtoPackage", packageConfig.getParent() + ".dto");
                map.put("dtoSwagger", ENABLE_DTO_SWAGGER);
                this.setMap(map);
            }
        };

        cfg.setFileCreate((configBuilder, fileType, filePath) -> {
            if(FileType.ENTITY == fileType) {
                return false;
            }

            if((!USE_TEMP_OUTPUT) && (FileType.XML == fileType)) {
                return false;
            }

            File file = new File(filePath);
            boolean exist = file.exists();
            if (!exist) {
                file.getParentFile().mkdirs();
               //PackageHelper.mkDir(file.getParentFile());
            }
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return !exist || configBuilder.getGlobalConfig().isFileOverride();
        });

        // 自定义输出配置 自定义配置会被优先输出
        List<FileOutConfig> focList = new ArrayList<>();
        if(!USE_TEMP_OUTPUT) {
            focList.add(createXmlFileOutConfig(packageConfig));
        }
        focList.add(createServiceFileOutConfig(packageConfig));
        focList.add(createDOFileOutConfig(packageConfig));
//        focList.add(createDTOFileOutConfig(packageConfig));
        cfg.setFileOutConfigList(focList);
        return cfg;
    }

    public void generateDefaultCode() throws IOException {
        GlobalConfig globalConfig = getDefaultGlobalConfig();
        DataSourceConfig dataSourceConfig = getDefaultDataSourceConfig();
        StrategyConfig strategyConfig = getDefaultStrategyConfig();
        PackageConfig packageConfig = getDefaultPackageConfig();
        TemplateConfig templateConfig = getDefaultTemplateConfig();
        InjectionConfig injectionConfig = getDefaultInjectionConfig(packageConfig);
        generateCode(globalConfig, dataSourceConfig, strategyConfig, packageConfig, templateConfig, injectionConfig);



    }

    public void generateCode(GlobalConfig globalConfig, DataSourceConfig dataSourceConfig,
                                    StrategyConfig strategyConfig, PackageConfig packageConfig,
                                    TemplateConfig templateConfig, InjectionConfig cfg) throws IOException {
        AutoGenerator mpg = new AutoGenerator();
        // 全局配置
        mpg.setGlobalConfig(globalConfig);
        // 数据源配置
        mpg.setDataSource(dataSourceConfig);
        // 策略配置
        mpg.setStrategy(strategyConfig);
        // 包配置
        mpg.setPackageInfo(packageConfig);
        // 模版配置
        mpg.setTemplate(templateConfig);

        mpg.setCfg(cfg);
        // 执行生成
        mpg.execute();
    }

    static class CustomMySqlQuery extends MySqlQuery {
        @Override
        public String tableFieldsSql() {
            return "show full fields from %s";
        }
    }

}

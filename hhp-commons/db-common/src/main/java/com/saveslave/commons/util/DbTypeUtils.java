package com.saveslave.commons.util;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidPooledConnection;
import com.saveslave.commons.constant.DbTypeConstant;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.sql.DatabaseMetaData;
import java.sql.SQLException;

/**
 */
@Component
public class DbTypeUtils {

    private  static String  currentDbType;
    private DbTypeUtils(){
    }
    /**
     * 获取数据库类型方法
     */
    public static  String getDBType(){
        if(StringUtils.isNotBlank(currentDbType)){
            return currentDbType;
        }
        DruidPooledConnection connection=null;
        try {
            connection = SpringUtil.getBean(DruidDataSource.class).getConnection();
            DatabaseMetaData dbmd = connection.getMetaData();
            String dataBaseType = dbmd.getDatabaseProductName();	              //获取数据库类型
            if(("Microsoft SQL Server").equals(dataBaseType)){
                currentDbType =  DbTypeConstant.sqlserver;
            }else if(("HSQL Database Engine").equals(dataBaseType)){
                currentDbType =  DbTypeConstant.hsql;
            }else if(("MySQL").equals(dataBaseType)){
                currentDbType= DbTypeConstant.MySQL;
            }else if(("Oracle").equals(dataBaseType)){
                currentDbType= DbTypeConstant.Oracle;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                if(connection!=null)
                 connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return currentDbType;
    }

    /**
     * 判断当前数据库是否是Oracle
     * @return
     */
    public  static  boolean isOracle(){
        if(StringUtils.isBlank(currentDbType)){
            getDBType();
        }
        return DbTypeConstant.Oracle.equalsIgnoreCase(currentDbType);
    }

    /**
     * 判断当前数据库是否是Oracle
     * @return
     */
    public  static  boolean isMysql(){
        if(StringUtils.isBlank(currentDbType)){
            getDBType();
        }
        return DbTypeConstant.MySQL.equalsIgnoreCase(currentDbType);
    }



}

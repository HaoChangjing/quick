package com.saveslave.commons.dao;

import com.saveslave.commons.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 序列生成器
 *
 */
@Slf4j
@Repository
public class SequenceGenerator {

    @Resource
    private DataSource dataSource;

    public String getNextValStr(String seqCode) {
        return String.valueOf(getNextVal(seqCode));
    }

    /**
     * 获取下一个序列值的方法
     *
     * @param seqCode           序列标识编码
     * @return                  下一个序列值
     */
    public long getNextVal(String seqCode) {
        Connection connection = null;
        PreparedStatement selectStmt = null;
        PreparedStatement updateStmt = null;
        ResultSet rs = null;
        long nextVal;

        try {
            // 1. 连接到数据库
            connection = dataSource.getConnection();

            // 2. 关闭自动提交，开启事务
            connection.setAutoCommit(false);

            // 3. 使用 SELECT ... FOR UPDATE 锁定行
            String selectSQL = "select value from sequence where code = ? for update";
            selectStmt = connection.prepareStatement(selectSQL);
            selectStmt.setString(1, seqCode);
            rs = selectStmt.executeQuery();

            if (rs.next()) {
                nextVal = rs.getLong("value");

                // 4. 更新 value + 1
                String updateSQL = "update sequence set value = value + 1 where code = ?";
                updateStmt = connection.prepareStatement(updateSQL);
                updateStmt.setString(1, seqCode);
                updateStmt.executeUpdate();
            } else {
                throw new SQLException("Sequence not found: " + seqCode);
            }

            // 5. 提交事务
            connection.commit();
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    // 如果发生错误，回滚事务
                    connection.rollback();
                } catch (SQLException rollbackEx) {
                    log.error("回滚事务异常", e);
                }
            }
            throw new BizException("获取序列失败: seq_code={}", e, seqCode);
        } finally {
            // 6. 关闭资源
            try {
                if (rs != null) {
                    rs.close();
                }
                if (selectStmt != null) {
                    selectStmt.close();
                }
                if (updateStmt != null) {
                    updateStmt.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                log.error("资源关闭异常", e);
            }
        }

        return nextVal + 1;  // 返回下一个序列值
    }

}


package com.saveslave.commons.util;

import com.saveslave.commons.constant.SequenceConstant;
import com.saveslave.commons.dao.SequenceGenerator;

/**
 */
public final class SequenceUtils {

    private SequenceUtils() {}

    /**
     * 生成交货单号
     */
    public static String generateBillNo() {
        long nextVal = SpringUtil.getBean(SequenceGenerator.class).getNextVal(SequenceConstant.SEQ_BILL_NO);
        return SequenceConstant.BILL_INFO_PREFIX + String.format("%010d", nextVal);
    }

    /**
     * 生成物料编码
     */
    public static String generateMaterialCode() {
        return SpringUtil.getBean(SequenceGenerator.class).getNextValStr(SequenceConstant.SEQ_MATERIAL_CODE);
    }

    /**
     * 生成返工单号
     */
    public static String generateReprodNo() {
        long nextVal = SpringUtil.getBean(SequenceGenerator.class).getNextVal(SequenceConstant.SEQ_REPROD_NUM);
        return String.format("F%012d", nextVal);
    }

}

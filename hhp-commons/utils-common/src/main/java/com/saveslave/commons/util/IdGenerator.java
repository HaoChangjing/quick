package com.saveslave.commons.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;

/**
 * 
 * @ClassName: IdGenerator
 * @Description: * 高效分布式ID生成算法(sequence),基于Snowflake算法优化实现64位自增ID算法。
 *               其中解决时间回拨问题的优化方案如下： 
 *               1. 如果发现当前时间少于上次生成id的时间(时间回拨)，着计算回拨的时间差 
 *               2. 如果时间差(offset)小于等于5ms，着等待 offset * 2 的时间再生成 
 *               3. 如果offset大于5，则直接抛出异常
 *
 */
public class IdGenerator {
	private static Sequence WORKER = new Sequence();

	public static long getId() {
		return WORKER.nextId();
	}

	public static String getIdStr() {
		return String.valueOf(WORKER.nextId());
	}

//	/**
//	 * @Description //生成各种CODE
//	 */
//	public static String getUniqueCode(){
//		return RandomUtil.randomStringUpper(20);
//		//return IdUtil.simpleUUID().toUpperCase().substring(12);//UUID后20位
//	}
	public static void main(String[] args) throws IOException {
		Long startTime=System.currentTimeMillis();
		File file=new File("F:/mark/test_snak.txt");
		java.io.FileWriter fileWriter = new java.io.FileWriter(file);
		BufferedWriter bufferedWriter=new BufferedWriter(fileWriter);
		for(int i =0;i<10000000;i++){
			bufferedWriter.write(IdGenerator.getId()+"\r\n");
		}
		bufferedWriter.close();
		Long endTime=System.currentTimeMillis();
		System.out.println(endTime-startTime);
	}
}

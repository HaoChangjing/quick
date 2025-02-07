package com.saveslave.commons.biz.model;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 公共分页参数
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class PageParams implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 当前页码
	 */
	private long current = 1;

	/**
	 * 页大小
	 */
	private long size = 10;

	/**
	 * 偏移量
	 */
	@JsonIgnore
	private long offset = 0;

	public Page getPage() {
		return new Page<>(this.getCurrent(), this.getSize());
	}

	public long getOffset() {
		return (current - 1) * size;
	}

	public <T> Page<T> getPage(Class<T> clazz) {
		return new Page<T>(this.getCurrent(), this.getSize());
	}

	public Page getPage(boolean isSearchCount) {
		return new Page<>(this.getCurrent(), this.getSize(), isSearchCount);
	}

	public <T> Page<T> getPage(Class<T> clazz, boolean isSearchCount) {
		return new Page<T>(this.getCurrent(), this.getSize(), isSearchCount);
	}

}

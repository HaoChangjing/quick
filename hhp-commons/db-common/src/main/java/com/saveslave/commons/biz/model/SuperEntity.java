package com.saveslave.commons.biz.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.saveslave.commons.serializer.Date2LongSerializer;
import com.saveslave.commons.serializer.JsonLongSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;


/**
 * 实体父类
 *
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class SuperEntity extends Model<SuperEntity> {

	private static final long serialVersionUID = 1L;

	/**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @JsonSerialize(using = JsonLongSerializer.class)
    private Long id;

    //默认返回时间戳
    @TableField(fill = FieldFill.INSERT)
    @JsonSerialize(using =  Date2LongSerializer.class)
    private Date createTime;

    @TableField(fill = FieldFill.INSERT)
    @JsonSerialize(using = JsonLongSerializer.class)
    private Long creatorId;

    //默认返回时间戳
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonSerialize(using =  Date2LongSerializer.class)
    private Date updateTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonSerialize(using = JsonLongSerializer.class)
    private Long updaterId;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}

package com.er.paiyipai.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author 
 * @since 2024-04-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Dealrecord implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "cjid", type = IdType.AUTO)
    private Integer cjid;

    private Integer aid;

    private String gname;

    private Integer cjmoney;

    private String cjtime;

    private String hid;

    private String hname;

    private Integer did;

    private String dbackup;

    @TableField(exist = false)
    private String pic;
    @TableField(exist = false)
    private String jieZhiTime;

}

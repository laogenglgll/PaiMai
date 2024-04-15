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
public class Auction implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "aid", type = IdType.AUTO)
    private Integer aid;

    private String gname;

    private Integer tid;

    private String gpic;

    private Integer gzan;

    private Integer anum;

    private Integer bprice;

    private Integer increase;

    private Integer abmoney;

    private String stime;

    private String etime;

    private Integer state;

    private String createrid;

    private Integer cflag;

    private String createtime;

    private String hid;

    private Integer nprice;

    private String abackup;

    @TableField(exist = false)
    private  Ptypes type;

}

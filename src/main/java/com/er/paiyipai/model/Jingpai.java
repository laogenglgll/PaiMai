package com.er.paiyipai.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

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
public class Jingpai implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "jpid", type = IdType.AUTO)
    private Integer jpid;

    private Integer aid;

    private Integer jprice;

    private String jptime;

    private String hid;

    private String jptalk;

    private String jbackup;


}

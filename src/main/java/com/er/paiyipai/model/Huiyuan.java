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
public class Huiyuan implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "hid", type = IdType.ASSIGN_ID)
    private String hid;

    private String hpwd;

    private String hname;

    private String htel;

    private String haddress;

    private Integer rid;

    private Integer hyue;

    private Integer hicemoney;

    private String hemail;

    private String hbackup;


}

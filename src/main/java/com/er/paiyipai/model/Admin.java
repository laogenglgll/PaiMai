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
public class Admin implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "aid", type = IdType.ASSIGN_ID)
    private String aid;

    private String aname;

    private String apwd;

    private String rid;

    private String abackup;


}

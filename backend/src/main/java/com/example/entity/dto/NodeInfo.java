package com.example.entity.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("nodeInfo")
public class NodeInfo {
    @TableId(value = "nodeInfoId", type = IdType.AUTO)
    Integer nodeInfoId;
    Integer source;
    Integer destination;
    Double length;
    Double speed;
    Double congestion;
}

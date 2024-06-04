package com.example.entity.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@TableName("node")
@AllArgsConstructor
public class Node {
    @TableId(value = "nodeId", type = IdType.AUTO)
    Integer nodeId;
    @TableField(value = "placeType")
    String placeType;
    @TableField(value = "xPosition")
    Double xPosition;
    @TableField(value = "yPosition")
    Double yPosition;
}

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
    int nodeId;
    @TableField(value = "placeType")
    String placeType;
    @TableField(value = "xPosition")
    double xPosition;
    @TableField(value = "yPosition")
    double yPosition;
}

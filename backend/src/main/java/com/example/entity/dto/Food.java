package com.example.entity.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@TableName("FoodData")
@AllArgsConstructor
public class Food {
    @TableId(value = "id", type = IdType.AUTO)
    Integer id;
    String name;
    String serious;
    @TableField(value = "windowName")
    String windowName;
    Double hot;
    Double rating;
    Double distance;
}

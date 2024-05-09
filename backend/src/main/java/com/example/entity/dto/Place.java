package com.example.entity.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@TableName("place")
@AllArgsConstructor
public class Place {
    @TableId(value = "placeId",type = IdType.AUTO)
    int placeId;
    @TableField(value = "placeName")
    String placeName;
    String information;
    String type;
    double rating;
}

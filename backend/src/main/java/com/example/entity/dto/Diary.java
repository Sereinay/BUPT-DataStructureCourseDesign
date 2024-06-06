package com.example.entity.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.entity.DiaryStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@TableName("diaries")
public class Diary {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @TableField(value = "siteName")
    private String siteName;
    @TableField(value = "studentName")
    private String studentName;
    private Integer popularity;
    private Double rating;
    @TableField(value = "rateNum")
    private Integer rateNum;
    private String title;
    private String content;
    private String code;
    @TableField(value = "createTime")
    private LocalDateTime createTime;
    @TableField(value = "updateTime")
    private LocalDateTime updateTime;
    private Integer padding;
    private DiaryStatusEnum status;
}

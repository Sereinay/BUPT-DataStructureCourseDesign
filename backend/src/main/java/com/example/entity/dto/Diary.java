package com.example.entity.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.entity.DiaryStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@TableName("diaries")
public class Diary {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private Long siteId;
    private Long studentId;
    private int popularity;
    private int rating;
    private String title;
    private String content;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private DiaryStatusEnum status;
}

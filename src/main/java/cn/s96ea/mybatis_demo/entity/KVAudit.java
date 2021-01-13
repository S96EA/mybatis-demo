package cn.s96ea.mybatis_demo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class KVAudit {
    Long id;
    String k;
    String v = "";
    Date createTime = new Date();
    Date updateTime = new Date();
    Boolean isDeleted = false;
    Long version = 0L;
}

package cn.s96ea.mybatis_demo.mapper;

import cn.s96ea.mybatis_demo.entity.KVAudit;

public interface KVAuditMapper {
    void save(KVAudit audit);

    KVAudit findByKey(String key);

    void deleteByKey(String key);

    int upsert(KVAudit audit);
}

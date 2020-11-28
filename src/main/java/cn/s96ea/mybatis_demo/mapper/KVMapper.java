package cn.s96ea.mybatis_demo.mapper;

import cn.s96ea.mybatis_demo.entity.KV;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface KVMapper {
    KV selectKV(String k);

    @Select("SELECT * FROM KV where k = #{k}")
    KV selectKVByK(String k);

    List<KV> selectByV(int v);

    void insert(KV kv);
}

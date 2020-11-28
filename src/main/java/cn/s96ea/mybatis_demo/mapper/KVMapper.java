package cn.s96ea.mybatis_demo.mapper;

import cn.s96ea.mybatis_demo.entity.KV;
import org.apache.ibatis.annotations.Select;

public interface KVMapper {
    KV selectKV(String k);

    @Select("SELECT * FROM KV where k = #{k}")
    KV selectKVByK(String k);
}

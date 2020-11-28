package cn.s96ea.mybatis_demo.mapper;

import cn.s96ea.mybatis_demo.entity.KV;
import cn.s96ea.mybatis_demo.entity.KVList;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface KVMapper {
    KV selectKV(String k);

    KV selectKVByResultMap(String k);

    @Select("SELECT * FROM KV where k = #{k}")
    KV selectKVByK(String k);

    List<KV> selectByV(int v);

    void insert(KV kv);

    KVList selectKVListByResultMap();
}

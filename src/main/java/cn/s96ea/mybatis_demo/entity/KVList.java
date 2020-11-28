package cn.s96ea.mybatis_demo.entity;

import java.util.List;

public class KVList {
    Integer id;
    Value value;
    List<KV> kvList;

    @Override
    public String toString() {
        return "KVList{" +
                "id=" + id +
                ", value=" + value +
                ", kvList=" + kvList +
                '}';
    }
}

class Value {
    Integer val;

    @Override
    public String toString() {
        return "Value{" +
                "val=" + val +
                '}';
    }
}
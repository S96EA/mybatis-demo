package cn.s96ea.mybatis_demo.entity;

public class KV {
    String k;
    Integer v;

    public KV(String k, Integer v) {
        this.k = k;
        this.v = v;
    }

    public String getK() {
        return k;
    }

    public void setK(String k) {
        this.k = k;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

    @Override
    public String toString() {
        return "KV{" +
                "k='" + k + '\'' +
                ", v=" + v +
                '}';
    }
}

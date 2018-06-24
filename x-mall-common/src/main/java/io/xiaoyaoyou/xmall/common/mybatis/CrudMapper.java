package io.xiaoyaoyou.xmall.common.mybatis;

public interface CrudMapper<T> {
    void insert(T value);

    T queryById(int id);

    T queryById(long id);

    int deleteById(String id);

    int deleteById(int id);

    int deleteById(long id);

    int update(T value);
}

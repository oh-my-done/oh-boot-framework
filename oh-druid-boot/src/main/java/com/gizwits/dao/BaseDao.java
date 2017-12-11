package com.gizwits.dao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * @author Feel
 * @date 2017/11/1
 * @email fye@gizwits.com
 * @since 1.0
 */
@Transactional
public class BaseDao<T> {

    @Resource
    protected JdbcTemplate jdbcTemplate;

    protected Class<T> entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

    private RowMapper<T> rowMapper = new BeanPropertyRowMapper<T>(entityClass);


    /**
     * 列表查询
     *
     * @param sql
     * @param args
     * @return
     */
    public List<T> findAll(String sql, Object[] args) {

        return jdbcTemplate.query(sql, args, rowMapper);
    }

    /**
     * 列表查询所有
     *
     * @param sql
     * @return
     */
    public List<T> findAll(String sql) {
        return jdbcTemplate.query(sql, rowMapper);
    }

    /**
     * 单个对象查询
     * 这里需要自己实现，为什么不直接使用 queryForObject。因为 当查询对象为null 或者大于1的 时候会抛异常。源码如下
     * requiredSingleResult
     * <code>
     * int size = (results != null ? results.size() : 0);
     * if (size == 0) {
     * return null;
     * }
     * if (!CollectionUtils.hasUniqueObject(results)) {
     * throw new IncorrectResultSizeDataAccessException(1, size);
     * }
     * return results.iterator().next();
     * </code>
     *
     * @param sql
     * @param args
     * @return
     */
    public T findOne(String sql, Object[] args) {

        List<T> results = jdbcTemplate.query(sql, args, new RowMapperResultSetExtractor<T>(rowMapper, 1));

        int size = (results != null ? results.size() : 0);
        if (size == 0) {
            return null;
        }

        return results.iterator().next();

    }


    public int update(String sql, List<Object> params) {
        return jdbcTemplate.update(sql, params.toArray());
    }


    public int save(String sql, List<Object> params) {
        return jdbcTemplate.update(sql, params.toArray());
    }


}

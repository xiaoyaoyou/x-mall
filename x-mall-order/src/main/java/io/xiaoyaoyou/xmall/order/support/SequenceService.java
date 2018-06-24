/*
 * Copyright © 上海庆谷豆信息科技有限公司.
 */

package io.xiaoyaoyou.xmall.order.support;

import io.xiaoyaoyou.xmall.common.util.SequenceGeneratorSkip4;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 */
@Service
public class SequenceService {
    @Autowired
    private JdbcTemplate internalJdbcTemplate;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public long nextSkip4(String name) {
        long seq = increaseSequence(name, 1);
        //跳过带4的数字
        long seq2 = SequenceGeneratorSkip4.next(seq);
        if (seq2 != seq) {
            seq = increaseSequence(name, seq2 - seq);
        }
        return seq;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public long next(String name) {
        return increaseSequence(name, 1);
    }

    private long increaseSequence(String name, long increaseValue) {
        internalJdbcTemplate.update("UPDATE yycc_common_sequence SET id=LAST_INSERT_ID(id + ?) WHERE name =?", increaseValue, name);
        return internalJdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Long.class);
    }
}

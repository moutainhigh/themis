package me.ele.themis.common.dao;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by guoxin on 23/8/2016.
 */
@Service
public interface InqueryDao {

    @Select("SELECT order_id FROM delivery_order WHERE id = 2494510 ")
    public Long getPrice();

}

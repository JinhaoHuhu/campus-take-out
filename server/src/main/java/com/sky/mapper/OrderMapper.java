package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.GoodsSalesDTO;
import com.sky.dto.OrdersPageQueryDTO;
import com.sky.entity.Orders;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Mapper
public interface OrderMapper {

    /**
     * 插入订单数据
     * @param orders
     */
    void insert(Orders orders);

    /**
     * 分页查询订单数据
     * @param ordersPageQueryDTO
     * @return
     */
    Page<Orders> pageQuery(OrdersPageQueryDTO ordersPageQueryDTO);

    /**
     * 根据订单id查询订单数据
     * @param id
     * @return
     */
    @Select("select * from orders where id = #{id}")
    Orders getById(Long id);

    /**
     * 动态更新订单数据
     * @param orders
     */
    void update(Orders orders);

    /**
     * 根据状态统计订单数量
     * @param status
     */
    @Select("select count(id) from orders where status = #{status}")
    Integer countStatus(Integer status);

    /**
     * 根据订单号查询订单数据
     * @param orderNumber
     * @return
     */
    @Select("select * from orders where number = #{orderNumber}")
    Orders getByNumber(String orderNumber);

    /**
     * 根据状态查询小于某个时间的订单
     * @param status
     * @param orderTime
     * @return
     */
    @Select("select * from orders where status = #{status} and order_time < #{orderTime}")
    List<Orders> getByStatusAndOrderTimeLT(Integer status, LocalDateTime orderTime);

    /**
     * 根据条件统计营业额
     * @param map
     * @return
     */
    Double sumByMap(Map<String, Object> map);

    /**
     * 根据条件统计订单数量
     * @param map
     * @return
     */
    Integer countByMap(Map<String, Object> map);

    /**
     * 根据条件统计商品销量
     * @param map
     * @return
     */
    List<GoodsSalesDTO> getSalesTop10(Map<String, Object> map);
}

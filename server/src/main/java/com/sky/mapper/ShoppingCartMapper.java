package com.sky.mapper;

import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ShoppingCartMapper {

    /**
     * 查询购物车
     * @param shoppingCart
     * @return
     */
    List<ShoppingCart> getShoppingCart(ShoppingCart shoppingCart);

    /**
     * 购物车商品数量更新
     * @param cart
     */
    @Update("update shopping_cart set number = #{number} where id = #{id}")
    void updateNumber(ShoppingCart cart);


    /**
     * 插入购物车数据
     * @param shoppingCart
     */
    @Insert("insert into shopping_cart (name, image, user_id, dish_id, setmeal_id, dish_flavor, amount, create_time) " +
            "VALUES " +
            "(#{name}, #{image}, #{userId}, #{dishId}, #{setmealId}, #{dishFlavor}, #{amount}, #{createTime})")
    void insert(ShoppingCart shoppingCart);

    /**
     * 根据用户id删除购物车数据
     * @param currentId
     */
    @Delete("delete from shopping_cart where user_id = #{currentId}")
    void deleteByUserId(Long currentId);

    /**
     * 根据id删除购物车数据
     * @param id
     */
    @Delete("delete from shopping_cart where id = #{id}")
    void deleteById(Long id);

    /**
     * 批量添加购物车数据
     * @param shoppingCartList
     */
    void insertBatch(List<ShoppingCart> shoppingCartList);
}

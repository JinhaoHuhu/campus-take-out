package com.sky.controller.user;

import com.sky.constant.StatusConstant;
import com.sky.entity.Dish;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController("userDishController")
@RequestMapping("/user/dish")
@Slf4j
@Api(tags = "C端-菜品浏览接口")
public class DishController {
    @Autowired
    private DishService dishService;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 根据分类id查询菜品
     *
     * @param categoryId
     * @return
     */
    @GetMapping("/list")
    @ApiOperation("根据分类id查询菜品")
    @Cacheable(cacheNames = "dish", key = "#categoryId")
    public Result<List<DishVO>> list(Long categoryId) {
        log.info("根据分类id查询菜品: {}", categoryId);
        Dish dish = Dish.builder().categoryId(categoryId).build();
        List<DishVO> list = dishService.listWithFlavor(dish);
        return Result.success(list);
    }

//    public Result<List<DishVO>> list(Long categoryId) {
//        log.info("根据分类id查询菜品：{}", categoryId);
//
//        //构造redis中的key，规则：dish_分类id
//        String key = "dish_" + categoryId;
//
//        //查询redis中是否存在菜品数据
//        List<DishVO> list = (List<DishVO>) redisTemplate.opsForValue().get(key);
//        if (list != null && list.size() > 0) {
//            //如果存在，则直接返回，无需查询数据库
//            return Result.success(list);
//        }
//
//        //如果不存在，则查询数据库，将查询的数据放入redis中
//        Dish dish = new Dish();
//        dish.setCategoryId(categoryId);
//        dish.setStatus(StatusConstant.ENABLE);//查询起售中的菜品
//
//        list = dishService.listWithFlavor(dish);
//        redisTemplate.opsForValue().set(key, list);
//        log.info("成功缓存数据, key = {}", key);
//
//        return Result.success(list);
//    }

}

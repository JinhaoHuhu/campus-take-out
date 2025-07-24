package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * 菜品管理
 */
@RestController
@RequestMapping("/admin/dish")
@Api(tags = "菜品相关接口")
@Slf4j
public class DishController {

    @Autowired
    private DishService dishService;
    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    /**
     * 新增菜品
     * @param dishDTO
     * @return
     */
    @PostMapping
    @ApiOperation("新增菜品")
    @CacheEvict(cacheNames = "dish", key = "#dishDTO.categoryId")
    public Result save(@RequestBody DishDTO dishDTO) {
        log.info("新增菜品: {}", dishDTO);
        dishService.saveWithFlavor(dishDTO);

//        //清理缓存数据
//        cleanCache(String.valueOf("dish_" + dishDTO.getCategoryId()));

        return Result.success();
    }

    /**
     * 菜品分页查询
     * @param dishPageQueryDTO
     * @return
     */
    @ApiOperation("菜品分页查询")
    @GetMapping("/page")
    public Result<PageResult> page(DishPageQueryDTO dishPageQueryDTO) {
        log.info("菜品分页查询: {}", dishPageQueryDTO);
        PageResult pageResult = dishService.pageQuery(dishPageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 菜品删除
     * @param ids
     * @return
     */
    @ApiOperation("菜品批量删除")
    @DeleteMapping
    @CacheEvict(cacheNames = "dish", allEntries = true)
    public Result delete(@RequestParam List<Long> ids) {
        log.info("菜品批量删除: {}", ids);
        dishService.deleteBatch(ids);

//        //将所有菜品缓存数据清理掉
//        cleanCache("dish_*");

        return Result.success();
    }

    /**
     * 根据id查询菜品
     * @param id
     * @return
     */
    @ApiOperation("根据id查询菜品")
    @GetMapping("/{id}")
    public Result<DishVO> getById(@PathVariable Long id) {
        log.info("根据id查询菜品: {}", id);
        DishVO dishVO = dishService.getByIdWithFlavor(id);
        return Result.success(dishVO);
    }

    /**
     * 更新菜品
     * @param dishDTO
     * @return
     */
    @ApiOperation("更新菜品")
    @PutMapping
    @CacheEvict(cacheNames = "dish", allEntries = true)
    public Result update(@RequestBody DishDTO dishDTO) {
        log.info("更新菜品: {}", dishDTO);
        dishService.updateWithFlavor(dishDTO);

//        //清理缓存数据
//        cleanCache("dish_*");

        return Result.success();
    }

    /**
     * 根据分类id查询菜品
     * @param categoryId
     * @return
     */
    @ApiOperation("根据分类id查询菜品")
    @GetMapping("/list")
    public Result<List<Dish>> list(Long categoryId) {
        log.info("根据分类id查询菜品: {}", categoryId);
        List<Dish> dishList = dishService.list(categoryId);
        return Result.success(dishList);
    }

    /**
     * 菜品起售、停售
     * @param status
     * @param id
     * @return
     */
    @ApiOperation("菜品起售、停售")
    @PostMapping("/status/{status}")
    @CacheEvict(cacheNames = "dish", allEntries = true)
    public Result updateStatus(@PathVariable Integer status, @RequestParam Long id) {
        log.info("菜品起售、停售: {}, {}", status, id);
        dishService.updateStatus(status, id);

//        //清理缓存数据
//        cleanCache("dish_*");

        return Result.success();
    }

//    private void cleanCache(String pattern) {
//        Set<Object> keys = redisTemplate.keys(pattern);
//        log.info("匹配到的键: {}", keys);
//        if (keys != null && !keys.isEmpty()) {
//            redisTemplate.delete(keys);
//            log.info("成功删除的键: {}", keys);
//        } else {
//            log.info("未匹配到任何键，pattern: {}", pattern);
//        }
//    }
}

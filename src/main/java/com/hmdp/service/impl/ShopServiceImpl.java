package com.hmdp.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.hmdp.dto.Result;
import com.hmdp.entity.Shop;
import com.hmdp.mapper.ShopMapper;
import com.hmdp.service.IShopService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.util.concurrent.TimeUnit;

import static com.hmdp.utils.RedisConstants.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 虎哥
 * @since 2021-12-22
 */
@Service
public class ShopServiceImpl extends ServiceImpl<ShopMapper, Shop> implements IShopService {
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public Result queryById(Long id) {
        // 查询缓存
        String cacheShopKey = CACHE_SHOP_KEY + id;
        String cacheShop = stringRedisTemplate.opsForValue().get(cacheShopKey);
        // 存在缓存
        if (StrUtil.isNotBlank(cacheShop)) {
            // 返回数据
            Shop shop = JSONUtil.toBean(cacheShop, Shop.class);
            return Result.ok(shop);
        }
        // 空字符串,用于缓存穿透
        if ("".equals(cacheShop)) {
            return Result.fail("该数据不存在");
        }
        // 不存在缓存,查询数据库
        Shop shop = getById(id);
        if (shop == null) {
            stringRedisTemplate.opsForValue().set(cacheShopKey, "", CACHE_NULL_TTL, TimeUnit.MINUTES);
            return Result.fail("数据不存在");
        }
        stringRedisTemplate.opsForValue().set(cacheShopKey, JSONUtil.toJsonStr(shop), CACHE_SHOP_TTL, TimeUnit.MINUTES);
        return Result.ok(shop);
    }

    @Transactional
    public Result update(Shop shop) {
        // 更新数据库
        this.updateById(shop);
        // 删除缓存
        stringRedisTemplate.delete(CACHE_SHOP_KEY + shop.getId());
        return Result.ok();
    }
}

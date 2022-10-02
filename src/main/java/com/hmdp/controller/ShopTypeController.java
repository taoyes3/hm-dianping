package com.hmdp.controller;


import com.hmdp.dto.Result;
import com.hmdp.dto.UserDTO;
import com.hmdp.entity.ShopType;
import com.hmdp.entity.User;
import com.hmdp.service.IShopTypeService;
import com.hmdp.service.IUserService;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 虎哥
 * @since 2021-12-22
 */
@RestController
@RequestMapping("/shop-type")
public class ShopTypeController {
    @Resource
    private IShopTypeService typeService;
    
    @Resource
    private IUserService userService;
    @Resource
    private MapperFacade mapperFacade;

    @GetMapping("list")
    public Result queryTypeList() {
        User user = userService.getById(1009);
        UserDTO userDTO = mapperFacade.map(user, UserDTO.class);
        System.out.println(userDTO);
        List<ShopType> typeList = typeService
                .query().orderByAsc("sort").list();
        return Result.ok(typeList);
    }
}

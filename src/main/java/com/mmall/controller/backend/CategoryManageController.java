package com.mmall.controller.backend;


import com.mmall.common.ServerResponse;
import com.mmall.service.ICategoryService;
import com.mmall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("manage/category")
public class CategoryManageController {

    @Autowired
    private IUserService iUserService;

    @Autowired
    private ICategoryService iCategoryService;

    /**
     * 增加品类
     * @param httpServletRequest
     * @param categoryName
     * @param parentId
     * @return
     */
    @RequestMapping(value = "add_category.do")
    @ResponseBody
    public ServerResponse addCategory(HttpServletRequest httpServletRequest, String categoryName, @RequestParam(value = "parentId",defaultValue = "0") int parentId) {
//        String loginToken = CookieUtil.readLoginToken(httpServletRequest);
//        if(StringUtils.isEmpty(loginToken)){
//            return ServerResponse.createByErrorMessage("用户未登录,无法获取当前用户的信息");
//        }
//        String userJsonStr = RedisShardedPoolUtil.get(loginToken);
//        User user = JsonUtil.string2Obj(userJsonStr,User.class);
//
//        if(user == null) {
//            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录");
//        }
//        //校验是否为管理员
//        if(iUserService.checkAdminRole(user).isSuccess()) {
//            //是管理员
//            //增加处理分类的逻辑
//            return iCategoryService.addCategory(categoryName, parentId);
//        } else {
//            return ServerResponse.createByErrorMessage("无权限操作，需要管理员权限");
//        }

        return iCategoryService.addCategory(categoryName, parentId);
    }

    /**
     * 更新品类名称
     * @param httpServletRequest
     * @param categoryName
     * @param categoryId
     * @return
     */
    @RequestMapping(value = "set_category_name.do")
    @ResponseBody
    public ServerResponse setCategoryName(HttpServletRequest httpServletRequest, String categoryName, Integer categoryId) {
//        String loginToken = CookieUtil.readLoginToken(httpServletRequest);
//        if(StringUtils.isEmpty(loginToken)){
//            return ServerResponse.createByErrorMessage("用户未登录,无法获取当前用户的信息");
//        }
//        String userJsonStr = RedisShardedPoolUtil.get(loginToken);
//        User user = JsonUtil.string2Obj(userJsonStr,User.class);
//
//        if(user == null) {
//            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录");
//        }
//        //校验是否为管理员
//        if(iUserService.checkAdminRole(user).isSuccess()) {
//            //是管理员
//            //更新categoryName
//            return iCategoryService.updateCategoryName(categoryName, categoryId);
//        } else {
//            return ServerResponse.createByErrorMessage("无权限操作，需要管理员权限");
//        }
        return iCategoryService.updateCategoryName(categoryName, categoryId);
    }

    @RequestMapping(value = "get_category.do")
    @ResponseBody
    public ServerResponse getChildParallelCategory(HttpServletRequest httpServletRequest, @RequestParam(value = "categoryId", defaultValue = "0") Integer categoryId) {
//        String loginToken = CookieUtil.readLoginToken(httpServletRequest);
//        if(StringUtils.isEmpty(loginToken)){
//            return ServerResponse.createByErrorMessage("用户未登录,无法获取当前用户的信息");
//        }
//        String userJsonStr = RedisShardedPoolUtil.get(loginToken);
//        User user = JsonUtil.string2Obj(userJsonStr,User.class);
//
//        if(user == null) {
//            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录");
//        }
//        //校验是否为管理员
//        if(iUserService.checkAdminRole(user).isSuccess()) {
//            //是管理员
//            //查询子节点的category信息，并且不递归，保持平级
//            return iCategoryService.getChildrenParallelCategory(categoryId);
//        } else {
//            return ServerResponse.createByErrorMessage("无权限操作，需要管理员权限");
//        }

        return iCategoryService.getChildrenParallelCategory(categoryId);
    }

    @RequestMapping(value = "get_deep_category.do")
    @ResponseBody
    public ServerResponse getCategoryAndChildrenCategory(HttpServletRequest httpServletRequest, @RequestParam(value = "categoryId", defaultValue = "0") Integer categoryId) {
//        String loginToken = CookieUtil.readLoginToken(httpServletRequest);
//        if(StringUtils.isEmpty(loginToken)){
//            return ServerResponse.createByErrorMessage("用户未登录,无法获取当前用户的信息");
//        }
//        String userJsonStr = RedisShardedPoolUtil.get(loginToken);
//        User user = JsonUtil.string2Obj(userJsonStr,User.class);
//
//        if(user == null) {
//            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录");
//        }
//        //校验是否为管理员
//        if(iUserService.checkAdminRole(user).isSuccess()) {
//            //是管理员
//            //查询当前节点的id和递归子节点的id
//            return iCategoryService.selectCategoryAndChildrenById(categoryId);
//        } else {
//            return ServerResponse.createByErrorMessage("无权限操作，需要管理员权限");
//        }
        return iCategoryService.selectCategoryAndChildrenById(categoryId);
    }

}

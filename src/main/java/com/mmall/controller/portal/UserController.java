package com.mmall.controller.portal;

import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.service.IUserService;
import com.mmall.util.CookieUtil;
import com.mmall.util.JsonUtil;
import com.mmall.util.RedisShardedPoolUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/user/")
public class UserController {
    @Autowired
    private IUserService iUserService;
    /**
     *用户登录
     * @param username
     * @param password
     * @param session
     * @return
     */
    @RequestMapping(value = "login.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> login(String username, String password, HttpSession session, HttpServletResponse httpServletResponse) {
        //service -> mybatis -> dao
        ServerResponse<User> response = iUserService.login(username, password);
        if(response.isSuccess()) {
            //session.setAttribute(Const.CURRENT_USER, response.getData());
            CookieUtil.writeLoginToken(httpServletResponse,session.getId());
            RedisShardedPoolUtil.setEx(session.getId(), JsonUtil.obj2String(response.getData()),Const.RedisCacheExtime.REDIS_SESSION_EXTIME);

        }
        return response;
    }

    /**
     * 登出
     * @param httpServletRequest
     * @param httpServletResponse
     * @return
     */
    @RequestMapping(value = "logout.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> logout(HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse){
        String loginToken = CookieUtil.readLoginToken(httpServletRequest);
        CookieUtil.delLoginToken(httpServletRequest,httpServletResponse);
        RedisShardedPoolUtil.del(loginToken);

//        session.removeAttribute(Const.CURRENT_USER);

        return ServerResponse.createBySuccess();
    }

    /**
     * 注册
     * @param user
     * @return
     */
    @RequestMapping(value = "register.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> register(User user) {
        return iUserService.register(user);
    }

    /**
     * 校验注册时的用户名和email是否合法
     * @param str
     * @param type
     * @return
     */
    @RequestMapping(value = "check_valid.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> checkValid(String str, String type) {
        return iUserService.checkValid(str, type);
    }

    @RequestMapping(value = "get_user_info.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> getUserInfo(HttpServletRequest httpServletRequest){

        String loginToken = CookieUtil.readLoginToken(httpServletRequest);
        if(StringUtils.isEmpty(loginToken)){
            return ServerResponse.createByErrorMessage("用户未登录,无法获取当前用户的信息");
        }
        String userJsonStr = RedisShardedPoolUtil.get(loginToken);
        User user = JsonUtil.string2Obj(userJsonStr,User.class);

        if(user != null){
            return ServerResponse.createBySuccess(user);
        }
        return ServerResponse.createByErrorMessage("用户未登录,无法获取当前用户的信息");
    }

    /**
     * 密码问题获取
     * @param username
     * @return
     */
    @RequestMapping(value = "forget_get_question.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> forgetGetQuestion(String username) {
        return iUserService.selectQuestion(username);
    }

    /**
     * 校验密保问题答案
     * @param username
     * @param question
     * @param answer
     * @return
     */
    @RequestMapping(value = "forget_check_answer.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> forgetCheckAnswer(String username, String question, String answer) {
        return iUserService.checkAnswer(username, question, answer);
    }

    /**
     * 忘记密码重置密码
     * @param username
     * @param passwordNew
     * @param forgetToken
     * @return
     */
    @RequestMapping(value = "forget_reset_password.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> forgetResetPassword(String username, String passwordNew, String forgetToken) {
        return iUserService.forgetResetPassword(username, passwordNew, forgetToken);
    }

    /**
     * 登录状态重置密码
     * @param httpServletRequest
     * @param passwordOld
     * @param passwordNew
     * @return
     */
    @RequestMapping(value = "reset_password.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> forgetResetPassword(HttpServletRequest httpServletRequest, String passwordOld, String passwordNew) {
        String loginToken = CookieUtil.readLoginToken(httpServletRequest);
        if(StringUtils.isEmpty(loginToken)){
            return ServerResponse.createByErrorMessage("用户未登录,无法获取当前用户的信息");
        }
        String userJsonStr = RedisShardedPoolUtil.get(loginToken);
        User user = JsonUtil.string2Obj(userJsonStr,User.class);

        if(user == null) {
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        return iUserService.resetPassword(passwordOld, passwordNew, user);

    }

    /**
     * 更新用户信息
     * @param httpServletRequest
     * @param user
     * @return
     */
    @RequestMapping(value = "update_information.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> updateInformation(HttpServletRequest httpServletRequest, User user) {
        String loginToken = CookieUtil.readLoginToken(httpServletRequest);
        if(StringUtils.isEmpty(loginToken)){
            return ServerResponse.createByErrorMessage("用户未登录,无法获取当前用户的信息");
        }
        String userJsonStr = RedisShardedPoolUtil.get(loginToken);
        User currentUser = JsonUtil.string2Obj(userJsonStr,User.class);

        if(currentUser == null) {
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        user.setId(currentUser.getId());
        user.setUsername(currentUser.getUsername());
        ServerResponse<User> response = iUserService.updateInformation(user);
        if(response.isSuccess()) {
            response.getData().setUsername(currentUser.getUsername());
            RedisShardedPoolUtil.setEx(loginToken, JsonUtil.obj2String(response.getData()),Const.RedisCacheExtime.REDIS_SESSION_EXTIME);

        }
        return response;
    }

    /**
     * 获取当前用户信息
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(value = "get_information.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> getInformation(HttpServletRequest httpServletRequest) {
        String loginToken = CookieUtil.readLoginToken(httpServletRequest);
        if(StringUtils.isEmpty(loginToken)){
            return ServerResponse.createByErrorMessage("用户未登录,无法获取当前用户的信息");
        }
        String userJsonStr = RedisShardedPoolUtil.get(loginToken);
        User currentUser = JsonUtil.string2Obj(userJsonStr,User.class);

        if(currentUser == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "未登录，需要强制登录，status = 10");
        }
        return iUserService.getInformation(currentUser.getId());


    }

}

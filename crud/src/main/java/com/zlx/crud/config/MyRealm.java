package com.zlx.crud.config;

import com.zlx.crud.entity.mysql.User;
import com.zlx.crud.service.PermissionService;
import com.zlx.crud.service.RoleService;
import com.zlx.crud.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

public class MyRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("授权方法");
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        //此处获取的是认证参数一传过来的值 若其传的为对象此处获得的为对象
        String userName = ((String) SecurityUtils.getSubject().getPrincipal());
//        String userName = ((String) principalCollection.getPrimaryPrincipal());
        User user = userService.queryUserbyNameOrPhone(userName);
        System.out.println(user.getId());
        Set<String> roles = roleService.queryRoleByUserId(user.getId());
        Set<String> permissions = permissionService.queryPermissioinByUserId(user.getId());
//        permissions.stream().forEach(permission-> System.out.println(permission));
        if (roles != null && roles.size()>0){
            simpleAuthorizationInfo.setRoles(roles);
            roles.stream().forEach(role-> System.out.println(role));
        }
        if (permissions != null && permissions.size() > 0){
            simpleAuthorizationInfo.setStringPermissions(permissions);
        }
        return simpleAuthorizationInfo;
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("认证方法");
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken)authenticationToken;
        System.out.println(usernamePasswordToken);
//        String userName = authenticationToken.getPrincipal().toString();
//        User user = userService.queryUserbyNameOrPhone(usernamePasswordToken.getUsername());
        User user = userService.queryUserbyNameOrPhone(usernamePasswordToken.getUsername());
        if (user != null){
            //参数1 是什么授权那里得到的就是什么
            return new SimpleAuthenticationInfo(user.getName(),user.getPasswd(), getName());
        }
        return null;
    }
}

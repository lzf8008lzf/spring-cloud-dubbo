# springboot-jwt-demo

## 简介
这是一个使用了springboot+springSecurity+jwt实现的基于token的权限管理的一个demo

## 使用

首先注册的url是`/auth/register`
参数很简单就`username`,`password`

然后登陆是`/auth/login`

参数名称 | 类型 | 说明
------- | ---- | ----
username | String | 用户名
password | String | 密码
rememberMe | Interge | 记住我，只有两个值可以选，0:不记住，1:记住

推荐使用postman去测试，登陆成功后在响应头里可以找到token

之后就可以访问`/tasks`了

<!doctype html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%request.setAttribute("ctx", request.getContextPath());%>

<html class="x-admin-sm">
<head>
    <title>后台登录-X-admin2.2</title>
    <link rel="stylesheet" href="${ctx}/core/Xadmin/css/font.css">
    <link rel="stylesheet" href="${ctx}/core/Xadmin/css/xadmin.css">
    <!-- <link rel="stylesheet" href="./css/theme5.css"> -->
    <script src="${ctx}/core/layui/layui.js" charset="utf-8"></script>
    <script src="${ctx}/core/Xadmin/js/xadmin.js" charset="utf-8"></script>
    <!-- 让IE8/9支持媒体查询，从而兼容栅格 -->
    <!--[if lt IE 9]>
    <script src="https://cdn.staticfile.org/html5shiv/r29/html5.min.js"></script>
    <script src="https://cdn.staticfile.org/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    <script>
        // 是否开启刷新记忆tab功能
        // var is_remember = false;
    </script>
</head>
<body class="index">
<!-- 顶部开始 -->
<div class="container">
    <div class="logo">
        <a href="${ctx}">返回首页</a></div>
    <div class="left_open">
        <a><i title="展开左侧栏" class="iconfont">&#xe699;</i></a>
    </div>
    <ul class="layui-nav left fast-add" lay-filter="">
        <li class="layui-nav-item">
            <a href="javascript:;">+新增</a>
            <dl class="layui-nav-child">
                <!-- 二级菜单 -->
                <dd>
                    <a onclick="xadmin.open('最大化','http://www.baidu.com','','',true)">
                        <i class="iconfont">&#xe6a2;</i>弹出最大化</a></dd>
                <dd>
                    <a onclick="xadmin.open('弹出自动宽高','http://www.baidu.com')">
                        <i class="iconfont">&#xe6a8;</i>弹出自动宽高</a></dd>
                <dd>
                    <a onclick="xadmin.open('弹出指定宽高','http://www.baidu.com',500,300)">
                        <i class="iconfont">&#xe6a8;</i>弹出指定宽高</a></dd>
                <dd>
                    <a onclick="xadmin.add_tab('在tab打开','member-list.html')">
                        <i class="iconfont">&#xe6b8;</i>在tab打开</a></dd>
                <dd>
                    <a onclick="xadmin.add_tab('在tab打开刷新','member-del.html',true)">
                        <i class="iconfont">&#xe6b8;</i>在tab打开刷新</a></dd>
            </dl>
        </li>
    </ul>
    <ul class="layui-nav right" lay-filter="">
        <li class="layui-nav-item">
            <a href="javascript:;">admin</a>
            <dl class="layui-nav-child">
                <!-- 二级菜单 -->
                <dd>
                    <a onclick="xadmin.open('个人信息','http://www.baidu.com')">个人信息</a></dd>
                <dd>
                    <a onclick="xadmin.open('切换帐号','http://www.baidu.com')">切换帐号</a></dd>
                <dd>
                    <a href="./login.html">退出</a></dd>
            </dl>
        </li>
        <li class="layui-nav-item to-index">
            <a href="/">前台首页</a></li>
    </ul>
</div>
<!-- 顶部结束 -->
<!-- 中部开始 -->
<!-- 左侧菜单开始 -->
<div class="left-nav">
    <div id="side-nav">
        <ul id="nav">
            <li>
                <a href="javascript:;">
                    <i class="iconfont left-nav-li" lay-tips="会员管理">&#xe6b8;</i>
                    <cite>会员管理</cite>
                    <i class="iconfont nav_right">&#xe697;</i></a>
                <ul class="sub-menu">
                    <li>
                        <a onclick="xadmin.add_tab('统计页面','welcome1.html')">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite>统计页面</cite></a>
                    </li>
                    <li>
                        <a onclick="xadmin.add_tab('会员列表(静态表格)','member-list.html')">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite>会员列表(静态表格)</cite></a>
                    </li>
                    <li>
                        <a onclick="xadmin.add_tab('会员列表(动态表格)','member-list1.html',true)">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite>会员列表(动态表格)</cite></a>
                    </li>
                    <li>
                        <a onclick="xadmin.add_tab('会员删除','member-del.html')">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite>会员删除</cite></a>
                    </li>
                    <li>
                        <a href="javascript:;">
                            <i class="iconfont">&#xe70b;</i>
                            <cite>会员管理</cite>
                            <i class="iconfont nav_right">&#xe697;</i></a>
                        <ul class="sub-menu">
                            <li>
                                <a onclick="xadmin.add_tab('会员删除','member-del.html')">
                                    <i class="iconfont">&#xe6a7;</i>
                                    <cite>会员删除</cite></a>
                            </li>
                            <li>
                                <a onclick="xadmin.add_tab('等级管理','member-list1.html')">
                                    <i class="iconfont">&#xe6a7;</i>
                                    <cite>等级管理</cite></a>
                            </li>
                        </ul>
                    </li>
                </ul>
            </li>
            <li>
                <a href="javascript:;">
                    <i class="iconfont left-nav-li" lay-tips="订单管理">&#xe723;</i>
                    <cite>订单管理</cite>
                    <i class="iconfont nav_right">&#xe697;</i></a>
                <ul class="sub-menu">
                    <li>
                        <a onclick="xadmin.add_tab('平时常用的','${ctx}/my/index.do')">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite>平时常用的</cite></a>
                    </li>
                    <li>
                        <a onclick="xadmin.add_tab('测试layui页面','${ctx}/my/layuiTest.do')">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite>测试layui页面</cite></a>
                    </li>
                </ul>
            </li>
            <li>
                <a href="javascript:;">
                    <i class="iconfont left-nav-li" lay-tips="图表们">&#xe723;</i>
                    <cite>图表们</cite>
                    <i class="iconfont nav_right">&#xe697;</i></a>
                <ul class="sub-menu">
                    <li>
                        <a onclick="xadmin.add_tab('图表示例','${ctx}/echarts/main.do')">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite>图表首页</cite></a>
                    </li>
                    <li>
                        <a onclick="xadmin.add_tab('WebSocket','${ctx}/websocket/c/main.do')">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite>测试WebSocket</cite></a>
                    </li>
                </ul>
            </li>
            <li>
                <a href="javascript:;">
                    <i class="iconfont left-nav-li" lay-tips="城市联动">&#xe723;</i>
                    <cite>城市联动</cite>
                    <i class="iconfont nav_right">&#xe697;</i></a>
                <ul class="sub-menu">
                    <li>
                        <a onclick="xadmin.add_tab('三级地区联动','city.html')">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite>三级地区联动</cite></a>
                    </li>
                </ul>
            </li>
            <li>
                <a href="javascript:;">
                    <i class="iconfont left-nav-li" lay-tips="管理员管理">&#xe726;</i>
                    <cite>管理员管理</cite>
                    <i class="iconfont nav_right">&#xe697;</i></a>
                <ul class="sub-menu">
                    <li>
                        <a onclick="xadmin.add_tab('管理员列表','admin-list.html')">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite>管理员列表</cite></a>
                    </li>
                    <li>
                        <a onclick="xadmin.add_tab('角色管理','admin-role.html')">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite>角色管理</cite></a>
                    </li>
                    <li>
                        <a onclick="xadmin.add_tab('权限分类','admin-cate.html')">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite>权限分类</cite></a>
                    </li>
                    <li>
                        <a onclick="xadmin.add_tab('权限管理','admin-rule.html')">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite>权限管理</cite></a>
                    </li>
                </ul>
            </li>
            <li>
                <a href="javascript:;">
                    <i class="iconfont left-nav-li" lay-tips="系统统计">&#xe6ce;</i>
                    <cite>系统统计</cite>
                    <i class="iconfont nav_right">&#xe697;</i></a>
                <ul class="sub-menu">
                    <li>
                        <a onclick="xadmin.add_tab('拆线图','echarts1.html')">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite>拆线图</cite></a>
                    </li>
                    <li>
                        <a onclick="xadmin.add_tab('拆线图','echarts2.html')">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite>拆线图</cite></a>
                    </li>
                    <li>
                        <a onclick="xadmin.add_tab('地图','echarts3.html')">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite>地图</cite></a>
                    </li>
                    <li>
                        <a onclick="xadmin.add_tab('饼图','echarts4.html')">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite>饼图</cite></a>
                    </li>
                    <li>
                        <a onclick="xadmin.add_tab('雷达图','echarts5.html')">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite>雷达图</cite></a>
                    </li>
                    <li>
                        <a onclick="xadmin.add_tab('k线图','echarts6.html')">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite>k线图</cite></a>
                    </li>
                    <li>
                        <a onclick="xadmin.add_tab('热力图','echarts7.html')">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite>热力图</cite></a>
                    </li>
                    <li>
                        <a onclick="xadmin.add_tab('仪表图','echarts8.html')">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite>仪表图</cite></a>
                    </li>
                </ul>
            </li>
            <li>
                <a href="javascript:;">
                    <i class="iconfont left-nav-li" lay-tips="图标字体">&#xe6b4;</i>
                    <cite>图标字体</cite>
                    <i class="iconfont nav_right">&#xe697;</i></a>
                <ul class="sub-menu">
                    <li>
                        <a onclick="xadmin.add_tab('图标对应字体','unicode.html')">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite>图标对应字体</cite></a>
                    </li>
                </ul>
            </li>
            <li>
                <a href="javascript:;">
                    <i class="iconfont left-nav-li" lay-tips="其它页面">&#xe6b4;</i>
                    <cite>其它页面</cite>
                    <i class="iconfont nav_right">&#xe697;</i></a>
                <ul class="sub-menu">
                    <li>
                        <a href="login.html" target="_blank">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite>登录页面</cite></a>
                    </li>
                    <li>
                        <a onclick="xadmin.add_tab('错误页面','error.html')">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite>错误页面</cite></a>
                    </li>
                    <li>
                        <a onclick="xadmin.add_tab('示例页面','demo.html')">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite>示例页面</cite></a>
                    </li>
                    <li>
                        <a onclick="xadmin.add_tab('更新日志','log.html')">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite>更新日志</cite></a>
                    </li>
                </ul>
            </li>
            <li>
                <a href="javascript:;">
                    <i class="iconfont left-nav-li" lay-tips="第三方组件">&#xe6b4;</i>
                    <cite>layui第三方组件</cite>
                    <i class="iconfont nav_right">&#xe697;</i></a>
                <ul class="sub-menu">
                    <li>
                        <a onclick="xadmin.add_tab('滑块验证','https://fly.layui.com/extend/sliderVerify/')" target="">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite>滑块验证</cite></a>
                    </li>
                    <li>
                        <a onclick="xadmin.add_tab('富文本编辑器','https://fly.layui.com/extend/layedit/')">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite>富文本编辑器</cite></a>
                    </li>
                    <li>
                        <a onclick="xadmin.add_tab('eleTree 树组件','https://fly.layui.com/extend/eleTree/')">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite>eleTree 树组件</cite></a>
                    </li>
                    <li>
                        <a onclick="xadmin.add_tab('图片截取','https://fly.layui.com/extend/croppers/')">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite>图片截取</cite></a>
                    </li>
                    <li>
                        <a onclick="xadmin.add_tab('formSelects 4.x 多选框','https://fly.layui.com/extend/formSelects/')">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite>formSelects 4.x 多选框</cite></a>
                    </li>
                    <li>
                        <a onclick="xadmin.add_tab('Magnifier 放大镜','https://fly.layui.com/extend/Magnifier/')">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite>Magnifier 放大镜</cite></a>
                    </li>
                    <li>
                        <a onclick="xadmin.add_tab('notice 通知控件','https://fly.layui.com/extend/notice/')">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite>notice 通知控件</cite></a>
                    </li>
                </ul>
            </li>
        </ul>
    </div>
</div>
<!-- <div class="x-slide_left"></div> -->
<!-- 左侧菜单结束 -->
<!-- 右侧主体开始 -->
<div class="page-content">
    <div class="layui-tab tab" lay-filter="xbs_tab" lay-allowclose="false">
        <ul class="layui-tab-title">
            <li class="home">
                <i class="layui-icon">&#xe68e;</i>我的桌面
            </li>
        </ul>
        <div class="layui-unselect layui-form-select layui-form-selected" id="tab_right">
            <dl>
                <dd data-type="this">关闭当前</dd>
                <dd data-type="other">关闭其它</dd>
                <dd data-type="all">关闭全部</dd>
            </dl>
        </div>
        <div class="layui-tab-content">
            <div class="layui-tab-item layui-show">
                <%--<iframe src='./welcome.html' frameborder="0" scrolling="yes" class="x-iframe"></iframe>--%>
            </div>
        </div>
        <div id="tab_show"></div>
    </div>
</div>
<div class="page-content-bg"></div>
<style id="theme_style"></style>
<!-- 右侧主体结束 -->
<!-- 中部结束 -->
<script>//百度统计可去掉
var _hmt = _hmt || [];
(function () {
    var hm = document.createElement("script");
    hm.src = "https://hm.baidu.com/hm.js?b393d153aeb26b46e9431fabaf0f6190";
    var s = document.getElementsByTagName("script")[0];
    s.parentNode.insertBefore(hm, s);
})();</script>
</body>

</html>
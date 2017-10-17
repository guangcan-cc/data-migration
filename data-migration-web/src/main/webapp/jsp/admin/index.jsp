<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/jsp/common/taglib.jspf"%>
<!DOCTYPE HTML>
<html>
<head>
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<LINK rel="Bookmark" href="../favicon.ico" >
<LINK rel="Shortcut Icon" href="../favicon.ico" />
<!--[if lt IE 9]>
<script type="text/javascript" src="../plugins/html5.js"></script>
<script type="text/javascript" src="../plugins/respond.min.js"></script>
<script type="text/javascript" src="../plugins/PIE-2.0beta1/PIE_IE678.js"></script>
<![endif]-->
<link rel="stylesheet" type="text/css" href="../h-ui/css/H-ui.min.css" />
<link rel="stylesheet" type="text/css" href="../h-ui/css/H-ui.admin.css" />
<link rel="stylesheet" type="text/css" href="../plugins/Hui-iconfont/1.0.7/iconfont.css" />
<link rel="stylesheet" type="text/css" href="../plugins/icheck/icheck.css" />
<link rel="stylesheet" type="text/css" href="../h-ui/skin/blue/skin.css" id="skin" />
<link rel="stylesheet" type="text/css" href="../h-ui/css/style.css" />
<link rel="stylesheet" type="text/css" href="../plugins/zTree/v3/css/zTreeStyle/zTreeStyle.css">
<!--[if IE 6]>
<script type="text/javascript" src="../plugins/DD_belatedPNG_0.0.8a-min.js" ></script>
<script>DD_belatedPNG.fix('*');</script>
<![endif]-->
<title>数据迁移系统</title>
</head>
<body>
<header class="navbar-wrapper">
	<div class="navbar navbar-fixed-top">
		<div class="container-fluid cl"> 
		<a class="logo navbar-logo f-l mr-10 hidden-xs" href="#">
		<img src="${ctx }/assets/img/logo-sinosoft.png" /> <span style="color: #000000;font-size: large;">数据迁移系统</span></a>
			<nav id="Hui-userbar" class="nav navbar-nav navbar-userbar hidden-xs">
				<ul class="cl">
					<li>${ userVO.username}</li>
					<li class="dropDown dropDown_hover"> <a href="#" class="dropDown_A">  ${ userVo.userCode} <i class="Hui-iconfont">&#xe6d5;</i></a>
						<ul class="dropDown-menu menu radius box-shadow">
							<li><a href="${ctx }/user/logout.do">退出</a></li>
						</ul>
					</li>
				</ul>
			</nav>
		</div>
	</div>
</header>
<aside class="Hui-aside">
	<input runat="server" id="divScrollValue" type="hidden" value="" />
	<div class="menu_dropdown bk_2">
		<dl id="menu-dataSource">
			<dt><i class="Hui-iconfont">&#xe620;</i> 数据源管理<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
			<dd>
				<ul>
					<li><a _href="../jsp/dataSourceInfo/data_source_info.jsp" data-title="数据源信息" href="javascript:;">数据源信息</a></li>
				</ul>
			</dd>
		</dl>
		<dl id="menu-migration-configuration">
			<dt><i class="Hui-iconfont">&#xe6c6;</i> 迁移配置<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
			<dd>
				<ul>
					<li><a _href="../jsp/migrationConfig/group_info.jsp" data-title="组配置" href="javascript:;">组配置</a></li>
				</ul>
			</dd>
		</dl>
		<dl id="menu-data-migration">
			<dt><i class="Hui-iconfont">&#xe6b6;</i> 数据迁移<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
			<dd>
				<ul>
					<li><a _href="../jsp/dataMigration/data_migration.jsp" data-title="迁移条件" href="javascript:;">迁移条件</a></li>
				</ul>
			</dd>
		</dl>
		<dl id="menu-log">
			<dt><i class="Hui-iconfont">&#xe623;</i> 日志管理<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
			<dd>
				<ul>
					<li><a _href="../jsp/log/log_info.jsp" data-title="日志信息" href="javascript:;">日志信息</a></li>
				</ul>
			</dd>
		</dl>
		<dl id="menu-system">
			<dt><i class="Hui-iconfont">&#xe62e;</i> 系统管理<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
			<dd>
				<ul>
					<li><a _href="../jsp/user/user_info.jsp" data-title="用户管理" href="javascript:;">用户管理</a></li>
					<li><a _href="../jsp/user/password_edit.jsp" data-title="修改密码" href="javascript:;">修改密码</a></li>
				</ul>
			</dd>
		</dl>
	</div>
</aside>
<div class="dislpayArrow hidden-xs"><a class="pngfix" href="javascript:void(0);" onClick="displaynavbar(this)"></a></div>
<section class="Hui-article-box">
	<div id="Hui-tabNav" class="Hui-tabNav hidden-xs">
		<div class="Hui-tabNav-wp">
			<ul id="min_title_list" class="acrossTab cl">
				<li class="active"><span title="我的桌面" data-href="welcome.html">我的桌面</span><em></em></li>
			</ul>
		</div>
		<div class="Hui-tabNav-more btn-group"><a id="js-tabNav-prev" class="btn radius btn-default size-S" href="javascript:;"><i class="Hui-iconfont">&#xe6d4;</i></a><a id="js-tabNav-next" class="btn radius btn-default size-S" href="javascript:;"><i class="Hui-iconfont">&#xe6d7;</i></a></div>
	</div>
	<div id="iframe_box" class="Hui-article">
		<div class="show_iframe">
			<div style="display:none" class="loading"></div>
			<iframe scrolling="yes" frameborder="0" src="welcome.do"></iframe>
		</div>
	</div>
</section>
<script type="text/javascript" src="../plugins/jquery/jquery.min.js"></script> 
<script type="text/javascript" src="../plugins/layer/2.1/layer.js"></script>
<script type="text/javascript" src="${ctx}/plugins/zTree/v3/js/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="${ctx}/plugins/zTree/v3/js/jquery.ztree.excheck-3.5.min.js"></script>
<script type="text/javascript" src="${ctx}/plugins/zTree/v3/js/jquery.ztree.exedit-3.5.min.js"></script>
<script type="text/javascript" src="../h-ui/js/H-ui.js"></script> 
<script type="text/javascript" src="../h-ui/js/H-ui.admin.js"></script>
<script type="text/javascript">


</script> 
</body>
</html>
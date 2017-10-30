//wodp  数据作业  数据挖掘  数据审计 的基础url

/*var wodp_url="http://10.1.131.103:9090/wodp";*/
var wodp_url="${yiji.url}";

var daerwen_url="${drw.url}";
var wajue_url="${mamp.url}";
var gateway_url="${gateway.url}";

//分别为 注册页面 登录页面  检查用户名  登出 到homepage页面
var login = "${sso.casserverurl}/cas/login?service="+wodp_url+"/unicom/index.html";
var logout = "${sso.casserverurl}/cas/logout?service="+wodp_url+"/homepage.html";
//获取二级菜单的 url  分别为 达尔文 数据挖掘  和gateway
var dew_erjiurl=daerwen_url+"/yiji/aaa?method=second";
var sjwj_erjiurl = wajue_url+"/yiji/UserRolePmn!findOneMenu.action";
var gw_erjiurl= gateway_url+"/yiji/searchFuncList.do";
//判断是否拥有系统管理项的接口的url 分别为 达尔文 数据挖掘  数据审计
var dew_sysurl=daerwen_url+"/yiji/aaa?method=systemcheck";
var sjwj_sysurl=wajue_url+"/yiji/UserRolePmn!findSystemManage.action";
var gw_sysurl=gateway_url+"/yiji/systemFuncCheck.do";
//点击对应的系统管理  接口的对应的url 
var dew_ctrurl=daerwen_url+"/yiji/aaa?method=system";
var sjwj_ctrurl=wajue_url+"/yiji/UserRolePmn!findSystemMenu.action";
var gw_ctrurl=gateway_url+"/yiji/searchUserSystemFunc.do";
//检查邮箱 qq是否可用的 接口的url
var check_email=wajue_url+"/yijireg/UserRegister!validationEmail.action";
var check_qq=wajue_url+"/yijireg/UserRegister!validationQQ.action";




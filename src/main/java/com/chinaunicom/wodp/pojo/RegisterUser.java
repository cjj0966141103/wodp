package com.chinaunicom.wodp.pojo;

import java.io.Serializable;

public class RegisterUser implements Serializable {

	        
	private Integer id;      //自增主键 唯一标识          
	private String ranNum;  //刘位随机数
	
	private String type ="gateway_register";             //消息类型
	private String version = "1.0";   //版本信息,当前版本1.0
	private String action = "request";            //请求动作
	
	
	private String username;		  //用户名
	private String loginName;          //登录名
	private Integer isAdmin;           //是否是管理员  注册的时候选择的
	private Integer admin;            //是否是管理员  数据挖掘需要的数据
	private Integer sex;              //性别
	
	private String email;             //电子邮箱
	private String qq;                //qq号码
	private String mobile;            //移动电话
	private String login_pwd;
	private String telephone;         //固定电话
	private String memo;              //备注
	private String pureOrgId;        //省份
	private String nickname;          //昵称
	private String loginPwd;          //密码
	private String certNo;            //身份证号码
	private String addr;              //联系地址
	private String roleIds;           //用户角色  (给后台传递key值) 多选框：
								                                 /*key：root   value:超级管理员 
								                                   key：m01   value: 模型需求方
								                                   key：m02   value: 业务分析人员
								                                   key：m03   value: 数据处理人员
								                                   key：m04   value: 数据挖掘人员
								                                   key：m05   value: 模型管理人员
								                                   key：m06   value: 系统管理人员*/

	private String registerDEW;      //是否选择 达尔文 注册   是一个标识符
	private String registerWJ;
	private String registerGA;
	
	
	private String rtw_roleId;            //用户角色  角色类型1,2,3:超级管理员，普通用户，只读权限
	private String rtw_orgId;            // 组织机构1,2,3,4:中国联通、北京分公司、上海分公司、福建分公司
	private String rtw_orgName;           //组织机构名称
	private String rtw_orgpid;           //组织机构的父id
	
	private String addNodeId;               //资源池：0,2,3,4,5:暂无资源池、资源池-cdh03、资源池-cdh01、资源池-cdh02、资源池-cdh05
	
	private String totalSpace;        //存储份额
	private String folderName;        //存储目录  /user/yimr/username
	private String userType;           //用户类型 dataUser(数据用户),orgUser(机构用户),safeUser(数据库管理员),auditUser(审核人员),mainateUser(运维人员)
	//getaway字段
	private String realName;           //文件上传的原来名字  例如 a.jar
	private String filePath;           //文件存放的路径
	private Integer pushFtp;          //PUSH FTP(是一个选择框)dataUser可选项
	private String ftpIp;             //FTP IP地址   最大长度15  pushFtp选中才显示，必选项
	private Integer ftpPort;          //FTP端口  最大长度4 pushFtp选中才显示，必选项
	private String ftpPath;           //ftp 目录路径   pushFtp选中才显示，必选项
	private String ftpUsername;       //ftp用户名  pushFtp选中才显示，必选项
	private String ftpPassword;        //ftp 密码 pushFtp选中才显示，必选项
	private String orgId;             //机构id dataUser 必选项 显示机构名字,传递的参数是机构的id
	
	private String orgName;              //机构名字   orgUser可选项
	private String orgHeadName;          //法人名字   orgUser可选项
	private String orgCertNo;            //法人身份证号码  orgUser可选项
	private String orgTel;               //机构联系电话  orgUser可选项
	private String regCode;              //工商编号  orgUser可选项
	private String orgAddr;              //公司地址   orgUser可选项
	private String onlineStatus;        //值班状态  0代表非值班 1代表值班  safeUser,auditUser,mainateUser可选项
	private String remark;               //备注  safeUser,auditUser,mainateUser可选项

	
	public String getRanNum() {
		return ranNum;
	}
	public void setRanNum(String ranNum) {
		this.ranNum = ranNum;
	}
	public Integer getIsAdmin() {
		return isAdmin;
	}
	public void setIsAdmin(Integer isAdmin) {
		this.isAdmin = isAdmin;
	}
	
	public String getRegisterDEW() {
		return registerDEW;
	}
	public void setRegisterDEW(String registerDEW) {
		this.registerDEW = registerDEW;
	}
	public String getRegisterWJ() {
		return registerWJ;
	}
	public void setRegisterWJ(String registerWJ) {
		this.registerWJ = registerWJ;
	}
	public String getRegisterGA() {
		return registerGA;
	}
	public void setRegisterGA(String registerGA) {
		this.registerGA = registerGA;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	
	
	
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public RegisterUser() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getRoleIds() {
		return roleIds;
	}
	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}
	public String getLogin_pwd() {
		return login_pwd;
	}
	public void setLogin_pwd(String login_pwd) {
		this.login_pwd = login_pwd;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
//	public String getType() {
//		return type;
//	}
//	public void setType(String type) {
//		this.type = type;
//	}
//	public String getVersion() {
//		return version;
//	}
//	public void setVersion(String version) {
//		this.version = version;
//	}
//	public String getAction() {
//		return action;
//	}
//	public void setAction(String action) {
//		this.action = action;
//	}
//	public Integer getStatus() {
//		return status;
//	}
//	public void setStatus(Integer status) {
//		this.status = status;
//	}
//	public String getDesc() {
//		return desc;
//	}
//	public void setDesc(String desc) {
//		this.desc = desc;
//	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public Integer getAdmin() {
		return admin;
	}
	public void setAdmin(Integer admin) {
		this.admin = admin;
	}
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}

	
	public String getPureOrgId() {
		return pureOrgId;
	}
	public void setPureOrgId(String pureOrgId) {
		this.pureOrgId = pureOrgId;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getLoginPwd() {
		return loginPwd;
	}
	public void setLoginPwd(String loginPwd) {
		this.loginPwd = loginPwd;
	}
	public String getCertNo() {
		return certNo;
	}
	public void setCertNo(String certNo) {
		this.certNo = certNo;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	

	
	public String getRtw_orgName() {
		return rtw_orgName;
	}
	public void setRtw_orgName(String rtw_orgName) {
		this.rtw_orgName = rtw_orgName;
	}
	
	public String getTotalSpace() {
		return totalSpace;
	}
	public void setTotalSpace(String totalSpace) {
		this.totalSpace = totalSpace;
	}
	public String getFolderName() {
		return folderName;
	}
	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	
	public Integer getPushFtp() {
		return pushFtp;
	}
	public void setPushFtp(Integer pushFtp) {
		this.pushFtp = pushFtp;
	}
	public String getFtpIp() {
		return ftpIp;
	}
	public void setFtpIp(String ftpIp) {
		this.ftpIp = ftpIp;
	}
	public Integer getFtpPort() {
		return ftpPort;
	}
	public void setFtpPort(Integer ftpPort) {
		this.ftpPort = ftpPort;
	}
	public String getFtpPath() {
		return ftpPath;
	}
	public void setFtpPath(String ftpPath) {
		this.ftpPath = ftpPath;
	}
	public String getFtpUsername() {
		return ftpUsername;
	}
	public void setFtpUsername(String ftpUsername) {
		this.ftpUsername = ftpUsername;
	}
	public String getFtpPassword() {
		return ftpPassword;
	}
	public void setFtpPassword(String ftpPassword) {
		this.ftpPassword = ftpPassword;
	}
	
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getOrgHeadName() {
		return orgHeadName;
	}
	public void setOrgHeadName(String orgHeadName) {
		this.orgHeadName = orgHeadName;
	}
	public String getOrgCertNo() {
		return orgCertNo;
	}
	public void setOrgCertNo(String orgCertNo) {
		this.orgCertNo = orgCertNo;
	}
	public String getOrgTel() {
		return orgTel;
	}
	public void setOrgTel(String orgTel) {
		this.orgTel = orgTel;
	}
	public String getRegCode() {
		return regCode;
	}
	public void setRegCode(String regCode) {
		this.regCode = regCode;
	}
	public String getOrgAddr() {
		return orgAddr;
	}
	public void setOrgAddr(String orgAddr) {
		this.orgAddr = orgAddr;
	}

	public String getRtw_roleId() {
		return rtw_roleId;
	}
	public void setRtw_roleId(String rtw_roleId) {
		this.rtw_roleId = rtw_roleId;
	}
	public String getRtw_orgId() {
		return rtw_orgId;
	}
	public void setRtw_orgId(String rtw_orgId) {
		this.rtw_orgId = rtw_orgId;
	}
	public String getRtw_orgpid() {
		return rtw_orgpid;
	}
	public void setRtw_orgpid(String rtw_orgpid) {
		this.rtw_orgpid = rtw_orgpid;
	}
	
	public String getAddNodeId() {
		return addNodeId;
	}
	public void setAddNodeId(String addNodeId) {
		this.addNodeId = addNodeId;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getOnlineStatus() {
		return onlineStatus;
	}
	public void setOnlineStatus(String onlineStatus) {
		this.onlineStatus = onlineStatus;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
	
	
	
	
	
}

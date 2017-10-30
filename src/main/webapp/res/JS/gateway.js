//注册机构列表查询
$(function(){
	$.ajax({
		type : "GET",
	    url : gateway_url+ "/gwRegister/orgList.do",
		dataType : "jsonp",
		
		success : function(data){
			var org = data.data;
			for(i=0,html="";i<org.length;i++){
				html+="<option value="+org[i].orgId+">"+org[i].orgName+"</option>"
				$("#orgId").html(html);
			}	
		},
		error:function(){
			alert('系统异常');
		}
	});
});
//对机构名字进行唯一性校验
$("#orgName").blur( function () { 
		var orgName = $("#orgName").val();
		$.ajax({
			type : "GET",
		    url : gateway_url+ "/gwRegister/orgNameCheck.do?orgName="+orgName,
			dataType : "jsonp",
			success : function(data){
				if(data.status==0){
					alert(data.desc);
					$("#orgName").val("");
				}
			},
			error:function(){
				alert("系统异常");
			}
		});
	} );
//ftp连接测试
$("#checkFtp").click( function () { 
	var ftpIp = $("#ftpIp").val();
	var ftpUsername = $("#ftpUsername").val();
	var ftpPassword = $("#ftpPassword").val();
	$("#ftptxt").val("正在测试请稍等!!!");
	$.ajax({
		type : "GET",
	    url : gateway_url+ "/gwRegister/ftpCheck.do",
		dataType : "jsonp",
		data:{"ftpIp":ftpIp,"ftpUsername":ftpUsername,"ftpPassword":ftpPassword},
		success : function(data){
			$("#ftptxt").val(data.desc);
		},
		error:function(){
			$("#ftptxt").val("系统异常!!!");
		}
	});
} );
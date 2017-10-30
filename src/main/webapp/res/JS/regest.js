
//将注册还是不注册传递到后台
	$(".registerDEW").click(function(){
		var val=$(this).val();
		
		if(val=="注册"){
			$("#registerDEW").val("1");
		}else{
			$("#registerDEW").val("0");
		}
	});
	$(".registerWJ").click(function(){
		var val=$(this).val();
		
		if(val=="注册"){
			$("#registerWJ").val("1");
		}else{
			$("#registerWJ").val("0");
		}
	});
	$(".registerGA").click(function(){
		var val=$(this).val();
		
		if(val=="注册"){
			$("#registerGA").val("1");
		}else{
			$("#registerGA").val("0");
		}
	});

    //下拉选项卡
    $("table:not('#table1')").hide();
    var select_zc;
    var parentid;
    $(" .main_c>.main_c_title>input:nth-child(2)").click(function(){

        $(this).addClass("title2");
        $(this).parent().parent().children("table").show(500);
        $(this).parent().children("input").eq(1).removeClass("title4").addClass("title3");
    });
    $(" .main_c>.main_c_title>input:nth-child(3)").click(function(){
        $(this).addClass("title4");
        $(this).parent().parent().children("table").hide(500);
        $(this).parent().children("input").eq(0).removeClass("title2").addClass("title1");

    });

    //下拉选项卡结束

    //注册公有验证
    function public_save(){
    	
        var mobile = /^(1\d{10})$/; //手机号码
        var email = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/; //邮箱
        //身份证验证
        var idReg = new RegExp(/^[1-9][0-9]{5}(19[0-9]{2}|200[0-9]|2010)(0[1-9]|1[0-2])(0[1-9]|[12][0-9]|3[01])[0-9]{3}[0-9xX]$/);

        if($("input[name='username']").val()==""){
            alert("请输入帐号");
            $("input[name='username']").focus();
            return 1;
        }
        if($("input[name='loginName']").val()==""){
            alert("请输入用户名");
            $("input[name='loginName']").focus();
            return 1;
        }
        if($("input[name='email']").val()==""){
            alert("请输入邮箱");
            $("input[name='email']").focus();
            return 1;
        }
        if(!email.test($("input[name='email']").val())){
            alert("请输入正确的邮箱");
            $("input[name='email']").focus();
            return 1;
        }

        if($("input[name='mobile']").val()==""){
            alert("请输入电话 ");
            $("input[name='mobile']").focus();
            return 1;
        }
        if(!mobile.test($("input[name='mobile']").val())){
            alert("请输入正确的电话");
            $("input[name='mobile']").focus();
            return 1;
        }
        if($("input[name='certNo']").val()==""){
            alert("请输入省份证 ");
            $("input[name='certNo']").focus();
            return 1;
        }
        if(!idReg.test($("input[name='certNo']").val())){
            alert("请输入正确的身份证");
            $("input[name='certNo']").focus();
            return 1;
        }

        if($("input[name='loginPwd']").val()==""){
            alert("请输入登录密码");
            $("input[name='loginPwd']").focus();
            return 1;
        }
        if($("input[name='login_pwd']").val()==""){
            alert("请再次输入密码");
            $("input[name='login_pwd']").focus();
            return 1;
        }
        if($("input[name='loginPwd']").val()!=$("input[name='login_pwd']").val()){
            alert("输入密码不一致");
            $("input[name='loginPwd']").focus();
            return 1;
        }
    }
    //注册公有验证结束
    //Darwin注册
    function darwin_save(){
    	
        if($("input[name='totalSpace']").val()==""){
            alert("请输入存储份额");
            $("input[name='totalSpace']").focus();
            return 1;
        }
        if($("input[name='folderName']").val()==""){
            alert("请输入存储目录");
            $("input[name='folderName']").focus();
            return 1;
        }
        if($("select[name='nodeId']").val()==""){
            alert("请选择资源池");
            $("select[name='nodeId']").focus();
            return 1;
        }
        if($("select[name='rtw_roleId']").val()==""){
            alert("请选择角色类型");
            $("select[name='rtw_roleId']").focus();
            return 1;
        }
    }
    //Darwin注册结束

    //挖掘分析注册
    //挖掘分析注册结束

    //geteway注册

    //图片上传影藏域的显示

    $("#upload").change(function(){
        var objUrl = getObjectURL(this.files[0]) ;
        console.log("objUrl = "+objUrl) ;
        if (objUrl) {
            $("#imgShow").attr("src", objUrl) ;
        }
    });

    //建立一个可存取到该file的url
    function getObjectURL(file) {
        var url = null ;
        if (window.createObjectURL!=undefined) { // basic
            url = window.createObjectURL(file) ;
        } else if (window.URL!=undefined) { // mozilla(firefox)
            url = window.URL.createObjectURL(file) ;
        } else if (window.webkitURL!=undefined) { // webkit or chrome
            url = window.webkitURL.createObjectURL(file) ;
        }
        return url ;
    }
    //图片上传影藏域的显示结束

    $("#upload").click(function(){
        $('tr[id=ttr20]').show();
    });
    $("#quxiao").click(function(){
        $("#ttr20").hide();
    });
    //点击确定图片提交单独的AJAX
    function ajaxFileUpload(){
        var formData = new FormData();
        formData.append('upload',$("#upload")[0].files[0]);    //将文件转成二进制形式
        $.ajax({
            type:"post",
            url:"./uploadfile",
            async:false,
            contentType: false,    //这个一定要写
            processData: false, //这个也一定要写，不然会报错
            data:formData,
            dataType:'text',    //返回类型，有json，text，HTML。这里并没有jsonp格式，所以别妄想能用jsonp做跨域了。
            success:function(data){
            	var data1=eval('('+data+')');
            	 $("#realName").val(data1.realName);
            	$("#filePath").val(data1.filePath);
            	alert(data1.desc);
            },
            error:function(XMLHttpRequest, textStatus, errorThrown, data){
                alert(errorThrown);
            }
        });
    }


    //geteway选项卡
    function changeUserType(){
    	if($('#isAdmin').val() == '2'){
        if($('#userType2').val() == 'orgUser'){
            $("tr[id^='ttr']").show();
            $("tr[id='ttr20'],tr[id='ttr0'],tr[data-name='gwUserFtpTr'],tr[data-name='dataUser'],tr[id='ttr17'],tr[id='ttr18'],tr[id='ttr19']").hide();
            $('#gw_org_list').show();
            $("#imageMsg").show();
        }else if($('#userType2').val() == 'dataUser'){
            $("tr[id='ttr20'],tr[data-name='gwOrgTr'],tr[id='ttr18'],tr[id='ttr19']").hide();
            $("tr[data-name='gwUserTr'],tr[data-name='dataUser'],tr[id='ttr16'],tr[id='ttr17']").show();
            if($("data-[name='pushFtp']:checked").val()=='1') $("tr[data-name='gwUserFtpTr']").show();
            $('#gw_org_list').hide();
            $("#imageMsg").hide();
        }
        }else{
            $("tr[id='ttr18'],tr[id='ttr19']").show();
            $("#gw_org_list,tr[data-name='gwUserTr'],tr[data-name='gwOrgTr'],tr[data-name='dataUser'],tr[id='ttr16'],tr[id='ttr17']").hide();
        }
    }

    //geteway校验
    function geteway_save(){
    	
        if($('#userType').val() == 'dataUser'){
            if($("select[name='orgId']").val()==""){
                alert("请选择机构组织id");
                $("select[name='orgId']").focus();
                return;
            }
            if($("select[name='orgName']").val()==""){
                alert("请选择机构名称");
                $("select[name='orgName']").focus();
                return 1;
            }
            if($("#upload").val()==""){
                alert("请上传证件扫描附件");
                $("#upload").focus();
                return 1;
            }
            if (!/\.(gif|jpg|jpeg|png|GIF|JPG|JPEG|PNG)$/.test($("#upload").val())) {
                alert("证件扫描附件必须是.gif,jpeg,jpg,png中的一种！");
                $("#upload").focus();
                return 1;
            }

        }else if($('#userType').val() == 'orgUser'){
            if($("input[name='orgHeadName']").val()==""){
                alert("请输入法人名称");
                $("input[name='orgHeadName']").focus();
                return 1;
            }
            if($("input[name='regCode']").val()==""){
                alert("请输入工商编号");
                $("input[name='regCode']").focus();
                return 1;
            }
            if($("input[name='orgTel']").val()==""){
                alert("请输入机构联系电话");
                $("input[name='orgTel']").focus();
                return 1;
            }
            if($("#upload").val()==""){
                alert("请上传证件扫描附件");
                $("#upload").focus();
                return 1;
            }
            if (!/\.(rar|zip)$/.test($("#upload").val())) {
                alert("证件扫描附件必须是压缩打包文件，且打包类型必须是.RAR,ZIP中的一种！");
                $("#upload").focus();
                return 1;
            }
        }else{
            if($("select[name='onlineStatus']").val()==""){
                alert("请选择值班状态");
                $("select[name='onlineStatus']").focus();
                return;
            }
        }
    }
    //geteway注册结束

    // 提交
    function submit_Form(){
        var temp2= $("#myform2 table").is(":hidden");//是否隐藏 //Darwin校验
        var temp3= $("#myform3 table").is(":hidden");//是否隐藏 //挖卷
        var temp4= $("#myform4 table").is(":hidden");//是否隐藏  //geteway校验

        if(temp2==false && temp3==false && temp4==false){
        	var a = public_save();
            var b = darwin_save();
            var c = geteway_save();
            if (a==1||b==1||c==1) {
            	return 1;
            }
        }else if(temp2==false && temp3==false && temp4==true){
            var a = public_save();
            var b = darwin_save();
            if (a==1||b==1) {
            	return 1;
            }
        }else if(temp2==false && temp3==true && temp4==true){
            var a = public_save();
            var b = darwin_save();
            if (a==1||b==1) {
            	return 1;
			}
        }else if(temp2==true && temp3==false && temp4==false){
            var a = public_save();
            var c = geteway_save();
            if (a==1||c==1) {
            	return 1;
			}
        }else if(temp2==true && temp3==true && temp4==false){
            var a = public_save();
            var c = geteway_save();
            if (a==1||c==1) {
            	return 1;
			}
        }else if(temp2==true && temp3==false && temp4==true){
            var a = public_save();
            if (a==1) {
            	return 1;
			}
        }else if(temp2==false && temp3==true && temp4==false){
        	var a = public_save();
            var b = darwin_save();
            var c = geteway_save();
            if (a==1||b==1||c==1) {
            	return 1;
			}
        }else{
        	alert("达尔文,数据挖掘和gateway至少注册一个！！！");
        	return 1;
        }
    }

    //提交表单
    $("#registbtn").unbind().click(function(){
    
        	var flag = submit_Form();
        	if (flag) {
    			return;
    		}
        	if ($("#registerGA").val()==1) {
        		if($("#userType").val()=="dataUser"||$("#userType").val()=="orgUser"){
        			if($("#realName").val()==""||$("#filePath").val()==""){
        				alert("文件没有上传成功,可能未点击确定按钮");
        				return;
        			}
        		}
			}
            $("#singupForm").ajaxSubmit({
                type: "POST",
                url: "./register",
                data: $("#singupForm").serialize(),
                dataType:"json",
                success: function(data){
                    
                	var message  = data.success;
                	var len = message.length;
                	var str = message.substring(len-2);
                	if (str=="成功") {
                		window.location.href="/wodp/resultSuccess.html?data="+encodeURI(data.success);
    				}else{
    					window.location.href="/wodp/resultFailure.html?data="+encodeURI(data.success);
                     
    				}
                   
                    
                },
                error:function(){
                    alert("注册失败，请联系管理员");
                }
            });
    	

    });
    $("#colsebtn").click(function(){
    	$("#registframe").hide();
    })

    //挖掘注册省份选择部分

        $("#nav").mouseenter(function(){
            $("#nav_first_c").stop(true,true).slideDown();
        }).mouseleave(function(){
            $("#nav_first_c").stop(true,true).slideUp();
        });
        $("#nav_c").mouseenter(function(){
            $("#nav_c_c").stop(true,true).slideDown();
        }).mouseleave(function(){
            $("#nav_c_c").stop(true,true).slideUp();
        });
        //省份注册显示
        function stopEvent(){ //阻止冒泡事件
            //取消事件冒泡
            var e=arguments.callee.caller.arguments[0]||event;
            if (e && e.stopPropagation) {
                // this code is for Mozilla and Opera
                e.stopPropagation();
            } else if (window.event) {
                // this code is for IE
                window.event.cancelBubble = true;
            }
        }


        $("#nav_first_c li:not('#nav_c')").click(function(){
            stopEvent();
            var text=$(this).text();
            var val=$(this).val();
            $("#selectbc").val(text);
            $("#selectb").val(val);
        });

    //挖掘注册省份选择部分结束



 
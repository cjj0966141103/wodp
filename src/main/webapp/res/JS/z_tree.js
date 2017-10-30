
	//加载树
	function loadTree() {
		var setting3 = {
				view: {
					addHoverDom: addHoverDom,
				},
				data: {
					simpleData: {
						enable: true//指定使用简单json格式的数据构造节点数据 
									}
								}
						};
		
	    	 	$.ajax({
		            type: "get",
		            /* 提交的地址 */
		            url: daerwen_url+"/page?method=orgTree&isTree=false",
		            /* orgpid ziyuan*/
		            data:{},
		            dataType:"jsonp",
		            success: function(data){
		            	$.fn.zTree.init($("#treeDemo"), setting3, data);
		            },
		            error:function(){
		                alert("树显示失败");
		            }
		        });
		
					}
	
					 loadTree();
					//添加按钮
					function addHoverDom(treeId, treeNode) {

							if((!treeNode["id"] || treeNode["id"]=="-1")){
								return;
							}

							var sObj = $("#" + treeNode.tId + "_span");
							if (treeNode.editNameFlag || $("#addBtn_"+treeNode.tId).length>0) return;
							var addStr = "";
								addStr = "<span class='button add' id='addBtn_" + treeNode.tId
									+ "' title='新增组织机构' onfocus='this.blur();'></span>";
							sObj.after(addStr);
												
												//选中节点事件
												var btn1 = $("#"+treeNode.tId+"_span");
												
												if(btn1) btn1.bind("click", function(){
													
													 $("#treeid").val(treeNode["id"]);
												    //alert( $("#treeid").val());			
												});
							
												//添加按钮的点击事件
												var btn = $("#addBtn_"+treeNode.tId);
												if (btn) btn.bind("click", function(){
													ziyuanchi();
													//将父id放入到隐藏域中
													 $("#orgpid").val(treeNode["id"]);
													//alert(treeNode.tId);
													//显示div
										        
										             $("#formboxq").show();
													 $("#ccc").click(function(){		
												         $("#formboxq").hide();
	   											   })
	   											   //当点击确定的时候
	   											    $("#sss").click(function(){
	   											    	//获得newOrg所需信息
	   											    	var orgname=$("#org").val();
	   											    	var orgpid=$("#orgpid").val();
	   											    	var nodeid=$("#ziyuanchi").val();
	   											    	 $.ajax({
	   											    		
	   											            type: "get",
	   											            /* 提交的地址 */
	   											            url: daerwen_url+ "/page?method=newOrg",
	   											            /* 传输参数*/
	   											            data: {"rtw_orgName":orgname,"addNodeId":nodeid,"rtw_orgpid":orgpid},
	   											            dataType:"jsonp",
	   											            success: function(data){
	   											               	 alert("添加节点成功");
	   											            	 loadTree();
	   											            },
	   											            error:function(){
	   											                alert("添加节点失败");
	   											            }
	   											        });

												         $("#formboxq").hide();
	   											   })
												});
												
												//节点的点击事件--选中分公司将其id放入隐藏域
												var btn2 = $("#"+treeNode.tId+"_span");
												if (btn2) btn2.bind("click", function(){
													//$("#pid").val(treeNode["pid"]);
													//将选中的节点赋值
													$("#id").val(treeNode["id"]);
													//var a=document.getElementById("id").value;
													//alert(a);
												});
											};
			

	function ziyuanchi(){
		
		$.ajax({
	            type: "get",
	            /* 提交的地址 */
	            url: daerwen_url+ "/page?method=ziyuancollection",
	            /* orgpid ziyuan*/
	            data: {},
	            dataType:"jsonp",
	            success: function(data){
	               	
	            	//alert(data[0].name);
	            	/*将json串中的资源池添加到名称为下拉框中*/
					for(i=0,html="";i<data.length;i++){
						html+="<option value="+data[i].id+">"+data[i].name+"("+data[i].ip+":"+data[i].port+")"+"</option>"
						$("#ziyuanchi").html(html+"<option value='0'>暂无资源池</option>");
					}	
	            },
	            error:function(){
	              //  alert("加载资源池失败");
	            }
	        });

	};
	
	
	$("#org").blur(function(){
		var text=$("#org").val();
		
		if(text.length != 0){
			//alert(text);
			$.ajax({
				type : "GET",
			    url : daerwen_url+ "/page?method=existName",
				dataType : "jsonp",
				data:{"name":text},
				success : function(data){
					//将ok、erro改为statuts
					alert(data.status);
				},
				error:function(){
					alert("检查名称发生错误");
				}
			});
	
		}else{
			alert("请输入机构名！！！");
		}
	});
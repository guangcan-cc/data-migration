<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<link rel="stylesheet" href="${ctx}/css/progress.css">
<link rel="stylesheet" href="${ctx}/css/style.css">
<script type="text/javascript" src="${ctx}/plugins/jquery/jquery.min.js"></script>

<title>历史影像文件处理</title>
</head>
<body>
	<div class="page-container">
		<form id="queryForm" name="queryForm" class="form-horizontal" role="form" method="post">
			<div class="content-head">
				<div class="head-bar"> <!--  头部板块 -->
		             <div class="head-title">
		                 <h4>历史影像文件处理</h4>
		             </div> 
		             <div class="head-btns">
		             	<button type="button" class="btn btn-success" id="startBtn">开始执行</button>
		             	<button type="button" class="btn btn-warning" id="stopBtn">停止执行</button>
		             	<button type="button" class="btn btn-success" id="refreshBtn">刷新</button>
		             </div> <!--  头部按钮 -->
		         </div> <!--  头部板块 end -->
			</div>
		
			<div class="content-body">
				<div class="query-condition">
					<div class="row form-inline ">
						<div class="form-group col-xs-4 col-md-4">
							<label class="col-xs-4 form-label">机构:</label>
							<div class="col-xs-8">
								<select class="input-text" id="comCode">
									<option value=''></option>
									<option value='510122'>510122</option>
									<option value='510000'>510000</option>
									<option value='510100'>510100</option>
								</select>
							</div>
						</div>
						<div class="form-group col-xs-4 col-md-4">
							<label class="col-xs-4 form-label"><span style="color:red">*</span>线程数：</label>
							<div class="col-xs-8">
								<input type="text" id="threadNum" datatype="n" onblur="this.value=this.value.replace(/[^0-9.]/g,'');" class="input-text" value="">
							</div>
						</div>
						<div class="form-group col-xs-4 col-md-4">
							<label class="col-xs-4 form-label"><span style="color:red">*</span>分组量：</label>
							<div class="col-xs-4">
								<input type="text" id="groupNum" datatype="n" onblur="this.value=this.value.replace(/[^0-9.]/g,'');" class="input-text" value="">
							</div>
							<label class="col-xs-4 form-other">天一组</label>
						</div>
					</div>
				</div>
				<!--  头部板块 end -->
				<hr class="line-blue">
	 			<div class="table-btns">执行状态</div>
				<div class="query-result">
					<table id="DataTable" class="table table-border table-bordered table-bg table-hover table-sort" style="width:100%">
						<thead>
							<tr class="text-c">
								<th width="15%">日期范围</th>
								<th width="15%">机构</th>
								<th width="15%">影像文件量</th>
								<th width="15%">处理进度</th>
								<th width="15%">开始时间</th>
								<th width="15%">完成时间</th>
								<th width="15%">用时</th>
							</tr>
						</thead>
						<tbody>
						</tbody>
					</table>
				</div>
			</div>
			<div class="content-footer"></div>
		</form>
	</div>
	<script type="text/javascript" src="${ctx}/plugins/datatables/1.10.12/jquery.dataTables.min.js"></script>
	<script type="text/javascript" src="${ctx}/js/AjaxList.js"></script>
	<script type="text/javascript" src="${ctx}/plugins/layer/2.1/layer.js"></script>
	<script type="text/javascript">
	
	var proPath="${pageContext.request.contextPath}" ;
	//定时器
	var timer;
	var ajaxList;
	$(function(){
		//执行
		$("#startBtn").click(function(){
			execute();
		})
		//结束执行
		$("#stopBtn").click(function(){
			stop();
		})
		//刷新
		$("#refreshBtn").click(function(){
			refresh();
		})
		ajaxList = new AjaxList("#DataTable")
		ajaxList.targetUrl = 'search.do';
		ajaxList.postData = $("#queryForm").serializeJson();
		ajaxList.rowCallback = rowCallback;
		ajaxList.columns = columns;
		refresh();
		
		//datatables重绘
		$.fn.dataTableExt.oApi.fnStandingRedraw = function(oSettings) {
		    //redraw to account for filtering and sorting
		    // concept here is that (for client side) there is a row got inserted at the end (for an add)
		    // or when a record was modified it could be in the middle of the table
		    // that is probably not supposed to be there - due to filtering / sorting
		    // so we need to re process filtering and sorting
		    // BUT - if it is server side - then this should be handled by the server - so skip this step
		    if(oSettings.oFeatures.bServerSide === false){
		        var before = oSettings._iDisplayStart;
		        oSettings.oApi._fnReDraw(oSettings);
		        //iDisplayStart has been reset to zero - so lets change it back
		        oSettings._iDisplayStart = before;
		        oSettings.oApi._fnCalculateEnd(oSettings);
		    }
		      
		    //draw the 'current' page
		    oSettings.oApi._fnDraw(oSettings);
		};
		
		refresh();
		//定时器开始扫数据库，拿到库里面的所有信息，进行匹配，
		//timer=self.setInterval("timingDevice()",10000);	
		timer=setInterval( function () {
			ajaxList.datatable.fnStandingRedraw(); // user paging is not reset on reload
		}, 5000 ); 
	})
	
	var columns = [ 
		{"data" : "startDate"},	
		{"data" : "comCode"},
		{"data" : "totalnum"}, 
		{"data" : "handledRate"}, 
		{"data" : "execstarttime"},
		{"data" : "execendtime"}, 
		{"data" : "execelapsetime"}
	];
	
	function rowCallback(row, data, displayIndex, displayIndexFull) {
		//时间格式化 yyyy-MM-dd HH:mm:ss   -->    yyyy-MM-dd
		var startDate=data.startDate.split(" ")[0];
		var endDate=data.endDate.split(" ")[0];
		//进度条
		var process_html;
		if(data.handledRate=='100' && data.status=='0'){
			process_html='<div class="table_progress">'
				+'<span class="green" style="width:'+data.handledRate+'%;">'
				+'<label>'+data.handledRate+'%</label></span></div>';
		}else{
			process_html='<div class="table_progress">'
				+'<span class="orange" style="width:'+data.handledRate+'%;">'
				+'<label>'+data.handledRate+'%</label></span></div>';
		}
		$('td:eq(0)', row).html('<input type="hidden" name="id" value="'+data.id+'">'+
				startDate+" 至 "+endDate);
		$('td:eq(1)', row).html(data.comCode);
		$('td:eq(3)', row).html(process_html);
		$('td:eq(4)', row).html(data.execstarttime);
		$('td:eq(5)', row).html(data.execendtime==null?'':data.execendtime);
		$('td:eq(6)', row).html(data.execelapsetime==null?'':data.execelapsetime+"秒");
	}

	//执行
	function execute(){
		var comCode=$("#comCode").val();
		var threadNum=$("#threadNum").val(); 
		var groupNum=$("#groupNum").val();
		if( threadNum==null|| threadNum=="" || groupNum==null || groupNum=="" ){
			layer.msg('请填写线程数和分组量！',{
				time:2000
			});
			return;
		}
		
		$.ajax({
			type : 'POST',
			url : proPath+"/datadump/startExecute.ajax",
			dataType : 'json',
			data:{comCode:comCode,threadNum:threadNum,groupNum:groupNum}
		});
	}
	//停止执行
	function stop(){
		var comCode=$("#comCode").val();
		var threadNum=$("#threadNum").val(); 
		var groupNum=$("#groupNum").val();
		
		$.ajax({
			type : 'POST',
			url : proPath+"/datadump/stopExecute.ajax",
			dataType : 'json',
			data:{comCode:comCode,threadNum:threadNum,groupNum:groupNum}
		});
	}
	//刷新--查所有
	function refresh(){
		ajaxList.query();
	}
	</script>
</body>
</html>

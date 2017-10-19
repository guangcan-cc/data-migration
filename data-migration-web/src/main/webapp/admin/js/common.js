/** 框架JS文件，所有系统通用，仅在claimcar-main项目中修改* */



//处理键盘事件
function banBackSpace(e){
	 var target = e.target ;
     var tag = e.target.tagName.toUpperCase();
     if(e.keyCode == 8){
      if((tag == 'INPUT' && !$(target).attr("readonly"))||(tag == 'TEXTAREA' && !$(target).attr("readonly"))){
       if((target.type.toUpperCase() == "RADIO") || (target.type.toUpperCase() == "CHECKBOX")){
        return false ;
       }else{
        return true ; 
       }
      }else{
       return false ;
      }
     }
}

function reloadRow() {
	$(".select2").select2({

	});
}

function ValidForm(selector) {
	this.selector = selector;
	this.rules = {};
	this.checkSuccess = null;
}

ValidForm.prototype.bindForm = function() {
	var innerForm = $(this.selector);
	var innerFormValidRules = this.rules;
	var innerFormCheckSuccess = this.checkSuccess;

	var validDataOpt = {
		label : ".form_label",
		showAllError : true,// 所以校验一起执行
		ajaxPost : false,
		ignoreHidden : true,
		tiptype : function(msg, o, cssctl) {
			// msg：提示信息;
			// o:{obj:*,type:*,curform:*},
			// obj指向的是当前验证的表单元素（或表单对象），type指示提示的状态，值为1、2、3、4，
			// 1：正在检测/提交数据，2：通过验证，3：验证失败，4：提示ignore状态, curform为当前form对象;
			// cssctl:内置的提示信息样式控制函数，该函数需传入两个参数：显示提示信息的对象 和 当前提示的状态（既形参o中的type）;
			if (!o.obj.is("form")) {// 验证表单元素时o.obj为该表单元素，全部验证通过提交表单时o.obj为该表单对象;
				if (o.type == 3) {
					o.obj.attr("title", msg);
					$(o.obj).qtip({ // Grab some elements to apply the tooltip
						// to
						hide : false,
						overwrite : false,
						content : {
							text : msg
						},
						position : {
							my : 'top left', // Position my top left...
							at : 'bottom left', // at the bottom right of...
							target : $(o.obj)
						// my target
						},
						show : {
							event : false,
							ready : true,
							tips : false,
						}
					});
				} else {
					$(o.obj).qtip('destroy');
				}
			} else {// 全部验证通过
				layer.msg('ALL OK');
			}
		},
		beforeCheck : function(curform) {
		},
		beforeSubmit : function(curform) {
		},
		callback : function(curform) {//

			innerFormCheckSuccess();
			return false;
		}
	};
	var fromValid = innerForm.Validform(validDataOpt);
	if (innerFormValidRules != null)
		fromValid.addRule(innerFormValidRules);
};

function bindValidForm(form, checkSuccess) {
	var validFrom = new ValidForm(form);
	validFrom.checkSuccess = checkSuccess;
	validFrom.bindForm();
}

/**
 * 多个元素必填其中一个校验
 * 
 * @param fmElement
 *            form表单jquery对象 $(form)
 * @param name
 *            元素 name 属性 可以为多个
 * @returns {Boolean} 全为空 返回 false， 其中一个不为空 返回 true
 */
function notNullOne(fmElement) {
	var t = arguments.length;
	for (var i = 1; i < t; i++) {
		var val = $(fmElement).find("[name='" + arguments[i] + "']").val();
		if (val != null && val.trim() != '') {
			return true;
		}
	}
	return false;
}

/** liuping 如果文本超过指定长度，进行截取处理 */
function shortTxt(text, length) {
	if (text == null) {
		return null;
	}
	text = text.replace(/&nbsp;/g, " ");
	var showHtml = text;
	if (text.length > length + 1) {
		showHtml = "<span title='" + text + "'>" + text.substring(0, length)
				+ "...</span>";
	}
	return showHtml;
}

/** liuping 将一个对象禁用几秒* */
function disabledSec(obj, sec) {
	$(obj).attr({
		"disabled" : "true"
	});
	setTimeout(function() {
		$(obj).removeAttr("disabled");
	}, sec * 1000);
}

/** liuping判断元素是否为空* */
function isEmpty(field) {
	if (field == "undefined" || field.val() == "" || field.val() == null
			|| field.val() == "null" || field == "" || field == null
			|| field == "null") {
		return true;
	} else {
		return false;
	}
}
/** liuping判断字符串是否为空* */
function isBlank(value) {
	if (value == null || value == "undefined" || value == "" || value == "null") {
		return true;
	} else {
		return false;
	}
}
/** liuping关闭Layer层* */
function closeLayer(){
	var index = parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
}


/**
 * 时间戳格式化  YYYY-MM-DD
 * @param nS
 * @returns {String}
 */
function formatDate(nS) { 
	if(nS == "" || nS == undefined || nS == null) return "     ";
	var now = new Date(parseInt(nS));
    var year=now.getFullYear(); 
    var month=(now.getMonth()+1)>=10?(now.getMonth()+1):"0"+(now.getMonth()+1); 
    var date=now.getDate()>=10?(now.getDate()):"0"+(now.getDate());  
    return year+"-"+month+"-"+date; 
} 

/**
 * 时间戳格式化  YYYY-MM-DD  hh:mm
 * @param nS
 * @returns {String}
 */
function formatDateMinutes(nS) { 
	if(nS == "" || nS == undefined || nS == null) return "     ";
	var now = new Date(parseInt(nS));
    var year=now.getFullYear(); 
    var month=(now.getMonth()+1)>=10?(now.getMonth()+1):"0"+(now.getMonth()+1); 
    var date=now.getDate()>=10?(now.getDate()):"0"+(now.getDate());  
    var hour=now.getHours()>=10?(now.getHours()):"0"+(now.getHours());   
    var minute=now.getMinutes()>=10?(now.getMinutes()):"0"+(now.getMinutes()); 
    return year+"-"+month+"-"+date+" "+hour+":"+minute; 
} 


/**
 * 时间戳格式化  YYYY-MM-DD  hh:mm:ss
 * @param nS
 * @returns {String}
 */
function formatDateFull(nS) { 
	if(nS == "" || nS == undefined || nS == null) return "     ";
	var now = new Date(parseInt(nS));
    var year=now.getFullYear(); 
    var month=(now.getMonth()+1)>=10?(now.getMonth()+1):"0"+(now.getMonth()+1); 
    var date=now.getDate()>=10?(now.getDate()):"0"+(now.getDate());  
    var hour=now.getHours()>=10?(now.getHours()):"0"+(now.getHours());   
    var minute=now.getMinutes()>=10?(now.getMinutes()):"0"+(now.getMinutes()); 
    var second=now.getSeconds()>=10?(now.getSeconds()):"0"+(now.getSeconds());  
    return year+"-"+month+"-"+date+" "+hour+":"+minute+":"+second; 
}

function show_extractscript(){

	layer.open({
		type: 1,
		title: false,
		closeBtn: 0,
		shadeClose: true,
		content: '<div style="padding: 50px; line-height: 22px; background-color: #393D49; color: #fff; font-weight: 300;">' + extractscript + '</div>'
	});
}



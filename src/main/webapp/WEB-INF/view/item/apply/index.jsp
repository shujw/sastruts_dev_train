<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="imui" uri="http://www.intra-mart.co.jp/taglib/imui" %>
<%@ taglib prefix="imart" uri="http://www.intra-mart.co.jp/taglib/core/standard" %>
<%@ taglib prefix="workflow" uri="http://www.intra-mart.co.jp/taglib/imw/workflow" %>
<%@ taglib prefix="im" uri="http://www.intra-mart.co.jp/taglib/im-tenant" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@ taglib prefix="f" uri="http://sastruts.seasar.org/functions" %>
<%@ taglib prefix="s" uri="http://sastruts.seasar.org" %>

<imui:head>
<title>物品購入</title>
<workflow:workflowOpenPageCsjs />
<script src="ui/libs/jquery-validation-1.9.0/jquery.validate.js"></script>
<script type="text/javascript">
var rules = {
 	inputItemName : {
 		required  : true,
	    maxlength: 25
 	},
	inputItemPrice : {
		required : true,
		number   : true,
		max      : 99999
	},
  inputItemDate : {
		required : true,
		date 	   : true
	},
	inputItemReason : {
	    maxlength: 125
	}
};
var tempRules = {
 	inputItemName : {
     maxlength : 25
 	},
	inputItemPrice : {
		number   : true,
		max      : 99999
	},
  inputItemDate : {
		date 	 : true
	},
	inputItemReason : {
    maxlength: 125
	}
};
var messages = {
	inputItemName : {
		required  : '品名が未入力です',
		maxlength : '品名は25文字以下で入力して下さい'
 	},
	inputItemPrice : {
		required : '金額が未入力です',
		number   : '金額は半角数字で入力して下さい',
	  max      : '金額は99999以下で入力して下さい'
	},
  inputItemDate : {
		required : '納入日が未入力です',
		date	   : '納入日はyyyy/MM/ddで入力して下さい'
	},
	inputItemReason : {
		 maxlength : '購入理由は125文字以下で入力して下さい'
	}
};
$(function(){
  // 戻る
	$('#back').click(function(){
		$('#backForm').submit();
		return false;
    });
  // 申請
	$('#openPage0').click(function(){
		if (!imuiValidate('#itemForm', rules, messages)) return;
		//物品情報セット
		setItem();
		workflowOpenPage('0');
		return false;
    });
  // 一時保存
	$('#openPage1').click(function(){
		if (!imuiValidate('#itemForm', tempRules, messages)) return;
		//物品情報セット
		setItem();
		workflowOpenPage('1');
		return false;
    });
  // 再申請
	$('#openPage3').click(function(){
		if (!imuiValidate('#itemForm', rules, messages)) return;
		//物品情報セット
		setItem();
		workflowOpenPage('3');
		return false;
    });
	function setItem() {
		//物品情報セット
		$('#workflowForm').find('#itemName').val($('#itemForm').find('#inputItemName').val()); 
		$('#workflowForm').find('#itemPrice').val($('#itemForm').find('#inputItemPrice').val()); 
		$('#workflowForm').find('#itemDate').val($('#itemForm').find('#inputItemDate').val()); 
		$('#workflowForm').find('#itemReason').val($('#itemForm').find('#inputItemReason').val()); 
	}
});
</script>
</imui:head>
<workflow:workflowOpenPage name="workflowForm"
						   id="workflowForm"
		   				 method="POST"
						   target="_top"
						   imwUserDataId="${f:h(imwUserDataId)}"
						   imwSystemMatterId="${f:h(imwSystemMatterId)}"
						   imwAuthUserCode="${f:h(imwAuthUserCode)}"
						   imwApplyBaseDate="${f:h(imwApplyBaseDate)}"
						   imwNodeId="${f:h(imwNodeId)}"
						   imwFlowId="${f:h(imwFlowId)}"
						   imwSerialProcParams="${f:h(imwSerialProcParams)}"
						   imwCallOriginalParams="${f:h(imwCallOriginalParams)}"
						   imwNextScriptPath="${f:h(imwCallOriginalPagePath)}">
	<INPUT type="hidden" name="itemName" id="itemName" value=""/>
	<INPUT type="hidden" name="itemPrice" id="itemPrice" value=""/>
	<INPUT type="hidden" name="itemDate" id="itemDate" value=""/>
	<INPUT type="hidden" name="itemReason" id="itemReason" value=""/>
</workflow:workflowOpenPage>

	<!-- タイトルバー -->
	<div class="imui-title-small-window">
		<h1>物品購入</h1>
	</div>
	<!-- ツールバー -->
	<div class="imui-toolbar-wrap">
	 	<div class="imui-toolbar-inner">
			<ul class="imui-list-toolbar">
				<li>
					<a href="#" id="back">
						<span class="im-ui-icon-common-16-back"></span>
					</a>
				</li>
			</ul>
		</div>
	</div>

	<div class="imui-form-container">
		<header class="imui-chapter-title">
			<h2>物品購入</h2>
		</header>

		<s:form styleId="itemForm" enctype="multipart/form-data">
			<table class="imui-form">
				<tbody>
					<tr>
						<th><label class="imui-required">品名</label></th>
						<td><imui:textbox value='${f:h(applyForm.itemName)}' id="inputItemName" name="inputItemName" size="50"/></td>
					</tr>
					<tr>
						<th><label class="imui-required">金額</label></th>
						<td><imui:textbox value='${f:h(applyForm.itemPrice)}' id="inputItemPrice" name="inputItemPrice" size="50"/></td>
					</tr>
					<tr>
						<th><label class="imui-required">納入日</label></th>
						<td>
							<imui:textbox value='${f:h(applyForm.itemDate)}' id="inputItemDate" name="inputItemDate" size="50"/>
							<im:calendar floatable="true" altField="#inputItemDate" />
						</td>
					</tr>
					<tr>
						<th><label>購入理由</label></th>
						<td>
							<imui:textArea value='${f:h(applyForm.itemReason)}' id="inputItemReason" name="inputItemReason" rows="3" cols="40"/>
						</td>
					</tr>
				</tbody>
			</table>
			<div class="imui-operation-parts">
				<imart:decision case="0" value="${requestScope.imwPageType}">
					<imui:button value="申請" id="openPage0" name="openPage0" class="imui-large-button" escapeXml="true" escapeJs="false" />
					<imui:button value="一時保存" id="openPage1" name="openPage1" class="imui-large-button" escapeXml="true" escapeJs="false" />
				</imart:decision>
				<imart:decision case="1" value="${requestScope.imwPageType}">
					<imui:button value="申請" id="openPage0" name="openPage0" class="imui-large-button" escapeXml="true" escapeJs="false" />
					<imui:button value="一時保存" id="openPage1" name="openPage1" class="imui-large-button" escapeXml="true" escapeJs="false" />
				</imart:decision>
				<imart:decision case="2" value="${requestScope.imwPageType}">
					<imui:button value="申請" id="openPage2" name="openPage2" class="imui-large-button" escapeXml="true" escapeJs="false" />
				</imart:decision>
				<imart:decision case="3" value="${requestScope.imwPageType}">
					<imui:button value="再申請" id="openPage3" name="openPage3" class="imui-large-button" escapeXml="true" escapeJs="false" />
				</imart:decision>
			</div>
		</s:form>
	</div>

<!-- 戻るボタンが押下された場合に利用されるformです -->
<form name="backForm" id="backForm" method="POST" action='${f:h(imwCallOriginalPagePath)}'>
    <input type="hidden" name=imwCallOriginalParams value='${f:h(imwCallOriginalParams)}' />
</form>

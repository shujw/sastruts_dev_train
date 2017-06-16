<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="imui" uri="http://www.intra-mart.co.jp/taglib/imui" %>
<%@ taglib prefix="imart" uri="http://www.intra-mart.co.jp/taglib/core/standard" %>
<%@ taglib prefix="workflow" uri="http://www.intra-mart.co.jp/taglib/imw/workflow" %>
<%@ taglib prefix="im" uri="http://www.intra-mart.co.jp/taglib/im-tenant" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@ taglib prefix="f" uri="http://sastruts.seasar.org/functions" %>
<%@ taglib prefix="s" uri="http://sastruts.seasar.org" %>

<imui:head>
<title>旅費申請 - JavaEE開発モデル -</title>
<workflow:workflowOpenPageCsjs />
<script src="ui/libs/jquery-validation-1.9.0/jquery.validate.js"></script>
<script type="text/javascript">
var rules = {
  inputStartDate : {
		required : true,
		date 	 : true
	},
	inputArrivalDate : {
		required : true,
		date 	 : true
	},
	inputDestination : {
		required : true,
    maxlength: 50
	},
	inputReason : {
		required : true,
    maxlength: 50
	},
	inputTExpenses : {
		required : true,
		number   : true,
		max      : 99999
	},
	inputLExpenses : {
		required : true,
		number   : true,
		max      : 99999
	}
};
var tempRules = {
  inputStartDate : {
		date 	 : true
	},
	inputArrivalDate : {
		date 	 : true
	},
	inputDestination : {
    maxlength: 50
	},
	inputReason : {
    maxlength: 50
	},
	inputTExpenses : {
		number   : true,
		max      : 99999
	},
	inputLExpenses : {
		number   : true,
		max      : 99999
	}
};
var messages = {
  inputStartDate : {
		required : '出発日が未入力です',
		date	 : '出発日はyyyy/MM/ddで入力して下さい'
	},
	inputArrivalDate : {
		required : '到着日が未入力です',
		date	 : '到着日はyyyy/MM/ddで入力して下さい'
	},
	inputDestination : {
		required : '出張先が未入力です',
	  maxlength : '出張先は50文字以下で入力して下さい'
	},
	inputReason : {
		required : '出張理由が未入力です',
	 maxlength : '出張理由は50文字以下で入力して下さい'
	},
	inputTExpenses : {
		required : '交通費が未入力です',
		number   : '交通費は半角数字で入力して下さい',
	  max      : '交通費は99999以下で入力して下さい'
	},
	inputLExpenses : {
		required : '宿泊費が未入力です',
		number   : '宿泊費は半角数字で入力して下さい',
	  max      : '宿泊費は99999以下で入力して下さい'
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
		if (!imuiValidate('#tripForm', rules, messages)) return;
		//旅費情報セット
		setTrip();
		workflowOpenPage('0');
		return false;
    });
  // 一時保存
	$('#openPage1').click(function(){
		if (!imuiValidate('#tripForm', tempRules, messages)) return;
		//旅費情報セット
		setTrip();
		workflowOpenPage('1');
		return false;
    });
  // 再申請
	$('#openPage3').click(function(){
		if (!imuiValidate('#tripForm', rules, messages)) return;
		//旅費情報セット
		setTrip();
		workflowOpenPage('3');
		return false;
    });
	function setTrip() {
		//旅費情報セット
		$('#workflowForm').find('#startDate').val($('#tripForm').find('#inputStartDate').val()); 
		$('#workflowForm').find('#arrivalDate').val($('#tripForm').find('#inputArrivalDate').val()); 
		$('#workflowForm').find('#destination').val($('#tripForm').find('#inputDestination').val()); 
		$('#workflowForm').find('#reason').val($('#tripForm').find('#inputReason').val()); 
		$('#workflowForm').find('#tExpenses').val($('#tripForm').find('#inputTExpenses').val()); 
		$('#workflowForm').find('#lExpenses').val($('#tripForm').find('#inputLExpenses').val());
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
	<INPUT type="hidden" name="startDate" id="startDate" value=""/>
	<INPUT type="hidden" name="arrivalDate" id="arrivalDate" value=""/>
	<INPUT type="hidden" name="destination" id="destination" value=""/>
	<INPUT type="hidden" name="reason" id="reason" value=""/>
	<INPUT type="hidden" name="tExpenses" id="tExpenses" value=""/>
	<INPUT type="hidden" name="lExpenses" id="lExpenses" value=""/>
</workflow:workflowOpenPage>

	<!-- タイトルバー -->
	<div class="imui-title-small-window">
		<h1>旅費申請 - JavaEE開発モデル -</h1>
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
			<h2>旅費申請 - JavaEE開発モデル -</h2>
		</header>

		<s:form styleId="tripForm" enctype="multipart/form-data">
			<table class="imui-form">
				<tbody>
					<tr>
						<th><label class="imui-required">出発日</label></th>
						<td>
							<imui:textbox value='${f:h(applyForm.startDate)}' id="inputStartDate" name="inputStartDate" size="50"/>
							<im:calendar floatable="true" altField="#inputStartDate" />
						</td>
					</tr>
					<tr>
						<th><label class="imui-required">到着日</label></th>
						<td>
							<imui:textbox value='${f:h(applyForm.arrivalDate)}' id="inputArrivalDate" name="inputArrivalDate" size="50"/>
							<im:calendar floatable="true" altField="#inputArrivalDate" />
						</td>
					</tr>
					<tr>
						<th><label class="imui-required">出張先</label></th>
						<td><imui:textbox value='${f:h(applyForm.destination)}' id="inputDestination" name="inputDestination" size="50"/></td>
					</tr>
					<tr>
						<th><label class="imui-required">出張理由</label></th>
						<td>
							<imui:textArea value='${f:h(applyForm.reason)}' id="inputReason" name="inputReason" rows="3" cols="40"/>
						</td>
					</tr>
					<tr>
						<th><label class="imui-required">交通費</label></th>
						<td><imui:textbox value='${f:h(applyForm.tExpenses)}' id="inputTExpenses" name="inputTExpenses" size="50"/></td>
					</tr>
					<tr>
						<th><label class="imui-required">宿泊費</label></th>
						<td><imui:textbox value='${f:h(applyForm.lExpenses)}' id="inputLExpenses" name="inputLExpenses" size="50"/></td>
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

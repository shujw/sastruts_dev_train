<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="imui" uri="http://www.intra-mart.co.jp/taglib/imui" %>
<%@ taglib prefix="imart" uri="http://www.intra-mart.co.jp/taglib/core/standard" %>
<%@ taglib prefix="im" uri="http://www.intra-mart.co.jp/taglib/im-tenant" %>
<%@ taglib prefix="f" uri="http://sastruts.seasar.org/functions" %>
<%@ taglib prefix="s" uri="http://sastruts.seasar.org" %>

<imui:head>
<title>旅費申請 - JavaEE開発モデル -</title>

<script src="ui/libs/jquery-validation-1.9.0/jquery.validate.js"></script>
<script type="text/javascript">
var rules = {
	mattername : {
		required : true
	},
	startDate : {
		required : true,
		date 	 : true
	},
	arrivalDate : {
		required : true,
		date 	 : true
	},
	destination : {
		required : true,
		maxlength: 50
	},
	reason : {
		required : true,
		maxlength: 50
	},
	tExpenses : {
		required : true,
		number   : true,
		max      : 99999
	},
	lExpenses : {
		required : true,
		number   : true,
		max      : 99999
	}
};
var messages = {
	mattername : {
		required : '案件名が未入力です'
	},
	startDate : {
		required : '出発日が未入力です',
		date	 : '出発日はyyyy/MM/ddで入力して下さい'
	},
	arrivalDate : {
		required : '到着日が未入力です',
		date	 : '到着日はyyyy/MM/ddで入力して下さい'
	},
	destination : {
		required : '出張先が未入力です',
    maxlength : '出張先は50文字以下で入力して下さい'
	},
	reason : {
		required : '出張理由が未入力です',
	  maxlength : '出張理由は50文字以下で入力して下さい'
	},
	tExpenses : {
		required : '交通費が未入力です',
		number   : '交通費は半角数字で入力して下さい',
	  max      : '交通費は99999以下で入力して下さい'
	},
	lExpenses : {
		required : '宿泊費が未入力です',
		number   : '宿泊費は半角数字で入力して下さい',
		max      : '宿泊費は99999以下で入力して下さい'
	}
};
jQuery(function(){
	$('#back').click(function(){
		$('#backForm').submit();
		return false;
    });
	$('#apply').click(function(){
		if (!imuiValidate('#tripForm', rules, messages)) return;
		$('#tripForm').submit();
		return false;
    });
});
</script>
</imui:head>
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

		<s:form styleId="tripForm" action='${f:h(apiApplyForm.path)}' method="POST" enctype="multipart/form-data">
			<table class="imui-form">
				<tbody>
					<tr>
						<th><label class="imui-required">案件名</label></th>
						<td><imui:textbox value='${f:h(apiApplyForm.mattername)}' id="mattername" name="mattername" size="50"/></td>
					</tr>
					<tr>
						<th><label class="imui-required">出発日</label></th>
						<td>
							<imui:textbox value='${f:h(apiApplyForm.startDate)}' id="startDate" name="startDate" size="50"/>
							<im:calendar floatable="true" altField="#startDate" />
						</td>
					</tr>
					<tr>
						<th><label class="imui-required">到着日</label></th>
						<td>
							<imui:textbox value='${f:h(apiApplyForm.arrivalDate)}' id="arrivalDate" name="arrivalDate" size="50"/>
							<im:calendar floatable="true" altField="#arrivalDate" />
						</td>
					</tr>
					<tr>
						<th><label class="imui-required">出張先</label></th>
						<td><imui:textbox value='${f:h(apiApplyForm.destination)}' id="destination" name="destination" size="50"/></td>
					</tr>
					<tr>
						<th><label class="imui-required">出張理由</label></th>
						<td>
							<imui:textArea value='${f:h(apiApplyForm.reason)}' id="reason" name="reason" rows="3" cols="40"/>
						</td>
					</tr>
					<tr>
						<th><label class="imui-required">交通費</label></th>
						<td><imui:textbox value='${f:h(apiApplyForm.tExpenses)}' id="tExpenses" name="tExpenses" size="50"/></td>
					</tr>
					<tr>
						<th><label class="imui-required">宿泊費</label></th>
						<td><imui:textbox value='${f:h(apiApplyForm.lExpenses)}' id="lExpenses" name="lExpenses" size="50"/></td>
					</tr>
					<tr>
						<th><label>コメント</label></th>
						<td>
							<imui:textArea value='${f:h(apiApplyForm.comment)}' id="comment" name="comment" rows="3" cols="40"/>
						</td>
					</tr>
				</tbody>
			</table>
			<div class="imui-operation-parts">
				<imart:decision case="0" value="${f:h(requestScope.imwPageType)}">
					<imui:button value="申請" id="apply" name="apply" class="imui-large-button" escapeXml="true" escapeJs="false" />
				</imart:decision>
			</div>
			<INPUT type="hidden" name="imwFlowId" value="${f:h(apiApplyForm.imwFlowId)}"/>
			<INPUT type="hidden" name="imwApplyBaseDate" value="${f:h(apiApplyForm.imwApplyBaseDate)}"/>
			<INPUT type="hidden" name="imwUserCode" value="${f:h(apiApplyForm.imwUserCode)}"/>
			<INPUT type="hidden" name="imwUserDataId" value="${f:h(apiApplyForm.imwUserDataId)}"/>
			<INPUT type="hidden" name="imwPageType" value="${f:h(apiApplyForm.imwPageType)}"/>
		</s:form>
	</div>

<!-- 戻るボタンが押下された場合に利用されるformです -->
<form name="backForm" id="backForm" method="POST" action='${f:h(imwCallOriginalPagePath)}'>
    <input type="hidden" name=imwCallOriginalParams value='${f:h(imwCallOriginalParams)}' />
</form>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="imui" uri="http://www.intra-mart.co.jp/taglib/imui" %>
<%@ taglib prefix="workflow" uri="http://www.intra-mart.co.jp/taglib/imw/workflow" %>
<%@ taglib prefix="f" uri="http://sastruts.seasar.org/functions" %>
<%@ taglib prefix="s" uri="http://sastruts.seasar.org" %>

<imui:head>
<title>旅費申請 - JavaEE開発モデル -</title>

<workflow:workflowOpenPageCsjs />
<script type="text/javascript">
jQuery(function(){
	$('#back').click(function(){
		$('#backForm').submit();
		return false;
    });
	$('#openPage').click(function(){
		workflowOpenPage('4');
		return false;
    });
});
</script>
</imui:head>
<workflow:workflowOpenPage name="workflowForm"
		   				 method="POST"
						   target="_top"
						   imwSystemMatterId="${f:h(imwSystemMatterId)}"
						   imwNodeId="${f:h(imwNodeId)}"
						   imwSerialProcParams="${f:h(imwSerialProcParams)}"
						   imwCallOriginalParams="${f:h(imwCallOriginalParams)}"
						   imwNextScriptPath="${f:h(imwCallOriginalPagePath)}">
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

		<s:form enctype="multipart/form-data">
			<table class="imui-form">
				<tbody>
					<tr>
						<th><label>出発日</label></th>
						<td><p>${f:h(approveForm.startDate)}</p></td>
					</tr>
					<tr>
						<th><label>到着日</label></th>
						<td><p>${f:h(approveForm.arrivalDate)}</p></td>
					</tr>
					<tr>
						<th><label>出張先</label></th>
						<td><p>${f:h(approveForm.destination)}</p></td>
					</tr>
					<tr>
						<th><label>出張理由</label></th>
						<td>
							<imui:textArea value='${f:h(approveForm.reason)}' id="reason" name="reason" rows="3" cols="40" readonly="true"/>
						</td>
					</tr>
					<tr>
						<th><label>交通費</label></th>
						<td><p>${f:h(approveForm.tExpenses)}</p></td>
					</tr>
					<tr>
						<th><label>宿泊費</label></th>
						<td><p>${f:h(approveForm.lExpenses)}</p></td>
					</tr>
				</tbody>
			</table>
			<div class="imui-operation-parts">
				<imui:button value="処理" id="openPage" name="openPage" class="imui-large-button" escapeXml="true" escapeJs="false" />
			</div>
		</s:form>
	</div>

<!-- 戻るボタンが押下された場合に利用されるformです -->
<form name="backForm" id="backForm" method="POST" action='${f:h(imwCallOriginalPagePath)}'>
    <input type="hidden" name=imwCallOriginalParams value='${f:h(imwCallOriginalParams)}' />
</form>

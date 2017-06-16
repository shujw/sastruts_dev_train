<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="imui" uri="http://www.intra-mart.co.jp/taglib/imui" %>
<%@ taglib prefix="workflow" uri="http://www.intra-mart.co.jp/taglib/imw/workflow" %>
<%@ taglib prefix="f" uri="http://sastruts.seasar.org/functions" %>

<imui:head>
<title>旅費申請 - JavaEE開発モデル -</title>

<workflow:workflowOpenPageCsjs />
<script type="text/javascript">
jQuery(function(){
	$('#close').click(function(){
		window.close();
		return false;
    });
});
</script>
</imui:head>

<body style="min-width:0px">
	<!-- タイトルバー -->
	<div class="imui-title-small-window">
		<h1>旅費申請 - JavaEE開発モデル -</h1>
	</div>
	<!-- ツールバー -->
	<div class="imui-toolbar-wrap">
	 	<div class="imui-toolbar-inner">
				<ul class="imui-list-toolbar">
					<li class="icon-show">
						<workflow:workflowMatterDetailLink systemMatterId='${f:h(detailForm.imwSystemMatterId)}'>
							<span style="margin-left:16px;">詳細</span>
						</workflow:workflowMatterDetailLink>
					</li>
				</ul>
				<ul class="imui-list-toolbar-utility">
					<li>
						<a href="javascript:void(0);" id="close">
							<span class="im-ui-icon-common-16-close mr-5"></span>
						</a>
					</li>
				</ul>
		</div>
	</div>

	<div class="imui-form-container">
		<header class="imui-chapter-title">
			<h2>旅費申請 - JavaEE開発モデル -</h2>
		</header>

		<table class="imui-form">
			<tbody>
				<tr>
					<th><label>出発日</label></th>
					<td><p>${f:h(detailForm.startDate)}</p></td>
				</tr>
				<tr>
					<th><label>到着日</label></th>
					<td><p>${f:h(detailForm.arrivalDate)}</p></td>
				</tr>
				<tr>
					<th><label>出張先</label></th>
					<td><p>${f:h(detailForm.destination)}</p></td>
				</tr>
				<tr>
					<th><label>出張理由</label></th>
					<td>
						<imui:textArea value='${f:h(detailForm.reason)}' id="reason" name="reason" rows="3" cols="40" readonly="true"/>
					</td>
				</tr>
				<tr>
					<th><label>交通費</label></th>
					<td><p>${f:h(detailForm.tExpenses)}</p></td>
				</tr>
				<tr>
					<th><label>宿泊費</label></th>
					<td><p>${f:h(detailForm.lExpenses)}</p></td>
				</tr>
			</tbody>
		</table>
	</div>
</body>

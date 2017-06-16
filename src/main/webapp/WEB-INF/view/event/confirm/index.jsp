<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="imui" uri="http://www.intra-mart.co.jp/taglib/imui" %>
<%@ taglib prefix="imart" uri="http://www.intra-mart.co.jp/taglib/core/standard" %>
<%@ taglib prefix="im" uri="http://www.intra-mart.co.jp/taglib/im-tenant" %>
<%@ taglib prefix="im-master" uri="http://www.intra-mart.co.jp/taglib/im-master" %>
<%@ taglib prefix="f" uri="http://sastruts.seasar.org/functions" %>
<%@ taglib prefix="s" uri="http://sastruts.seasar.org" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<imui:head>
<title>イベント情報参照　- JavaEE開発モデル -</title>

<script src="ui/libs/jquery-validation-1.9.0/jquery.validate.js"></script>
<im-master:imACMSearch />
<script type="text/javascript">
// 入力検証ルールのオブジェクト
jQuery(function(){
	$('#back').click(function(){
		$('#backForm').submit();
		return false;
    });
});

</script>
</imui:head>
	<!-- タイトルバー -->
	<div class="imui-title-small-window">
		<h1>イベント情報参照  - JavaEE開発モデル -</h1>
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
			<h2>イベント情報詳細</h2>
		</header>
		<font color="#FF0000"><b><html:errors /></b></font>
		<s:form styleId="eventForm" action="" method="POST">
			<table class="imui-form">
				<tbody>
					<tr>
						<th><label>イベント名</label></th>
						<td><p>${f:h(eventForm.eventName)}</p></td>
					</tr>
					<tr>
						<th><label>イベント内容</label></th>
						<td>
							<imui:textArea value='${f:h(eventForm.eventDetail)}' id="eventDetail" name="eventDetail" rows="3" cols="40" readonly="true"/>
						</td>
					</tr>
					<tr>
						<th><label>開催日</label></th>
						<td><p>${f:h(eventForm.eventDate)}</p></td>
					</tr>
					<tr>
						<th><label>参加者</label></th>
						<td><p>${f:h(eventForm.entryUser)}</p></td>
					</tr>
				</tbody>
			</table>
		</s:form>
	</div>

<!-- 戻るボタンが押下された場合に利用されるformです -->
<form name="backForm" id="backForm" method="POST" action="home">
</form>


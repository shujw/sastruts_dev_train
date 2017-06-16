<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="imui" uri="http://www.intra-mart.co.jp/taglib/imui"%>
<%--
    SAStruts IM-Workflow APIによる申請結果画面
    @author NTTデータ イントラマート
--%>
<imui:head>
	<title>旅費申請 - JavaEE開発モデル -</title>

	<script type="text/javascript">
    jQuery(function() {
      $('#back').click(function() {
        $('#backForm').submit();
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
			<li><a href="#" id="back"> <span
					class="im-ui-icon-common-16-back"></span>申請一覧
			</a></li>
		</ul>
	</div>
</div>
<div class="imui-form-container">
	<header class="imui-chapter-title">
		<h2>旅費申請 - JavaEE開発モデル -</h2>
	</header>
	申請に成功しました。
</div>
<!-- 戻るボタンが押下された場合に利用されるformです -->
<form name="backForm" id="backForm" method="POST"
	action="im_workflow/user/apply/apply_list"></form>


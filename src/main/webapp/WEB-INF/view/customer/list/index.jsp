<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="imui" uri="http://www.intra-mart.co.jp/taglib/imui" %>
<%@ taglib prefix="im" uri="http://www.intra-mart.co.jp/taglib/im-tenant" %>
<%@ taglib prefix="f" uri="http://sastruts.seasar.org/functions" %>
<%@ taglib prefix="s" uri="http://sastruts.seasar.org" %>

<imui:head>
<title>顧客マスタメンテナンス - JavaEE開発モデル -</title>

<script src="ui/libs/jquery-validation-1.9.0/jquery.validate.js"></script>
<script type="text/javascript">
jQuery(function(){
	$('#create').click(function(){
		$('#editForm').submit();
		return false;
    });
});
function updateFormSubmit(customerCd) {
  $('#editForm').attr("action","customer/edit/search/" + customerCd );
	$('#editForm').submit();
	return false;
};

</script>
</imui:head>
	<!-- タイトルバー -->
	<div class="imui-title-small-window">
		<h1>顧客情報一覧</h1>
	</div>
	<!-- ツールバー -->
	<div class="imui-toolbar-wrap">
	 	<div class="imui-toolbar-inner">
			<ul class="imui-list-toolbar">
				<li>
					<a href="#" id="create">
						<span class="im-ui-icon-common-16-new mr-5"></span>新規登録
					</a>
				</li>
			</ul>
		</div>
	</div>

	<div class="imui-form-container">
		<header class="imui-chapter-title">
			<h2>顧客情報</h2>
		</header>

		<s:form styleId="customerListForm" method="POST" enctype="multipart/form-data">
	    <imui:listTable id="searchList" process="java" target="jp.co.tutorial.processor.CustomerListTableProcessor" viewRecords="true" page="${f:h(customerListForm.page)}" sortName="${f:h(customerListForm.sortName)}" sortOrder="${f:h(customerListForm.sortOrder)}" autoEncode="true" height="100%">
	      <pager rowNum="${f:h(customerListForm.rowNum)}" rowList="${f:h(customerListForm.pagerRowList)}" />
	      <cols>
	        <col name="edit" caption="編集" sortable="false" width="40" align="center">
	          <callFunction name="updateFormSubmit" />
	        </col>
	        <col name="customerCd" caption="顧客コード" key="true" />
	        <col name="customerName" caption="顧客名" />
	        <col name="chargeStf" caption="担当者" sortable="false" />
	      </cols>
	    </imui:listTable>
		</s:form>
	</div>

<!-- 新規登録ボタンが押下された場合に利用されるformです -->
<form name="editForm" id="editForm" method="POST" action="${f:h(customerListForm.path)}">
	<INPUT type="hidden" id="path" name="path" value=""/>
</form>

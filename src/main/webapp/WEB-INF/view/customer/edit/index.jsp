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
<title>顧客マスタメンテナンス- JavaEE開発モデル -</title>

<script src="ui/libs/jquery-validation-1.9.0/jquery.validate.js"></script>
<im-master:imACMSearch />
<script type="text/javascript">
jQuery(function(){
  // 性別の初期値セット
  $('input[name="customerSex"]').eq($('#hidCustomerSex').val()).attr("checked", true);
  //入力検証ルールのオブジェクト
  var rules = {
	  customerCd : {
      required     : true,
      alphanumeric : true,
      maxlength    : 20
    },
    customerName : {
      maxlength : 25
    },
    customerTelno : {
      number    : true,
      maxlength : 20
    },
    customerAddress : {
      maxlength: 50
    },
    customerSex : {
      required : true
    },
    customerBirthday : {
      date : true
    },
    chargeStfCd : {
      maxlength : 50
    }
};
// エラーメッセージのセット
	var messages = {
	  customerCd : {
      required     : '顧客コードが未入力です',
      alphanumeric : '顧客コードは半角英数字で入力してください',
      maxlength    : '顧客コードは20文字以下で入力してください'
    },
    customerName : {
      maxlength    : '顧客名は25文字以下で入力してください'
    },
    customerTelno : {
      number       : '電話番号は半角数字で入力して下さい',
      maxlength    : '電話番号は20文字以下で入力してください'
    },
    customerAddress : {
      maxlength    : '住所は50文字以下で入力してください'
    },
    customerSex : {
      required     : '性別が未入力です'
    },
    customerBirthday : {
      date         : '生年月日は(yyyy/MM/dd)の形式で入力してください。'
    },
    chargeStfCd : {
      maxlength    : '担当者は50文字以下で入力して下さい'
    }
};
	$('#back').click(function(){
		$('#backForm').submit();
		return false;
    });
	$('#create').click(function(){
		if (!imuiValidate('#customerForm', rules, messages)) return;
		$('#customerForm').find('#editType').val("0"); 
		$('#customerForm').submit();
		return false;
    });
	$('#update').click(function(){
		if (!imuiValidate('#customerForm', rules, messages)) return;
		$('#customerForm').find('#editType').val("1"); 
		$('#customerForm').submit();
		return false;
    });
	$('#delete').click(function(){
		$('#customerForm').find('#editType').val("2"); 
		$('#customerForm').submit();
		return false;
    });
	$('#attachmentFile').click(function(){
	  if ($('#uploadedFileName').val() != null && $('#uploadedFileName').val() != '') {
		  return confirm('アップロード済みファイルが削除されます。よろしいですか？');
	  }
    });
	$('#selectStaff').click(function(){
    var parameter = {
      tabs : [
          {
            id    : "jp.co.intra_mart.master.app.search.tabs.user.list_user",
            title : "キーワード"
          }
      ],
      prop : {
          'jp.co.intra_mart.master.app.search.tabs.user.list_user' : ['user_cd', 'user_name']
      },
      callback_function           : 'setUser',
      basic_area                  : 'jp.co.intra_mart.master.app.search.headers.readonly',
      wnd_title                   : "ユーザ検索",
      message                     : "ユーザ検索",
      wnd_close                   : true,
      type                        : 'single',
      deleted_data                : false,
      target_locale               : 'ja',
      additional_disp             : true,
      additional_user_search_name : true,
      additional_dept             : true
    };
    
    // 検索画面を開く
    imACMSearch.open(parameter);
  });
	
	
});
function setUser(object){
  if(object.length != 1){
      return;
  }
  var user = object[0].data;
  $('#chargeStf').val(user.user_name);
  $('#chargeStfCd').val(user.user_cd);
  return;
}

</script>
</imui:head>
	<!-- タイトルバー -->
	<div class="imui-title-small-window">
		<h1>顧客マスタメンテナンス  - JavaEE開発モデル -</h1>
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
			<h2>顧客情報詳細</h2>
		</header>
		<font color="#FF0000"><b><html:errors /></b></font>
		<s:form styleId="customerForm" action='${f:h(customerForm.path)}' method="POST" enctype="multipart/form-data">
			<table class="imui-form">
				<tbody>
					<tr>
						<imart:decision case="0" value="${f:h(customerForm.editType)}">
							<th><label class="imui-required">顧客コード</label></th>
							<td><imui:textbox value='${f:h(customerForm.customerCd)}' id="customerCd" name="customerCd" size="50"/></td>
						</imart:decision>
						<imart:decision case="1" value="${f:h(customerForm.editType)}">
							<th><label>顧客コード</label></th>
							<td><p>${f:h(customerForm.customerCd)}</p></td>
						</imart:decision>
					</tr>
					<tr>
						<th><label>顧客名</label></th>
						<td><imui:textbox value='${f:h(customerForm.customerName)}' id="customerName" name="customerName" size="50"/></td>
					</tr>
					<tr>
						<th><label>電話番号</label></th>
						<td><imui:textbox value='${f:h(customerForm.customerTelno)}' id="customerTelno" name="customerTelno" size="50"/></td>
					</tr>
					<tr>
						<th><label>住所</label></th>
						<td>
							<imui:textArea value='${f:h(customerForm.customerAddress)}' id="customerAddress" name="customerAddress" rows="3" cols="40"/>
						</td>
					</tr>
					<tr>
						<th><label>性別</label></th>
						<td>
							<imui:radio id="customerSex" name="customerSex" label="男" value="0"/>
							<imui:radio id="customerSex" name="customerSex" label="女" value="1"/>
							<INPUT type="hidden" id="hidCustomerSex" name="hidCustomerSex" value='${f:h(customerForm.customerSex)}' />
						</td>
					</tr>
					<tr>
						<th><label>生年月日</label></th>
						<td>
							<imui:textbox value='${f:h(customerForm.customerBirthday)}' id="customerBirthday" name="customerBirthday" size="50"/>
							<im:calendar floatable="true" altField="#customerBirthday"  format='${f:h(customerForm.format)}' changeYear="true" changeMonth="true"/>
						</td>
					</tr>
					<tr>
						<th><label>参考資料</label></th>
							<td>
						    <c:if test="${customerForm.uploadedFileName != null}">
                  <s:link href="download/${customerForm.customerCd}/">
                      ${f:h(customerForm.uploadedFileName)}
                  </s:link>
                </c:if>
                <input type="file" id="attachmentFile" name="attachmentFile" value='${f:h(customerForm.attachmentFile)}' />
						</td>
					</tr>
					<tr>
						<th><label>担当者</label></th>
						<td>
							<imui:textbox value='${f:h(customerForm.chargeStf)}' id="chargeStf" name="chargeStf" size="50" readonly />
							<imui:button value="検索" id="selectStaff" name="selectStaff" class="imui-small-button" />
							</td>
					</tr>
				</tbody>
			</table>
			<div class="imui-operation-parts">
				<imart:decision case="0" value="${f:h(customerForm.editType)}">
					<imui:button value="登録" id="create" name="create" class="imui-large-button" escapeXml="true" escapeJs="false" />
				</imart:decision>
				<imart:decision case="1" value="${f:h(customerForm.editType)}">
					<imui:button value="更新" id="update" name="update" class="imui-large-button" escapeXml="true" escapeJs="false" />
					<imui:button value="削除" id="delete" name="delete" class="imui-large-button" escapeXml="true" escapeJs="false" />
				</imart:decision>
			</div>
			<INPUT type="hidden" id="editType" name="editType" value=""/>
			<INPUT type="hidden" id="customerCd" name="customerCd" value="${f:h(customerForm.customerCd)}"/>
			<INPUT type="hidden" name="path" value="${f:h(customerForm.path)}"/>
			<INPUT type="hidden" id="chargeStfCd" name="chargeStfCd" value="${f:h(customerForm.chargeStfCd)}"/>
			<INPUT type="hidden" id="uploadedFileName" name="uploadedFileName" value="${f:h(customerForm.uploadedFileName)}"/>
		</s:form>
	</div>

<!-- 戻るボタンが押下された場合に利用されるformです -->
<form name="backForm" id="backForm" method="POST" action="customer/list">
</form>


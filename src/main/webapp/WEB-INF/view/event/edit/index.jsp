<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="imui" uri="http://www.intra-mart.co.jp/taglib/imui" %>
<%@ taglib prefix="im" uri="http://www.intra-mart.co.jp/taglib/im-tenant" %>
<%@ taglib prefix="im-master" uri="http://www.intra-mart.co.jp/taglib/im-master" %>
<%@ taglib prefix="f" uri="http://sastruts.seasar.org/functions" %>
<%@ taglib prefix="s" uri="http://sastruts.seasar.org" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>

<imui:head>
<title>イベント情報登録　- JavaEE開発モデル -</title>

<script src="ui/libs/jquery-validation-1.9.0/jquery.validate.js"></script>
<im-master:imACMSearch />
<script type="text/javascript">
// 入力検証ルールのオブジェクト
jQuery(function(){
  var rules = {
    eventName : {
      required     : true,
      maxlength    : 25
    },
    eventDetail : {
      maxlength: 50
    },
    eventDate : {
      date : true
    },
    entryUserName : {
      maxlength : 50
    }
};
// エラーメッセージのセット
	var messages = {
	  eventName : {
      required     : 'イベント名が未入力です',
      maxlength    : 'イベント名は25文字以下で入力してください'
    },
    eventDetail : {
      maxlength    : 'イベント内容は50文字以下で入力してください'
    },
    eventDate : {
      date         : '開催日は(yyyy/MM/dd)の形式で入力してください。'
    },
    entryUserName : {
      maxlength    : '参加者は50文字以下で入力して下さい'
    }
};
	$('#back').click(function(){
		$('#backForm').submit();
		return false;
    });
	$('#create').click(function(){
		if (!imuiValidate('#eventForm', rules, messages)) return;
		$('#eventForm').submit();
		return false;
    });
	$('#selectUser').click(function(){
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
      type                        : 'multiple',
      deleted_data                : false,
      target_locale               : 'ja',
      login_user_only             : true,
      additional_disp             : true,
      additional_user_search_name : true,
      additional_dept             : true
    };
    
    // 検索画面を開く
    imACMSearch.open(parameter);
  });
	
});
function setUser(object){
  if(object.length == 0){
    return;
	}
	var userNameList = new Array();
	var userCdList =  new Array();
	for(var i = 0; i < object.length; i++) {
	  var user = object[i].data;
	  userNameList.push(user.user_name);
	  userCdList.push(user.user_cd);
	}
	$('#entryUserName').val(userNameList);
	$('#entryUser').val(userCdList);
  return;
}

</script>
</imui:head>
	<!-- タイトルバー -->
	<div class="imui-title-small-window">
		<h1>イベント情報登録  - JavaEE開発モデル -</h1>
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
			<h2>イベント情報登録</h2>
		</header>
		<font color="#FF0000"><b><html:errors /></b></font>
		<s:form styleId="eventForm" action='${f:h(eventForm.path)}' method="POST">
			<table class="imui-form">
				<tbody>
					<tr>
						<th><label class="imui-required">イベント名</label></th>
						<td><imui:textbox value='${f:h(eventForm.eventName)}' id="eventName" name="eventName" size="50"/></td>
					</tr>
					<tr>
						<th><label>イベント内容</label></th>
						<td>
							<imui:textArea value='${f:h(eventForm.eventDetail)}' id="eventDetail" name="eventDetail" rows="3" cols="40"/>
						</td>
					</tr>
					<tr>
						<th><label>開催日</label></th>
						<td>
							<imui:textbox value='${f:h(eventForm.eventDate)}' id="eventDate" name="eventDate" size="50"/>
							<im:calendar floatable="true" altField="#eventDate"  format='${f:h(eventForm.format)}' changeYear="true" changeMonth="true"/>
						</td>
					</tr>
					<tr>
						<th><label>参加者</label></th>
						<td>
							<imui:textbox value='${f:h(eventForm.entryUser)}' id="entryUserName" name="entryUserName" size="50" readonly />
							<imui:button value="検索" id="selectUser" name="selectUser" class="imui-small-button" />
							</td>
					</tr>
				</tbody>
			</table>
			<div class="imui-operation-parts">
				<imui:button value="登録" id="create" name="create" class="imui-large-button" escapeXml="true" escapeJs="false" />
			</div>
			<INPUT type="hidden" id="entryUser" name="entryUser" value="${f:h(eventForm.entryUser)}"/>
		</s:form>
	</div>

<!-- 戻るボタンが押下された場合に利用されるformです -->
<form name="backForm" id="backForm" method="POST" action="home">
</form>


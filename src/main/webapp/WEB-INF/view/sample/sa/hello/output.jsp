<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="imui" uri="http://www.intra-mart.co.jp/taglib/imui"%>
<%@ taglib prefix="s" uri="http://sastruts.seasar.org" %>
<%@ taglib prefix="f" uri="http://sastruts.seasar.org/functions" %>

<!-- HEADタグ -->
<imui:head>
  <title>Hello111, World </title>
  <script type="text/javascript">
    function back() {
      $('#back-form').submit();
    };
  </script>
</imui:head>

<!-- 画面上に表示されるタイトル -->
<div class="imui-title">
  <h1>Hello, World (SAStruts)</h1>
</div>

<!-- ツールバー(前の画面へと戻るボタンを配置) -->
<div class="imui-toolbar-wrap">
  <div class="imui-toolbar-inner">
    <ul class="imui-list-toolbar">
      <li>
      <!-- 「戻る」ボタンのアイコン、HelloActionのindexメソッドが呼び出される。 -->
        <a href=${f:url("/sample/hello")} class="imui-toolbar-icon"　title="back">
          <span class="im-ui-icon-common-16-back"></span>
        </a>
      </li>
    </ul>
  </div>
</div>

<!-- 出力結果 -->
<div class="imui-form-container-narrow">
  <label>
    <imui:textbox type="text" value="${f:h(helloDto.outputString)}" id="name" name="name" size="10" class="imui-text-readonly" readonly />
  </label>
</div>
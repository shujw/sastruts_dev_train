<%@page pageEncoding="UTF-8"%>
<%@ taglib prefix="imui" uri="http://www.intra-mart.co.jp/taglib/imui"%>
<%@ taglib prefix="s" uri="http://sastruts.seasar.org" %>
<%@ taglib prefix="f" uri="http://sastruts.seasar.org/functions" %>

<!-- HEADタグqqqqqqqqqqqqqqqqq -->
<imui:head>
  <title>Hello, World</title>
  <script type="text/javascript">
    $(function() {
      $('#button').click(function() {
        $('#helloForm').submit();
      });
    });
  </script>
</imui:head>

<!-- 画面上に表示されるタイトル -->
<div class="imui-title">
  <h1>Hello1111, World (SAStruts)</h1>
</div>

<!-- 入力画面 -->
<div class="imui-form-container-narrow">
  <p>
    <label for="name">Please input the name. </label>
  </p>
  <!-- 入力フォームの設定
       actionに結果表示画面へのパスを入力(Helloアクションのoutputメソッドを呼び出す) -->
  <s:form action="/sample/hello/output/" styleId="helloForm">
    <div>
      <!-- テキストボックス -->
      <imui:textbox type="text" value='' id="name" name="name" size="10" />
    </div>
    <!-- submitボタン -->
    <imui:button name="button" value="Hello!!" class="mt-10" id="button"></imui:button>
  </s:form>
</div>
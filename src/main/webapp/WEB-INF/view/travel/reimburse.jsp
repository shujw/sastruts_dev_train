<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="imarttag" uri="http://www.intra-mart.co.jp/taglib/foundation/imarttag" %>
<%@ taglib prefix="imarttag2" uri="http://www.intra-mart.co.jp/taglib/core/standard" %>
<%@ taglib prefix="imart" uri="http://www.intra-mart.co.jp/taglib/core/standard" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@ taglib prefix="f" uri="http://sastruts.seasar.org/functions" %>
<%@ taglib prefix="imdesign"   uri="http://www.intra-mart.co.jp/taglib/foundation/imarttag" %>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<%@ taglib prefix="imui" uri="http://www.intra-mart.co.jp/taglib/imui"%>
<%@ taglib prefix="im" uri="http://www.intra-mart.co.jp/taglib/im-tenant" %>
<%@ taglib prefix="workflow" uri="http://www.intra-mart.co.jp/taglib/imw/workflow" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head lang='ja'>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="Content-Style-Type" content="text/css">
	
	<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
	<style type="text/css">
            
    </style>
	<title>出差费报销</title>
</head>
<body>
	<header class="imui-title-small-window">
            <h1>出差费报销</h1>
    </header>
<!--     ***** -->
    <div class="imui-toolbar-wrap">
         <div class="imui-toolbar-inner">
             <!-- ツールバー左側 -->
             <ul class="imui-list-toolbar">
                 <li><a href="javascript:history.go(-1);">返回</a></li>
             </ul>
         </div>
    </div>
    <html:errors /> 
    
    <form id="mainForm" name="mainForm" action="" runat="server">
    
    </form>
</body>
</html>
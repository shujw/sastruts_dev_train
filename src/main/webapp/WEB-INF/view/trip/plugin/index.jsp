<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="f" uri="http://sastruts.seasar.org/functions" %>

<html>
<head>
<script type="text/javascript">
function callBack() {
    var returnObject = [
                        {
                           "extensionPointId" : '${f:h(pluginForm.extensionPointId)}',
                           "pluginId"         : '${f:h(pluginForm.pluginId))}',
                           "parameter"        : '${f:h(pluginForm.parameter)}',
                           "pluginName"       : '${f:h(pluginForm.pluginName)}',
                           "displayName"      : '${f:h(pluginForm.displayName)}',
                           "targetDate"       : '${f:h(pluginForm.targetDate)}',
                           "targetType"       : '${f:h(targetType)}',
                           "targetCode"       : '${f:h(targetCode)}'
                         }
                       ];
    parent['${f:h(pluginForm.callBackFunction)}'](returnObject);
}
</script>
</head>
<body onload="javascript:callBack();"></body>
</html>

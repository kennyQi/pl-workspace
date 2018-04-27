<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0,user-scalable=no" name="viewport" />
<title>出错啦！</title>
  </head>
  
  <body onload="setTimeout('byebye()',1000)">
  <img style="width: 99%" src="<%=request.getContextPath()%>/resources/images/error.png">
  <a id="user" href="<%=request.getContextPath()%>/user/view"></a>
  </body>
  <script >
    function byebye(){
   		document.getElementById("user").click();
    }
</script>
</html>

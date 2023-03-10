<%--
  Created by IntelliJ IDEA.
  User: hee
  Date: 2022/12/23
  Time: 2:40 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Todo Modify/Remove</title>
</head>
<body>
<form id="form1" action="/todo/modify" method="post"> <!--수정-->
  <div>
    <input type="text" name="tno" value="${dto.tno}" readonly>
  </div>
  <div>
    <input type="text" name="title" value="${dto.title}">
  </div>
  <div>
    <input type="date" name="dueDate" value="${dto.dueDate}">
  </div>
  <div>
    <input type="checkbox" name="finished" ${dto.finished?"checked":""}>
  </div>
  <div>
    <button type="submit">Modify</button>
  </div>
</form>

<form id="form2" action="/todo/remove" method="post"> <!--삭제-->
  <input type="hidden" name="tno" value="${dto.tno}" readonly>
  <div>
    <button type="submit">Remove</button>
  </div>
</form>
</body>
</html>

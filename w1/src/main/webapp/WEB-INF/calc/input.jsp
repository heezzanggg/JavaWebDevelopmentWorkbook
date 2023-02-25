<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<%--<form action="calcResult.jsp" method="post">--%>
<%--<form action="calcResult" method="post">--%>
<form action="/calc/makeResult" method="post">
<!--양식을 어디에 어떤방식으로 전송할것인가-->
    <input type="number" name="num1">
    <input type="number" name="num2">
    <button type="submit">SEND</button>
</form>

</body>
</html>

<!--
GET Method
http://localhost:8080/calc/input?num1=&num2=
쿼리스트링 & 파라미터
Post Method

-->

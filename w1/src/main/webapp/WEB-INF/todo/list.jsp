<%--
  Created by IntelliJ IDEA.
  User: hee
  Date: 2022/12/22
  Time: 10:52 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%--jstl라이브러리 : EL이 출력만을 담당하기 때문에 제어문이나 반복처리 위해 사용하는 라이브러리
JSP에서 동작하는 새로운 태그들의 묶음--%>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>List Page</h1>

<%--var : EL에서 사용될 변수 이름--%>
<%--items : List,Set,Map,Enumeration,Iterator등의 컬렉션--%>
<%--begin/end : 반복의 시작/끝 값 --%>

<ul>
    <c:forEach var = "dto" items="${list}">
        <li>${dto}</li>
    </c:forEach>
</ul>
========================================================================
<ul>
    <c:forEach var="num" begin="1" end="5">
        <li>${num}</li>
    </c:forEach>
</ul>
========================================================================
<%--test 속성값으로는 true/false로 나올수 있는 식이나 변수 등 들어감--%>
<c:if test="${list.size()%2==0}">짝수</c:if>
<c:if test="${list.size()%2!=0}">홀수</c:if>
========================================================================
<%--c:choose 경우, 자바의 switch구문과 비슷--%>
<%--c:when test=.. , c:otherwise 이용해서 if ~ else if ~ else 처리 가능--%>
<c:choose>
    <c:when test="${list.size()%2==0}">
        짝수
    </c:when>
    <c:otherwise>
        홀수
    </c:otherwise>
</c:choose>
========================================================================
<%--새로운 변수 생성해야 할 경우 c:set 이용해서 변수 생성하고 사용함
c:set은 var 속성으로 변수명 지정, value속성으로 값 지정--%>

<c:set var="target" value="5"></c:set>
<ul>
    <c:forEach var="num" begin="1" end="10">
        <c:if test="${num == target}">
            num = ${num} <br>
            target = ${target} <br>
            num is target
        </c:if>
    </c:forEach>
</ul>


</body>
</html>

<%--jsp는 주로 HTML태그들과 자바스크립트, CSS등을 이용해서 코드 작성--%>
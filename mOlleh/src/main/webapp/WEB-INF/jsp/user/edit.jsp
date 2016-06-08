<%--
  Created by IntelliJ IDEA.
  User: lg
  Date: 2016-05-20
  Time: 오후 6:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@ include file="/WEB-INF/include/include-header.jspf" %>
    <style>
        ul{list-style:none;}
        ul>li{float:left;width:78px;border:1px solid #999999;text-align:center;background-color:#eaeaea;cursor:hand;}
        .dayOn{color:#ffffff;background-color:#777777}
        .errorMessage{border: 2px solid red;}
    </style>
</head>
<body>
사용자 수정폼
<form action="/user/edit" method="post">

    <spring:bind path="user.user_id">
        <label for="ID">user_id : </label>
        <input type="text" id="${status.expression}" name="${status.expression}" value="${status.value}" />
        <span class="errorMessage">
        <c:forEach var="errorMessage" items="${status.errorMessages}">
            ${errorMessage}
        </c:forEach>
        </span>
    </spring:bind>
    <br>
    비번 : <input type="text" name="user_pw" value="${user.user_pw}" /><br>
    이름 : <input type="text" name="user_nm" value="${user.user_nm}" /><br>
    이메일 : <input type="text" name="user_email" value="${user.user_email}" /><br>
    휴대폰 : <input type="text" name="user_mobile" value="${user.user_mobile}" /><br>
    나이 : <input type="text" name="user_age" value="${user.user_age}" /><br>
    <input type="submit" value="수정하기" />
	<c:if test="${bindingResult.hasErrors()}">
	   수정중 에러가 발생했습니다.
	   err:${bindingResult.toString() }
	</c:if>
</form>

<%@ include file="/WEB-INF/include/include-body.jspf" %>
</body>
</html>

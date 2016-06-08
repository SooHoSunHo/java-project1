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
    </style>
    <script type="text/javascript">
        $(document).ready(function() {
            $("#loginIdChk").on("click", function (e) {
                e.preventDefault();
                fn_loginIdChk();
            });

        });

        fn_loginIdChk = function() {
            var chkId = $("#user_id").val();

            $.getJSON('/user/checkLoginId/'+chkId, function(result){
                console.log(result);
                if(result.duplicated){
                    alert("이미 등록된 사용자ID입니다." + result.availableId + "는 사용할 수 있습니다.");
                }
                else {
                    alert("사용할 수 있는 ID입니다.");
                }
            });

        }


    </script>
</head>
<body>
    사용자 등록폼...1
    <form action="/user/regist" method="post">
        아이디 : <input type="text" id="user_id" name="user_id" value="${user.user_id }" />
                <a href="#this" class="btn" id="loginIdChk">중복확인</a>
        <br>
        비번 : <input type="text" name="user_pw" value="${user.user_pw }" /><br>
        이름 : <input type="text" name="user_nm" value="${user.user_nm }" /><br>
        이메일 : <input type="text" name="user_email" value="${user.user_email }" /><br>
        휴대폰 : <input type="text" name="user_mobile" value="${user.user_mobile }" /><br>
        나이 : <input type="text" name="user_age" value="${user.user_age }" /><br>
        <input type="submit" value="가입하기" />
    </form>
	<c:if test="${result.hasErrors()}">
	   에러가 발생했습니다.
	</c:if>
    <%@ include file="/WEB-INF/include/include-body.jspf" %>


</body>
</html>

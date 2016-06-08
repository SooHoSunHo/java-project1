<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<%@ include file="/WEB-INF/include/include-header.jspf" %>
	<script type="text/javascript">
	$(document).ready(function() {
		$("#list").on("click", function(e) {
			e.preventDefault();
			fn_userList();
		});
		
		$("#edit").on("click", function(e) {
			e.preventDefault();
			fn_userEdit();
		});

        $("#delete").on("click", function(e) {
            e.preventDefault()
            fn_userDelete();
        });
		
		$("a[name='file']").on("click", function(e){
			e.preventDefault();
			fn_downloadFile($(this));
		});
	});
	
	fn_userList = function() {
		var comSubmit = new ComSubmit();
		comSubmit.setUrl("<c:url value='/user/' />");
		comSubmit.submit();
	}
	
	fn_userEdit = function() {
		var uid = "${userInfo.user_id}";
		
		location.href="/user/edit?user_id="+uid;
	}

    fn_userDelete = function(){
        var uid = "${userInfo.user_id}";
        location.href = "/user/delete?user_id="+uid;
    }
	</script>
</head>

<body>
	<table class="board_view">
        <colgroup>
            <col width="15%"/>
            <col width="35%"/>
            <col width="15%"/>
            <col width="35%"/>
        </colgroup>
        <caption>게시글 상세</caption>
        <tbody>
            <tr>
                <th scope="row">아이디</th>
                <td>${userInfo.user_id }</td>
                <th scope="row">이름</th>
                <td>${userInfo.user_nm }</td>
            </tr>
            <tr>
                <th scope="row">이메일</th>
                <td>${userInfo.user_email }</td>
                <th scope="row">전화번호</th>
                <td>${userInfo.user_mobile }</td>
            </tr>
            <tr>
                <th scope="row">나이</th>
                <td colspan="3">${userInfo.user_age }</td>
            </tr>
            <tr>
                <th scope="row">가족</th>
                <td colspan="3">${codes.myFamily }</td>
            </tr>
           
        </tbody>
    </table>
    
    <a href="#this" class="btn" id="list">목록으로</a>
    <a href="#this" class="btn" id="edit">수정하기</a>
    <a href="#this" class="btn" id="delete">삭제하기</a>
    
    <%@ include file="/WEB-INF/include/include-body.jspf" %>
    
</body>
</html>
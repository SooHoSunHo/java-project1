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
				fn_boardList();
			});
			
			$("#update").on("click", function(e) {
				e.preventDefault();
				fn_boardUpdate();
			});
			
			$("a[name='file']").on("click", function(e){
				e.preventDefault();
				fn_downloadFile($(this));
			});
		});
		
		fn_boardList = function() {
			var comSubmit = new ComSubmit();
			comSubmit.setUrl("<c:url value='/board/boardList' />");
			comSubmit.submit();
		}
		
		fn_boardUpdate = function() {
			var idx = "${map.idx}";
			var comSubmit = new ComSubmit();
			comSubmit.setUrl("<c:url value='/board/boardUpdate' />");
			comSubmit.addParam("idx", idx);
			comSubmit.submit();
		}
		
		fn_downloadFile = function(obj) {
			var idx = obj.parent().find("#idx").val();
			var comSubmit = new ComSubmit();
			comSubmit.setUrl("<c:url value='/common/downloadFile' />");
			comSubmit.addParam("idx", idx);
			comSubmit.submit();
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
                <th scope="row">글 번호</th>
                <td>${map.title }</td>
                <th scope="row">조회수</th>
                <td>${map.read }</td>
            </tr>
            <tr>
                <th scope="row">작성자</th>
                <td>${map.reg_id }</td>
                <th scope="row">작성시간</th>
                <td>${map.reg_date }</td>
            </tr>
            <tr>
                <th scope="row">제목</th>
                <td colspan="3">${map.title }</td>
            </tr>
            <tr>
                <td colspan="4">${map.content }</td>
            </tr>
            <tr>
                <th scope="row">첨부파일</th>
                <td colspan="3">
                <c:choose>
                	<c:when test="${fn:length(list)>0 }">
                		<c:forEach var="row" items="${list }">
                		<p>
	                        <input type="hidden" id="idx" value="${row.idx }">
	                        <a href="#this" name="file">${row.origin_file_name }</a> 
	                        (${row.file_size }kb)
	                    </p>
	                    </c:forEach>
                	</c:when>
                    <c:otherwise>
                    	--
                    </c:otherwise>
                </c:choose>
                </td>
            </tr>
        </tbody>
    </table>
    
    <a href="#this" class="btn" id="list">목록으로</a>
    <a href="#this" class="btn" id="update">수정하기</a>
    
    <%@ include file="/WEB-INF/include/include-body.jspf" %>
    
</body>
</html>
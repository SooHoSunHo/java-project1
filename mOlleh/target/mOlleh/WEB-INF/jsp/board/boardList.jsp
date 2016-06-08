<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/include/include-header.jspf" %>
</head>

<body>
<h2>게시판 목록</h2>
<table style="border:1px solid #ccc">
    <colgroup>
        <col width="10%"/>
        <col width="*"/>
        <col width="15%"/>
        <col width="20%"/>
    </colgroup>
    <thead>
        <tr>
            <th scope="col">글번호</th>
            <th scope="col">제목</th>
            <th scope="col">조회수</th>
            <th scope="col">작성일</th>
        </tr>
    </thead>
    <tbody>
        
    </tbody>
</table>

<div id="page_navi"></div>
<input type="hidden1" id="page_index" name="page_index" />

<br />
<a href="#this" class="btn" id="write">글쓰기</a>

<%@ include file="/WEB-INF/include/include-body.jspf" %>
<script type="text/javascript">
	$(document).ready(function() {
		fn_selectBoardList(1);
		
		$("#write").on("click", function(e){	// write
			e.preventDefault();
			fn_boardWrite();
		});
	});
	
	fn_boardWrite = function() {
		var comSubmit = new ComSubmit();
		comSubmit.setUrl("<c:url value='/board/boardWrite' />");
		comSubmit.submit();
	}
	
	fn_boardDetail = function(obj) {
		var comSubmit = new ComSubmit();
		comSubmit.setUrl("<c:url value='/board/boardDetail' />");
		comSubmit.addParam("idx", obj.parent().find("#idx").val());
		comSubmit.submit();
	}
	
	function fn_selectBoardList(pageNo){
		var comAjax = new ComAjax();
		comAjax.setUrl("<c:url value='/board/selectBoardList' />");
		comAjax.setCallback("fn_selectBoardListCallback");
		comAjax.addParam("page_index",pageNo);
		comAjax.addParam("page_row", 5);
		comAjax.addParam("idx_fe", $("#idx_fe").val());
		comAjax.ajax();
	}
	
	fn_selectBoardListCallback = function(data) {
		var total = data.total;
		var body = $("table>tbody");
		body.empty();
		
		if(total==0){
			var str = "<tr>" +
							"<td colspan='4'>조회된 결과가 없습니다.</td>" +
						"</tr>";
			body.append(str);
		}
		else {
			var params = {
					divId : "page_navi",
					pageIndex : "page_index",
					totalCount : total,
					eventName : "fn_selectBoardList"
			};
			gfn_renderPaging(params);
			
			var str = "";
			var dspNo = total;
			$.each(data.list, function(key, value){
				str += "<tr>" + 
							"<td>" + dspNo + "</td>" +
							"<td>" +
								"<a href='#this' name='title'>" + value.title + "</a>" +
								"<input type='hidden' id='idx' name='idx' value=" + value.idx + ">" +
							"</td>" + 
							"<td>" + value.read + "</td>" +
							"<td>" + value.reg_date + "</td>" +
						"<tr>";
				dspNo --;
			});
			body.append(str);
			
			$("a[name='title']").on("click", function(e){
				e.preventDefault();
				fn_boardDetail($(this));
			});
		}
	}
</script>

</body>
</html>
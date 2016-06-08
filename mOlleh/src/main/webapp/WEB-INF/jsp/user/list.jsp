<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<!DOCTYPE html>
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
			fn_selectUserList(1);
			
			$("#regist").on("click", function(e){	// write
				e.preventDefault();
				fn_userRegist();
			});
		});
		
		fn_userRegist = function() {
			var comSubmit = new ComSubmit();
			comSubmit.setUrl("<c:url value='/user/regist' />");
			comSubmit.submit();
		}
		
		fn_userDetail = function(obj) {
			var comSubmit = new ComSubmit();
			comSubmit.setUrl("<c:url value='/user/detail' />");
			comSubmit.addParam("user_id", obj.parent().find("#user_id").val());
			//console.log(obj.parent().find("#user_id").val());
			comSubmit.submit();
		}
		
		function fn_selectUserList(pageNo){
			var comAjax = new ComAjax();
			comAjax.setUrl("<c:url value='/user/selectUserList' />");
			comAjax.setCallback("fn_selectUserListCallback");
			comAjax.addParam("page_index",pageNo);
			comAjax.addParam("page_row", 10);
			comAjax.addParam("idx_fe", $("#idx_fe").val());
			comAjax.ajax();
		}
		
		fn_selectUserListCallback = function(data) {
			var total = data.total;
			console.log(data);
			var body = $("table>tbody");
			body.empty();
			
			if(total==0){
				var str = "<tr>" +
								"<td colspan='5'>조회된 결과가 없습니다.</td>" +
							"</tr>";
				body.append(str);
			}
			else {
				var params = {
						divId : "page_navi",
						pageIndex : "page_index",
						totalCount : total,
						eventName : "fn_selectUserList"
				};
				gfn_renderPaging(params);
				
				var str = "";
				var dspNo = total;
				$.each(data.list, function(key, value){
					str += "<tr>" + 
								"<td>" + dspNo + "</td>" +
								"<td>" +
									"<a href='#this' name='user_nm'>" + value.user_nm + "</a>" +
									"<input type='hidden' id='user_id' name='user_id' value=" + value.user_id + ">" +
								"</td>" + 
								"<td>" + value.user_email + "</td>" +
								"<td>" + value.user_mobile + "</td>" +
								"<td>" + value.user_age + "</td>" +
							"<tr>";
					dspNo --;
				});
				body.append(str);
				
				$("a[name='user_nm']").on("click", function(e){
					e.preventDefault();
					fn_userDetail($(this));
				});
			}
		}
	</script>
</head>
<body>
	<table style="border:1px solid #ccc">
    <colgroup>
        <col width="10%"/>
        <col width="20%"/>
        <col width="*"/>
        <col width="20%"/>
        <col width="10%"/>
    </colgroup>
    <thead>
        <tr>
            <th scope="col">No</th>
            <th scope="col">이름</th>
            <th scope="col">이메일</th>
            <th scope="col">전화번호</th>
            <th scope="col">Age</th>
        </tr>
    </thead>
    <tbody></tbody>
</table>
    <div id="page_navi"></div>
    <input type="hidden" id="page_index" name="page_index" />
    <%@ include file="/WEB-INF/include/include-body.jspf" %>
</body>
</html>

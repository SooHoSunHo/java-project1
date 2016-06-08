<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
		var cal = new Date();
		var sWeekCnt = cal.getDay();
		var tryCnt = 0;
	
		$(document).ready(function() {
			$("#saveWebtoon").on("click", function(e){	// save Webtoon
				e.preventDefault();
				fn_saveTodayWebtoon(sWeekCnt);
			});
			
			$("ul>li").on("click", function(e){
				var sId = $(this).attr("id");
				var cd = sId.substr(4,1);
				
				fn_selectWebtoonList(cd);
			});
			
			// 오늘자 웹툰 display...
			fn_selectWebtoonList(sWeekCnt);
		});
		
		fn_chgDayTab = function(cd){
			var selId = "day_"+cd;
			$("ul>li").removeClass("dayOn");
			$("#"+selId).addClass("dayOn");
		}
		
		fn_saveTodayWebtoon = function(sWeekCnt) {
			console.log(sWeekCnt);
			
			var comAjax = new ComAjax();
			comAjax.setUrl("<c:url value='/webtoon/saveTodayWebtoon' />");
			comAjax.setCallback("fn_saveTodayWebtoonCallback");
			comAjax.addParam("sWeekCnt", sWeekCnt);
			comAjax.ajax();
		}
		
		fn_saveTodayWebtoonCallback = function(result){
			fn_selectWebtoonList(sWeekCnt);
		}
		
		fn_selectWebtoonList = function(sWeekCnt){
			//console.log(sWeekCnt);
			fn_chgDayTab(sWeekCnt);
			
			var comAjax = new ComAjax();
			comAjax.setUrl("<c:url value='/webtoon/selectWebtoonList' />");
			comAjax.setCallback("fn_selectWebtoonListCallback");
			comAjax.addParam("sWeekCnt", sWeekCnt);
			comAjax.ajax();
		}
		
		fn_selectWebtoonListCallback = function(data){
			//console.log(data.list.length);
			/*
			if (data.list.length == 0) {
				if (tryCnt < 3) {
					fn_saveTodayWebtoon(sWeekCnt);
					sWeekCnt++;
				}
				else {
					console.log("연동에러");
					return;
				}
				
			}
			*/
			
			var body = $("table>tbody");
			body.empty();

			var str = "";
			
			$.each(data.list, function(key, value){
				str += "<tr>" + 
							"<td><img style='border:1px solid #999999;width:100px;' src='/storage/webtoon/" + value.weekcd + "/" + value.imgpath + "' /></td>" +
							"<td>" +
								"<a href='#this' name='webtoonnm'>" + value.webtoonnm + "<br>" + value.timestitle + "</a>" +
								"<input type='hidden' id='listUrl' name='listUrl' value=" + value.timesviewurl + ">" +
							"</td>" + 
							
							"<td>" + value.authors + "</td>" +
						"<tr>";
			});
			body.append(str);
			//console.log(str);
			
			$("a[name='webtoonnm']").on("click", function(e){
				e.preventDefault();
				location.href=$(this).parent().find("#listUrl").val();
			});
		}
	</script>
</head>

<body>
<h2>웹툰 목록</h2>
<div style="width:600px;">
	<ul class="weekUl">
		<li id="day_0">일</li>
		<li id="day_1">월</li>
		<li id="day_2">화</li>
		<li id="day_3">수</li>
		<li id="day_4">목</li>
		<li id="day_5">금</li>
		<li id="day_6">토</li>
	</ul>
</div>
<table style="padding-top:20px;width:600px;border:0px solid #ccc;clear:both;">
    <colgroup>
        <col width="100px"/>
        <col width="400px"/>
        <col width="100px"/>
    </colgroup>
    <tbody>
    </tbody>
</table>
<a href='#this' id="saveWebtoon" name="saveWebtoon">웹툰저장</a>
<div id="page_navi"></div>
<input type="hidden" id="page_index" name="page_index" />
<%@ include file="/WEB-INF/include/include-body.jspf" %>

</body>
</html>
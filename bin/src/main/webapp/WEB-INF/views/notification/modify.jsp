<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<%@ include file="/WEB-INF/views/include/head.jsp" %>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <link rel="stylesheet" href="../assets/css/main.css">

	<style>
      .bg-nav {
      	background-color: #7895B2;
      }
  	</style>
</head>
<body>
	<c:if test="${userinfo.grade ne '관리자'}">
		<script>
			alert("접근권한이 없습니다.");
			location.href = "${root}/user/login";
		</script>
  	</c:if>
	<%@ include file="/WEB-INF/views/include/nav.jsp" %>
	<div class="row justify-content-center">
        <div class="col-lg-8 col-md-10 col-sm-12">
          <h2 class="my-3 py-3 shadow-sm bg-light text-center">
            <mark class="sky">글수정</mark>
          </h2>
        </div>
        <div class="col-lg-8 col-md-10 col-sm-12">
          <form id="form-modify" method="POST" action="">
          	<input type="hidden" name="pgno" value="${pgno}">
		    <input type="hidden" name="key" value="${key}">
		   	<input type="hidden" name="word" value="${word}">
            <input type="hidden" name="id" value="${notice.id}">
            <div class="mb-3">
              <label for="subject" class="form-label">제목 : </label>
              <input type="text" class="form-control" id="title" name="title" value="${notice.title}" />
            </div>
            <div class="mb-3">
              <label for="content" class="form-label">내용 : </label>
              <textarea class="form-control" id="content" name="content" rows="7">${notice.content}</textarea>
            </div>
            <div class="col-auto text-center">
              <button type="button" id="btn-modify" class="btn btn-outline-primary mb-3">
                글수정
              </button>
              <button type="button" id="btn-list" class="btn btn-outline-danger mb-3">
                목록으로이동...
              </button>
            </div>
          </form>
        </div>
      </div>
     <form id="form-param" method="get" action="">
      	<input type="hidden" id="pgno" name="pgno" value="${pgno}">
      	<input type="hidden" id="key" name="key" value="${key}">
      	<input type="hidden" id="word" name="word" value="${word}">
     </form>
     <script>
     document.querySelector("#btn-modify").addEventListener("click", function () {
         if (!document.querySelector("#title").value) {
           alert("제목 입력!!");
           return;
         } else if (!document.querySelector("#content").value) {
           alert("내용 입력!!");
           return;
         } else {
           let form = document.querySelector("#form-modify");
           form.setAttribute("action", "${root}/notice/modify");
           form.submit();
         }
       });
       
       document.querySelector("#btn-list").addEventListener("click", function () {
        	if(confirm("취소를 하시면 작성중인 글은 삭제됩니다.\n취소하시겠습니까?")) {
        		let form = document.querySelector("#form-param");
           	form.setAttribute("action", "${root}/notice/list");
             form.submit();
       	}
       });
     </script>
     <%@ include file="/WEB-INF/views/include/footer.jsp" %>
</body>
</html>
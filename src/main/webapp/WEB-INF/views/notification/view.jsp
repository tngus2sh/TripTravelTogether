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
	<%@ include file="/WEB-INF/views/include/nav.jsp" %>
	<c:if test="${notification eq null}">
		<script>
		alert("글이 삭제되었거나 부적절한 URL 접근입니다.");
		location.href = "${root}/notification/list";
		</script>
	</c:if>
	<div class="row justify-content-center">
        <div class="col-lg-8 col-md-10 col-sm-12">
          <h2 class="my-3 py-3 shadow-sm bg-light text-center">
            <mark class="sky">공지 사항</mark>
          </h2>
        </div>
        <div class="col-lg-8 col-md-10 col-sm-12">
          <div class="row my-2">
            <h2 class="text-secondary px-5">${notification.id}. ${notification.title}</h2>
          </div>
          <div class="row">
            <div class="col-md-8">
              <div class="clearfix align-content-center">
                <img
                  class="avatar me-2 float-md-start bg-light p-2"
                  src="https://raw.githubusercontent.com/twbs/icons/main/icons/person-fill.svg"
                />
                <p>
                  <span class="fw-bold">${notification.userId}</span> <br />
                  <span class="text-secondary fw-light"> ${notification.createdAt} 조회 : ${notification.hit} </span>
                </p>
              </div>
            </div>
            <div class="divider mb-3"></div>
            <div class="text-secondary">
              ${notification.content}
            </div>
            <div class="divider mt-3 mb-3"></div>
            <div class="d-flex justify-content-end">
              <button type="button" id="btn-list" class="btn btn-outline-primary mb-3">
                 목록
              </button>
	         <c:if test="${userinfo.grade eq '관리자'}">
	              <button type="button" id="btn-mv-modify" class="btn btn-outline-success mb-3 ms-1">
	                글수정
	              </button>
	              <button type="button" id="btn-delete" class="btn btn-outline-danger mb-3 ms-1">
	                글삭제
	              </button>
	              <form id="form-no-param" method="get" action="${root}/notification">
				      <input type="hidden" id="npgno" name="pgno" value="${pgno}">
				      <input type="hidden" id="nkey" name="key" value="${key}">
				      <input type="hidden" id="nword" name="word" value="${word}">
				      <input type="hidden" id="id" name="id" value="${notification.id}">
				  </form>
				  <script>
		      		document.querySelector("#btn-mv-modify").addEventListener("click", function () {
				    	let form = document.querySelector("#form-no-param");
				   		form.setAttribute("action", "${root}/notification/modify");
				    	form.submit();
				  	});
				      
					document.querySelector("#btn-delete").addEventListener("click", function () {
						if(confirm("정말 삭제하시겠습니까?")) {
							let form = document.querySelector("#form-no-param");
				      	  	form.setAttribute("action", "${root}/notification/delete");
				          	form.submit();
						}
					});
				  </script>
              </c:if>
            </div>
          </div>
        </div>
      </div>
      <form id="form-param" method="get" action="">
      	<input type="hidden" id="pgno" name="pgno" value="${pgno}">
    	<input type="hidden" id="key" name="key" value="${key}">
      	<input type="hidden" id="word" name="word" value="${word}">
      </form>
<script>
	document.querySelector("#btn-list").addEventListener("click", function () {
	  let form = document.querySelector("#form-param");
	  form.setAttribute("action", "${root}/notification/list");
      form.submit();
  	});

</script>
<%@ include file="/WEB-INF/views/include/footer.jsp" %>
</body>
</html>
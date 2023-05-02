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
<div class="row justify-content-center">
        <div class="col-lg-8 col-md-10 col-sm-12">
          <h2 class="my-3 py-3 shadow-sm bg-light text-center">
            <mark class="sky">공지 사항</mark>
          </h2>
        </div>
        <div class="col-lg-8 col-md-10 col-sm-12">
          <div class="row align-self-center mb-2">
            <div class="col-md-2 text-start">
            <c:if test="${userinfo.grade eq '관리자'}">
	      	
              	<button type="button" id="btn-mv-register" class="btn btn-outline-primary btn-sm">
                등록
              	</button>
              </c:if>
            </div>
            <div class="col-md-7 offset-3">
              <form class="d-flex" id="form-search" action="">
                <input type="hidden" name="pgno" value="1"/>
                <select
                  name="key"
                  id="key"
                  class="form-select form-select-sm ms-5 me-1 w-50"
                  aria-label="검색조건"
                >
                  <option selected>검색조건</option>
                  <option value="subject">제목</option>
                </select>
                <div class="input-group input-group-sm">
                  <input type="text" name="word" id="word" class="form-control" placeholder="검색어..." />
                  <button id="btn-search" class="btn btn-dark" type="button">검색</button>
                </div>
              </form>
            </div>
          </div>
          <table class="table table-hover">
            <thead>
              <tr class="text-center">
                <th scope="col">글번호</th>
                <th scope="col">제목</th>
                <th scope="col">작성자</th>
                <th scope="col">조회수</th>
                <th scope="col">작성일</th>
              </tr>
            </thead>
            <tbody>    
				<c:forEach var="notification" items="${notifications}">    
	              <tr class="text-center">
	                <th scope="row">${notification.id}</th>
	                <td class="text-start">
	                  <a
	                    href="#"
	                    class="notification-title link-dark"
	                    data-no="${notification.id}"
	                    style="text-decoration: none"
	                  >
	                    ${notification.title}
	                  </a>
	                </td>
	                <td>${notification.userId}</td>
	                <td>${notification.hit}</td>
	                <td>${notification.createdAt}</td>
	              </tr>            
				</c:forEach>   
            </tbody>
          </table>
        </div>
        <div class="row">
          ${navigation.navigator}
        </div>
       </div>
       <form id="form-param" method="get" action="">
      <input type="hidden" name="pgno" id="pgno" value="${pgno}">
      <input type="hidden" name="key" value="${key}">
      <input type="hidden" name="word" value="${word}">
    </form>
    <form id="form-no-param" method="get" action="${root}/notification/view">
     	<input type="hidden" name="pgno" value="${pgno}">
      	<input type="hidden" name="key" value="${key}">
      	<input type="hidden" name="word" value="${word}">
      <input type="hidden" id="id" name="id" value="">
    </form>
    <script>
      let titles = document.querySelectorAll(".notification-title");
      titles.forEach(function (title) {
        title.addEventListener("click", function () {
          document.querySelector("#id").value = this.getAttribute("data-no");
          document.querySelector("#form-no-param").submit();
        });
      });

      document.querySelector("#btn-mv-register").addEventListener("click", function () {
    	  let form = document.querySelector("#form-param");
          form.setAttribute("action", "${root}/notification/write");
          form.submit();
      });
      
      document.querySelector("#btn-search").addEventListener("click", function () {
    	  let form = document.querySelector("#form-search");
          form.setAttribute("action", "${root}/notification/list");
          form.submit();
      });
      
      let pages = document.querySelectorAll(".page-link");
      pages.forEach(function (page) {
        page.addEventListener("click", function () {
       	  document.querySelector("#pgno").value = this.parentNode.getAttribute("data-pg");
          let form = document.querySelector("#form-param");
          form.setAttribute("action", "${root}/notification/list");
          form.submit();
        });
      });
    </script>2
<%@ include file="/WEB-INF/views/include/footer.jsp" %>
</body>
</html>
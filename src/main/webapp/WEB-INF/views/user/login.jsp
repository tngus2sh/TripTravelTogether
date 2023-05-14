<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
  <%@ include file="/WEB-INF/views/include/head.jsp" %>
  <link rel="stylesheet" href="assets/css/main.css">
  
  <script type="text/javascript" src="${root}/assets/js/rsa.js"></script>
  <script type="text/javascript" src="${root}/assets/js/jsbn.js"></script>
  <script type="text/javascript" src="${root}/assets/js/prng4.js"></script>
  <script type="text/javascript" src="${root}/assets/js/rng.js"></script>
  
  <style>
    body {
		text-align: center;
	}
  </style>
</head>
<body>
<%@ include file="/WEB-INF/views/include/nav.jsp" %>
	<c:if test="${cookie.user_id.value ne null}">
		<c:set var="idck" value="checked"/>
		<c:set var="saveid" value="${cookie.user_id.value}"/>
	</c:if>
	<div class="sign-container">
        <div class="signin">
        	<form id="signin-form" method="POST" role="search">
                <%-- <input type="hidden" id="signin-RSAModulus" value="${RSAModulus}"/>
    			<input type="hidden" id="signin-RSAExponent" value="${RSAExponent}"/>
       			<input type="hidden" id="signin-encode-id" name="signin-encode-id">
       			<input type="hidden" id="signin-encode-password" name="signin-encode-password"> --%>
        		
        		<div class="row mt-4 ms-2">
	            	<h2>로그인</h2>
	            </div>
	            <hr>
				<!-- <div class="form-check mb-3 float-end"> -->
				<div class="d-flex justify-content-center mt-4 ms-2 me-2">
				  <input
				    class="form-check-input"
				    type="checkbox"
				    value="ok"
				    id="saveid"
				    name="saveid"
				    ${idck}
				  />
				  <label class="form-check-label" for="saveid"> 아이디저장 </label>
				</div>
	            <div class="row d-flex justify-content-center my-4">
	            	<div class="col-5">
	                	<input type="text" class="form-control" name="userId" id="userId" placeholder="아이디" value="${saveid}">
					</div>
	            </div>
	            <div class="row d-flex justify-content-center my-4">
	            	<div class="col-5">
	                	<input type="password" class="form-control" name="userPwd" id="userPwd" placeholder="비밀번호">
	            	</div>
	            </div>
	            <div class="row d-flex justify-content-center my-4">
	                <div class="col-5">
	                	<button type="button" id="signin-btn" class="btn submit-btn" style="width: 100%">로그인</button>
	                </div>
                  <div>
		              <a class="nav-link active" id="pw-search" data-bs-toggle="modal" data-bs-target="#pwModal">
		               	비밀번호 찾기
		              </a>
	            </div>
	            </div>
        	</form>
        </div>
        
        <!-- 비밀번호 찾기 modal -->
        <form class="d-flex" id="pw-search-form" method="POST" role="search">
        <div
          class="modal fade mt-5"
          id="pwModal"
          tabindex="-1"
          aria-labelledby="exampleModalLabel"
          aria-hidden="true"
        >
          <div class="modal-dialog">
            <div class="modal-content">
              <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">비밀번호 찾기</h5>
                <button
                  type="button"
                  class="btn-close"
                  data-bs-dismiss="modal"
                  aria-label="Close"
                ></button>
              </div>
              <div class="modal-body">
                <div class="login-container text-center">
                  <div style="display: inline-block; text-align:left; width: 25rem">
                    <div class="row d-flex justify-content-center">
                      <div class="col-10 mb-3">
                        <span>아이디</span>
                        <input
                          type="text"
                          class="form-control my-3 px-3 py-2"
                          id="search-id"
                          name="search-id"
                          value=""
                        />
                      </div>
                    </div>
                    <div class="row d-flex justify-content-center">
                      <div class="col-10">
                        <span>이메일</span>
                        <input
                          type="text"
                          class="form-control my-3 px-3 py-2"
                          id="search-email-id"
                          name="search-email-id"
                          value=""
                        />
                        <span>@</span>
                        <input
                          type="text"
                          class="form-control my-3 px-3 py-2"
                          id="search-email-domain"
                          name="search-email-domain"
                          value=""
                        />
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              <div class="modal-footer">
                <button type="button" id="pw-search-btn" class="btn submit-btn me-2">메일 보내기</button>
              </div>
            </div>
          </div>
        </div>
        </form>
        <!-- 비밀번호 찾기 modal end -->
    </div>
    
    
    <script>
	  document.getElementById('signin-btn').addEventListener("click", function() {
		  let id = $("#userId").val();
	      let pw = $("#userPwd").val();
		  
		  // 입력값 검증
		  if(!document.querySelector("#userId").value) {
			alert("아이디를 입력해주세요.");
		  }
		  else if(!document.querySelector("#userPwd").value) {
			alert("비밀번호를 입력해주세요.");
		  }
		  else {
			  /* let rsa = new RSAKey();
			  rsa.setPublic($('#signin-RSAModulus').val(),$('#signin-RSAExponent').val());
				
			  $("#signin-encode-id").val(rsa.encrypt(id));
		      $("#signin-encode-password").val(rsa.encrypt(pw)); */
		        
		      /* document.getElementById('id').value = "";
		      document.getElementById('password').value = ""; */
		        
			  let form = document.querySelector("#signin-form");
			  form.setAttribute("action", "${root}/user/login");
			  form.submit();
		  }
	  });
	  
	  document.querySelector("#pw-search-btn").addEventListener("click", function() {
		  // 입력값 검증
		  if(!document.querySelector("#search-id").value) {
			  alert("아이디를 입력해주세요.");
		  }
		  else if(!document.querySelector("#search-email-id").value || !document.querySelector("#search-email-domain")) {
			  alert("이메일을 입력해주세요.");
		  }
		  else {
			  let form = document.querySelector("#pw-search-form");
			  form.setAttribute("action", "${root}/user/mail");
			  form.submit();
		  }
	  });
    </script>
    <%@ include file="/WEB-INF/views/include/footer.jsp" %>
</body>
</html>
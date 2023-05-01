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

	<div class="sign-container">
        <div class="signup">
        	<form id="signup-form" method="POST" role="search">
<%--         		<input type="hidden" name="action" value="signup">
                <input type="hidden" id="signup-RSAModulus" value="${RSAModulus}"/>
    			<input type="hidden" id="signup-RSAExponent" value="${RSAExponent}"/>
       			<input type="hidden" id="signup-encode-id" name="signup-encode-id">
       			<input type="hidden" id="signup-encode-password" name="signup-encode-password">
        		<input type="hidden" id="signup-encode-name" name="signup-encode-name">
       			<input type="hidden" id="signup-encode-email" name="signup-encode-email"> --%>
       			
        		<div class="row mt-4 ms-2">
	            	<h2>회원가입</h2>
	            </div>
	            <hr>
	            <div class="row d-flex justify-content-center my-4">
	            	<div class="col-10">
	                	<input type="text" class="form-control" name="id" id="id" placeholder="아이디">
					</div>
	            </div>
	            <div class="row d-flex justify-content-center my-4">
	            	<div class="col-10">
	                	<div id="check-id-result"></div>
					</div>
	            </div>
	            <div class="row d-flex justify-content-center login_pw my-4">
	            	<div class="col-10">
	                	<input type="password" class="form-control" name="password" id="password" placeholder="비밀번호">
	            	</div>
	            </div>
	            <div class="row d-flex justify-content-center login_pw my-4">
	            	<div class="col-10">
	                	<input type="password" class="form-control" name="pwdcheck" id="pwdcheck" placeholder="비밀번호 확인">
	            	</div>
	            </div>
	            <div class="row d-flex justify-content-center my-4">
	            	<div class="col-10">
	                	<input type="text" class="form-control" name="name" id="name" placeholder="이름">
					</div>
	            </div>
	            <div class="row d-flex justify-content-center my-4">
					<label for="emailId" class="form-label">이메일 : </label>
					<div class="input-group" style="width: 80%">
					  <input
					    type="text"
					    class="form-control"
					    id="emailId"
					    name="emailId"
					    placeholder="이메일아이디"
					  />
					  <span class="input-group-text">@</span>
					  <select
					    class="form-select"
					    id="emailDomain"
					    name="emailDomain"
					    aria-label="이메일 도메인 선택"
					  >
					    <option selected>선택</option>
					    <option value="ssafy.com">싸피</option>
					    <option value="google.com">구글</option>
					    <option value="naver.com">네이버</option>
					    <option value="kakao.com">카카오</option>
					  </select>
					</div>
	            </div>
	            <div class="row d-flex justify-content-center submit my-4">
	                <div class="col-10">
	                	<button type="button" id="signup-btn" class="btn submit-btn" style="width: 100%">회원가입</button>
	                </div>
	            </div>
        	</form>
        </div>
    </div>


	<script>
		let isValidId = false;
		
		document.getElementById('signup-btn').addEventListener("click", function() {
		  let id = document.getElementById('id').value;
		  let pw = document.getElementById('password').value;
		  let pwCheck = document.getElementById('pwdcheck').value;
		  let name = document.getElementById('name').value;
		  let emailId = document.getElementById('emailId').value;
		  let emailDomain = document.getElementById('emailDomain').value;
	
		  console.log("hello");
		  // 입력값 검증
		  if (id == '' || pw == '' || name == '' || emailId == '' || emailDomain == '') {
		    alert("빈칸이 없도록 입력해 주세요.");
		    return;
		  }
		  else if(!isValidId) {
			  alert("중복된 아이디입니다. 다른 아이디를 사용해 주세요.");
		  }
		  else if(pw != pwCheck) {
			  alert("비밀번호와 비밀번호 확인이 일치하지 않습니다.");
		  }
		  else {
			  /* let rsa = new RSAKey();
			  rsa.setPublic($('#signup-RSAModulus').val(),$('#signup-RSAExponent').val()); */
			  
/* 			  $("#signup-encode-id").val(rsa.encrypt(id));
		      $("#signup-encode-password").val(rsa.encrypt(pw));
		      $("#signup-encode-name").val(rsa.encrypt(name));
		      $("#signup-encode-email").val(rsa.encrypt(email));
		        
		      document.getElementById('signup-id').value = "";
		      document.getElementById('signup-password').value = "";
		      document.getElementById('signup-name').value = "";
		      document.getElementById('signup-email').value = ""; */
				
		      let form = document.querySelector("#signup-form");
		      form.setAttribute("action", "${root}/user/join");
		      form.submit();
		  }
	  	});
		
		document.getElementById("id").addEventListener("keyup", function() {
			let userid = this.value;
			
			let resultDiv = document.querySelector("#check-id-result");
			if(userid.length < 6 || userid.length > 16) {
				resultDiv.setAttribute("class", "mb-3 text-danger");
				resultDiv.textContent = "아이디는 6자 이상 16자 이하 입니다.";
				isValidId = false;
			}
			else {
				fetch("${root}/user/" + this.value)
				.then(response => response.text())
				.then(data => {
					if(data == 0) {
						resultDiv.setAttribute("class", "mb-3 text-primary");
						resultDiv.textContent  = this.value + "는 사용 가능한 아이디입니다.";
						isValidId = true;
					}
					else {
						resultDiv.setAttribute("class", "mb-3 text-danger");
						resultDiv.textContent  = this.value + "는 사용할 수 없는 아이디입니다.";
						isValidId = false;
					}
				});
			}

		});
	</script>
	<%@ include file="/WEB-INF/views/include/footer.jsp" %>
</body>
</html>
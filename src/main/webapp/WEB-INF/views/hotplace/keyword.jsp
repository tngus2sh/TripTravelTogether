<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <%@ include file="/WEB-INF/views/include/head.jsp" %>
  <link rel="stylesheet" href="/assets/css/main.css">
  
  <style>
  	body {
  	  text-align: center;
  	}
  	
  	.form-container {
  	  display: inline-block;
  	  width: 700px;
  	  margin-top: 2rem;
  	  border-radius: 0.5rem;
  	  border: 1px solid lightgray;
  	}
  	
  	.place-container {
  		display: inline-block;
  		width: 100%;
  		padding: 1.5rem 1rem;
  		text-align: center;
  		border: 1px solid white;
  	}
  </style>
</head>
<body>
  <!-- <%@ include file="/WEB-INF/views/include/check.jsp" %> -->
  
  <div class="form-container">
  	<form>
  	  <div class="row justify-content-center form-group-row">
  	    <div class="col-7 mt-3">
  	      <input type="text" id="search-keyword" class="form-control" placeholder="장소명">
  	    </div>
  	    <div class="col-2 mt-3">
  	      <button type="button" id="keyword-search-btn" class="btn submit-btn" style="width: 100%;">검색</button>
  	    </div>
  	    <div class="col-2 mt-3">
  	      <button type="button" id="title-submit-btn" class="btn submit-btn" style="width: 100%;">선택</button>
  	    </div>
      </div>
      <div id="result-container" class="mt-3"></div>
  	</form>
  </div>

  
  <script type="text/javascript"
	src="//dapi.kakao.com/v2/maps/sdk.js?appkey=31b399e99305a76bd610e0ad347aacd4&libraries=services,clusterer,drawing"></script>
  <script type="text/javascript" src="/assets/js/keyword.js"></script>
  <%@ include file="/WEB-INF/views/include/footer.jsp" %>
  
</body>
</html>
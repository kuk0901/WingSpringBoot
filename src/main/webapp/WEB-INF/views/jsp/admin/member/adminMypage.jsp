<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>

<head>
  <meta charset="UTF-8">
  <title>마이페이지</title>
  <script src="https://code.jquery.com/jquery-3.7.0.js"
          integrity="sha256-JlqSTELeR4TLqP0OG9dxM7yDPqX1ox/HfgiSLBj8+kM="
          crossorigin="anonymous">
  </script>
  <script defer  type="module" src="/js/admin/member/adminMypage.js"></script>

 <link rel="stylesheet" href="/css/admin/member/adminMypage.css">
  <jsp:include page="/WEB-INF/views/jsp/common/common.jsp"/>
</head>


<body>
<section id="root">
  <jsp:include page="/WEB-INF/views/jsp/components/NavAdmin.jsp"/>
  <div id="content">
    <div class="title-container">
      <div class="title btn_red text__white">
        마이페이지
      </div>
    </div>

    <main class="main-container">
      <div class="info_head">
        정보 수정
      </div>
      <form id="updateForm" >
      <div class="line">
        <label class="form-label">이메일:</label>
        <input type="email" name="email"  id="email" value="${memberInfo.email}" class="form-input"
               pattern="^(?=.{6,36}$)[a-z0-9_]+@[a-z0-9.\-]+\.[a-z]{2,}$"
               autocomplete="off"/>
        <span id="emailError"></span>
      </div>
      <div class="line">
        <label class="form-label">이름:</label>
        <input type="text" name="name" id="name" value="${memberInfo.name}" class="form-input"/>
      </div>
      <div class="line">
        <label class="form-label">비밀번호:</label>
        <input type="password" name="pwd" id="pwd" value="${memberInfo.pwd}" class="form-input"
               pattern="^(?=.*\d)(?=.*[a-zA-Z])[a-zA-Z\d]{8,21}$"
               autocomplete="off"/>
      </div>
      <div class="line">
        <label class="form-label">비밀번호 확인:</label>
        <input type="password" id="pwdCheck" value="${memberInfo.pwd}" class="form-input"
               pattern="^(?=.*\d)(?=.*[a-zA-Z])[a-zA-Z\d]{8,21}$"
               autocomplete="off"/>
        <span id="pwdError"></span>



      </div>
      <div class="line">
        <label class="form-label">휴대폰번호:</label>
        <input type="text" name="phone" id="phone" value="${memberInfo.phone}" class="form-input"
               pattern="^01[016789]-?[0-9]{3,4}-?[0-9]{4}$"
               maxlength="14"/>
      </div>
      <div class="line">
        <label class="form-label">가입일자:</label>
        <input type="text" value="<fmt:formatDate value='${memberInfo.creDate}' pattern='yyyy년 MM월 dd일' />" class="form-input"
        readonly/>
      </div>

        <div class="line submit-line">
          <button type="submit" id="submitBtn" class="btn btn__generate">정보 수정</button>
        </div>
        <input type="hidden" id="memberNo" value="${sessionScope.member.memberNo}" />
        </form>
    </main>


  </div>
</section>
</body>
</html>
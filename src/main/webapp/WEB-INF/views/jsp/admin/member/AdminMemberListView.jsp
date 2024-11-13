<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<jsp:include page="/WEB-INF/views/jsp/common/common.jsp"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>회원관리</title>
  <script defer src="/js/admin/member/adminMember.js"></script>
  <link rel="stylesheet" href="/css/admin/member/adminMember.css">
</head>
<body>
<section id="root">
  <jsp:include page="/WEB-INF/views/jsp/components/NavAdmin.jsp"/>
  <div id="content">
    <div class="title-container">
      <div class="title btn__yellow text__white">
        회원 관리
      </div>
    </div>

    <main  class="main-container">
      <div id="memberDetailContainer" class="table_container">
        <div>
          <div class="list-container one-line text__semibold text__center">
            <div class="box__s">회원번호</div>
            <div class="box__l">이메일</div>
            <div class="box__s">회원명</div>
            <div class="box__l">가입일자</div>
            <div class="box__l">전화번호</div>
            <div class="box__s">상품구매여부</div>
            <div class="box__s">탈퇴 신청</div>
            <div class="box__s">비고</div>
          </div>

          <c:forEach var="member" items="${memberList}">
              <div class="list-container one-line text__center">
                <div class="list-item box__s">${member.memberNo}</div>
                <div class="aTagStyle list-item box__l"
                    data-member-no="${member.memberNo}"
                    onclick="restRequestMemberDetail(this);">${member.email}</div>
                <div class="list-item box__s">${member.userName}</div>
                <div class="list-item box__l">
                  <fmt:formatDate value="${member.creDate}" pattern="yyyy년 MM월 dd일" />
                </div>
                <div class="list-item box__l">${member.phone}</div>
                <div class="list-item box__s">${member.productPurchase}</div>
                <div class="list-item box__s">${member.quitApply}</div>
                <div class="list-item box__s">
                  <button class="deleteMember"
                          data-member-no="${member.memberNo}"
                          data-quit-apply="${member.quitApply}"
                          data-product-purchase="${member.productPurchase}"
                  >
                    탈퇴
                  </button>
                </div>
              </div>

            </c:forEach>
          </tbody>
        </div>
      </div>
    </main>
    <div id="pagingContainer">
      <jsp:include page="/WEB-INF/views/jsp/common/Paging.jsp">
        <jsp:param value="${pagingMap}" name="pagingMap" />
      </jsp:include>
    </div>
  </div>

  <form id='pagingForm' action="./list" method="post">
    <input type="hidden" id='curPage' name="curPage"
           value="${pagingMap.pagingVo.curPage}">
  </form>
</section>
</body>
</html>
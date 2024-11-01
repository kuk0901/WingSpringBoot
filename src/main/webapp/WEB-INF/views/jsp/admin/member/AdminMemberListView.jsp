<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>

<head>
  <meta charset="UTF-8">
  <title>회원관리</title>
  <script src="https://code.jquery.com/jquery-3.7.0.js"
          integrity="sha256-JlqSTELeR4TLqP0OG9dxM7yDPqX1ox/HfgiSLBj8+kM="
          crossorigin="anonymous">
  </script>
  <script defer src="/js/admin/member/adminMember.js"></script>
  <script type="text/javascript">



  </script>
  <link rel="stylesheet" href="/css/admin/member/adminMember.css">
  <jsp:include page="/WEB-INF/views/jsp/common/common.jsp"/>
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
        <table>
          <tr class="text__semibold">
            <th>회원번호</th>
            <th>이메일</th>
            <th>회원명</th>
            <th>가입일자</th>
            <th>전화번호</th>
            <th>상품구매여부</th>
            <th>탈퇴 신청</th>
            <th>비고</th>
          </tr>
          <tbody id="memberBody"></tbody>
          <c:forEach var="member" items="${memberList}">
            <%-- memberNo가 1인 경우 출력 생략 --%>
              <tr>
                <td>${member.memberNo}</td>
                <td class='aTagStyle'
                    data-member-no="${member.memberNo}"
                    onclick="restRequestMemberDetail(this);">${member.email}</td>
                <td>${member.userName}</td>
                <td>
                  <fmt:formatDate value="${member.creDate}" pattern="yyyy년 MM월 dd일" />
                </td>
                <td>${member.phone}</td>
                <td>${member.productPurchase}</td>
                <td>${member.quitApply}</td>
                <td>
                  <button class="deleteMember"
                          data-member-no="${member.memberNo}"
                          data-quit-apply="${member.quitApply}"
                          data-product-purchase="${member.productPurchase}">
                    탈퇴
                  </button>
                </td>
              </tr>

            </c:forEach>
          </tbody>
        </table>
      </div>
    </main>
    <div id="pagingContainer">
      <jsp:include page="/WEB-INF/views/jsp/common/Paging.jsp"/>
    </div>
  </div>

  <form id='pagingForm' action="./list" method="post">
    <input type="hidden" id='curPage' name="curPage"
           value="${pagingMap.pagingVo.curPage}">
  </form>
</section>
</body>
</html>
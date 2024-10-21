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
  <%--<script defer src="/js/accountbook/accountbook.js"></script>--%>
  <script type="text/javascript">


      // 회원 상세 정보를 AJAX로 요청하는 함수
      function restRequestMemberDetail(tdElement) {
          var memberNo = $(tdElement).data('member-no')
          console.log("Member No: ", memberNo); // Member No 확인
          // 현재 페이지 입력값
          var curPageInput = $('#curPage').val();

          $.ajax({
              url: '/admin/member/' + memberNo + '?curPage=' + curPageInput,
              method: 'GET',
              dataType: 'json',
              success: function(data) {
                  // 받아온 데이터 처리
                  console.log(JSON.stringify(data));
                  var memberDetail = data.memberVo;
                  var curPage = data.curPage;
                  // Paging.jsp 숨기기
                  $('#pagingContainer').hide(); // ID가 pagingContainer인 요소 숨기기
                  var backButtonHtml = `
                  <div class="title-container" style="display: flex;">
                      <div class="title btn__yellow text__white" style="display: flex;  justify-content: flex-start;
                          flex-direction: column-reverse;">
                          회원 관리
                      </div>
                      <button id="backButton" class="btn btn__yellow" style="margin-left: 10px; height: 50px;
                       ">돌아가기</button>
                  </div>`;

                  // 상세 정보를 표시할 HTML 생성
                  var detailHtml = '<div class="main-container">';
                  detailHtml += '<table style="width: 100%;"><tr>';
                  detailHtml += '<th>회원번호</th><td>' + memberDetail.memberNo + '</td></tr>';
                  detailHtml += '<tr><th>이메일</th><td>' + memberDetail.email + '</td></tr>';
                  detailHtml += '<tr><th>회원명</th><td>' + (memberDetail.name ? memberDetail.name : '정보 없음') + '</td></tr>';
                  detailHtml += '<tr><th>전화번호</th><td>' + memberDetail.phone + '</td></tr>';
                  detailHtml += '<tr><th>가입일자</th><td>' + new Date(memberDetail.creDate).toLocaleDateString('ko-KR') + '</td></tr>';
                  detailHtml += '<tr><th>연봉</th><td>' + memberDetail.yearlySalary + ' 원</td></tr>';
                  detailHtml += '<tr><th>월급</th><td>' + memberDetail.monthlySalary + ' 원</td></tr>';
                  detailHtml += '<tr><th>상품구매여부</th><td>' + (memberDetail.productPurchase === 'Y' ? '구매함' : '구매안함') + '</td></tr>';
                  detailHtml += '<tr><th>탈퇴 신청</th><td>' + (memberDetail.quitApply === 'Y' ? '신청함' : 'N') + '</td></tr>';
                  detailHtml += '<tr><th>판매일자</th><td>' + new Date(memberDetail.sellingDate).toLocaleDateString('ko-KR') + '</td></tr>';
                  detailHtml += '<tr><th>보유카드 이름</th><td>' + memberDetail.cardName + '</td></tr>';
                  detailHtml += '<tr><th>가계부수</th><td>' + memberDetail.accountBookCount + '</td></tr>';
                  detailHtml += '<tr><th>카드 신청 날짜</th><td>' + memberDetail.sellingDate + '</td></tr>';
                  detailHtml += '<tr><th>카드 해지 날짜</th><td>' + (memberDetail.terminationDate ? memberDetail.terminationDate : 'N') + '</td></tr>';
                  detailHtml += '<tr><th>보유 카드 번호</th><td>' + memberDetail.memberCardNo + '</td></tr>';
                  detailHtml += '</table></div>';

                  var terminationReasonHtml = '<div class="termination-reason" style="margin-top: 20px;">';
                  terminationReasonHtml += '<h4>카드 해지 사유</h4>';
                  terminationReasonHtml += '<p>' + (memberDetail.terminationReason ? memberDetail.terminationReason : '해지 사유 없음') + '</p>';
                  terminationReasonHtml += '</div>';


                  $('#memberDetailContainer').html(detailHtml+terminationReasonHtml); // 상세 정보 표시
                  $('.title-container').html(backButtonHtml);
                  // 돌아가기 버튼 클릭 이벤트 핸들러 추가
                  $('#backButton').on('click', function() {
                      window.location.href = './list';  // 뒤로가기
                  });
                  // 페이지를 이동하는 대신 새로운 내용을 추가하여 화면 전환
              },
              error: function(jqXHR, textStatus, errorThrown) {
                  console.error("회원 상세 정보를 가져오는 데 실패했습니다: ", textStatus, errorThrown);
              }
          });
      }


  </script>
 <link rel="stylesheet" href="/css/member/adminMember.css">
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
          <tr>
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
              <tr>
                <td>${member.memberNo}</td>
                <td class='aTagStyle'
                    data-member-no="${member.memberNo}"
                    onclick="restRequestMemberDetail(this);">${member.email}</td>
                <td>${member.name}</td>
                <td>
                  <fmt:formatDate value="${member.creDate}" pattern="yyyyMMdd"/>
                </td>
                <td>${member.phone}</td>
                <td>${member.productPurchase}</td>
                <td>${member.quitApply}</td>
                <td>[삭제]</td>
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
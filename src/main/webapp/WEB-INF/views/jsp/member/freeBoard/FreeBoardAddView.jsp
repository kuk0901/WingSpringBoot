<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/jsp/common/common.jsp" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>FreeBoardAdd</title>
  <link rel="stylesheet" href="/css/member/freeBoard/memberFreeBoard.css" />
  <script defer type="module" src="/js/member/freeBoard/freeBoard.js"></script>
</head>
<body>

<section id="root">
  <jsp:include page="/WEB-INF/views/jsp/components/NavMember.jsp" />

  <div id="content" class="bg">

    <div class="title-container one-line">
      <div id="title" class="title btn__yellow text__white">
        게시글 추가
      </div>
    </div>

    <main class="main-container bg__white">
      <div class="freeBoard-container freeBoard-container-css">
        <div class="freeBoard-title one-line">
          <input type="hidden" id="memberNo"  value="${member.memberNo}">
          <input type="hidden" id="noticeBoardNo" value="${noticeBoardNo}">

          <div class="info-container one-line">
            <div class="label-container bg__gray text__black box__s text__center">
              <label for="titleVal">제목</label>
            </div>
            <div class="input-container">
              <input id="titleVal" type="text" class="info-item bg__white text__black box__l" placeholder="제목을 입력하세요">
            </div>
          </div>
        </div>

        <div class="freeBoard-sub one-line">
          <div class="info-container one-line">
            <div class="label-container bg__gray text__black box__s text__center">
              <label for="emailVal">작성자</label>
            </div>
            <div class="input-container">
              <input id="emailVal" type="text" class="info-writer bg__white text__black box__l" value="${member.email}" readonly>
            </div>
          </div>
          <div class="info-container one-line box__l">
            <div class="label-container bg__gray text__black box__s text__center">
              <label for="writeDate">작성일</label>
            </div>
            <div class="input-container">
              <input type="text" id="writeDate" class="info-date bg__white text__black box__l" value="${currentDate}" readonly>
            </div>
          </div>
        </div>

        <div class="info-container">
          <div class="label-container reason--title bg__gray text__black box__xl text__center">
            <label for="contentVal">내용</label>
          </div>
          <div class="input-container textarea-content">
            <textarea id="contentVal" class="contentArea" placeholder="내용을 입력하세요"></textarea>
          </div>
        </div>
      </div>
    </main>


    <div class="btn-container one-line">
      <a id="listMove" class="btn btn__generate listMove" href="/member/freeBoard/list?curPage=${curPage}&noticeBoardNo=${noticeBoardNo}&freeBoardSearch=${freeBoardSearch}">
        취소
      </a>
      <button id="addFreeBoard" class="btn btn__generate listMove text__center text__bold">
        게시글 등록
      </button>
    </div>

    <div class="hidden-ui"></div>


    <form id="pagingForm" action="./list" method="post">
      <input type="hidden" id="curPage" name="curPage" value="${pagingMap.pagingVo.curPage}" />
    </form>

  </div>
</section>

</body>
<script>

</script>
</html>
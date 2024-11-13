<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/jsp/common/common.jsp" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>FreeBoardListView</title>
  <link rel="stylesheet" href="/css/member/freeBoard/memberFreeBoard.css" />
  <script defer type="module" src="/js/member/freeBoard/freeBoard.js"></script>
</head>
<body>

<section id="root">
  <jsp:include page="/WEB-INF/views/jsp/components/NavMember.jsp" />

  <section id="memberSection">

    <div id="content" class="bg">

      <div class="title-container">
        <div class="title btn__yellow text__white">
          자유게시판 목록
        </div>
      </div>

      <div class="search-container one-line">
        <div class="input-container">
          <form id="searchForm" action="./list" method="post" class="one-line">
            <label for="search" class="searchFreeBoardName bg__gray text__black text__center text__semibold">제목</label>
            <input type="text" id="search" name="freeBoardSearch" class="info-item" value="${freeBoardSearch}">
            <input type="submit" id="searchFreeBoard" value="검색" class="searchFreeBoard btn btn__generate btn__post" />
          </form>
        </div>

        <div class="addBtn-container">
          <a class="btn btn__generate addMove text__center" href="./add?noticeBoardNo=${postVo.noticeBoardNo}&curPage=${pagingMap.pagingVo.curPage}&freeBoardSearch=${freeBoardSearch}">
            게시글 작성
          </a>
        </div>
      </div>

      <main class="main-container freeBoard__list bg__white">
        <div class="freeBoard-container">
          <div class="list-container list-container--title container-title one-line ">
            <div class="list--supply text__semibold text__center">번호</div>
            <div class="list--title text__semibold text__center">제목</div>
            <div class="list--supply text__semibold text__center">작성자</div>
            <div class="list--date text__semibold text__center">작성날짜</div>
          </div>

          <c:choose>
            <c:when test="${not empty freeBoardList}">
              <div class="bg__white">
                <c:forEach items="${freeBoardList}" var="freeBoardVo" >
                  <div class="list-container list-content one-line " data-free-board-no="${freeBoardVo.freeBoardNo}">
                    <input type="hidden" id="noticeBoardNo" value="${freeBoardVo.noticeBoardNo}">
                    <div class="list--supply text__center">${freeBoardVo.freeBoardNo}</div>
                    <div class="list--title text__center">${freeBoardVo.title}</div>
                    <div class="list--supply text__center">${freeBoardVo.email}</div>
                    <div class="list--date text__center">
                      <fmt:formatDate value="${freeBoardVo.creDate}" pattern="yyyy-MM-dd HH:mm:ss" />
                    </div>
                  </div>
                </c:forEach>
              </div>
            </c:when>
            <c:otherwise>
              <div class="list-container">
                <div class="list--div list__empty text__semibold text__correct text__center">등록된 게시글이 없습니다.</div>
              </div>
            </c:otherwise>
          </c:choose>
        </div>
      </main>

      <c:if test="${not empty freeBoardList}">
        <jsp:include page="/WEB-INF/views/jsp/common/Paging.jsp">
          <jsp:param value="${pagingMap}" name="pagingMap" />
        </jsp:include>
      </c:if>

      <form id="pagingForm" action="./list" method="post">
        <input type="hidden" id="curPage" name="curPage" value="${pagingMap.pagingVo.curPage}" />
        <input type="hidden" id="freeBoardSearch" name="freeBoardSearch" value="${freeBoardSearch}" />
      </form>

      <div class="hidden-ui"></div>
    </div>

    <jsp:include page="/WEB-INF/views/jsp/components/Footer.jsp"/>
  </section>

  <input id="abc" type="hidden" value="${sessionScope.member.memberNo}">


</section>



</body>
</html>
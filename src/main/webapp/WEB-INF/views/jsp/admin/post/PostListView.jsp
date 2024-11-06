<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/jsp/common/common.jsp" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>Post</title>
  <link rel="stylesheet" href="/css/admin/post/adminPost.css" />
  <script defer type="module" src="/js/admin/post/post.js"></script>
</head>
<body>

<section id="root">
  <jsp:include page="/WEB-INF/views/jsp/components/NavAdmin.jsp" />

  <div id="content" class="bg">

    <div class="title-container">
      <div class="title btn__yellow text__white">
        공지사항 목록
      </div>
    </div>

    <div class="search-container one-line">
      <div class="input-container">
        <form id="searchForm" action="./list" method="post" class="one-line">
          <label for="postSearch" class="searchPostName bg__gray text__black text__center text__semibold">제목</label>
          <input type="text" id="postSearch" name="postSearch" class="info-item" value="${postSearch}">
          <input type="submit" id="searchPost" value="검색" class="searchPost btn btn__generate btn__post" />
        </form>
      </div>
      <div class="addBtn-container">
        <a class="btn btn__generate addMove text__center" href="./list/add">
          공지사항 추가
        </a>
      </div>
    </div>

    <main class="main-container post__list">
      <div class="post-container">
        <div class="list-container list-container--title container-title one-line bg__white">
          <div class="list--supply text__semibold text__center">번호</div>
          <div class="list--title text__semibold text__center">제목</div>
          <div class="list--date text__semibold text__center">작성날짜</div>
          <div class="list--supply text__semibold text__center">작성자</div>
        </div>

        <c:choose>
          <c:when test="${not empty postList}">
            <div class="bg__white">
              <c:forEach items="${postList}" var="postVo" >
                <div class="list-container list-content one-line " data-post-no="${postVo.postNo}">
                  <div class="list--supply text__center">${postVo.postNo}</div>
                  <div class="list--title text__center">${postVo.title}</div>
                  <div class="list--date text__center">
                    <fmt:formatDate value="${postVo.creDate}" pattern="yyyy-MM-dd HH:mm:ss" />
                  </div>
                  <div class="list--supply text__center">${postVo.email}</div>
                </div>
              </c:forEach>
            </div>
          </c:when>
          <c:otherwise>
            <div class="list-container">
              <div class="list--div list__empty text__semibold text__correct text__center">등록된 공지사항이 없습니다.</div>
            </div>
          </c:otherwise>
        </c:choose>
      </div>
    </main>

    <jsp:include page="/WEB-INF/views/jsp/common/Paging.jsp">
      <jsp:param value="${pagingMap}" name="pagingMap" />
    </jsp:include>

    <form id="pagingForm" action="./list" method="post">
      <input type="hidden" id="curPage" name="curPage" value="${pagingMap.pagingVo.curPage}" />
    </form>

  </div>

  <input id="abc" type="hidden" value="${sessionScope.member.memberNo}">
</section>

</body>
</html>
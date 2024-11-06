<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script defer src="/js/common/paging.js"></script>

<div class="paging">
  <ul class="paging--list">
    <!--    ㄷ 한자 -->
    <c:if test="${pagingMap.pagingVo.prevBlock ne 1}">
      <li class="paging--item">
        <a href="#" onclick="goPage(${pagingMap.pagingVo.prevBlock});" class="btn__paging left-arrow">
          <img src="/img/arrow_left.svg" alt="≪" width="20" height="20" />
        </a>
      </li>
    </c:if>

    <c:forEach var="num" begin="${pagingMap.pagingVo.blockBegin}" end="${pagingMap.pagingVo.blockEnd}">
      <li id="pageButton${num}" class="paging--item">
        <a href="#" onclick="goPage(${num});" class="btn__paging">
          <c:out value="${num}" />
        </a>
      </li>
    </c:forEach>

    <c:if test="${pagingMap.pagingVo.curBlock lt pagingMap.pagingVo.totBlock}">
      <li class="paging--item">
        <a href="#" onclick="goPage(${pagingMap.pagingVo.nextBlock});" class="btn__paging right-arrow">
          <img src="/img/arrow_right.svg" alt="≫" width="20" height="20" />
        </a>
      </li>
    </c:if>

  </ul>
</div>
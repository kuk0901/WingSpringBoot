<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/jsp/common/common.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>MinusCategoryView</title>
<%--    <jsp:include page="/WEB-INF/views/jsp/common/common.jsp" />--%>
    <link rel="stylesheet" href="/css/auth/signin.css" />
<%--    <script defer src="/js/auth/signin.js"></script>--%>
</head>
<body>
<div>
    <button class="btn btn__blue"
            onclick="moveAddFunc()">
        카테고리 추가
    </button>
</div>
<div>
    <table>

        <tr>
            <th colspan="2">카테고리명</th>
            <th>비고</th>
        </tr>

        <c:choose>

            <c:when test="${not empty minusCategoryList}">

                <c:forEach var="minusCategoryVo" items="${minusCategoryList}">
                    <c:if test="${minusCategoryVo.categoryNo != 1}">
                        <tr>
                            <td colspan="2">
                                    ${minusCategoryVo.categoryName != '' ? minusCategoryVo.categoryName : '없음'}
                            </td>
                            <td>
                                <button class="btn btn__blue"
                                        onclick="moveModFunc(${minusCategoryVo.categoryNo})">수정</button>
                                <button class="btn bg__red text__white"
                                        onclick="moveDelFunc(${minusCategoryVo.categoryNo})">삭제</button>
                            </td>
                        </tr>
                    </c:if>
                </c:forEach>
            </c:when>

            <c:otherwise>
                <tr>
                    <td colspan="2">카테고리가 존재하지 않습니다</td>
                </tr>
            </c:otherwise>

        </c:choose>
    </table>
</div>
</body>
<script>
    function moveAddFunc() {
        // 수정 페이지로 이동
        window.location.href = '/category/add';
    }

    function moveModFunc(categoryNo) {
        // 수정 페이지로 이동
        window.location.href = '/category/modify?categoryNo=' + categoryNo;
    }

    function moveDelFunc(categoryNo) {
        if(confirm('정말로 삭제하시겠습니까?')) {
            fetch('/category/delete/' + categoryNo, {
                method: 'DELETE',
            })
                .then(response => {
                    if (response.ok) {
                        alert('카테고리가 성공적으로 삭제되었습니다.');
                        location.reload(); // 페이지 새로고침
                    } else {
                        alert('카테고리 삭제에 실패했습니다.');
                    }
                })
                .catch(error => {
                    console.error('Error:', error);
                    alert('카테고리 삭제 중 오류가 발생했습니다.');
                });
        }
    }
</script>
</html>
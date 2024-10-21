<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>가계부</title>
    <script src="https://code.jquery.com/jquery-3.7.0.js"
            integrity="sha256-JlqSTELeR4TLqP0OG9dxM7yDPqX1ox/HfgiSLBj8+kM="
            crossorigin="anonymous">
    </script>
    <style type="text/css">

        .table_container table {
            border: 1px solid black;
            border-collapse: collapse;
            width: 100%; /* 전체 너비를 사용하도록 설정 */
            border-spacing: 0; /* 셀 간의 간격을 없애기 */
        }

        .table_container th, .table_container td {
            padding: 10px; /* 셀 패딩 추가 */
            text-align: left; /* 텍스트 정렬 */
        }
    </style>

    <script type="text/javascript">

        function sortTable(column) {
            let sortDirection = $('#sortDirection').val();
            //정렬변경
            console.log('현재 sortDirection 값:', sortDirection);
            if (sortDirection == 'ASC') {
                sortDirection = 'DESC';
            } else {
                sortDirection = 'ASC';
            }
            console.log('변경된 sortDirection 값:', sortDirection);

            $('#sortDirection').val(sortDirection);
            console.log('업데이트된 hidden input 값:', $('#sortDirection').val());
            location.href = './list?orderBy=' + column + '&sortDirection=' + sortDirection;

        }

        // 		$.ajax({
        // 	        url: './list',
        // 	        type: 'GET',
        // 	        data: {
        // 	            orderBy: column,
        // 	            sortDirection: sortDirection
        // 	        },
        // 	        success: function(data) {

        //

        // 	        },
        // 	        error: function() {
        // 	            alert('데이터를 불러오는데 실패했습니다.');
        // 	        }
        // 	    });


    </script>
    <jsp:include page="/WEB-INF/views/jsp/common/common.jsp"/>
    <link rel="stylesheet" href="/css/auth/signup.css"/>
    <script defer type="module" src="/js/auth/signup.js"></script>
</head>


<body>
<section id="root">
    <jsp:include page="/WEB-INF/views/jsp/components/NavAdmin.jsp"/>
    <div id="content">
        <div class="title-container">
            <div class="title btn__yellow">가계부</div>
        </div>
        <div id="main-container">
            <div class="table_container">
                <table>
                    <tr>
                        <th>번호</th>
						<th>작성일자</th>
						<th>이메일</th>
                        <th>카테고리</th>
                        <th>결제 수단</th>
                        <th>금액</th>
                    </tr>
                    <c:forEach var="accountBook" items="${accountBookList}">
                        <tr>
                            <td>${accountBook.accountBookNo}</td>
							<td>${accountBook.creDate}</td>
							<td>${accountBook.memberEmail}</td>
                            <td>${accountBook.minusCategoryName}</td>
                            <td>${accountBook.paymentMethodName}</td>
                            <td>${accountBook.paymentAmount}</td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </div>
    </div>
</section>
</body>
</html>
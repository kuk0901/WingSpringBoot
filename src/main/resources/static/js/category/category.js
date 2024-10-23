// // 카테고리 추가 페이지로 이동하는 함수
// function moveAddFunc() {
//     fetch('/admin/api/category/add', {
//         method: 'GET',
//         headers: {
//             'Accept': 'application/json',
//         },
//     })
//         .then(response => response.json())
//         .then(data => {
//             // 현재 페이지의 내용을 지우고 새 내용으로 대체
//             document.body.innerHTML = '';
//
//             // 새 페이지의 내용을 동적으로 생성
//             const pageContent = `
//             <div id="addCategoryPage">
//                 <h2>카테고리 추가</h2>
//                 <form id="addCategoryForm">
//                     <input type="text" id="categoryName" placeholder="카테고리 이름" required>
//                     <button type="submit">추가</button>
//                     <button type="button" onclick="goBack()">취소</button>
//                 </form>
//             </div>
//         `;
//
//             document.body.insertAdjacentHTML('beforeend', pageContent);
//
//             // 폼 제출 이벤트 리스너 추가
//             document.getElementById('addCategoryForm').addEventListener('submit', function(e) {
//                 e.preventDefault();
//                 addCategory();
//             });
//         })
//         .catch(error => {
//             console.error('Error:', error);
//             alert('페이지 로딩 중 오류가 발생했습니다.');
//         });
// }

// 카테고리 추가 함수
function addCategory() {
    const categoryName = document.getElementById('categoryName').value;

    fetch('/admin/api/category/add', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ categoryName: categoryName })
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            console.log('Success:', data);
            alert('카테고리가 성공적으로 추가되었습니다.');
            goBack(); // 목록 페이지로 돌아가기
        })
        .catch((error) => {
            console.error('Error:', error);
            alert('카테고리 추가 중 오류가 발생했습니다.');
        });
}

// 이전 페이지로 돌아가는 함수
function goBack() {
    window.history.back();
}
/*
// js ajax 부분
$("#등록버튼").click(function() {
    const categoryName = $("#categoryName").val() // 등록할 카테고리 명
    const division = $("#division").val() // minus || plus -> table을 위해 선택

    $.ajax({
        data: { categoryName: categoryName, division: division }
    })
})

*/
/*
// controller 부분
division == plus
1. selet로 중복 검사
1-1. 있으면 바로 error
1-2. 없으면 insert
2. insert 됐는지 조회로 확인 (해당 table에서 해당 카테고리 명 where절 사용해서 조회)

division == minus
1. selet로 중복 검사
1-1. 있으면 바로 error
1-2. 없으면 insert
2. insert 됐는지 조회로 확인 (해당 table에서 해당 카테고리 명 where절 사용해서 조회)
*/
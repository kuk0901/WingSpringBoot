function formatDate(dateString) {
    if (!dateString) return '해당 사항 없음'; // null 또는 undefined일 경우
    const date = new Date(dateString);
    const year = date.getFullYear();
    const month = date.getMonth() + 1; // getMonth는 0부터 시작
    const day = date.getDate();
    return `${year}년 ${month}월 ${day}일`;
}

// 회원 상세 정보를 AJAX로 요청하는 함수
function restRequestMemberDetail(tdElement) {
    var memberNo = $(tdElement).data('member-no')
    console.log("Member No: ", memberNo); // Member No 확인
    // 현재 페이지 입력값
    var curPageInput = $('#curPage').val();

    $.ajax({
        url: '/admin/api/member/' + memberNo + '?curPage=' + curPageInput,
        method: 'GET',
        dataType: 'json',
        success: function (data) {
            // 받아온 데이터 처리
            console.log(JSON.stringify(data));
            var memberDetail = data.memberVo;
            var curPage = data.curPage;
            // Paging.jsp 숨기기
            $('#pagingContainer').hide(); // ID가 pagingContainer인 요소 숨기기
            var backButtonHtml = `
                  <div class="title-containerSecond" >
                      <div class="title btn__yellow text__white">
                          회원 관리
                      </div>
                  </div>
                    <div class="btn-back-container" >
                        <button id="backButton" class="btn btn__generate">돌아가기</button>
                   <div>`;

            // 상세 정보를 표시할 HTML 생성
            var detailHtml = `
           
  
              <div class="one-line two-info ">
                <div class="label">이메일</div>
                <div class="value">${memberDetail.email}</div>
             
             
                <div class="label">회원명</div>
                <div class="value">${memberDetail.userName ? memberDetail.userName : '정보 없음'}</div>
             </div>
              <div class="one-line two-info ">
                <div class="label">전화번호</div>
                <div class="value">${memberDetail.phone}</div>
            
          
                <div class="label">가입일자</div>
                <div class="value">${formatDate(memberDetail.creDate)}</div>
           
              </div>
              <div class="one-line three-info">
                <div class="label">연봉</div>
                <div class="value">${memberDetail.yearlySalary}만원</div>
              
                <div class="label">월급</div>
                <div class="value">${memberDetail.monthlySalary}만원</div>
             
                <div class="label">상품구매여부</div>
                <div class="value">${memberDetail.productPurchase}</div>
              </div>
              <div class="one-line two-info">
                <div class="label">가계부 작성수</div>
                <div class="value">${memberDetail.accountBookCount}</div>
                
                <div class="label">게시글 작성수</div>
                <div class="value">${memberDetail.accountBookCount}</div>
              </div>
              <div class="one-line">
                <div class="label">보유카드 이름</div>
                <div class="value">${memberDetail.cardName ? memberDetail.cardName : '해당 사항 없음'}</div>
              
                <div class="label">카드 신청 날짜</div>
                <div class="value">${memberDetail.sellingDate ? formatDate(memberDetail.sellingDate): '해당 사항 없음'}</div>
             
                <div class="label">탈퇴 신청</div>
                <div class="value">${memberDetail.quitApply}</div>
              </div>
          
              <div class="one-line two-info">
                <div class="label">카드 해지 날짜</div>
                <div class="value">${memberDetail.terminationDate ? formatDate(memberDetail.terminationDate) : 'N'}</div>
             
                <div class="label">보유 카드 번호</div>
                <div class="value">${memberDetail.memberCardNo ? memberDetail.memberCardNo : '미보유'}</div>
              </div>
            
              <div class="termination-reason-container">
                <h4>카드 해지 사유</h4>
                <p>${memberDetail.terminationReason ? memberDetail.terminationReason : '해지 사유 없음'}</p>
              </div>
          
                    



                    `;


            $('#memberDetailContainer').html(detailHtml); // 상세 정보 표시
            $('.title-container').html(backButtonHtml);
            // 돌아가기 버튼 클릭 이벤트 핸들러 추가
            $('#backButton').on('click', function () {
                window.location.href = './list?curPage=' + curPage;
            });
            // 페이지를 이동하는 대신 새로운 내용을 추가하여 화면 전환
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.error("회원 상세 정보를 가져오는 데 실패했습니다: ", textStatus, errorThrown);
        }
    });
}

$(document).on('click', '.deleteMember', function() {
    var memberNo = $(this).data('member-no');
    var quitApply = $(this).data('quit-apply');
    var productPurchase=$(this).data('product-purchase');

   /* if (productPurchase === 'Y') {
        // 구매한 상품이 있는 유저는 탈퇴 불가
        alert("구독한 상품이 있는 유저는 탈퇴할 수 없습니다.");
        return;  // 함수 종료, 더 이상 진행하지 않음 ->기능삭제
    }*/
    if (quitApply === 'Y') {
        // QUIT_APPLY가 Y일 경우, 바로 삭제
        deleteMember(memberNo);
    } else if (quitApply === 'N') {
        // QUIT_APPLY가 N일 경우, 확인 메시지 표시
        if (confirm("정말 삭제하시겠습니까?")) {
            deleteMember(memberNo);
        }
    }
});

function deleteMember(memberNo) {
    $.ajax({
        url: '/admin/api/member/delete/' + memberNo,
        type: 'PATCH',
        success: function(response) {
            alert(response); // 삭제 성공 메시지 표시
            location.reload(); // 페이지 새로 고침
        },
        error: function(xhr, status, error) {
            alert("삭제    중 오류가 발생했습니다: " + error);
        }
    });
}

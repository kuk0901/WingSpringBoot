import {showAlertMsg} from "../../util/toast.js";

$('.list-content').click(function() {
  const sellingCardNo = $(this).data('selling-card-no');
  const curPage = $('#curPage').val();
  const cardNo = $('#cardNo').val();
  const termination = $('#termination').val();

  $.ajax({
    url: `/admin/api/salesDashboard/${sellingCardNo}`,
    type: 'GET',
    data: { curPage: curPage, cardNo: cardNo, termination: termination },
    success: function(res) {
      createDetailView(res);
    },
    error: function(xhr, status, error) {
      const msg = xhr.responseJSON ? xhr.responseJSON.alertMsg : "알 수 없는 오류가 발생했습니다.";

      showAlertMsg(msg);
    }
  });
});

function createDetailView(data) {
  const sellingCardDetail = `
    <div class="title-container">
      <div id="titleName" class="title btn__yellow text__white">
        판매 카드 상세
      </div>
    </div>

    <main class="card__detail">
      <div class="flex-container">
        <div class="info-container">
          <div class="info-item bg__gray text__black box__l text__center">카드명</div>
          <div class="info-item bg__white text__black box__l text__center">${data.CARDNAME}</div>
        </div>
        <div class="info-container">
          <div class="info-item bg__gray text__black box__l text__center">이메일</div>
          <div class="info-item bg__white text__black box__l text__center">${data.EMAIL}</div>
        </div>
        <div class="info-container">
          <div class="info-item bg__gray text__black box__l text__center">판매 날짜</div>
          <div class="info-item bg__white text__black box__l text__center">${data.SELLINGDATE}</div>
        </div>
        <div class="info-container">
          <div class="info-item bg__gray text__black box__l text__center">판매 분류</div>
          <div class="info-item bg__white text__black box__l text__center">${data.CARDRECOMMEND}</div>
        </div>
        <div class="info-container">
          <div class="info-item bg__gray text__black box__l text__center">카드 해지 신청</div>
          <div class="info-item bg__white text__black box__l text__center">${data.CARDTERMINATION}</div>
        </div>
      </div>
      
      <div class="flex-container flex-col">
        <div class="info-item reason--title bg__gray text__black box__xl text__center">카드 해지 신청 사유</div>
        <div class="reason--content bg__white text__black box__xl box_reason">${data.TERMINATIONREASON ? data.TERMINATIONREASON : ""}</div>
      </div>
    </main>
    
    <div class="btn-container">
      <button id="listMove" class="btn btn__generate btn__p" data-cur-page="${data.curPage}" data-card-no="${data.cardNo}" data-termination="${data.termination}">
        돌아가기
      </button>
    </div>
  `

  $("#content").html(sellingCardDetail);

  // 돌아가기 버튼에 이벤트 리스너 추가
  $("#listMove").click(function() {
    const curPage = $(this).data("cur-page");
    const cardNo = $(this).data("card-no");
    const termination = $(this).data("termination")
    window.location.href = `/admin/salesDashboard/list?curPage=${curPage}&cardNo=${cardNo}&termination=${termination}`;
  });
}
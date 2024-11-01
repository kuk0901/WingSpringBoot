$('.list-content').click(function() {
  const inquiryNo = $(this).data('inquiry-no');
  const curPage = $('#curPage').val() || '1'; // 기본값 설정
  const answerTermination = $('#answerTermination').val();

  $.ajax({
    url: `/member/api/cs/inquiry/${inquiryNo}`,
    type: 'GET',
    data: { curPage: curPage },
    success: function(res) {
      console.log('Server response:', res);
      createDetailView(res, answerTermination);
    },
    error: function(xhr, status, error) {
      console.error("Error fetching details:", error);
      console.log("Status:", status);
      console.log("Response:", xhr.responseText);
      alert("데이터를 불러오는 데 실패했습니다. 관리자에게 문의하세요.");
    }
  });
});

function formatDate(dateString) {
  const date = new Date(dateString);
  const year = date.getFullYear();
  const month = String(date.getMonth() + 1).padStart(2, '0');
  const day = String(date.getDate()).padStart(2, '0');
  return `${year}-${month}-${day}`;
}

function createDetailView(data, answerTermination) {
  const formattedInquiryDate = formatDate(data.INQUIRYDATE);
  const formattedAnswerDate = data.ANSWERDATE ? formatDate(data.ANSWERDATE) : '';

  console.log("Data received in createDetailView:", data);
  const inquiryDetail = `
    <div class="title-container one-line">
      <div id="title" class="title btn__yellow text__white">
        1대1 문의사항 상세
      </div>
      <div class="btn-container">
        <button id="listMove" class="btn btn__generate listMove text__center" data-cur-page="${data.curPage}" data-answer-termination="${data.ANSWERTERMINATION}">
          돌아가기
        </button>
    </div>
    </div>

    <main class="main-container bg__white">
      <div class="inquiry-container">
        <div class="inquiry-title one-line">
          <input type="hidden" id="inquiryNo" value="${data.INQUIRYNO}">
        
          <div class="info-title bg__gray text__black box__l text__center">제목</div>
          <div class="info-item bg__white text__black box__l">${data.TITLE}</div>

          <div class="info-title bg__gray text__black box__l text__center">분류</div>
          <div class="info-dv-item bg__white text__black box__l">${data.DIVISION}</div>

        </div>
        
        <div class="inquiry-sub one-line">
        
          <div class="info-title bg__gray text__black box__l text__center">작성자</div>
          <div class="info-writer bg__white text__black box__l">${data.INQUIRYWRITEREMAIL}</div>
        
          <div class="info-title bg__gray text__black box__l text__center">작성일</div>
          <div class="info-date bg__white text__black box__l">${formattedInquiryDate}</div> 
          
        </div>
        
        <div class="info-content-div reason--title bg__gray text__black box__xl text__center">문의 내용</div>
      
        <div class="info-content bg__white text__black box__l">${data.INQUIRYCONTENT}</div>
        
      </div>
      
      <div class="inquiry-comment-container answer-container">
      
        ${data.ANSWERCONTENT ? `
          <div class="reply-container one-line">
            <input type="hidden" id="inquiryCommentNo" value="${data.INQUIRYCOMMENTNO}">
            <div class="info-comment reason--title bg__gray text__black box__xl text__center">답변</div>
          </div>
          <div class="answer-info">
            <div class="info-comment-container one-line">
              <div class="info-item bg__gray text__black box__l text__center">답변 작성자</div>
              <div class="info-detail bg__white text__black box__l">${data.ANSWERWRITEREMAIL}</div>
              <div class="info-item bg__gray text__black box__l text__center">답변 등록일</div>
              <div class="info-detail bg__white text__black box__l">${formattedAnswerDate}</div>
            </div>
          </div>
          <div class="answer-content">
            <div class="reason--content bg__white text__black">${data.ANSWERCONTENT}</div>
          </div>
        ` : `
          <div class="reply-container one-line">
            <div class="info-comment reason--title bg__gray text__black box__xl text__center">답변</div>
          </div>
          <div class="no-data-container bg__white text__black text__center">답변이 등록되지 않은 문의글입니다.</div>
        `}
      </div>
    </main>
  `;

  $("#content").html(inquiryDetail);

  $("#listMove").click(function() {
    const curPage = $(this).data('cur-page') || '1';
    const answerTermination = $(this).data('answer-termination');
    window.location.href = `/member/cs/inquiry/list?curPage=${curPage}&answerTermination=${answerTermination}`;
  });

}

$("#addInquiry").click(function(e) {
  e.preventDefault(); // 기본 이벤트 동작 방지

  // 입력 필드에서 데이터 수집
  var inquiryData = {
    title: $(".info-item").val(),
    division: $(".info-dv-item").val(),
    writer: $(".info-writer").val(),
    date: $(".info-date").val(),
    content: $("textarea").val()
  };

  // AJAX 요청 보내기
  $.ajax({
    url: '/member/api/cs/inquiry/add', // 요청을 보낼 URL
    type: 'POST',
    contentType: 'application/json',
    data: JSON.stringify(inquiryData),
    success: function(response) {
      console.log('Success:', response);
      alert('문의가 성공적으로 등록되었습니다.');
      // 성공 후 추가 작업 (예: 페이지 리로드 또는 다른 페이지로 이동)
    },
    error: function(xhr, status, error) {
      console.error('Error:', error);
      alert('문의 등록 중 오류가 발생했습니다.');
    }
  });
});


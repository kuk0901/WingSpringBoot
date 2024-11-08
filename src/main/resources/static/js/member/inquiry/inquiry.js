const $abc = $("#abc").value;

$("#listMove").click(function () {
  const curPage = $(this).data('cur-page') || '1';
  window.location.href = `/member/cs/inquiry/list?curPage=${curPage}`;
});

$("#addInquiry").click(function (e) {
  e.preventDefault();

  const memberNo = $("#memberNo").val();
  const title = $("#titleVal").val();
  const division = $("#divisionVal").val();
  const creDate = $("#writeDate").val();
  const content = $("#contentVal").val();

  const inquiryData = {
    memberNo: memberNo,
    title: title,
    division: division,
    creDate: creDate,
    content: content
  };

  console.log(inquiryData);

  $.ajax({
    url: '/member/api/cs/inquiry/list/add',
    type: 'POST',
    contentType: 'application/json', // JSON 형식으로 전송
    data: JSON.stringify(inquiryData),
    success: function (res) {
      console.log("문의가 성공적으로 등록되었습니다:", res);
      alert("문의가 성공적으로 등록되었습니다.");
      window.location.href = '/member/cs/inquiry/list';
    },
    error: function (xhr, status, error) {
      console.error("문의 등록 실패:", error);
      alert("문의 등록에 실패하였습니다.");
    }
  });
});

$('.list-container').click(function () {
  const inquiryNo = $(this).data('inquiry-no');
  const curPage = $('#curPage').val() || '1';

  $.ajax({
    url: `/member/cs/inquiry/list/${inquiryNo}`,
    type: 'GET',
    data: {curPage: curPage},
    success: function (res) {
      console.log('Server response:', res);
      createDetailView(res);
    },
    error: function (xhr, status, error) {
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
  const hours = String(date.getHours()).padStart(2, '0');
  const minutes = String(date.getMinutes()).padStart(2, '0');
  const seconds = String(date.getSeconds()).padStart(2, '0');

  return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
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
        
          <div class="one-line">
            <div class="info-title bg__gray text__black box__l text__center">제목</div>
            <div class="info-item bg__white text__black box__l">${data.TITLE}</div>
          </div>
          <div class="one-line">
            <div class="info-title bg__gray text__black box__l text__center">분류</div>
            <div class="info-dv-item bg__white text__black box__l">${data.DIVISION}</div>
          </div>
        </div>
        
        <div class="inquiry-sub one-line">
          <div class="one-line">
            <div class="info-title bg__gray text__black box__l text__center">작성자</div>
            <div class="info-writer bg__white text__black box__l">${data.INQUIRYWRITEREMAIL}</div>
          </div>
          <div class="one-line">
            <div class="info-title bg__gray text__black box__l text__center">작성일</div>
            <div class="info-date bg__white text__black box__l">${formattedInquiryDate}</div> 
          </div>
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
              <div class="info-item bg__gray text__black box__l text__center">관리자</div>
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
    window.location.href = `/member/cs/inquiry/list?curPage=${curPage}`;
  });

}
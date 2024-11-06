const $abc = $("#abc").value;

$('.list-content').click(function() {
  const inquiryNo = $(this).data('inquiry-no');
  const curPage = $('#curPage').val() || '1'; // 기본값 설정
  const answerTermination = $('#answerTermination').val();

  $.ajax({
    url: `/admin/api/cs/inquiry/${inquiryNo}`,
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
            <div class="btn-container">
              <button id="modReply" class="btn addReply btn__generate" data-cur-page=${data.curPage}" data-no="${data.INQUIRYNO}">
              답변 수정
              </button>
            </div>
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
            <div class="btn-container">
              <button id="addReply" class="btn addReply btn__generate" data-cur-page="${data.curPage}" data-no="${data.INQUIRYNO}">
              답변 작성
              </button>
            </div>
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
    window.location.href = `/admin/cs/inquiry/list?curPage=${curPage}&answerTermination=${answerTermination}`;
  });

  $("#modReply").click(function(e) {
    e.preventDefault();

    const no = $(this).data("no");

    $.ajax({
      url: `/admin/api/cs/inquiry/update/${no}`,
      type: 'POST',
      success: function(res) {
        console.log(res);
        createUpdateView(res);
      },
      error: function(xhr, status, error) {
        console.log(error);
      }
    })
  })

  $("#addReply").click(function (e) {
    e.preventDefault();

    const no = $(this).data("no");

    $.ajax({
      url: `/admin/api/cs/inquiry/add/${no}`,
      type: 'POST',
      success: function(res) {
        console.log(res);
        createAddView(res);
      },
      error: function(xhr, status, error) {
        console.log(error);
      }
    })
  })
}

function createUpdateView(res) {

  const formattedInquiryDate = formatDate(res.INQUIRYDATE);
  const formattedAnswerDate = res.ANSWERDATE ? formatDate(res.ANSWERDATE) : '';

  console.log(res)
  const inquiryCommentUpdate = `
  <div class="title-container">
    <div id="title" class="title btn__yellow text__white">
      1대1 문의사항 수정
    </div>
  </div>

  <main class="main-container bg__white">
    <div class="inquiry-container">
      <div class="inquiry-title one-line">
        <input type="hidden" id="inquiryNo" value="${res.INQUIRYNO}">  
        <div class="info-title bg__gray text__black box__l text__center">제목</div>
        <div class="info-item bg__white text__black box__l">${res.TITLE}</div>
        <div class="info-title bg__gray text__black box__l text__center">분류</div>
        <div class="info-dv-item bg__white text__black box__l">${res.DIVISION}</div>
      </div>
      
      <div class="inquiry-sub one-line"> 
        <div class="info-title bg__gray text__black box__l text__center">작성자</div>
        <div class="info-writer bg__white text__black box__l">${res.INQUIRYWRITEREMAIL}</div>
        <div class="info-title bg__gray text__black box__l text__center">작성일</div>
        <div class="info-date bg__white text__black box__l">${formattedInquiryDate}</div> 
      </div>
      
      <div class="info-content-div reason--title bg__gray text__black box__xl text__center">문의 내용</div>   
      <div class="info-content bg__white text__black box__l">${res.INQUIRYCONTENT}</div>   
    </div>
    
    <div class="inquiry-comment-container answer-container">
      
      <div class="answer-info">
        <div class="info-comment-container one-line">
          <div class="info-item bg__gray text__black box__l text__center">관리자</div>
          <div class="info-detail bg__white text__black box__l">${res.ANSWERWRITEREMAIL}</div>
        </div>
      </div>
      
      <div class="reply-container one-line">
        <input type="hidden" id="commentNo" value="${res.INQUIRYCOMMENTNO}">
        <div class="info-comment reason--title bg__gray text__black box__xl text__center">답변</div>
      </div>
      
      <div class="answer-content">
        <div class="reason--content bg__white text__black">
          <textarea id="answerContent" class="answerContent">${res.ANSWERCONTENT}</textarea>
        </div>
      </div>
    </div>
    
    <div class="btn-container answer-btn-container one-line">
        <button id="cancleUpdate" class="btn btn__generate listMove">돌아가기</button>
        <button id="updateReply" class="btn btn__generate listUpdate" data-mod="${res.INQUIRYCOMMENTNO}">답변 수정</button>
    </div>
  </main>
  `

  $("#content").html(inquiryCommentUpdate);

  $("#updateReply").click(function(e) {

    e.preventDefault();

    const inquiryCommentNo = $(this).data("mod");
    const content = $("#answerContent").val();

    $.ajax({
      url: `/admin/api/cs/inquiry/update/${inquiryCommentNo}`,
      type: 'PATCH',
      contentType: 'application/json',
      data: JSON.stringify({ content: content }),
      success: function(res) {
        console.log("작업 성공:", res);
        if ($("#commentNo").val()) {
          alert("답변이 성공적으로 수정되었습니다.");
        }
      },
      error: function(xhr, status, error) {
        console.log("수정 실패:", error);
        alert("답변 수정에 실패했습니다.");
      }
    });
  });

  $("#cancleUpdate").on("click", function() {
    // 취소 버튼 클릭 시 수행할 동작 (예: 이전 페이지로 돌아가기)
    window.location.href = '/admin/cs/inquiry/list';
  });

}

function createAddView(res) {
  const formattedInquiryDate = formatDate(res.INQUIRYDATE);
  const formattedAnswerDate = res.ANSWERDATE ? formatDate(res.ANSWERDATE) : '';

  console.log(res)
  const inquiryCommentAdd = `
  <div class="title-container">
    <div id="title" class="title btn__yellow text__white">
      1대1 문의글 답변 작성
    </div>
  </div>

  <main class="main-container bg__white">
    <div class="inquiry-container">
      <div class="inquiry-title one-line">
        <input type="hidden" id="inquiryNo" value="${res.INQUIRYNO}">  
        <div class="info-title bg__gray text__black box__l text__center">제목</div>
        <div class="info-item bg__white text__black box__l">${res.TITLE}</div>
        <div class="info-title bg__gray text__black box__l text__center">분류</div>
        <div class="info-dv-item bg__white text__black box__l">${res.DIVISION}</div>
      </div>
      
      <div class="inquiry-sub one-line"> 
        <div class="info-title bg__gray text__black box__l text__center">작성자</div>
        <div class="info-writer bg__white text__black box__l">${res.INQUIRYWRITEREMAIL}</div>
        <div class="info-title bg__gray text__black box__l text__center">작성일</div>
        <div class="info-date bg__white text__black box__l">${formattedInquiryDate}</div> 
      </div>
      
      <div class="info-content-div reason--title bg__gray text__black box__xl text__center">문의 내용</div>   
      <div class="info-content bg__white text__black box__l">${res.INQUIRYCONTENT}</div>   
    </div>
    
    <div class="inquiry-comment-container answer-container">
      
      <div class="answer-info">
        <div class="info-comment-container one-line">
          <div class="info-item bg__gray text__black box__l text__center">관리자</div>
          <div class="info-detail bg__white text__black box__l">${res.adminEmail}</div>
        </div>
      </div>
      
      <div class="reply-container one-line">
        <div class="info-comment reason--title bg__gray text__black box__xl text__center">답변</div>
      </div>
      
      <div class="answer-content">
        <div class="reason--content bg__white text__black">
          <textarea id="answerContent" class="answerContent"></textarea>
        </div>
      </div>
    </div>
    
    <div class="btn-container answer-btn-container one-line">
        <button id="cancleAdd" class="btn btn__generate listMove">돌아가기</button>
        <button id="replyAdd" class="btn btn__generate listUpdate" data-add="${res.INQUIRYNO}">답변 추가</button>
    </div>
  </main>
  
  <input type="hidden" id="curPage" value="${res.curPage || '1'}">
  <input type="hidden" id="answerTermination" value="${res.answerTermination || ''}">
  `

  $("#content").html(inquiryCommentAdd);

  $("#replyAdd").click(function(e) {

    e.preventDefault();

    const inquiryNo = $(this).data("add");
    const content = $("#answerContent").val();
    const curPage = $('#curPage').val() || '1';
    const answerTermination = $('#answerTermination').val() || '';

    $.ajax({
      url: `/admin/api/cs/inquiry/add/${inquiryNo}`,
      type: 'PATCH',
      contentType: 'application/json',
      data: JSON.stringify({ CONTENT: content }),
      success: function(res) {
        console.log("작업 성공:", res);
        alert("답변이 성공적으로 추가되었습니다.");
        // 성공 후 리스트 페이지로 이동하거나 현재 페이지를 새로고침
        window.location.href = `/admin/cs/inquiry/list?curPage=${curPage}&answerTermination=${answerTermination}`;
      },
      error: function(xhr, status, error) {
        console.log("추가 실패:", error);
        alert("답변 추가에 실패했습니다.");
      }
    });
  });

  $("#cancleAdd").on("click", function() {
    // 취소 버튼 클릭 시 수행할 동작 (예: 이전 페이지로 돌아가기)
    window.location.href = '/admin/cs/inquiry/list';
  });
}
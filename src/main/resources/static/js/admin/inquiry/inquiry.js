import { showAlertMsg, checkAndShowStoredMessage } from "../../util/toast.js";
checkAndShowStoredMessage();

const $abc = $("#abc").value;

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

$('.list-content').click(function() {
  const inquiryNo = $(this).data('inquiry-no');
  const curPage = $('#curPage').val() || '1'; // 기본값 설정
  const answerTermination = $(this).data('answer-termination');
  const inquirySearch = $('#search').val();

  $.ajax({
    url: `/admin/api/cs/inquiry/${inquiryNo}`,
    type: 'GET',
    data: {
      curPage: curPage,
      inquirySearch: inquirySearch
    },
    success: function(res) {
      createDetailView(res.inquiryVo, curPage, inquirySearch);
    },
    error: function(res) {
      showAlertMsg(res.alertMsg);
    }
  });
});

function createDetailView(data, curPage, inquirySearch) {
  const formattedInquiryDate = formatDate(data.INQUIRYDATE);
  const formattedAnswerDate = data.ANSWERDATE ? formatDate(data.ANSWERDATE) : '';

  console.log(inquirySearch);

  const inquiryDetail = `
    <div class="title-container one-line">
      <div id="title" class="title btn__yellow text__white">
        1대1 문의사항 상세
      </div>
      <div class="btn-container">
        <a id="listMove" class="btn btn__generate listMove text__center" href="/admin/cs/inquiry/list?curPage=${curPage}&inquirySearch=${inquirySearch}">
          돌아가기
        </a>
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
            <div class="info-dv-item bg__white text__black box__l">${data.DIVISION ? data.DIVISION : "X"}</div>
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
            <div class="btn-container non-m-container">
              <button id="modReply" class="btn addReply btn__generate" data-cur-page="${curPage}" 
              data-inquiry-no="${data.INQUIRYNO}" data-inquiry-search="${inquirySearch}">
              답변 수정
              </button>
            </div>
          </div>
          <div class="answer-info">
            <div class="info-comment-container one-line">
              <div class="one-line">
                <div class="info-item bg__gray text__black box__l text__center">관리자</div>
                <div class="info-detail bg__white text__black box__l">${data.ANSWERWRITEREMAIL}</div>
              </div>
              <div class="one-line">
                <div class="answer-creDate info-item bg__gray text__black box__l text__center">답변 등록일</div>
                <div class="info-detail bg__white text__black box__l">${formattedAnswerDate}</div>
              </div>
            </div>
          </div>
          <div class="answer-content">
            <div class="reason--content bg__white text__black">${data.ANSWERCONTENT}</div>
          </div>
        ` : `
          <div class="reply-container one-line">
            <div class="info-comment reason--title bg__gray text__black box__xl text__center">답변</div>
            <div class="btn-container">
              <button id="addReply" class="btn addReply btn__generate" data-cur-page="${curPage}"
               data-no="${data.INQUIRYNO}" data-inquiry-search="${inquirySearch}">
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

  $("#modReply").click(function(e) {
    e.preventDefault();

    const inquiryNo = $(this).data("inquiry-no");
    const curPage = $(this).data("cur-page");
    const inquirySearch = $(this).data("inquiry-search");

    $.ajax({
      url: `/admin/api/cs/inquiry/update/${inquiryNo}`,
      type: 'GET',
      data: {
        inquiryNo: inquiryNo,
        curPage: curPage,
        inquirySearch: inquirySearch
      },
      success: function(res) {
        createUpdateView(res.inquiryVo, curPage, inquirySearch);
      },
      error: function(res) {
        showAlertMsg(res.alertMsg);
      }
    })
  })

  $("#addReply").click(function (e) {
    e.preventDefault();

    const inquiryNo = $(this).data("no");
    const curPage = $(this).data("cur-page");
    const inquirySearch = $(this).data("inquiry-search");

    $.ajax({
      url: `/admin/api/cs/inquiry/add/${inquiryNo}`,
      type: 'GET',
      data: {
        inquiryNo: inquiryNo,
        curPage: curPage,
        inquirySearch: inquirySearch
      },
      success: function(res) {
        createAddView(res.inquiryVo, curPage, inquirySearch);
      },
      error: function(res) {
        showAlertMsg(res.alertMsg);
      }
    })
  })
}

function createUpdateView(res, curPage, inquirySearch) {

  const formattedInquiryDate = formatDate(res.INQUIRYDATE);
  const formattedAnswerDate = res.ANSWERDATE ? formatDate(res.ANSWERDATE) : '';

  console.log(res)
  const inquiryCommentUpdate = `
  <div class="title-container">
    <div id="title" class="title btn__yellow text__white">
      1대1 문의사항 수정
    </div>
  </div>

  <main class="main-container bg__white main-p-container">
    <div class="inquiry-container">
      <div class="inquiry-title one-line">
        <input type="hidden" id="inquiryNo" value="${res.INQUIRYNO}">  
        <div class="info-title bg__gray text__black box__l text__center">제목</div>
        <div class="info-item bg__white text__black box__l">${res.TITLE}</div>
        <div class="info-title bg__gray text__black box__l text__center">분류</div>
        <div class="info-dv-item bg__white text__black box__l">${res.DIVISION ? res.DIVISION : "X"}</div>
      </div>
      
      <div class="inquiry-sub one-line"> 
        <div class="one-line">
          <div class="info-title bg__gray text__black box__l text__center">작성자</div>
          <div class="info-writer bg__white text__black box__l">${res.INQUIRYWRITEREMAIL}</div>
        </div>
        <div class="one-line">
          <div class="info-title bg__gray text__black box__l text__center">작성일</div>
          <div class="info-date bg__white text__black box__l">${formattedInquiryDate}</div> 
        </div>
      </div>
      
      <div class="info-content-div reason--title bg__gray text__black box__xl text__center">문의 내용</div>   
      <div class="info-content bg__white text__black box__l">${res.INQUIRYCONTENT}</div>   
    </div>
    
    <div class="inquiry-comment-container answer-container">
      <div class="reply-container one-line">
        <input type="hidden" id="commentNo" value="${res.INQUIRYCOMMENTNO}">
        <div class="info-comment reason--title bg__gray text__black box__xl text__center">답변</div>
      </div>
      
      <div class="answer-info">
        <div class="info-comment-container one-line answer-update">
          <div class="info-item bg__gray text__black box__l text__center">관리자</div>
          <div class="info-detail bg__white text__black box__l">${res.ANSWERWRITEREMAIL}</div>
        </div>
      </div>
      
      
      <div class="answer-content">
        <div class="reason--content bg__white text__black">
          <textarea id="answerContent" class="answerContent">${res.ANSWERCONTENT}</textarea>
        </div>
      </div>
    </div>
    
    <div class="btn-container answer-btn-container one-line">
        <button id="updateReply" class="btn btn__generate listUpdate" data-mod="${res.INQUIRYCOMMENTNO}" 
          data-inquiry-no="${res.INQUIRYNO}" data-cur-page="${curPage}" data-inquiry-search="${inquirySearch}">
          수정
        </button>
        <button id="cancleUpdate" class="btn btn__generate listMove" data-inquiry-no="${res.INQUIRYNO}" data-cur-page="${curPage}" 
          data-inquiry-search="${inquirySearch}">
          돌아가기
        </button> 
    </div>
  </main>
  `

  $("#content").html(inquiryCommentUpdate);

  $("#updateReply").click(function(e) {
    e.preventDefault();

    const inquiryNo = $(this).data("inquiry-no");
    const inquiryCommentNo = $(this).data("mod");
    const content = $("#answerContent").val();
    const curPage = $(this).data("cur-page");
    const inquirySearch = $(this).data("inquiry-search");

    $.ajax({
      url: `/admin/api/cs/inquiry/update/${inquiryCommentNo}?inquiryNo=${inquiryNo}&curPage=${curPage}&inquirySearch=${encodeURIComponent(inquirySearch)}`,
      type: 'PATCH',
      contentType: 'application/json',
      data: JSON.stringify({
        content: content,
        curPage: curPage,
        inquirySearch: inquirySearch
      }),
      success: function(res) {
        createDetailView(res.inquiryVo, curPage, inquirySearch);
        showAlertMsg(res.alertMsg);
      },
      error: function(res) {
        showAlertMsg(res.alertMsg)
      }
    });
  });

  $("#cancleUpdate").click( function (e) {
    e.preventDefault();

    const inquiryNo = $(this).data("inquiry-no");
    const curPage = $(this).data("cur-page");
    const inquirySearch = $(this).data("inquiry-search");

    $.ajax({
      url: `/admin/api/cs/inquiry/${inquiryNo}`,
      type: 'GET',
      data: {
        curPage: curPage,
        inquirySearch: inquirySearch,
      },
      success: function(res) {
        createDetailView(res.inquiryVo, curPage, inquirySearch);
      },
      error: function(res) {
        showAlertMsg(res.alertMsg);
      }
    });
  });

}

function createAddView(res, curPage, inquirySearch) {
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
        <input type="hidden" id="answerTermination" value="${res.ANSWERTERMINATION}">
        <div class="info-title bg__gray text__black box__l text__center">제목</div>
        <div class="info-item bg__white text__black box__l">${res.TITLE}</div>
        <div class="info-title bg__gray text__black box__l text__center">분류</div>
        <div class="info-dv-item bg__white text__black box__l">${res.DIVISION}</div>
      </div>
      
      <div class="inquiry-sub one-line">
        <div class="one-line">
          <div class="info-title bg__gray text__black box__l text__center">작성자</div>
          <div class="info-writer bg__white text__black box__l">${res.INQUIRYWRITEREMAIL}</div>
        </div>
        <div class="one-line">
          <div class="info-title bg__gray text__black box__l text__center">작성일</div>
          <div class="info-date bg__white text__black box__l">${formattedInquiryDate}</div> 
        </div>
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
        <button id="cancleAdd" class="btn btn__generate listMove" data-inquiry-no="${res.INQUIRYNO}" 
        data-cur-page="${curPage}" data-inquiry-search="${inquirySearch}">돌아가기</button>
        <button id="replyAdd" class="btn btn__generate listUpdate" data-add="${res.INQUIRYNO}" 
        data-cur-page="${curPage}" data-inquiry-search="${inquirySearch}">
          답변 추가
        </button>
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
    const curPage = $(this).data("cur-page");
    const inquirySearch = $(this).data("inquiry-search");

    $.ajax({
      url: `/admin/api/cs/inquiry/add/${inquiryNo}`,
      type: 'PATCH',
      contentType: 'application/json',
      data: JSON.stringify({
        CONTENT: content,
        curPage: curPage,
        inquirySearch: inquirySearch,
      }),
      success: function(res) {
        const message = encodeURIComponent(res.alertMsg || "답변 추가에 성공했습니다");

        // 성공 후 리스트 페이지로 이동하거나 현재 페이지를 새로고침
        window.location.href = `/admin/cs/inquiry/${inquiryNo}?message=${message}`;
      },
      error: function(res) {
        showAlertMsg(res.alertMsg);
      }
    });
  });

  $("#cancleAdd").on("click", function(e) {
    e.preventDefault();

    const no = $(this).data("inquiry-no");
    const curPage = $(this).data("cur-page");
    const inquirySearch = $(this).data("inquiry-search");

    $.ajax({
      url: `/admin/api/cs/inquiry/${no}`,
      type: 'GET',
      data: {
        curPage: curPage,
        inquirySearch: inquirySearch,
      },
      success: function(res) {
        createDetailView(res.inquiryVo, curPage, inquirySearch);
      },
      error: function(res) {
        showAlertMsg(res.alertMsg);
      }
    });
  });
}
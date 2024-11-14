import {checkAndShowStoredMessage, showAlertMsg} from "../../util/toast.js";
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

$('.list-content').click(function () {
  const freeBoardNo = $(this).data('free-board-no');
  const curPage = $('#curPage').val() || '1';
  const freeBoardSearch = $('#search').val() || '';
  const noticeBoardNo = $('#noticeBoardNo').val();

  $.ajax({
    url: `/admin/freeBoard/list/${freeBoardNo}`,
    type: 'GET',
    data: {
      curPage: curPage,
      freeBoardSearch: freeBoardSearch,
      noticeBoardNo: noticeBoardNo
    },
    success: function (res) {
      createDetailView(res.freeBoardVo, res.freeBoardCommentVoList, curPage, freeBoardSearch, noticeBoardNo);
    },
    error: function (res) {
      showAlertMsg(res.alertMsg);
    }
  })
})

function createDetailView(data, freeBoardCommentVoList, curPage, freeBoardSearch, noticeBoardNo) {

  const commentList = freeBoardCommentVoList.map(comment => `
    <div class="comment-container one-line">
      <div class="comment-email">${comment.email}</div>
      <div class="comment-content"><input class="" value="${comment.content}" readonly /></div>
      <div class="comment-date">${formatDate(comment.creDate)}</div>
      <div class="btn-container">
        <button class="btn btn__generate deleteCommentBtn text__center commentDelBtn" 
          data-free-board-comment-no="${comment.freeBoardCommentNo}" data-free-board-no="${data.freeBoardNo}" 
          data-cur-page="${curPage}" data-notice-board-no="${noticeBoardNo}" data-free-board-search="${freeBoardSearch}">
          삭제
        </button>
      </div>
    </div>
  `).join("");

  const freeBoardDetail = `
    <div class="title-container one-line">
      <div id="title" class="title btn__yellow text__white">
        게시글 상세
      </div>
      <div class="btn-container">
        <a id="listMove" class="btn btn__generate listMove text__center" href="/admin/freeBoard/list?curPage=${curPage}&freeBoardSearch=${freeBoardSearch}">
          돌아가기
        </a>
    </div>
    </div>

    <main class="main-container bg__white">
      <div class="freeBoard-container one-line">
        <div class="freeBoard-title one-line">
          <input type="hidden" id="freeBoardNo" value="${data.freeBoardNo}">
          <input type="hidden" id="noticeBoardNo" value="${noticeBoardNo}">
          <input type="hidden" id="freeBoardSearch" value="${freeBoardSearch}">
          <div class="info-title bg__gray text__black box__l text__center">제목</div>
          <div id="freeBoardTitle" class="info-item bg__white text__black box__l">${data.title}</div>
        </div>
        
        <div class="freeBoard-sub one-line">
          <div class="one-line">
            <div class="info-title bg__gray text__black box__l text__center">작성자</div>
            <div class="info-writer bg__white text__black box__l">${data.email}</div>
          </div>
          <div class="one-line">
            <div class="info-title bg__gray text__black box__l text__center">작성일</div>
            <div class="info-date bg__white text__black box__l">${formatDate(data.creDate)}</div> 
          </div>
        </div>
        
        <div class="info-content-div reason--title bg__gray text__black box__xl text__center">내용</div>
      
        <div class="info-content bg__white text__black box__l">
          <div id="freeBoardContent" class="bg__white text__black box__l">${data.content}</div>
        </div>      
      </div>
      
      <div class="btn-container one-line">
        <button id="deleteBtn" class="btn btn__generate deleteBtn text__center" 
          data-free-board-no="${data.freeBoardNo}" data-cur-page="${curPage}" data-notice-board-no="${noticeBoardNo}" data-free-board-search="${freeBoardSearch}">
          삭제
        </button>
      </div> 
            
    </main>
    
    ${freeBoardCommentVoList.length > 0 ?
      `<div id="comment-list-container" class="comment-list-container bg__white">
        ${commentList}
      </div>` : `<div class="hiddenDiv"></div>`}
  `;

  $("#content").html(freeBoardDetail);

  $("#deleteBtn").click(function (e) {
    e.preventDefault();

    const freeBoardNo = $(this).data("free-board-no");
    const curPage = $(this).data("cur-page");
    const noticeBoardNo = $(this).data("notice-board-no");
    const freeBoardSearch = $(this).data("free-board-search");

    $.ajax({
      url: `/admin/api/freeBoard/list/${freeBoardNo}/delete`,
      type: 'DELETE',
      data: {
        curPage: curPage,
        noticeBoardNo: noticeBoardNo,
        freeBoardSearch: freeBoardSearch
      },
      success: function (res) {
        const message = encodeURIComponent(res.alertMsg || "게시글 삭제에 성공했습니다");
        window.location.href = `/admin/freeBoard/list?curPage=${curPage}&noticeBoardNo=${noticeBoardNo}&freeBoardSearch=${freeBoardSearch}&message=${message}`;
      },
      error: function (res) {
        showAlertMsg(res.alertMsg);
      }
    })
  })

  $(".commentDelBtn").click(function (e) {
    e.preventDefault();

    const freeBoardCommentNo = $(this).data("free-board-comment-no");
    const curPage = $(this).data("cur-page");
    const freeBoardSearch = $(this).data("free-board-search");
    const freeBoardNo = $(this).data("free-board-no");

    $.ajax({
      url: `/admin/api/freeBoard/list/${freeBoardCommentNo}/deleteComment?freeBoardNo=${freeBoardNo}`,
      type: 'DELETE',
      data: JSON.stringify({
        freeBoardNo: freeBoardNo,
        curPage: curPage,
        freeBoardSearch: freeBoardSearch
      }),
      success: function (res) {
        createDetailView(res.freeBoardVo, res.freeBoardCommentList, curPage, freeBoardSearch, noticeBoardNo);
        showAlertMsg(res.alertMsg);
      },
      error: function (res) {
        showAlertMsg(res.alertMsg);
      }
    })
  })
}
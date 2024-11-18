import {checkAndShowStoredMessage, showAlertMsg} from "../../util/toast.js";
checkAndShowStoredMessage();

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
    url: `/admin/api/freeBoard/list/${freeBoardNo}`,
    type: 'GET',
    data: {
      curPage: curPage,
      freeBoardSearch: freeBoardSearch,
      noticeBoardNo: noticeBoardNo
    },
    success: function (res) {
      createDetailView(res.freeBoardVo, res.freeBoardCommentVoList, curPage, freeBoardSearch, noticeBoardNo, res.currentMemberNo);
    },
    error: function (res) {
      showAlertMsg(res.alertMsg);
    }
  })
})

function createDetailView(freeBoardVo, freeBoardCommentVoList, curPage, freeBoardSearch, noticeBoardNo, currentMemberNo) {
  const commentList = freeBoardCommentVoList.map(comment => `
    <div class="comment-container one-line">
      <div class="comment-email">${comment.email}</div>
       ${comment.memberNo === currentMemberNo ? `
        <div class="comment-content one-line">
          ${comment.isModified === 1 ? '<span class="modified-indicator">[수정]</span>' : ''}
          <input class="inputComment" value="${comment.content}" data-free-board-comment-no="${comment.freeBoardCommentNo}"/>
        </div>
      ` : `
        <div class="comment-content one-line">
          ${comment.isModified === 1 ? '<span class="modified-indicator">[수정]</span>' : ''}
          <div>${comment.content}</div>
        </div>
      `}
      <div class="comment-date">${formatDate(comment.creDate)}</div>
       
        ${comment.memberNo == currentMemberNo ? `
         <div class="btn-container">
            <button class="btn btn__generate updateCommentBtn text__center commentModBtn" 
              data-free-board-comment-no="${comment.freeBoardCommentNo}" data-free-board-no="${freeBoardVo.freeBoardNo}" 
              data-cur-page="${curPage}" data-notice-board-no="${noticeBoardNo}" data-free-board-search="${freeBoardSearch}">
              수정
            </button>
            <button class="btn btn__generate deleteCommentBtn text__center commentDelBtn" 
              data-free-board-comment-no="${comment.freeBoardCommentNo}" data-free-board-no="${freeBoardVo.freeBoardNo}" 
              data-cur-page="${curPage}" data-notice-board-no="${noticeBoardNo}" data-free-board-search="${freeBoardSearch}">
              삭제
            </button>
        </div>
      ` : `
      <div class="btn-container">
        <button class="btn btn__generate deleteCommentBtn text__center commentDelBtn" 
          data-free-board-comment-no="${comment.freeBoardCommentNo}" data-free-board-no="${freeBoardVo.freeBoardNo}" 
          data-cur-page="${curPage}" data-notice-board-no="${noticeBoardNo}" data-free-board-search="${freeBoardSearch}">
          삭제
        </button>
      </div>
      `}
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

    <main class="main-container bg__white detail-container">
      <div class="freeBoard-container one-line">
        <div class="freeBoard-title one-line">
          <input type="hidden" id="freeBoardNo" value="${freeBoardVo.freeBoardNo}">
          <input type="hidden" id="noticeBoardNo" value="${noticeBoardNo}">
          <input type="hidden" id="freeBoardSearch" value="${freeBoardSearch}">
          <div class="info-title bg__gray text__black box__l text__center">제목</div>
          <div id="freeBoardTitle" class="info-item bg__white text__black box__l">${freeBoardVo.title}</div>
        </div>
        
        <div class="freeBoard-sub one-line">
          <div class="one-line">
            <div class="info-title bg__gray text__black box__l text__center">작성자</div>
            <div class="info-writer bg__white text__black box__l">${freeBoardVo.email}</div>
          </div>
          <div class="one-line">
            <div class="info-title bg__gray text__black box__l text__center">작성일</div>
            <div class="info-date bg__white text__black box__l">${formatDate(freeBoardVo.creDate)}</div> 
          </div>
        </div>
        
        <div class="info-content-div reason--title bg__gray text__black box__xl text__center">내용</div>
      
        <div class="info-content bg__white text__black box__l">
          <div id="freeBoardContent" class="bg__white text__black box__l">${freeBoardVo.content}</div>
        </div>      
      </div>
      
      <div class="btn-container one-line">
        <button id="deleteBtn" class="btn btn__generate deleteBtn text__center" 
          data-free-board-no="${freeBoardVo.freeBoardNo}" data-cur-page="${curPage}" data-notice-board-no="${noticeBoardNo}" data-free-board-search="${freeBoardSearch}">
          삭제
        </button>
      </div> 
            
    </main>
    
    ${freeBoardCommentVoList.length > 0 ?
      `<div id="comment-list-container" class="comment-list-container bg__white">
        <div class="bg__gray answer-title text__center text__semibold">댓글</div>
        <div class="comment-add-container one-line">
          <div class="input-container">
            <textarea id="comment-textarea" class="comment-textarea" maxlength="600"></textarea>
          </div>   
          <div id="admin-addBtn-container" class="btn-container one-line text__center">
            <button id="addCommentBtn" class="btn btn__generate addCommentBtn text__center" 
              data-free-board-no="${freeBoardVo.freeBoardNo}" data-cur-page="${curPage}" data-notice-board-no="${noticeBoardNo}" data-free-board-search="${freeBoardSearch}">
              댓글 추가
            </button>
          </div>
        </div>
        ${commentList}
      </div>` : `
      <div id="comment-list-container" class="comment-list-container bg__white">
        <div class="bg__gray answer-title text__center text__semibold">댓글</div>
        <div class="comment-add-container one-line">
          <div class="input-container">
            <textarea id="comment-textarea" class="comment-textarea" maxlength="600"></textarea>
          </div>   
          <div id="member-addBtn-container" class="btn-container one-line text__center">
            <button id="addCommentBtn" class="btn btn__generate addCommentBtn text__center" 
              data-free-board-no="${freeBoardVo.freeBoardNo}" data-cur-page="${curPage}" data-notice-board-no="${noticeBoardNo}" data-free-board-search="${freeBoardSearch}">
              댓글 추가
            </button>
          </div>
        </div>
      </div>
    `}
    <div class="hiddenDiv"></div>
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

  $("#addCommentBtn").click(function (e) {
    e.preventDefault();

    const content = $("#comment-textarea").val();
    const freeBoardNo = $(this).data("free-board-no");
    const noticeBoardNo = $(this).data("notice-board");
    const curPage = $(this).data("cur-page");
    const freeBoardSearch = $(this).data("free-board-search");

    $.ajax({
      url: `/admin/api/freeBoard/list/${freeBoardNo}/addComment`,
      type: 'POST',
      contentType: 'application/json',
      data: JSON.stringify({
        freeBoardNo: freeBoardNo,
        curPage: curPage,
        noticeBoardNo: noticeBoardNo,
        freeBoardSearch: freeBoardSearch,
        content: content,
      }),
      success: function (res) {
        createDetailView(res.freeBoardVo, res.freeBoardCommentList, curPage, freeBoardSearch, noticeBoardNo, res.currentMemberNo);
        showAlertMsg(res.alertMsg);
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
    const noticeBoardNo = $(this).data("notice-board-no");

    $.ajax({
      url: `/admin/api/freeBoard/list/${freeBoardCommentNo}/deleteComment?freeBoardNo=${freeBoardNo}`,
      type: 'DELETE',
      data: {
        freeBoardNo: freeBoardNo,
        curPage: curPage,
        freeBoardSearch: freeBoardSearch,
        noticeBoardNo: noticeBoardNo
      },
      success: function (res) {
        createDetailView(res.freeBoardVo, res.freeBoardCommentList, curPage, freeBoardSearch, noticeBoardNo, res.currentMemberNo);
        showAlertMsg(res.alertMsg);
      },
      error: function (res) {
        showAlertMsg(res.alertMsg);
      }
    })
  })

  $(".commentModBtn").click(function (e) {
    e.preventDefault();

    const $commentContainer = $(this).closest('.comment-container');
    const freeBoardCommentNo = $(this).data("free-board-comment-no");
    const curPage = $(this).data("cur-page");
    const freeBoardSearch = $(this).data("free-board-search");
    const freeBoardNo = $(this).data("free-board-no");
    const noticeBoardNo = $(this).data("notice-board-no");
    const freeBoardCommentVal = $commentContainer.find('.inputComment').val();

    $.ajax({
      url: `/admin/api/freeBoard/list/${freeBoardCommentNo}/updateComment?freeBoardNo=${freeBoardNo}`,
      type: 'PATCH',
      data: {
        curPage: curPage,
        noticeBoardNo: noticeBoardNo,
        freeBoardSearch: freeBoardSearch,
        freeBoardCommentContent: freeBoardCommentVal
      },
      success: function (res) {
        createDetailView(res.freeBoardVo, res.freeBoardCommentList, curPage, freeBoardSearch, noticeBoardNo, res.currentMemberNo);
        showAlertMsg(res.alertMsg);
      },
      error: function (res) {
        showAlertMsg(res.alertMsg);
      }
    })
  })
}
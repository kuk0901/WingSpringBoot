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
    url: `/member/freeBoard/list/${freeBoardNo}`,
    type: 'GET',
    data: {
      curPage: curPage,
      freeBoardSearch: freeBoardSearch,
      noticeBoardNo: noticeBoardNo
    },
    success: function (res) {
      createDetailView(res.freeBoardVo, res.freeBoardCommentList, curPage, freeBoardSearch, noticeBoardNo, res.currentMemberNo);
    },
    error: function (res) {
      showAlertMsg(res.alertMsg);
    }
  })
})

// data -> freeBoardVo로 수정
function createDetailView(freeBoardVo, freeBoardCommentList, curPage, freeBoardSearch, noticeBoardNo, currentMemberNo) {

  const commentList = freeBoardCommentList.map(comment => `
      <div class="comment-container one-line">
        <div class="comment-email">${comment.email}</div>
        <div class="comment-content">${comment.content}</div>
        <div class="comment-date">${formatDate(comment.creDate)}</div>
      </div>
  `).join("");

  const freeBoardDetail = `
    <div class="title-container one-line">
      <div id="title" class="title btn__yellow text__white">
        게시글 상세
      </div>
      <div class="btn-container detailP-btn-container">
        <a id="listMove" class="btn btn__generate listMove text__center" href="/member/freeBoard/list?curPage=${curPage}&freeBoardSearch=${freeBoardSearch}">
          돌아가기
        </a>
    </div>
    </div>

    <main class="main-container detail-board-content bg__white">
      <div class="freeBoard-container">
        <div class="freeBoard-title">
          <input type="hidden" id="freeBoardNo" value="${freeBoardVo.freeBoardNo}">
          <input type="hidden" id="noticeBoardNo" value="${noticeBoardNo}">
          <input type="hidden" id="freeBoardSearch" value="${freeBoardSearch}">
          <input type="hidden" id="memberNo" value="${freeBoardVo.memberNo}">
          <input type="hidden" id="memberNumber" value="${currentMemberNo}">
          <div class="info-container one-line">
            <div class="label-container bg__gray text__black text__center box__s">
              <label for="freeBoardTitle" class="info-title">제목</label>
            </div>
            <div class="input-container bg__white text__black">
              <input id="freeBoardTitle" class="info-item" value="${freeBoardVo.title}" />
           </div>
          </div>
        </div>
        
        <div class="freeBoard-sub one-line">
          <div class="info-container one-line">
            <div class="label-container bg__gray text__black text__center box__s">
              <label for="email" class="info-title">작성자</label>
            </div>
            <div class="input-container bg__white text__black">
              <input id="email" class="info-writer" value="${freeBoardVo.email}" />
            </div>
          </div>
          
          <div class="info-container one-line">
            <div class="label-container bg__gray text__black text__center box__s">
             <label for="creDate" class="info-title">작성일</label>
            </div>
            <div class="input-container bg__white text__black">
              <input id="creDate" class="info-date" value="${formatDate(freeBoardVo.creDate)}" /> 
            </div>
          </div>
        </div>
        
        <div class="info-container">
          <div class="label-container reason--title bg__gray text__black text__center">
           <label for="freeBoardContent" class="info-content-div">내용</label>
          </div>
          <div class="input-container bg__white text__black textarea-content">
            <textarea id="freeBoardContent" class="contentArea">${freeBoardVo.content}</textarea>
          </div>      
        </div>
      </div>
      
      ${freeBoardVo.memberNo == currentMemberNo ? `
        <div class="btn-container one-line">
          <button id="deleteBtn" class="btn btn__generate deleteBtn text__center" 
            data-free-board-no="${freeBoardVo.freeBoardNo}" data-cur-page="${curPage}" data-notice-board-no="${noticeBoardNo}" data-free-board-search="${freeBoardSearch}">
            삭제
          </button>
          <button id="updateMoveBtn" class="btn btn__generate updateMoveBtn text__center" data-free-board-no="${freeBoardVo.freeBoardNo}" 
            data-cur-page="${curPage}" data-notice-board-no="${noticeBoardNo}" data-free-board-search="${freeBoardSearch}">
            수정
          </button>
        </div>
        ` : `
        <div class="member-btn-container one-line">
          <div id="member-addBtn-container" class="one-line">
            <textarea id="comment-textarea" class="comment-textarea"></textarea>
            <button id="addCommentBtn" class="btn btn__generate addCommentBtn text__center" 
              data-free-board-no="${freeBoardVo.freeBoardNo}" data-cur-page="${curPage}" data-notice-board-no="${noticeBoardNo}" data-free-board-search="${freeBoardSearch}">
              답변 추가
            </button>
          </div>
        </div>
        `
      }
    </main>
    
    ${freeBoardCommentList ? `<div id="comment-list-container" class="comment-list-container bg__white">${commentList}</div>` : ""}
    
    <div class="hidden-ui"></div>
  `;

  $("#content").html(freeBoardDetail);

  $("#updateMoveBtn").click(function (e){
    e.preventDefault();

    const freeBoardNo = $(this).data("free-board-no");
    const noticeBoardNo = $(this).data("notice-board");
    const curPage = $(this).data("cur-page");
    const freeBoardSearch = $(this).data("free-board-search");

    $.ajax({
      url: `/member/freeBoard/list/${freeBoardNo}/update`,
      type: 'GET',
      data: {
        noticeBoardNo: noticeBoardNo,
        curPage: curPage,
        freeBoardSearch: freeBoardSearch,
      },
      success: function (res) {
        createUpdateView(res.freeBoardVo, res.curPage, res.noticeBoardNo, res.freeBoardSearch);
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

    console.log(content);

    $.ajax({
      url: `/member/api/freeBoard/list/${freeBoardNo}/addComment`,
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
        const message = encodeURIComponent(res.alertMsg || "답글 등록에 성공했습니다");
        window.location.href = `/member/freeBoard/list?curPage=${curPage}&freeBoardSearch=${freeBoardSearch}&message=${message}`;
      },
      error: function (res) {
        showAlertMsg(res.alertMsg);
      }
    })
  })
}

function createUpdateView(freeBoardVo, curPage, noticeBoardNo, freeBoardSearch) {

  const freeBoardUpdate = `
    <div class="title-container one-line">
      <div id="title" class="title btn__yellow text__white">
        게시글 상세
      </div>
    </div>

    <main class="main-container detail-board-content bg__white">
      <div class="freeBoard-container">
        <div class="freeBoard-title">
          <input type="hidden" id="freeBoardNo" value="${freeBoardVo.freeBoardNo}">
          <input type="hidden" id="noticeBoardNo" value="${noticeBoardNo}">
          <input type="hidden" id="freeBoardSearch" value="${freeBoardSearch}">
          <input type="hidden" id="memberNo" value="${freeBoardVo.memberNo}">
          <div class="info-container one-line">
            <div class="label-container bg__gray text__black text__center box__s">
              <label for="freeBoardTitle" class="info-title">제목</label>
            </div>
            <div class="input-container bg__white text__black">
              <input id="freeBoardTitle" class="info-item" value="${freeBoardVo.title}">
            </div>
          </div>
        </div>
        
        <div class="freeBoard-sub one-line">
          <div class="info-container one-line">
            <div class="label-container bg__gray text__black text__center box__s">
              <label for="email" class="info-title">작성자</label>
            </div>
            <div class="input-container bg__white text__black">
              <input id="email" class="info-writer" value="${freeBoardVo.email}" />
            </div>
          </div>
          <div class="info-container text__center text__black one-line">
            <div class="label-container info-title bg__gray box__s">
              <label for="modDate">작성일</label>
            </div>
            <div class="input-container bg__white">
              <input id="modDate" class="info-date" value="${formatDate(freeBoardVo.modDate)}" />
            </div>
          </div> 
        </div>
        
        <div class="info-container">
          <div class="label-container reason--title bg__gray text__black text__center">
            <label for="freeBoardContent" class="reason--title">내용</label>
          </div>
      
          <div class="input-container textarea-content bg__white text__black">
            <textarea id="freeBoardContent" class="contentArea">${freeBoardVo.content}</textarea>
          </div>    
        </div>  
      </div>

        
      <div class="btn-container one-line">
        <a id="listMove" class="btn btn__generate listMove text__center" href="/member/freeBoard/list?curPage=${curPage}&freeBoardSearch=${freeBoardSearch}&noticeBoardNo=${noticeBoardNo}">
          돌아가기
        </a>
        <button id="updateBtn" class="btn btn__generate updateMoveBtn text__center" data-free-board-no="${freeBoardVo.freeBoardNo}" 
          data-cur-page="${curPage}" data-notice-board-no="${noticeBoardNo}" data-free-board-search="${freeBoardSearch}">
          수정
        </button>
      </div>
    </main>
    
    <div class="hidden-ui"></div>
  `;

  $("#content").html(freeBoardUpdate);

  $("#updateBtn").click(function (e) {
    e.preventDefault();

    const freeBoardNo = $(this).data("freeBoardNo");
    const curPage = $(this).data("cur-page");
    const noticeBoardNo = $(this).data("notice-board-no") || 3;
    const freeBoardSearch = $(this).data("free-board-search");
    const title = $("#freeBoardTitle").val();
    const content = $("#freeBoardContent").val();

    console.log(noticeBoardNo);

    $.ajax({
      url: `/member/api/freeBoard/list/${freeBoardNo}/update`,
      type: 'PATCH',
      contentType: 'application/json',
      data: JSON.stringify({
        curPage: curPage,
        noticeBoardNo: noticeBoardNo,
        freeBoardSearch: freeBoardSearch,
        title: title,
        content: content,
      }),
      success: function (res) {
        const message = encodeURIComponent(res.alertMsg || "게시글 수정에 성공했습니다");
        window.location.href = `/member/freeBoard/list?curPage=${curPage}&freeBoardSearch=${freeBoardSearch}&message=${message}`;
      },
      error: function (res) {
        showAlertMsg(res.alertMsg);
      }
    })
  })
}

$("#addFreeBoard").click(function (e) {
  e.preventDefault();

  const memberNo = $("#memberNo").val();
  const title = $("#titleVal").val();
  const content = $("#contentVal").val();
  const email = $("#emailVal").val();
  const noticeBoardNo = $("#noticeBoardNo").val();
  const curPage = $("#curPage").val();

  // 간단한 유효성 검사
  if (!title.trim() || !content.trim()) {
    alert("제목과 내용을 모두 입력해주세요.");
    return;
  }

  const freeBoardData = {
    memberNo: memberNo,
    title: title,
    content: content,
    email: email,
    noticeBoardNo: noticeBoardNo,
    curPage: curPage
  };

  $.ajax({
    url: `/member/api/freeBoard/add`,
    type: 'POST',
    contentType: 'application/json',
    data: JSON.stringify(freeBoardData),
    success: function (res) {
      const message = encodeURIComponent(res.alertMsg || "게시글 추가에 성공했습니다");
      window.location.href = `/member/freeBoard/list?message=${message}`;
    },
    error: function (res) {
      showAlertMsg(res.alertMsg);
    }
  });
})
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

  console.log(freeBoardNo);
  console.log(curPage);
  console.log(freeBoardSearch);
  console.log(noticeBoardNo);

  $.ajax({
    url: `/member/cs/freeBoard/${freeBoardNo}`,
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
        <div>${comment.email}</div>
        <div>${comment.content}</div>
        <div>${formatDate(comment.creDate)}</div>
      </div>
  `).join("");

  const freeBoardDetail = `
    <div class="title-container one-line">
      <div id="title" class="title btn__yellow text__white">
        게시글 상세
      </div>
      <div class="btn-container">
        <a id="listMove" class="btn btn__generate listMove text__center" href="/member/cs/freeBoard/list?curPage=${curPage}&freeBoardSearch=${freeBoardSearch}">
          돌아가기
        </a>
    </div>
    </div>

    <main class="main-container bg__white">
      <div class="freeBoard-container">
        <div class="freeBoard-title one-line">
          <input type="hidden" id="freeBoardNo" value="${freeBoardVo.freeBoardNo}">
          <input type="hidden" id="noticeBoardNo" value="${noticeBoardNo}">
          <input type="hidden" id="freeBoardSearch" value="${freeBoardSearch}">
          <input type="hidden" id="memberNo" value="${freeBoardVo.memberNo}">
          <input type="hidden" id="memberNumber" value="${currentMemberNo}">
          <div class="info-title bg__gray text__black box__l text__center">제목</div>
          <div id="freeBoardTitle" class="info-item bg__white text__black box__l">${freeBoardVo.title}</div>
        </div>
        
        <div class="freeBoard-sub one-line">
          <div class="info-title bg__gray text__black box__l text__center">작성자</div>
          <div class="info-writer bg__white text__black box__l">${freeBoardVo.email}</div>
          <div class="info-title bg__gray text__black box__l text__center">작성일</div>
          <div class="info-date bg__white text__black box__l">${formatDate(freeBoardVo.creDate)}</div> 
        </div>
        
        <div class="info-content-div reason--title bg__gray text__black box__xl text__center">내용</div>
      
        <div class="info-content bg__white text__black box__l">
          <div id="freeBoardContent" class="bg__white text__black box__l">${freeBoardVo.content}</div>
        </div>      
      </div>
      
      ${freeBoardVo.memberNo == currentMemberNo ? `

        <div class="member-btn-container one-line">
          <div>
            <button id="deleteBtn" class="btn btn__generate deleteBtn text__center" 
              data-free-board-no="${freeBoardVo.freeBoardNo}" data-cur-page="${curPage}" data-notice-board-no="${noticeBoardNo}" data-free-board-search="${freeBoardSearch}">
              삭제
            </button>
          </div>
          <div>
            <button id="updateMoveBtn" class="btn btn__generate updateMoveBtn text__center" data-free-board-no="${freeBoardVo.freeBoardNo}" 
              data-cur-page="${curPage}" data-notice-board-no="${noticeBoardNo}" data-free-board-search="${freeBoardSearch}">
              수정
            </button>
          </div>
        </div>
        ` : ""
      }
    </main>
    
    ${freeBoardCommentList ? `<div id="comment-list-container">${commentList}</div>` : ""}
    
    <div class="hidden-ui"></div>
  `;

  $("#content").html(freeBoardDetail);

}
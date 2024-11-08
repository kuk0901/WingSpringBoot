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
  const postNo = $(this).data('post-no');
  const curPage = $('#curPage').val() || '1';
  const postSearch = $('#search').val() || '';
  const noticeBoardNo = $('#noticeBoardNo').val();

  $.ajax({
    url: `/member/cs/post/list/${postNo}`,
    type: 'GET',
    data: {
      curPage: curPage,
      postSearch: postSearch,
      noticeBoardNo: noticeBoardNo
    },
    success: function (res) {
      createDetailView(res.postVo, curPage, postSearch, noticeBoardNo);
    },
    error: function (res) {
      showAlertMsg(res.alertMsg);
    }
  })
})

function createDetailView(data, curPage, postSearch, noticeBoardNo) {
  const formattedPostDate = formatDate(data.CREDATE);

  const postDetail = `
    <div class="title-container one-line">
      <div id="title" class="title btn__yellow text__white">
        공지사항 상세
      </div>
      <div class="btn-container">
        <a id="listMove" class="btn btn__generate listMove text__center" href="/member/cs/post/list?curPage=${curPage}&postSearch=${postSearch}">
          돌아가기
        </a>
    </div>
    </div>

    <main class="main-container bg__white">
      <div class="post-container">
        <div class="post-title one-line">
          <input type="hidden" id="postNo" value="${data.POSTNO}">
          <input type="hidden" id="noticeBoardNo" value="${noticeBoardNo}">
          <input type="hidden" id="postSearch" value="${postSearch}">
          <div class="info-title bg__gray text__black box__l text__center">제목</div>
          <div id="postTitle" class="info-item bg__white text__black box__l">${data.TITLE}</div>
        </div>
        
        <div class="post-sub one-line">
          <div class="one-line">
            <div class="info-title bg__gray text__black box__l text__center">작성자</div>
            <div class="info-writer bg__white text__black box__l">${data.EMAIL}</div>
            </div>
          <div class="one-line">
            <div class="info-title bg__gray text__black box__l text__center">작성일</div>
            <div class="info-date bg__white text__black box__l">${formattedPostDate}</div> 
          </div>
        </div>
        
        <div class="info-content-div reason--title bg__gray text__black box__xl text__center">내용</div>
      
        <div class="info-content bg__white text__black box__l">
          <div id="postContent" class="bg__white text__black box__l">${data.CONTENT}</div>
        </div>      
      </div>
            
    </main>
  `;

  $("#content").html(postDetail);

}
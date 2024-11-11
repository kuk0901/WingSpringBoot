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

$("#addPost").click(function (e) {
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

  const postData = {
    memberNo: memberNo,
    title: title,
    content: content,
    email: email,
    noticeBoardNo: noticeBoardNo,
    curPage: curPage
  };

  $.ajax({
    url: `/admin/api/cs/post/list/add`,
    type: 'POST',
    contentType: 'application/json',
    data: JSON.stringify(postData),
    success: function (res) {
      alert("게시글이 성공적으로 추가되었습니다.");
      // 게시글 목록 페이지로 리다이렉트
      window.location.href = '/admin/cs/post/list';
    },
    error: function (xhr, status, error) {
      console.error("게시글 추가 중 오류가 발생했습니다.", error);
      alert("게시글 추가 중 오류가 발생했습니다. 다시 시도해 주세요.");
    }
  });
});

$('.list-content').click(function () {
  const postNo = $(this).data('post-no');
  const curPage = $('#curPage').val() || '1';
  const postSearch = $('#search').val() || '';
  const noticeBoardNo = $('#noticeBoardNo').val();

  $.ajax({
    url: `/admin/cs/post/list/${postNo}`,
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
        <a id="listMove" class="btn btn__generate listMove text__center" href="/admin/cs/post/list?curPage=${curPage}&postSearch=${postSearch}">
          돌아가기
        </a>
    </div>
    </div>

    <main class="main-container bg__white">
      <div class="post-container post-detail-container">
        <div class="post-title one-line">
          <input type="hidden" id="postNo" value="${data.POSTNO}">
          <input type="hidden" id="noticeBoardNo" value="${noticeBoardNo}">
          <input type="hidden" id="postSearch" value="${postSearch}">
          <div class="info-title bg__gray text__black box__l text__center">제목</div>
          <div id="postTitle" class="info-item bg__white text__black box__l">${data.TITLE}</div>
        </div>
        
        <div class="post-sub one-line">
          <div class="info-title bg__gray text__black box__l text__center">작성자</div>
          <div class="info-writer bg__white text__black box__l">${data.EMAIL}</div>
          <div class="info-title bg__gray text__black box__l text__center">작성일</div>
          <div class="info-date bg__white text__black box__l">${formattedPostDate}</div> 
        </div>
        
        <div class="info-content-div reason--title bg__gray text__black box__xl text__center">내용</div>
      
        <div class="info-content bg__white text__black box__l">
          <div id="postContent" class="bg__white text__black box__l">${data.CONTENT}</div>
        </div>      
      </div>
            
    </main>
    
    <div class="btn-container btn one-line">
      <div>
        <button id="deleteBtn" class="btn btn__generate deleteBtn text__center" 
          data-post-no="${data.POSTNO}" data-cur-page="${curPage}" data-notice-board-no="${noticeBoardNo}" data-post-search="${postSearch}">
          삭제
        </button>
      </div>
      <div>
        <button id="updateMoveBtn" class="btn btn__generate updateMoveBtn text__center" data-post-no="${data.POSTNO}" 
          data-cur-page="${curPage}" data-notice-board-no="${noticeBoardNo}" data-post-search="${postSearch}">
          수정
        </button>
      </div>
    </div>
  `;

  $("#content").html(postDetail);

  $("#updateMoveBtn").click(function (e) {
    e.preventDefault();

    const postNo = $(this).data("post-no");
    const curPage = $(this).data("cur-page");
    const noticeBoardNo = $(this).data("notice-board-no");
    const postSearch = $(this).data("post-search");

    $.ajax( {
      url: `/admin/cs/post/list/${postNo}/update`,
      type: 'GET',
      data: {
        curPage: curPage,
        noticeBoardNo: noticeBoardNo,
        postSearch: postSearch,
      },
      success: function(res) {
        createUpdateView(res.postVo, curPage, noticeBoardNo, postSearch);
      },
      error: function(res) {
        showAlertMsg(res.alertMsg);
      }
    })
  })

  $("#deleteBtn").click(function (e) {
    e.preventDefault();

    const postNo = $(this).data("post-no");
    const curPage = $(this).data("cur-page");
    const noticeBoardNo = $(this).data("notice-board-no");
    const postSearch = $(this).data("post-search");

    $.ajax({
      url: `/admin/api/cs/post/list/${postNo}/delete`,
      type: 'DELETE',
      data: {
        curPage: curPage,
        noticeBoardNo: noticeBoardNo,
        postSearch: postSearch
      },
      success: function(res) {
        const message = encodeURIComponent(res.alertMsg || "공지사항 삭제에 성공했습니다");
        window.location.href = `/admin/cs/post/list?curPage=${curPage}&noticeBoardNo=${noticeBoardNo}&postSearch=${postSearch}&message=${message}`;
      },
      error: function(res) {
        showAlertMsg(res.alertMsg);
      }
    })
  })

}

function createUpdateView(res, curPage, noticeBoardNo, postSearch) {
  const formattedPostDate = formatDate(res.CREDATE);

  const postUpdate = `
  <div class="title-container one-line">
      <div id="title" class="title btn__yellow text__white">
        공지사항 수정
      </div>
    </div>

    <main class="main-container bg__white">
      <div class="post-container post-detail-container">
        <div class="post-title one-line">
          <input type="hidden" id="memberNo"  value="${res.MEMBERNO}">
          <input type="hidden" id="noticeBoardNo" value="${noticeBoardNo}">
          <input type="hidden" id="curPage" value="${curPage}">
          <input type="hidden" id="postSearch" value="${postSearch}">

          <div class="info-title bg__gray text__black box__l text__center">제목</div>
          <input id="postTitle" class="info-item bg__white text__black box__l" value="${res.TITLE}">
        </div>

        <div class="post-sub one-line">
          <div class="info-title bg__gray text__black box__l text__center">작성자</div>
          <div id="email" class="info-writer bg__white text__black box__l">${res.EMAIL}</div>

          <div class="info-title bg__gray text__black box__l text__center">작성일</div>
          <div id="writeDate" class="info-date bg__white text__black box__l">${formattedPostDate}</div>
        </div>

        <div class="info-content-div reason--title bg__gray text__black box__xl text__center">문의 내용</div>

        <div class="info-content bg__white text__black box__l">
          <textarea id="postContent" class="contentArea">${res.CONTENT}</textarea>
        </div>
      </div>
    </main>

    <div class="btn-container-patch-move one-line">
      <div class="btn-container">
        <a id="moveList" class="btn btn__generate moveList" href="/admin/cs/post/list?curPage=${curPage}&noticeBoardNo=${noticeBoardNo}&postSearch=${postSearch}">
          취소
        </a>
      </div>
      <div class="btn-container">
        <button id="postUpdateBtn" class="btn btn__generate postUpdateBtn text__center text__bold" 
          data-post-no="${res.POSTNO}" data-notice-board-no="${noticeBoardNo}" data-cur-page="${curPage}" data-post-search="${postSearch}" data-email="${res.EMAIL}">
          문의 수정
        </button>
      </div>
    </div>
  `

  $("#content").html(postUpdate);

  $("#postUpdateBtn").click(function (e) {
    e.preventDefault();

    const postNo = $(this).data("post-no");
    const curPage = $(this).data("cur-page");
    const noticeBoardNo = $(this).data("notice-board-no");
    const postSearch = $(this).data("post-search");
    const email = $(this).data("post-email");

    const title = $("#postTitle").val();
    const content = $("#postContent").val();

    $.ajax({
      url: `/admin/api/cs/post/list/${postNo}/update`,
      type: 'PATCH',
      contentType: 'application/json',
      data: JSON.stringify({
        curPage: curPage,
        noticeBoardNo: noticeBoardNo,
        postSearch: postSearch,
        title: title,
        content: content,
        email: email
      }),
      success: function(res) {
        const message = encodeURIComponent(res.alertMsg || "답변 추가에 성공했습니다");
        window.location.href = `/admin/cs/post/list?curPage=${curPage}&noticeBoardNo=${noticeBoardNo}&postSearch=${postSearch}&message=${message}`;
      },
      error: function(res) {
        showAlertMsg(res.alertMsg);
      }
    })
  })

}
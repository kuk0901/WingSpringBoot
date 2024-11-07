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
      console.log("게시글이 성공적으로 추가되었습니다.", res);
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

  console.log(curPage);

  $.ajax({
    url: `/admin/cs/post/${postNo}`,
    type: 'GET',
    data: {
      curPage: curPage,
      postSearch: postSearch
    },
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
  })
})

function createDetailView(data) {
  const formattedPostDate = formatDate(data.POSTDATE);

  console.log("Data received in createDetailView:", data);

  const postDetail = `
    <div class="title-container one-line">
      <div id="title" class="title btn__yellow text__white">
        공지사항 상세
      </div>
      <div class="btn-container">
        <a id="listMove" class="btn btn__generate listMove text__center" href="/admin/cs/post/list?curPage=${data.curPage}&postSearch=${data.postSearch}">
          돌아가기
        </a>
    </div>
    </div>

    <main class="main-container bg__white">
      <div class="post-container">
        <div class="post-title one-line">
          <input type="hidden" id="postNo" value="${data.POSTNO}">
          <div class="info-title bg__gray text__black box__l text__center">제목</div>
          <div class="info-item bg__white text__black box__l">${data.TITLE}</div>
        </div>
        
        <div class="post-sub one-line">
          <div class="info-title bg__gray text__black box__l text__center">작성자</div>
          <div class="info-writer bg__white text__black box__l">${data.POSTWRITEREMAIL}</div>
          <div class="info-title bg__gray text__black box__l text__center">작성일</div>
          <div class="info-date bg__white text__black box__l">${formattedPostDate}</div> 
        </div>
        
        <div class="info-content-div reason--title bg__gray text__black box__xl text__center">내용</div>
      
        <div class="info-content bg__white text__black box__l">
          <div id="postContent" class="bg__white text__black box__l">${data.POSTCONTENT}</div>
        </div>      
      </div>
            
    </main>
    
    <div class="btn-container btn one-line">
      <div>
        <a id="deleteTag" class="btn btn__generate deleteTag text__center" href="./list/update?postNo=${data.POSTNO}">삭제</a>
      </div>
      <div>
        <button id="updateMoveBtn" class="btn btn__generate updateMoveBtn text__center" data-post-no="${data.POSTNO}" data-cur-page="${data.curPage}">수정</button>
      </div>
    </div>
  `;

  $("#content").html(postDetail);

  $("#updateMoveBtn").click(function (e) {
    e.preventDefault();

    const postNo = $(this).data("post-no");
    const curPage = $(this).data("cur-page");

    console.log(curPage);

    $.ajax( {
      url: `/admin/api/cs/post/update/${postNo}`,
      type: 'POST',
      data: { curPage: curPage },
      success: function(res) {
        console.log("보내지는 값은:", res);
        createUpdateView(res);
      },
      error: function(xhr, status, error) {
        console.log(error);
      }
    })
  })

}

function createUpdateView(res) {
  const formattedPostDate = formatDate(res.POSTDATE);

  console.log(res);

  const inquiryCommentUpdate = `
  <div class="title-container one-line">
      <div id="title" class="title btn__yellow text__white">
        공지사항 수정
      </div>
    </div>

    <main class="main-container bg__white">
      <div class="post-container">
        <div class="post-title one-line">
          <input type="hidden" id="memberNo"  value="${res.MEMBERNO}">
          <input type="hidden" id="noticeBoardNo" value="${res.NOTICEBOARDNO}">

          <div class="info-title bg__gray text__black box__l text__center">제목</div>
          <div class="info-item bg__white text__black box__l">${res.POSTTITLE}</div>
        </div>

        <div class="post-sub one-line">
          <div class="info-title bg__gray text__black box__l text__center">작성자</div>
          <div class="info-writer bg__white text__black box__l">${res.POSTWRITER}</div>

          <div class="info-title bg__gray text__black box__l text__center">작성일</div>
          <div id="writeDate" class="info-date bg__white text__black box__l">${formattedPostDate}</div>
        </div>

        <div class="info-content-div reason--title bg__gray text__black box__xl text__center">문의 내용</div>

        <div class="info-content bg__white text__black box__l">
          <textarea id="contentVal" class="contentArea">${res.POSTCONTENT}</textarea>
        </div>
      </div>
    </main>

    <div class="btn-container-all one-line">
      <div class="btn-container">
        <a id="listMove" class="btn btn__generate listMove" href="/admin/cs/post/list?curPage=${curPage}&noticeBoardNo=${noticeBoardNo}&postSearch=${postSearch}">
          취소
        </a>
      </div>
      <div class="btn-container">
        <button id="postUpdateBtn" class="btn btn__generate listMove text__center text__bold" data-mod="${res.POSTNO}">
          문의 수정
        </button>
      </div>
    </div>
  `
}
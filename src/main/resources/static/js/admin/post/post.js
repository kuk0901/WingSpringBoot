const $abc = $("#abc").value;

$("#addPost").click(function (e) {
    e.preventDefault();

    const memberNo = $("#memberNo").val();
    const title = $("#titleVal").val();
    const content = $("#contentVal").val();
    const email = $("#emailVal").val();
    const noticeBoardNo = $("#noticeBoardNo").val();

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
        noticeBoardNo: noticeBoardNo
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
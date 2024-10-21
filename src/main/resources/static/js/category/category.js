function moveAddFunc() {
    // 수정 페이지로 이동
    window.location.href = '/category/add';
}

function moveModFunc(categoryNo) {
    // 수정 페이지로 이동
    window.location.href = '/category/modify?categoryNo=' + categoryNo;
}

function moveDelFunc(categoryNo) {
    if(confirm('정말로 삭제하시겠습니까?')) {
        fetch('/category/delete/' + categoryNo, {
            method: 'DELETE',
        })
            .then(response => {
                if (response.ok) {
                    alert('카테고리가 성공적으로 삭제되었습니다.');
                    location.reload(); // 페이지 새로고침
                } else {
                    alert('카테고리 삭제에 실패했습니다.');
                }
            })
            .catch(error => {
                console.error('Error:', error);
                alert('카테고리 삭제 중 오류가 발생했습니다.');
            });
    }
}
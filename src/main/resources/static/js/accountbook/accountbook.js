
  $('#searchForm').on('submit', function (event) {
    event.preventDefault(); // 기본 제출 이벤트 방지

    // 선택된 값 가져오기
    var category = $('#categorySelect').val();
    var paymentMethod = $('#paymentMethodSelect').val();

    // AJAX 요청
    $.ajax({
      url: '/api/accountBook/admin/list',
      method: 'GET',
      data: {
        category: category,
        paymentMethod: paymentMethod
      },
      success: function (response) {
        console.log(response); // 받아온 데이터를 콘솔에 출력 (디버깅용)

        // 테이블 업데이트
        var tableBody = '';
        $.each(response, function(index, accountBook) {
          tableBody += '<tr>';
          tableBody += '<td>' + accountBook.accountBookNo + '</td>';
          tableBody += '<td>' + accountBook.creDate + '</td>';
          tableBody += '<td>' + accountBook.memberEmail + '</td>';
          tableBody += '<td>' + accountBook.minusCategoryName + '</td>';
          tableBody += '<td>' + accountBook.paymentMethodName + '</td>';
          tableBody += '<td>' + accountBook.paymentAmount + '</td>';
          tableBody += '</tr>';
        });

        // 기존 테이블 내용을 지우고 새로운 내용으로 업데이트
        $('#resultContainer').html(tableBody);


      },
      error: function (xhr, status, error) {
        // 에러 처리
        console.error('Error fetching data:', error);
      }
    });
  });
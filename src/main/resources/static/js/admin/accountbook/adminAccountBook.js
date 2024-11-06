
  $('#searchForm').on('submit', function (event) {
    event.preventDefault(); // 기본 제출 이벤트 방지

    // 선택된 값 가져오기
    var category = $('#categorySelect').val();
    var paymentMethod = $('#paymentMethodSelect').val();

    // AJAX 요청
    $.ajax({
      url: '/admin/api/accountBook/list',
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
          // creDate를 원하는 형식으로 변환
          var date = new Date(accountBook.creDate); // creDate를 Date 객체로 변환
          var year = date.getFullYear();
          var month = ('0' + (date.getMonth() + 1)).slice(-2); // 월은 0부터 시작하므로 +1
          var day = ('0' + date.getDate()).slice(-2);

          // YYYYMMDD 형식으로 변환된 날짜
          var formattedDate = year+"-"+ month+"-"+day;
          // paymentAmount를 포맷팅
          var formattedPaymentAmount = formatNumber(accountBook.paymentAmount);
          tableBody += '<tr>';
          tableBody += '<td class="no">' + accountBook.accountBookNo + '</td>';
          tableBody += '<td>' + formattedDate + '</td>';
          tableBody += '<td>' + accountBook.memberEmail + '</td>';
          tableBody += '<td>' + accountBook.minusCategoryName + '</td>';
          tableBody += '<td>' + accountBook.paymentMethodName + '</td>';
          tableBody += '<td>' +formattedPaymentAmount+ '</td>';
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


  $(document).ready(function() {
    $.ajax({
      url: '/admin/api/accountBook/topPaymentMethods',
      type: 'GET',
      success: function(data) {
        console.log("상위 결제 방법:", data);
        // 데이터를 처리하여 화면에 출력
        const paymentMethodsContainer = $('#topPaymentMethods');
        paymentMethodsContainer.empty(); // 기존 내용 삭제

        // "Top 결제 수단" 텍스트 추가
        paymentMethodsContainer.append('<span class="topPaymentMethodsBox">Top 결제 수단</span>');
        // 결제 방법 리스트를 화면에 출력
        data.forEach(function(method) {
          paymentMethodsContainer.append(`<span class="category-item text__semibold">${method.paymentMethodName} (사용 횟수: ${method.usageCount}회)</span> `);
        });
      },
      error: function(xhr, status, error) {
        console.error("오류 발생:", error);
      }
    });

      // 상위 카테고리 AJAX 호출
      $.ajax({
        url: '/admin/api/accountBook/topCategories',  // API 경로 수정
        type: 'GET',
        success: function(data) {
          console.log("상위 카테고리:", data);
          const categoriesContainer = $('#topCategoriesContainer'); // 카테고리 컨테이너 선택 (id 변경)
          categoriesContainer.empty(); // 기존 내용 지우기
          // "Top 카테고리" 텍스트 추가
          categoriesContainer.append('<span class="topCategoriesBox">Top 카테고리</span>');
          // 데이터를 처리하여 화면에 출력
          data.forEach(category => {
            categoriesContainer.append(`<span class="category-item text__semibold">${category.minusCategoryName} (사용 횟수: ${category.usageCount}회)</span> `);
          });
        },
        error: function(xhr, status, error) {
          console.error("오류 발생:", error);
        }
      });
  });


  function formatNumber(amount) {
    // 숫자를 제외한 모든 문자 제거 (콤마와 하이픈 포함)
    const numbers = amount.toString().replace(/\D/g, "");

    const regex = /^[1-9]\d*$/;

    // 패턴에 맞는지 확인 (1-9로 시작하는 숫자)
    if (!regex.test(numbers)) {

      return  "";
    }

    // 3자리마다 콤마 추가
    return numbers.replace(/\B(?=(\d{3})+(?!\d))/g, ",");
  }
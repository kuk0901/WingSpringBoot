// $(document).ready(function() {
//     // 폼 제출 이벤트 리스너 추가
//     $("#addPaymentMethodForm").submit(function(e) {
//         e.preventDefault();
//         const paymentMethodName = $("#paymentMethodName").val();
//
//         $.ajax({
//             url: '/admin/api/paymentMethod/add',
//             type: 'POST',
//             contentType: 'application/json',
//             data: JSON.stringify({
//                 paymentMethodName: paymentMethodName
//             }),
//             success: function(response) {
//                 console.log('결제 수단 추가 성공:', response);
//                 alert('결제 수단이 성공적으로 추가되었습니다.');
//                 window.location.href = '/admin/api/paymentMethod/list';
//             },
//             error: function(xhr, status, error) {
//                 console.error('결제 수단 추가 실패:', error);
//                 alert('결제 수단 추가에 실패했습니다. 다시 시도해주세요.');
//             }
//         });
//     });
//
//     // 취소 버튼 이벤트 리스너 추가
//     $("#cancelAdd").click(function() {
//         window.location.href = '/admin/api/paymentMethod/list';
//     });
// });


$("#paymentMethodAdd").click(function(e) {
  e.preventDefault();

  var paymentMethodName = $("#paymentMethodName").val();

  $.ajax({
    url: `/admin/api/paymentMethod/add`,
    type: 'POST',
    data: { paymentMethodName: paymentMethodName },
    success: function(res) {
      alert('결제 수단이 성공적으로 추가되었습니다.');
      window.location.href = '/admin/api/paymentMethod/list';
    },
    error: function(xhr, status, error) {
      const msg = xhr.responseJSON ? xhr.responseJSON.msg  : "결제 수단 등록에 실패했습니다.";

      alert(msg);
    }
  });
});

$("#cancelAdd").click(function() {
  window.location.href = '/admin/api/paymentMethod/list';
});

function moveDelFunc(no) {
  $.ajax( {
    url: `/admin/api/paymentMethod/delete/${no}`,
    type: 'GET',
    success: function (res) {
      if (res.totalCount > 0) {
        alert(res.msg);
        return;
      }

      // 진짜로 삭제하는 함수 호출
      paymentMethodDelete(no);
    }
  })
}

function paymentMethodDelete(deleteNo) {
  $.ajax({
    url: `/admin/api/paymentMethod/delete/${deleteNo}`,
    type: 'DELETE',
    success: function (res) {
      if (res.status === "success") {
        // 삭제가 성공한 경우, 실제로 삭제할 것인지 다시 한 번 확인합니다.
        if (confirm(`${res.paymentMethodName} 결제 방법을 실제로 삭제하시겠습니까? 이 작업은 되돌릴 수 없습니다.`)) {
          // 사용자가 최종 확인을 한 경우, 실제 삭제를 진행합니다.
          $.ajax({
            url: `/admin/api/paymentMethod/finalDelete/${deleteNo}`,
            type: 'DELETE',
            success: function (finalRes) {
              if (finalRes.status === "success") {
                alert(finalRes.message);
                location.reload(); // 페이지 새로고침
              } else {
                alert(finalRes.message || "결제 방법 삭제에 실패했습니다.");
              }
            },
            error: function (xhr, status, error) {
              alert("오류가 발생했습니다: " + error);
            }
          });
        }
      } else {
        alert(res.message || "결제 방법을 찾을 수 없습니다.");
      }
    },
    error: function (xhr, status, error) {
      alert("오류가 발생했습니다: " + error);
    }
  });
}
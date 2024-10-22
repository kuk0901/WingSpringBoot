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
    type: 'POST',
    success: function (res) {
      alert("")
    //   이곳에 no 뿐만이 아니라 name도 들어가서 name이 삭제 되었습니다 라고 알려줘야 한다
    }
  })
}
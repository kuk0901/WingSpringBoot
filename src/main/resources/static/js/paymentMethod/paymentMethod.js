$("#paymentMethodAdd").click(function(e) {
  e.preventDefault();

  var paymentMethodName = $("#paymentMethodName").val();

  $.ajax({
    url: `/admin/api/paymentMethod/add`,
    type: 'POST',
    data: { paymentMethodName: paymentMethodName },
    success: function(res) {
      alert('결제 수단이 성공적으로 추가되었습니다.');
      window.location.href = '/admin/paymentMethod/list';
    },
    error: function(xhr, status, error) {
      const msg = xhr.responseJSON ? xhr.responseJSON.msg  : "결제 수단 등록에 실패했습니다.";

      alert(msg);
    }
  });
});

$("#cancelAdd").click(function() {
  window.location.href = '/admin/paymentMethod/list';
});

function moveDelFunc(no) {
  $.ajax( {
    url: `/admin/paymentMethod/delete/${no}`,
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

// ajax로 서버에서 데이터를 가져온 후 함수를 이용해 화면을 꾸밈
function paymentMethodUpdate(no) {
  $.ajax( {
    url: `/admin/api/paymentMethod/update/${no}`,
    type: 'POST',
    success: function(res) {
      console.log(res);
      createUpdateView(res);
    },
    error: function(xhr, status, error) {
      console.log(error);
    }
  })
}

// update view 화면을 구현함
function createUpdateView(res) {
  console.log(res)
  const paymentMethodUpdate = `
    <div class="title-container">
      <div class="title btn__yellow text__white">
        결제 수단 수정
      </div>
    </div>
    
    <main class="main-container payment-method__change">
      <div class="list-container one-line-start">
        <div class="label-container info-item bg__gray text__black box__l text__center label-size">
          <label for="paymentMethodName" class="info-item bg__gray text__black box__l text__center label-size">결제수단 명</label>
        </div>
        <div class="input-container info-item bg__white text__black box__l label-size">
          <input id="paymentMethodName" name="paymentMethodName" value="${res.paymentMethodName}" class="info-item bg__white text__black box__l label-size"/>
        </div>
      </div>
       
      <div class="btn-container">
<!--       버튼 알아서~ 두개~ -->
        <button id="paymentMethodAdd" type="submit" class="btn btn__generate btn__blue">수정</button>
        <button id="cancelAdd" type="button" class="btn btn__generate btn__red">취소</button>
      </div>
    </main>
  `;

  $("#content").html(paymentMethodUpdate);

  // 버튼에 이벤트 리스너 추가
  $("#paymentMethodAdd").on("click", function() {
    updatePaymentMethod(res.paymentMethodNo);
  });

  $("#cancelAdd").on("click", function() {
    // 취소 버튼 클릭 시 수행할 동작 (예: 이전 페이지로 돌아가기)
    history.back();
  });
}

function updatePaymentMethod(paymentMethodNo) {
  const updatedName = $("#paymentMethodName").val();

  $.ajax({
    url: `/admin/api/paymentMethod/update/${paymentMethodNo}`,
    type: 'PATCH',
    data: { paymentMethodName: updatedName },
    success: function(res) {
      console.log("수정 성공:", res);
      alert("결제 수단이 성공적으로 수정되었습니다.");
    },
    error: function(xhr, status, error) {
      console.log("수정 실패:", error);
      alert("결제 수단 수정에 실패했습니다.");
    }
  });
}
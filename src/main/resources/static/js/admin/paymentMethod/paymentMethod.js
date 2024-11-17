import { showAlertMsg, checkAndShowStoredMessage } from "../../util/toast.js";
checkAndShowStoredMessage();

$("#paymentMethodAdd").click(function(e) {
  e.preventDefault();

  var paymentMethodName = $("#paymentMethodName").val();

  $.ajax({
    url: `/admin/api/paymentMethod/add`,
    type: 'POST',
    data: { paymentMethodName: paymentMethodName },
    success: function(res) {
      window.location.href = `/admin/paymentMethod/list?message=${res.alertMsg}`;
    },
    error: function(xhr, status, error) {
      const msg = xhr.responseJSON ? xhr.responseJSON.alertMsg  : "결제 수단 등록에 실패했습니다.";

      showAlertMsg(msg);
    }
  });
});

$("#cancelAdd").click(function(e) {
  e.preventDefault();

  window.location.href = '/admin/paymentMethod/list';
});

$(".remove-pm-btn").click(function(e) {
  e.preventDefault();

  const no = $(this).data("pmn");

  if (no === 3) {
    alert("해당 결제수단은 삭제가 불가능합니다")
    return;
  }

  $.ajax({
    url: `/admin/api/paymentMethod/delete/${no}`,
    type: 'GET',
    success: function (res) {
      if (res.totalCount > 0) {
        alert(res.msg);
        return;
      }

      // 진짜로 삭제하는 함수 호출
      paymentMethodDelete(no);
    }, error: function() {
      const msg = xhr.responseJSON ? xhr.responseJSON.alertMsg  : "서버에서 오류가 발생했습니다. 잠시 후 다시 시도해 주세요.";
      showAlertMsg(msg);
    }
  })
});

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
                window.location.href = `/admin/paymentMethod/list?message=${finalRes.alertMsg}`;
              } else {
                showAlertMsg(alertMsg);
              }
            },
            error: function (res) {
              showAlertMsg(alertMsg);
            }
          });
        }
      } else {
        showAlertMsg(alertMsg);
      }
    },
    error: function (res) {
      showAlertMsg(alertMsg);
    }
  });
}

// ajax로 서버에서 데이터를 가져온 후 함수를 이용해 화면을 꾸밈
$(".update-pm-btn").click(function(e) {
  e.preventDefault();

  const no = $(this).data("pmn");

  $.ajax({
    url: `/admin/api/paymentMethod/countPaymentMethod/${no}`,
    type: 'GET',
    success: function (res) {

      if(no === 3) {
        window.location.href = `/admin/paymentMethod/list?message=${res.msg}`;
      }

      if(res.totalCount > 0) {
        window.location.href = `/admin/paymentMethod/list?message=${res.msg}`;
      }

      $.ajax( {
        url: `/admin/api/paymentMethod/update/${no}`,
        type: 'GET',
        success: function(res) {
          createUpdateView(res.paymentMethodList, res.paymentMethodVo);
        },
        error: function(res) {
          showAlertMsg(alertMsg);
        }
      })
    }
  })
});


// update view 화면을 구현함
function createUpdateView(paymentMethodList, paymentMethodVo) {

  const paymentMethodNames = paymentMethodList.map(name => `
    <div class="paymentMethod-item">${name}</div>
  `).join("");

  const paymentMethodUpdate = `
    <div class="title-container">
      <div class="title btn__yellow text__white">
        결제 수단 수정
      </div>
    </div>
    
    <main class="main-container payment-method__change">
      <form class="addPaymentMethodForm">
        <div class="paymentMethod-form one-line">
          <div class="label-container bg__gray text__black text__center label-size">
            <label for="paymentMethodName" class="text__black text__center label-size">결제수단 명</label>
          </div>
          <div class="input-container bg__white text__black label-size">
            <input id="paymentMethodName" name="paymentMethodName" value="${paymentMethodVo.paymentMethodName}" class="info-item bg__white text__black box__l label-size"/>
          </div>
          
          <div class="existing-payments bg">
            <div class="bg__gray text__center text__semibold check-container">기존 결제수단</div>
            <div id="paymentMethodList" class="category-container bg__white">
              <div id="paymentMethodItems" class="text__center categoryItems one-line">
                <div class="paymentMethod-container one-line">
                  <div class="paymentMethod-flex one-line">
                    ${paymentMethodNames}
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
         
        <div class="btn-container one-line">
          <button id="paymentMethodAdd" class="btn btn__generate btn--margin" data-payment-method-no="${paymentMethodVo.paymentMethodNo}">수정</button>
          <button id="cancelAdd" class="btn btn__generate btn--margin">취소</button>
        </div>
      </form>
    </main>
  `;

  $("#content").html(paymentMethodUpdate);

  // 버튼에 이벤트 리스너 추가
  $("#paymentMethodAdd").on("click", function(e) {
    e.preventDefault();

    const paymentMethodNo = $(this).data("payment-method-no");
    const updatedName = $("#paymentMethodName").val();

    $.ajax({
      url: `/admin/api/paymentMethod/update/${paymentMethodNo}`,
      type: 'PATCH',
      data: { paymentMethodName: updatedName },
      success: function(res) {
        const message = encodeURIComponent(res.alertMsg || "결제수단 수정에 성공했습니다");

        // 성공 후 리스트 페이지로 이동하거나 현재 페이지를 새로고침
        window.location.href = `/admin/paymentMethod/list?message=${message}`;
      },
      error: function(res) {
        showAlertMsg(res.alertMsg);
      }
    });
  });

  $("#cancelAdd").on("click", function(e) {
    e.preventDefault();

    // 취소 버튼 클릭 시 수행할 동작 (예: 이전 페이지로 돌아가기)
    window.location.href = '/admin/paymentMethod/list';
  });
}

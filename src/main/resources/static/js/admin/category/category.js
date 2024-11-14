import {checkAndShowStoredMessage, showAlertMsg} from "../../util/toast.js";
checkAndShowStoredMessage();

$("#categoryAdd").click(function(e) {
  e.preventDefault();

  var categoryName = $("#categoryName").val();
  var division = $("#division").val();

  if (!categoryName) {
    alert("카테고리 명을 입력해주세요.");
    return;
  }

  if(division == "plus") {
    $.ajax({
      url: '/admin/api/category/addPlus',
      type: 'POST',
      data: {categoryName: categoryName},
      success: function (res) {
        const message = encodeURIComponent(res.alertMsg || "카테고리 추가에 성공했습니다");
        window.location.href = `/admin/category/list?message=${message}`; // 카테고리 목록 페이지로 이동
      },
      error: function (res) {
        showAlertMsg(res.alertMsg);
      }
    });
  }

  if(division == "minus") {
    $.ajax({
      url: '/admin/api/category/addMinus',
      type: 'POST',
      data: {categoryName: categoryName},
      success: function (res) {
        const message = encodeURIComponent(res.alertMsg || "카테고리 추가에 성공했습니다");
        window.location.href = `/admin/category/list?message=${message}`; // 카테고리 목록 페이지로 이동
      },
      error: function (res) {
        showAlertMsg(res.alertMsg);
      }
    });
  }
});

// 취소 버튼 클릭 시 동작
$("#cancelAdd").click(function(e) {
  e.preventDefault();

  window.location.href = '/admin/category/list';// 이전 페이지로 돌아갑니다.
});

$(".moveModFunc").click(function (e) {

  const no = $(this).data("category-no");
  const categoryType = $(this).data("category-type");

  if(categoryType == "plus") {
    $.ajax({
      url: `/admin/category/countPlusCategory/${no}`,
      type: 'GET',
      success: function (res) {
        if (res.totalCount > 0) {
          showAlertMsg(res.alertMsg);
          return;
        }

        $.ajax({
          url: `/admin/api/category/updatePlus/${no}`,
          type: 'POST',
          success: function (res) {
            createPlusUpdateView(res);
          },
          error: function (res) {
            showAlertMsg(res.alertMsg);
          }
        });
      }
    })
  }else if(categoryType == "minus"){
    $.ajax({
      url: `/admin/category/countMinusCategory/${no}`,
      type: 'GET',
      success: function (res) {
        if (res.totalCount > 0) {
          showAlertMsg(res.alertMsg);
          return;
        }

        $.ajax({
          url: `/admin/api/category/updateMinus/${no}`,
          type: 'POST',
          success: function (res) {
            createMinusUpdateView(res);
          },
          error: function (res) {
            showAlertMsg(res.alertMsg);
          }
        });
      }
    })
  }
})


// update view 화면을 구현함
function createPlusUpdateView(res) {
  console.log(res)
  const plusCategoryUpdate = `
    <div class="title-container">
      <div class="title btn__yellow text__white">
        카테고리 수정
      </div>
    </div>
    
    <main class="main-container payment-method__change">
      <form class="categoryForm">
        <div class="list-container one-line">
          <div class="label-container info-item bg__gray text__black box__l text__center">
            <label for="categoryName" class="info-item bg__gray text__black box__l text__center">카테고리 명</label>
          </div>
          <div class="input-container info-item bg__white text__black box__l">
            <input id="categoryName" name="categoryName" value="${res.categoryName}" class="info-item bg__white text__black box__l"/>
          </div>
        </div>
       
        <div class="btn-container">
<!--       버튼 알아서~ 두개~ -->
          <button id="plusCatergoryUpdate" type="submit" class="btn btn__generate btn--margin">수정</button>
          <button id="cancelAdd" type="button" class="btn btn__generate btn--margin">취소</button>
        </div>
      </form>
    </main>
  `;

  $("#content").html(plusCategoryUpdate);

  // 버튼에 이벤트 리스너 추가
  $("#plusCatergoryUpdate").on("click", function() {
    updatePlusCategory(res.categoryNo);
  });

  $("#cancelAdd").on("click", function() {
    window.location.href = '/admin/category/list';
  });
}

function updatePlusCategory(categoryNo) {
  const categoryName = $("#categoryName").val();

  $.ajax({
    url: `/admin/api/category/updatePlus/${categoryNo}`,
    type: 'PATCH',
    data: { categoryName: categoryName },
    success: function(res) {
      const message = encodeURIComponent(res.alertMsg || "카테고리 수정에 성공했습니다");
      window.location.href = `/admin/category/list?message=${message}`;
    },
    error: function(res) {
      showAlertMsg(res.alertMsg);
    }
  });
}

function createMinusUpdateView(res) {
  console.log(res)
  const minusCategoryUpdate = `
    <div class="title-container">
      <div class="title btn__yellow text__white">
        카테고리 수정
      </div>
    </div>
    
    <main class="main-container payment-method__change">
      <form class="categoryForm">
        <div class="list-container one-line">
          <div class="label-container info-item bg__gray text__black box__l text__center">
            <label for="categoryName" class="info-item bg__gray text__black box__l text__center">카테고리 명</label>
          </div>
          <div class="input-container info-item bg__white text__black box__l">
            <input id="categoryName" name="categoryName" value="${res.categoryName}" class="info-item bg__white text__black box__l"/>
          </div>
        </div>
       
        <div class="btn-container">
          <button id="minusCatergoryUpdate" class="btn btn__generate btn--margin">수정</button>
          <button id="cancelAdd" class="btn btn__generate btn--margin">취소</button>
        </div>
      </form>
    </main>
  `;

  $("#content").html(minusCategoryUpdate);

  // 버튼에 이벤트 리스너 추가
  $("#minusCatergoryUpdate").on("click", function() {
    updateMinusCategory(res.categoryNo);
  });

  $("#cancelAdd").click(function(e) {
    e.preventDefault()
    // 취소 버튼 클릭 시 수행할 동작 (예: 이전 페이지로 돌아가기)
    window.location.href = '/admin/category/list';
  });
}

function updateMinusCategory(categoryNo) {
  const categoryName = $("#categoryName").val();

  $.ajax({
    url: `/admin/api/category/updateMinus/${categoryNo}`,
    type: 'PATCH',
    data: { categoryName: categoryName },
    success: function(res) {
      const message = encodeURIComponent(res.alertMsg || "카테고리 수정에 성공했습니다");
      window.location.href = `/admin/category/list?message=${message}`;
    },
    error: function(res) {
      showAlertMsg(res.alertMsg);
    }
  });
}

$(".moveDelFunc").click(function (e) {

  e.preventDefault();

  const no = $(this).data("category-no");
  const categoryType = $(this).data("category-type");

  if(categoryType == "plus") {
    $.ajax({
      url: `/admin/category/deletePlusCategory/${no}`,
      type: 'GET',
      success: function (res) {
        if (res.totalCount > 0) {
          showAlertMsg(res.alertMsg);
          return;
        }

        // 진짜로 삭제하는 함수 호출
        plusCategoryDelete(no);
      }
    })
  }else if(categoryType == "minus"){
    $.ajax({
      url: `/admin/category/deleteMinusCategory/${no}`,
      type: 'GET',
      success: function (res) {
        if (res.totalCount > 0) {
          showAlertMsg(res.alertMsg);
          return;
        }

        // 진짜로 삭제하는 함수 호출
        minusCategoryDelete(no);
      }
    })
  }
})

function plusCategoryDelete(deleteNo) {
  $.ajax({
    url: `/admin/api/category/deletePlusCategory/${deleteNo}`,
    type: 'DELETE',
    success: function (res) {
      if (res.status === "success") {
        // 삭제가 성공한 경우, 실제로 삭제할 것인지 다시 한 번 확인합니다.
        if (confirm(`${res.categoryName} 카테고리를 실제로 삭제하시겠습니까? 이 작업은 되돌릴 수 없습니다.`)) {
          // 사용자가 최종 확인을 한 경우, 실제 삭제를 진행합니다.
          $.ajax({
            url: `/admin/api/category/finalDeletePlusCategory/${deleteNo}`,
            type: 'DELETE',
            success: function (res) {
              const message = encodeURIComponent(res.alertMsg || "카테고리 삭제에 성공했습니다");
              window.location.href = `/admin/category/list?message=${message}`; // 카테고리 목록 페이지로 이동

            },
            error: function (res) {
              showAlertMsg(res.alertMsg);
            }
          });
        }
      } else {
        showAlertMsg(res.alertMsg);
      }
    },
    error: function (res) {
      showAlertMsg(res.alertMsg);
    }
  });
}

function minusCategoryDelete(deleteNo) {
  $.ajax({
    url: `/admin/api/category/deleteMinusCategory/${deleteNo}`,
    type: 'DELETE',
    success: function (res) {
      if (res.status === "success") {
        // 삭제가 성공한 경우, 실제로 삭제할 것인지 다시 한 번 확인합니다.
        if (confirm(`${res.categoryName} 카테고리를 실제로 삭제하시겠습니까? 이 작업은 되돌릴 수 없습니다.`)) {
          // 사용자가 최종 확인을 한 경우, 실제 삭제를 진행합니다.
          $.ajax({
            url: `/admin/api/category/finalDeleteMinusCategory/${deleteNo}`,
            type: 'DELETE',
            success: function (res) {
              const message = encodeURIComponent(res.alertMsg || "카테고리 삭제에 성공했습니다");
              window.location.href = `/admin/category/list?message=${message}`; // 카테고리 목록 페이지로 이동

            },
            error: function (res) {
              showAlertMsg(res.alertMsg);
            }
          });
        }
      } else {
        showAlertMsg(res.alertMsg);
      }
    },
    error: function (res) {
      showAlertMsg(res.alertMsg);
    }
  });
}
import {showAlertMsg} from "../../util/toast.js";

$('.remove-card-btn').click(function() {
  const cardNo = $(this).data('card-no');
  const curPage = $('#curPage').val();
  const categoryName = $('#categoryName').val();

  $.ajax({
    url: `/admin/api/productManagement/card/delete/${cardNo}`,
    type: 'DELETE',
    data: { cardNo: cardNo, curPage: curPage, categoryName: categoryName },
    success: function(res) {
      const url = new URL(window.location.href);
      url.searchParams.set('curPage', res.curPage);
      url.searchParams.set('categoryName', categoryName);
      url.searchParams.set('message', encodeURIComponent(res.alertMsg));

      window.location.href = url.toString();
    },
    error: function(xhr, status, error) {
      const msg = xhr.responseJSON ? xhr.responseJSON.alertMsg : "알 수 없는 오류가 발생했습니다.";

      showAlertMsg(msg);
    }
  });
});
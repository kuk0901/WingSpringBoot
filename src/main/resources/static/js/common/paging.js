const curPageDoc = $("#curPage"); // input
const pageButtonId = document.getElementById(("pageButton" + curPageDoc.val()));
$(pageButtonId).children('a').addClass("paging__active");

function goPage(pageNumber) {
  curPageDoc.val(pageNumber);
  $('#pagingForm').submit();
}




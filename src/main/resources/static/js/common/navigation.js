const currentPageUrl = window.location.pathname;
const navLinks = "nav a:not(#logoLink, #userName)";
const activeClasses = "active-link text__semibold";
const $inquiry = $("#inquiry");

// 모든 네비게이션 링크에서 aria-current 속성과 클래스 제거
$(navLinks).removeAttr("aria-current").removeClass(activeClasses);

// 현재 페이지 URL과 일치하는 링크에 aria-current 속성 추가 및 클래스 추가
$(navLinks).filter(function() {
  const linkHref = $(this).attr("href");
  const regex = new RegExp(`^${linkHref}(/|$)`);
  return regex.test(currentPageUrl);
}).attr("aria-current", "page").addClass(activeClasses);

$("#csControlBtn").on("click", function() {
  if ($inquiry.hasClass("active")) {
    $inquiry.removeClass("active"); // active 클래스 제거
  } else {
    $inquiry.addClass("active"); // active 클래스 추가
  }
})
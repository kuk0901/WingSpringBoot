const currentPageUrl = window.location.pathname;
const navLinks = "nav a:not(#logoLink, #userName), #csControlBtn";
const activeClasses = "active-link text__semibold";
const $inquiry = $("#inquiry");
const $post = $("#post");
const $csControlBtn = $("#csControlBtn");

// 모든 네비게이션 링크에서 aria-current 속성과 클래스 제거
$(navLinks).removeAttr("aria-current").removeClass(activeClasses);

// 현재 페이지 URL과 일치하는 링크에 aria-current 속성 추가 및 클래스 추가
$(navLinks).filter(function() {
  if (this.id === "csControlBtn") {
    // csControlBtn의 경우 특별 처리
    return currentPageUrl.includes("/admin/cs/");
  }
  const linkHref = $(this).attr("href");
  const regex = new RegExp(`^${linkHref}(/|$)`);
  return regex.test(currentPageUrl);
}).attr("aria-current", "page").addClass(activeClasses);

$csControlBtn.on("click", function() {
  $inquiry.toggleClass("active");
  $post.toggleClass("active");
});


// URL이 /admin/cs/로 시작하면 csControlBtn 활성화
if (currentPageUrl.includes("/admin/cs/")) {
  $csControlBtn.addClass(activeClasses).attr("aria-current", "page");
}

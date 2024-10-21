const currentPageUrl = window.location.pathname;

// 모든 네비게이션 링크에서 aria-current 속성과 클래스 제거
// (logoLink, userName 제외)
$("nav a:not(#logoLink, #userName)").removeAttr("aria-current").removeClass("active-link text__semibold");

// 현재 페이지 링크에 aria-current 속성 추가 및 클래스 추가
// (logoLink, userName 제외)
$(`nav a[href="${currentPageUrl}"]:not(#logoLink, #userName)`)
    .attr("aria-current", "page")
    .addClass("active-link text__semibold");
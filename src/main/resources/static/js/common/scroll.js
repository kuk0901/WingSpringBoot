const $toTopEl = $("#to-top");
const $contentEl = $("#content");

// 페이지 로드 시 초기 상태 설정
function setInitialState() {
  if ($contentEl.scrollTop() <= 500) {
    $toTopEl.css({
      'transform': 'translateX(100px)',
      'opacity': '0'
    });
  }
}

// 페이지 로드 시 초기 상태 설정 함수 호출
setInitialState();

$contentEl.on('scroll', _.throttle(function () {
  const scrollTop = $contentEl.scrollTop();

  if (scrollTop > 500) {
    // 버튼 보이기!
    gsap.to($toTopEl[0], 0.2, {
      x: 0,
      opacity: 1
    });
  } else {
    // 버튼 숨기기!
    gsap.to($toTopEl[0], 0.2, {
      x: 100,
      opacity: 0
    });
  }
}, 300));

$toTopEl.on("click", function () {
  gsap.to($contentEl, 0.7, {
    scrollTo: 0
  });
});
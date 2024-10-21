const toTopEl = document.querySelector("#to-top");

// 페이지 로드 시 초기 상태 설정
function setInitialState() {
  if (window.scrollY <= 500) {
    toTopEl.style.transform = 'translateX(100px)';
    toTopEl.style.opacity = '0';
  }
}

// 페이지 로드 시 초기 상태 설정 함수 호출
setInitialState();

window.addEventListener(
    "scroll",
    _.throttle(function () {
      if (window.scrollY > 500) {
        // 버튼 보이기!
        gsap.to(toTopEl, 0.2, {
          x: 0,
          opacity: 1
        });
      } else {
        // 버튼 숨기기!
        gsap.to(toTopEl, 0.2, {
          x: 100,
          opacity: 0
        });
      }
    }, 300)
);

toTopEl.addEventListener("click", function () {
  gsap.to(window, 0.7, {
    scrollTo: 0
  });
});
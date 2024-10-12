const toTopEl = document.querySelector("#to-top");

window.addEventListener(
    "scroll",
    _.throttle(function () {
      //console.log(window.scrollY);

      if (window.scrollY > 500) {
        // gsap.to(요소, 지속시간, 옵션);
        // 버튼 보이기!
        gsap.to(toTopEl, 0.2, {
          x: 0
        });
      } else {
        // 버튼 숨기기!
        gsap.to(toTopEl, 0.2, {
          x: 100
        });
      }
    }, 300)
);
// _.throttle(함수, 시간(ms))

toTopEl.addEventListener("click", function () {
  gsap.to(window, 0.7, {
    scrollTo: 0
  });
});

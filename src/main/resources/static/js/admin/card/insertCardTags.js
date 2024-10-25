import { formatCardBenefitSummaryToTags } from "../../util/format.js";

function insertCardBenefitTags() {
  const benefitContainers = document.querySelectorAll('.card-benefit');

  benefitContainers.forEach(container => {
    // data-benefit 속성에서 혜택 정보를 가져옴
    const summBenefit = container.getAttribute('data-benefit');

    if (summBenefit) {
      // formatCardBenefitSummaryToTags 함수를 호출하여 태그 생성
      const benefitTags = formatCardBenefitSummaryToTags(summBenefit);

      // 생성된 태그를 컨테이너에 삽입
      container.innerHTML = `<ul class="ul--ui"><span class="list--style"></span>${benefitTags}</ul>`;
    } else {
      container.innerHTML = "카드 요약 혜택에 대한 정보가 없습니다.";
    }
  });
}

insertCardBenefitTags();
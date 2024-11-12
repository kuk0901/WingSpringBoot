import { showAlertMsg } from "../../util/toast.js"
import { createDetailView } from "./AjaxCardDetail.js";

const $productInsertBtn = $("#productInsertBtn");

const today = new Date().toISOString().split('T')[0];
$("#registerDate").attr("min", today).val(today).on("change", function() {
  if ($(this).val() < today) {
    $("#alertMsg").val('등록 날짜는 오늘 이후의 날짜만 선택 가능합니다.');
    $(this).val(today);
  }
})

// 세부 내용 전용 변수
let uniqueId = 1;

const $serviceEl = $("#service");
const $benefitAddBtn = $("#benefitAddBtn");

const removeServiceContent = (id) => {
  $(`.service-content-${id}`).remove();
};

const addProductClickEvent = (e) => {
  e.preventDefault();

  const $cardBenefitDivision = $("#cardBenefitDivision");
  const $cardBenefitDetail = $("#cardBenefitDetail");
  const $cardPercentage = $("#cardPercentage");

  const $cardBenefitDivisionVal = $cardBenefitDivision.val();
  const $cardBenefitDetailVal = $cardBenefitDetail.val();
  const $cardPercentageVal = $cardPercentage.val();

  if ($cardBenefitDivisionVal === "" || $cardBenefitDivisionVal === undefined) {
    alert("구분 내용을 입력해야 합니다.");
    return;
  }

  if ($cardBenefitDetailVal === "" || $cardBenefitDetailVal === undefined) {
    alert("세부 내용을 입력해야 합니다.");
    return;
  }

  if ($cardPercentageVal === "" || $cardPercentageVal === undefined) {
    alert("할인율을 입력해야 합니다.");
    return;
  }

  const currentId = uniqueId++; // 현재 ID를 저장하고 증가

  const element = `
    <div class="service-content one-line service-content-${currentId}" data-cardBenefitNo="${currentId}">
      <input value="${$cardBenefitDivisionVal}" class="box-m" name="cardBenefitDivision-${currentId}" required />
      <input value="${$cardBenefitDetailVal}" class="box-l" name="cardBenefitDetail-${currentId}" required />
      <input value="${$cardPercentageVal}" class="box-m" name="cardPercentage-${currentId}" required />
      <button class="btn btn--remove removeBtn-${currentId} box-s">삭제</button>
    </div>
  `;

  // 템플릿을 jQuery 객체로 변환
  const $newElement = $(element);

  // 삭제 버튼에 이벤트 리스너 추가
  $newElement.find(`.removeBtn-${currentId}`).on("click", (e) => {
    e.preventDefault();
    removeServiceContent(currentId);
  });

  // 서비스 요소에 새로운 항목 추가
  $serviceEl.append($newElement);

  // 입력 필드 초기화
  $cardBenefitDivision.val("").focus();
  $cardBenefitDetail.val("");
  $cardPercentage.val("");
};

$benefitAddBtn.on("click", function(e) {
  e.preventDefault();
  e.stopPropagation();
  addProductClickEvent(e);
});

const validateForm = () => {
  // 필수 필드 체크
  const requiredFields = ['cardName', 'cardCompany', 'categoryNo', 'registerDate', 'cardImgName'];
  for (const fieldId of requiredFields) {
    const $field = $(`#${fieldId}`);
    if (!$field.val()) {
      alert(`필수 입력란을 작성하지 않았습니다.`);
      $field.focus();
      return false;
    }
  }

  // 혜택 데이터 체크 (uniqueId를 활용)
  if (uniqueId <= 1) {
    alert('최소 하나 이상의 주요 혜택을 추가해야 합니다.');
    return false;
  }

  return true;
};

const collectFormData = () => {
  const formData = new FormData();
  formData.set('cardName', $("#cardName").val());
  formData.set('cardCompany', $("#cardCompany").val());
  formData.set('categoryNo', $("#categoryNo").val());
  formData.set('registerDate', $("#registerDate").val());

  const cardImgFile = $("#cardImgName")[0].files[0]; // 첫 번째 파일 가져오기
  if (cardImgFile) {
    formData.set('originalFileName', cardImgFile); // formData에 파일 추가
  }

  const benefits = [];
  const benefitMap = new Map();
  let firstDuplicate = null;

  $('.service-content').each(function(index) {
    const cardBenefitNo = $(this).data('cardBenefitNo') || index;
    const cardBenefitDivision = $(this).find('input[name^="cardBenefitDivision"]').val();
    const cardBenefitDetail = $(this).find('input[name^="cardBenefitDetail"]').val();
    const cardPercentage = $(this).find('input[name^="cardPercentage"]').val();

    const benefitKey = `구분: ${cardBenefitDivision} 세부내용: ${cardBenefitDetail}`;

    if (benefitMap.has(benefitKey)) {
      if (!firstDuplicate) {
        firstDuplicate = `${benefitKey}\n(기존 할인율: ${benefitMap.get(benefitKey)}%, 새로운 할인율: ${cardPercentage}%)`;
      }
      return true; // 중복 발견 시 다음 항목으로 넘어감
    }

    benefitMap.set(benefitKey, cardPercentage);
    benefits.push({ cardBenefitNo, cardBenefitDivision, cardBenefitDetail, cardPercentage });
  });

  if (firstDuplicate) {
    alert(`중복된 혜택이 존재합니다.\n\n${firstDuplicate}\n\n중복되는 부분을 삭제 또는 수정해 주세요.`);
    return null;
  }

  // 특정 키를 포함하는 모든 항목 삭제
  for (const [key] of formData.entries()) {
    if (key.includes("cardBenefitDivision") || key.includes("cardBenefitDetail") || key.includes("cardPercentage")) {
      formData.delete(key);
    }
  }

  if (benefits.length > 0) {
    formData.append('benefits', JSON.stringify(benefits));
  } else {
    alert("혜택이 없습니다.");
    return null;
  }

  return formData;
};

const submitForm = (formData) => {
  $.ajax({
    url: '/admin/api/productManagement/card/insert',
    method: 'POST',
    data: formData,
    processData: false,
    contentType: false,
    success: function(res) {
        fetchCardDetails(res.cardNo, res.alertMsg);
    },
    error: function(xhr, status, error) {
      const msg = xhr.responseJSON ? xhr.responseJSON.alertMsg : "알 수 없는 오류가 발생했습니다.";

      showAlertMsg(msg);
    }
  });
};

$productInsertBtn.on('click', function(e) {
  e.preventDefault(); // 폼 제출 방지

  if (!validateForm()) {
    return;
  }

  const formData = collectFormData();

  if (formData === null) {
    return; // 중복된 혜택이 있으면 처리 중단
  }

  submitForm(formData);
});

const fetchCardDetails = (cardNo, message) => {

  const curPage = $productInsertBtn.data("curPage");
  const categoryName = $productInsertBtn.data("categoryName");

  $.ajax({
    url: `/admin/api/productManagement/card-detail/${cardNo}`,
    method: 'GET',
    data: { "cardNo": cardNo, "message": message, "curPage": curPage, "categoryName": categoryName },
    success: function(res) {
      if (res.status === "success") {
        // 가져온 데이터로 화면 업데이트
        createDetailView(res);
        showAlertMsg(res.alertMsg);
      } else {
        showAlertMsg("카드 정보를 가져오는데 실패했습니다.");
      }
    },
    error: function(xhr, status, error) {
      showAlertMsg("카드 정보를 가져오는 중 오류가 발생했습니다.");
    }
  });
};
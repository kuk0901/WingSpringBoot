import { showAlertMsg } from "../../util/toast.js";
import { playScroll, stopScroll } from "./cardApplicationWindow.js";
import { generateCardNumber } from "../../util/createCardNum.js";

let tosData, cautionData;

$.getJSON('/js/data/termsOfService.json', function(data) {
  tosData = data;
});

$.getJSON('/js/data/cautions.json', function(data) {
  cautionData = data;
});

$('.detail-card-btn').click(function() {
  const cardNo = $(this).data('card-no');
  const curPage = $('#curPage').val();
  const categoryName = $('#categoryName').val();

  $.ajax({
    url: `/member/api/product/card-detail/${cardNo}`,
    type: 'GET',
    data: { cardNo: cardNo, curPage: curPage, categoryName: categoryName },
    success: function(res) {
      createDetailView(res);
    },
    error: function(xhr, status, error) {
      const msg = xhr.responseJSON ? xhr.responseJSON.alertMsg : "알 수 없는 오류가 발생했습니다.";

      showAlertMsg(msg);
    }
  });
});


function createDetailView(data) {
  const cardVo = data.cardVo

  const cardDetail = `
    <main class="main-container">
      <section class="summary-info one-line">
        <div class="img-container">
          <img class="card--img" src="/img/card/${cardVo.storedFileName}" alt="${cardVo.cardName}" />
        </div>
        
        <div class="info-container bg__gray">
          <div class="info-m-container one-line">
            <div class="card-info text__semibold">카드 명</div>
            <div class="card-detail">${cardVo.cardName}</div>
          </div>
          <div class="info-m-container one-line">
            <div class="card-info text__semibold">카드사</div>
            <div class="card-detail">${cardVo.cardCompany}</div>
          </div>
          <div class="info-m-container one-line flex-space">
            <div class="info-s-container one-line">
              <div class="card-info text__semibold">연회비</div>
              <div class="card-detail">12,000</div>
            </div>
            <div class="info-s-container one-line m-r">
              <div class="card-info text__center text__semibold">분류</div>
              <div class="card-detail">${cardVo.categoryName}</div>
            </div>
          </div>
          <div class="list--simple style-position">
            <div class="card-info text__semibold">혜택 요약</div>
            <ul class="ul--ui">
              ${data.benefitList.slice(0, 4).map(item =>
                `<li class="li--ui">
                  <span class="list--style"></span>
                  ${item.cardBenefitDetail} ${item.cardPercentage}%
                  ${item.cardBenefitDivision} 할인
                </li>`
              ).join("")}
            </ul>
            ${data.benefitList.length > 4 ?
              `<span class="benefit-comment">(외 ${data.benefitList.length - 4}개)</span>` :
            ''}
          </div>
        </div>
      </section>
      
      <section class="side-container">
        <div class="btn-container one-line">
          <a href="./list?cardNo=${cardVo.cardNo}&curPage?${data.curPage}&categoryName=${data.categoryName}" class="btn btn__generate">돌아가기</a>
          <button id="cardApplicationWindowBtn" class="btn btn__generate btn__primary">신청하러 가기</button>
        </div>
      </section>
      
      <section class="main-info">
        <div class="main-content">
          <div class="info-title-container text__semibold bg__gray ">
            <div class="title text__center">주요 혜택</div>
          </div>
          
          <div class="content-container bg__white">
            <div class="content-title one-line">
              <div class="title box__s text__semibold text__center">구분</div>
              <div class="title box__l text__semibold text__center">세부 내용</div>
              <div class="title box__s text__semibold text__center">할인율(금액)</div>
            </div>
              ${data.benefitList.map(item =>
                `<div class="content-detail one-line"> 
                  <div class="detail box__s text__center">${item.cardBenefitDivision}할인</div>
                  <div class="detail box__l text__center">${item.cardBenefitDetail}</div>
                  <div class="detail box__s text__center">${item.cardPercentage}%</div>
                </div>`
              ).join("")}
          </div>
        </div>
        
        <div class="sub-content">
          <div class="info-title-container text__semibold bg__gray">
            <div class="title text__center">이용 전 확인사항</div>
          </div>
          <div class="content-container list-container tos">
            <ul class="ul--ui bg__white">
              ${tosData.map(item => `<li class="li--ui text__semibold"><span class="list--style"></span>${item.content}</li>`).join("")}
            </ul>
          </div>
        </div>
      </section>
      
      <section class="cautions">
        <ul class="ul--ui bg__darkgray">
          ${cautionData.map(item => `<li class="li--ui text__white list__white"><span class="list--style"></span>${item.content}</li>`).join("")}
        </ul>
        <div class="hidden-ui"></div>
      </section>
  </main>
  `;

  const purchaseEls  = `
    <section class="purchase-line">
      <main class="main-container card-list-container">
        <form id="purchaseCardForm">
          <div class="exist-container">
            <img id="existBtn" src="/img/close.svg" alt="X"/>
          </div>
        
          <div class="purchaseCard-container one-line bg__gray">
            <div class="label-container text__center text__semibold">
              <label for="cardName">카드 명</label>
            </div>
            <div class="input-container bg__gray">
              <input id="cardName" name="cardName" class="bg__gray" value="${cardVo.cardName}" readonly required/>
            </div>
            </div>
      
            <div class="purchaseCard-container one-line bg__gray">
              <div class="label-container text__center text__semibold">
                <label for="annualFee">연회비</label>
              </div>
              <div class="input-container bg__gray">
                <input id="annualFee" name="annualFee" class="bg__gray" value="12,000" readonly required/>
              </div>
            </div>
      
            <div class="purchaseCard-container one-line bg__gray">
              <div class="label-container text__center text__semibold">
                <label for="userName">발급자 명</label>
              </div>
              <div class="input-container bg__gray">
                <input id="userName" name="userName" class="bg__gray" value="${data.member.userName}" readonly required/>
              </div>
            </div>
      
            <div class="purchaseCard-container one-line bg__gray">
              <div class="label-container text__center text__semibold">
                <label for="address">주소</label>
              </div>
              <div class="input-container bg__gray">
                <input id="address" name="address" class="bg__gray" required placeholder="도로명 주소를 기입해 주세요."/>
              </div>
            </div>
      
            <div class="purchaseCard-container one-line bg__gray">
              <div class="label-container text__center text__semibold">
                <label for="accountNumber">계좌번호</label>
              </div>
              <div class="one-line">
                <div class="input-container bg__gray">
                  <input id="accountNumber" name="accountNumber" class="bg__gray" required placeholder="-을 포함해 입력해 주세요."/>
                </div>
                <div class="select-container">
                  <select class="select-bank" name="backName">
                    <option value="KB국민은행" selected>KB국민은행</option>
                    <option value="SC제일은행">SC제일은행</option>
                    <option value="하나은행">하나은행 </option>
                    <option value="iM뱅크">iM뱅크</option>
                    <option value="신한은행">신한은행</option>
                    <option value="광주은행">광주은행</option>
                    <option value="부산은행">부산은행</option>
                    <option value="전북은행">전북은행</option>
                    <option value="경남은행">경남은행</option>
                  </select>
                </div>
              </div>
            </div>
      
            <div class="purchaseCard-container">
              <div class="btn-container bg__gray">
                <button id="cardPurchaseBtn" class="btn bg__gray" data-cn="${cardVo.cardNo}" data-mn="${data.member.memberNo}">카드 신청</button>
              </div>
            </div>
          </form>
        </main>
      </section>
    `

  $("body").append(purchaseEls);

  $("#content").html(cardDetail).addClass("bg__white");

  $("#cardApplicationWindowBtn").click(function(e) {
    e.preventDefault();
    e.stopPropagation();
    $("#root").addClass("purchase");
    stopScroll();
    $("#existBtn").addClass("exist");
    $("#address").focus();
  });

  $("#existBtn").click(function(e) {
    e.preventDefault();
    e.stopPropagation();
    $("#root").removeClass("purchase");
    playScroll();
    $("#existBtn").removeClass("exist");
  });

  $("#cardPurchaseBtn").click(function(e) {
    e.preventDefault();

    const cn = $(this).data('cn');
    const mn = $(this).data('mn');

    const sellingCardData = {
      cardNo: cn,
      memberNo: mn,
      memberCardNo: generateCardNumber()
    }

    const accountBookData = {
      plusCategoryNo: 1,
      minusCategoryNo: 4,
      memberNo: mn,
      paymentMethodNo: 3,
      content: "WING_ 카드 구매",
      paymentAmount: "12,000",
    }

    $.ajax("", {
      url: `/member/api/sellingCard/purchase/general`,
      contentType: 'application/json',
      type: 'POST',
      data: JSON.stringify({ sellingCardVo: sellingCardData, accountBookVo: accountBookData }),
      success: function(res) {
        if (res.status === "success") window.location.href = `/member/user/myPage?message=${res.alertMsg}`;
      },
      error: function(xhr, status, error) {
        e.preventDefault();
        e.stopPropagation();

        $("#root").removeClass("purchase");
        playScroll();
        $("#existBtn").removeClass("exist");

        const msg = xhr.responseJSON ? xhr.responseJSON.alertMsg : "알 수 없는 오류가 발생했습니다.";

        showAlertMsg(msg);
      }
    })
  })
}


















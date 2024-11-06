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
    url: `/admin/api/productManagement/card-detail/${cardNo}`,
    type: 'GET',
    data: { cardNo: cardNo, curPage: curPage, categoryName: categoryName },
    success: function(res) {
      createDetailView(res);
    },
    error: function(xhr, status, error) {
      console.error("Error fetching details:", error);
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
        <div class="btn-container">
          <a href="./list?cardNo=${cardVo.cardNo}&curPage=${data.curPage}&categoryName=${data.categoryName}" class="btn btn__generate">돌아가기</a>
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
          ${cautionData.map(item => `<li class="li--ui text__white list__white"><span class="list--style"></span>${item.content}</li>`)
      .join("")}
        </ul>
        <div class="hidden-ui"></div>
      </section>
  </main>
  `;

  $("#content").html(cardDetail);
}
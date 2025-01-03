<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/jsp/common/common.jsp" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>product insert</title>
  <link rel="stylesheet" href="/css/admin/card/adminProductManagement.css" />
  <link rel="stylesheet" href="/css/admin/card/adminAddProduct.css" />
  <script defer src="/js/admin/card/previewImage.js"></script>
  <script defer type="module" src="/js/admin/card/addProductViewAllFncs.js"></script>
</head>
<body>

<jsp:include page="/WEB-INF/views/jsp/components/toast.jsp">
  <jsp:param value="${alertMsg}" name="alertMsg" />
</jsp:include>

<section id="root">
  <jsp:include page="/WEB-INF/views/jsp/components/NavAdmin.jsp" />

  <div id="content">
    <div class="title-container">
      <div class="title btn__yellow text__white">
        카드 추가
      </div>
    </div>

    <main class="main-container">
      <form id="productAddForm" class="productAddForm bg__white" enctype="multipart/form-data">
        <div class="product-container one-line">
          <div class="label-container">
            <label for="cardName" class="bg__gray text__semibold text__center box-s"><span class="text__red">*</span> 카드 명</label>
          </div>
          <div class="input-container">
            <input id="cardName" name="cardName" required class="box-xl" placeholder="ex) WING_Traffic" maxlength="30" />
          </div>
          <div class="label-container">
            <label for="cardCompany" class="bg__gray text__semibold text__center box-s"><span class="text__red">*</span> 카드사</label>
          </div>
          <div class="input-container">
            <input id="cardCompany" name="cardCompany" required class="box-l" placeholder="ex) WING_Card" maxlength="30" />
          </div>
        </div>

        <div class="product-container one-line">
          <div class="label-container">
            <label for="categoryNo" class="bg__gray text__semibold text__center box-s"><span class="text__red">*</span> 분류</label>
          </div>
          <div class="input-container">
            <select id="categoryNo" name="categoryNo" class="box-s">
              <option value="3" selected>Traffic</option>
              <option value="4">Shopping</option>
              <option value="5">Daily</option>
            </select>
          </div>
          <div class="label-container">
            <label for="registerDate" class="bg__gray text__semibold text__center box-sm"><span class="text__red">*</span> 등록 날짜</label>
          </div>
          <div class="input-container">
            <input type="date" id="registerDate" name="registerDate" required />
          </div>
        </div>

        <div class="product-container one-line">
          <div class="label-container">
            <label for="cardImgName" class="bg__gray text__semibold text__center box-m"><span class="text__red">*</span> 카드 이미지(파일)</label>
          </div>
          <div class="input-container">
            <input id="cardImgName" type="file" accept="image/*"  name="originalFileName" onchange="previewImage(this);" required class="btn bg__white" />
          </div>
        </div>

        <div class="product-container one-line preview-container">
          <div class="bg__gray exam-title text__semibold text__center box-m">카드 이미지 미리보기</div>
          <div id="imgContainer" class="img-container">
            <p id="previewPlaceholder" class="preview-placeholder">이미지를 선택하면 여기에 미리보기가 표시됩니다.</p>
            <img id="imagePreview" src="#" alt="preview" class="exam-img hidden"/>
          </div>
        </div>

        <div class="product-container">
          <div class="label-container">
            <label for="summBenefit" class="bg__gray text__semibold text__center">혜택 요약</label>
          </div>
          <div class="input-container">
            <textarea id="summBenefit" placeholder="혜택 요약은 주요 혜택을 요약한 내용입니다. (1. 국내외 가맹점 10% 생활 할인 2. 국내 가맹점 5% 식비 할인...)" class="textarea-content"></textarea>
          </div>
        </div>

        <div class="product-container benefit-container">
          <div class="label-container">
            <div class="bg__gray text__semibold text__center label--style"><span class="text__red">*</span> 주요 혜택</div>
          </div>

          <div class="border-style">
            <div class="btn-container one-line">
              <button id="benefitAddBtn" class="btn btn__generate text__center text__semibold">추가</button>
            </div>
            <div class="card-insert one-line">
              <div class="input-container">
                <input id="cardBenefitDivision" name="cardBenefitDivision" type="text" class="box-m" placeholder="교통할인" maxlength="30" />
              </div>

              <div class="input-container">
                <input id="cardBenefitDetail" name="cardBenefitDetail" type="text" class="box-l" placeholder="항공사, 기차, 고속버스, 대중교통" maxlength="160" />
              </div>

              <div class="input-container">
                <input
                  id="cardPercentage"
                  name="cardPercentage"
                  type="number"
                  class="box-m"
                  placeholder="10"
                  min="0"
                  max="100"
                />
              </div>
            </div>

            <div id="service">
              <ul class="service-content-header one-line">
                <li class="service-li box-m text__semibold text__center">구분</li>
                <li class="service-li box-l text__semibold text__center">세부내용</li>
                <li class="service-li box-m text__semibold text__center">할인율(금액)</li>
                <li class="service-li box-s text__center">비고</li>
              </ul>
            </div>
          </div>
        </div>
      </form>

      <div class="btn-container one-line">
        <a href="${pageContext.request.contextPath}/admin/productManagement/list?curPage=${curPage}&categoryName=${categoryName}"
          class="btn btn__generate"
        >취소</a>
        <button id="productInsertBtn" type="button" class="btn btn__generate text__center" data-cur-page="${curPage}" data-category-name="${categoryName}">등록</button>
      </div>

      <div class="hidden-ui"></div>
    </main>
  </div>

</section>
</body>
</html>
import { formatNumber,formatPhoneNumber,unformatNumber, formatPaymentAmountNumber } from "../../util/format.js"; // 상대 경로로 import
import { checkAndShowStoredMessage, showAlertMsg  } from "../../util/toast.js";

    const phoneInput = document.getElementById('phone');
    const yearlySalaryInput = document.getElementById('yearlySalary');
    const monthlySalaryInput = document.getElementById('monthlySalary');
    const memberNo = $("#memberNo").val();  // 숨겨진 필드에서 memberNo 값을 가져옴
    checkAndShowStoredMessage();
    /*$("#phone").val(response.phone);
    formatPhoneNumber(phoneInput);*/
    formatNumber(yearlySalaryInput);
    formatNumber(monthlySalaryInput);

const creDateInput = document.getElementById('creDate');
formatDate(creDateInput);
    $('#updateButton').on('click', function (e) {
        e.preventDefault(); // 기본 제출 방지

        const phoneInput = document.getElementById('phone');
        const yearlySalaryInput = document.getElementById('yearlySalary');
        const monthlySalaryInput = document.getElementById('monthlySalary');


        const memberNo = $("#memberNo").val();  // 숨겨진 필드에서 memberNo 값을 가져옴
        $.ajax({
            url: "/member/api/user/myPage/info",
            type: "GET",
            data: {memberNo: memberNo},
            success: function (response) {

                $("#email").val(response.email);
                $("#Name").val(response.userName);
                $("#phone").val(response.phone);
                formatPhoneNumber(phoneInput);
                const formattedDate = formatDate(response.creDate);
                $("#creDate").val(formattedDate); // 포맷된 날짜를 #creDate에 설정
                $("#yearlySalary").val(response.yearlySalary);
                $("#monthlySalary").val(response.monthlySalary);
                $("#password").val(response.pwd);  // 비밀번호 필드 채우기
                $("#confirmPassword").val(response.pwd);  // 비밀번호 확인 필드 채우기
                // 초기 포맷팅을 위해 응답 받은 후 formatNumber 호출
                formatNumber(yearlySalaryInput);
                formatNumber(monthlySalaryInput);


                // 구매 상태 확인 후 카드 영역 표시
                if (response.productPurchase === 'Y') {
                    fetchSellingCards(memberNo); // 구매 상태가 'Y'인 경우에만 카드 정보를 가져오는 함수 호출
                }

                // 탈퇴 신청 상태 확인 후 메시지 표시
                if (response.quitApply === 'Y') {
                    $("#quitApplyNotice").text("탈퇴 신청이 완료되었습니다.").show(); // 메시지를 보여줍니다
                } else {
                    $("#quitApplyNotice").hide(); // 탈퇴 신청이 아닐 경우 메시지를 숨김
                }
            },
            error: function () {
                alert("회원 정보를 불러오는 데 실패했습니다.");

            }
        });

        $('#updateButton').on('click', function (e) {
            e.preventDefault(); // 기본 제출 방지

            // 폼 유효성 검사
            if (!this.checkValidity()) {
                this.reportValidity(); // 유효성 검사 결과 보고
                return // 유효하지 않으면 종료
            }

            // formData 객체 명시적으로 정의
            const formData = {
                memberNo: memberNo,
                email: $email.val(),
                userName: $userName.val(),
                pwd: $pwdInput.val(),
                phone: $phoneInput.val(),
                monthlySalary: unFormatNumberString($monthlySalaryInput.val()), // 숫자 형식 변환
                yearlySalary: unFormatNumberString($salaryInput.val()), // 숫자 형식 변환
            };

            // AJAX 요청 함수 호출
            updateMemberInfo(formData);
        });

        /*  fetchSellingCards()*/
        $('#quitMemberButton').on('click', handleQuitButtonClick);

    });


// unformatNumberString 함수
    function unFormatNumberString(value) {
        return parseInt(value.replace(/만원/g, '').replace(/,/g, '').trim());
    }

    function formatDate(dateString) {
        if (!dateString) return '해당 사항 없음'; // null 또는 undefined일 경우
        const date = new Date(dateString);
        const year = date.getFullYear();
        const month = date.getMonth() + 1; // getMonth는 0부터 시작
        const day = date.getDate();
        return `${year}년 ${month}월 ${day}일`;
    }

//가져옴
//여기부터 수정용
    const $submitBtn = $("#updateButton");
    const $form = $("#myPageForm");
    const $email = $("#email");
    const $userName = $("#Name");
    const $emailError = $("#emailError");
    const $pwdInput = $("#password");
    const $pwdCheck = $("#confirmPassword");
    const $pwdCheckError = $("#pwdCheckError");
    const $pwdError = $("#pwdError");
    const $phoneInput = $("#phone");
    const $phoneError = $("#phoneError");
    const $salaryInput = $("#yearlySalary");
    const $monthlySalaryInput = $("#monthlySalary");
    const $salaryError = $("#salaryError");
    const $payError = $("#payError");
    const $userNameError = $("#userNameError");


    const emailRegex = /^(?=.{6,36}$)[a-z0-9_]+@[a-z0-9.\-]+\.[a-z]{2,}$/;
    const forbiddenPatterns = ['null', 'undefined', 'admin'];
    const pwdRegex = /^(?=.*\d)(?=.*[a-zA-Z])[a-zA-Z\d]{8,21}$/;
    const userNameRegex = /^[가-힣]{2,7}$/;
    const phoneRegex = /^01[016789]-?[0-9]{3,4}-?[0-9]{4}$/;
    const salaryRegex = /^[1-9]\d{0,3}(,\d{3})*$/;

    function validateInput(value, regex, errorMessage) {
        if (value == null || value === "") {
            return {isValid: false, message: "필수 입력 항목입니다."};
        }
        if (!regex.test(value)) {
            return {isValid: false, message: errorMessage};
        }
        return {isValid: true, message: ""};
    }

    function validateEmail(email) {
        const baseValidation = validateInput(email, emailRegex, "올바른 이메일 형식이 아닙니다.");
        if (!baseValidation.isValid) return baseValidation;
        if (forbiddenPatterns.some(pattern => email.toLowerCase().includes(pattern))) {
            return {isValid: false, message: "사용할 수 없는 이메일입니다."};
        }
        return {isValid: true, message: ""};
    }

    function validatePassword() {
        const pwdValue = $pwdInput.val();
        const pwdCheckValue = $pwdCheck.val();

        const isPwdValid = pwdRegex.test(pwdValue);

        if (!isPwdValid) {
            $pwdError.text("비밀번호는 8-21자의 영문자와 숫자 조합이어야 합니다.")
                .removeClass("text__correct")
                .addClass("text__error");
        } else {
            $pwdError.text("")
                .removeClass("text__error text__correct");
        }

        const isMatching = pwdValue === pwdCheckValue;

        if (pwdCheckValue) {
            $pwdCheckError.text(isMatching ? "비밀번호가 일치합니다." : "비밀번호가 일치하지 않습니다.")
                .removeClass("text__correct text__error")
                .addClass(isMatching ? "text__correct" : "text__error");
        } else {
            $pwdCheckError.text("")
                .removeClass("text__correct text__error");
        }

        return {isPwdValid, isMatching};
    }

    function updateUI($input, $error, validationResult) {
        if (validationResult.isValid) {
            $error.removeClass("text__error").addClass("text__transparent");
        } else {
            $error.text(validationResult.message)
                .removeClass("text__transparent")
                .addClass("text__error");
        }
        updateSubmitButtonState();
    }

    function updateSubmitButtonState() {
        const isEmailValid = validateEmail($email.val()).isValid;

        // 비밀번호 유효성 검사 결과 받기
        const {isPwdValid, isMatching} = validatePassword();

        const isUserNameValid = validateInput($userName.val(), userNameRegex, "").isValid;
        const isPhoneValid = validateInput($phoneInput.val(), phoneRegex, "").isValid;
        const isSalaryValid = validateInput($salaryInput.val(), salaryRegex, "").isValid;
        const isPayValid = validateInput($monthlySalaryInput.val(), salaryRegex, "").isValid;

        // 버튼 활성화 상태 업데이트
        $submitBtn.prop("disabled", !(isEmailValid && isPwdValid && isMatching && isUserNameValid && isPhoneValid && isSalaryValid && isPayValid));
    }

    $email.on("blur", function () {
        updateUI($email, $emailError, validateEmail($(this).val()));
    });

    $pwdInput.on("focus", function () {
        $pwdError.text("").removeClass("text__error text__correct");
        $pwdCheckError.text("").removeClass("text__error text__correct");
    });


    $pwdInput.on("blur", function () {
        const pwdValue = $(this).val();
        if (pwdValue === "") {
            $pwdError.text("필수 입력 항목입니다.")
                .removeClass("text__correct")
                .addClass("text__error");
        } else {
            validatePassword(); // 비밀번호 유효성 검사
        }
    });

    $pwdCheck.on("blur", function () {
        const pwdCheckValue = $(this).val();
        if (pwdCheckValue === "") {
            $pwdCheckError.text("필수 입력 항목입니다.")
                .removeClass("text__correct")
                .addClass("text__error");
        } else {
            validatePassword(); // 비밀번호 확인 유효성 검사
        }
    });

    $userName.on("blur", function () {
        updateUI($userName, $userNameError, validateInput($(this).val(), userNameRegex, "2~7자의 한글 이름을 입력해주세요."));
    });

    $phoneInput.on("blur", function () {
        formatPhoneNumber(this);
        updateUI($phoneInput, $phoneError, validateInput($(this).val(), phoneRegex, "올바른 휴대폰 번호 형식으로 입력해주세요."));
    });

    $salaryInput.on("blur", function () {
        formatNumber(this);
        updateUI($salaryInput, $salaryError, validateInput($(this).val(), salaryRegex, "올바른 형식으로 입력해주세요. (예: 4,500 또는 6,000)"));
    });

    $monthlySalaryInput.on("blur", function () {
        formatNumber(this);
        updateUI($monthlySalaryInput, $payError, validateInput($(this).val(), salaryRegex, "올바른 형식으로 입력해주세요. (예: 300 또는 500)"));
    });

    const messages = {
        email: "올바른 이메일 형식으로 입력해주세요. (예: wind@gmail.com)",
        pwd: "비밀번호는 8자의 영문자와 숫자 조합이어야 합니다. (대소문자 구분 없음, 공백 제외)",
        pwdCheck: "비밀번호가 일치하지 않습니다.",
        userName: "2~7자의 한글 이름을 입력해주세요.",
        phone: "올바른 휴대폰 번호 형식으로 입력해주세요. (예: 01012345678)",
        salary: "올바른 형식으로 입력해주세요. (예: 4,500 또는 6,000)",
        pay: "올바른 형식으로 입력해주세요. (예: 300 또는 500)"
    };

// AJAX 요청 함수
    function updateMemberInfo(data) {

        $.ajax({
            url: '/member/api/user/myPage/update', // API 엔드포인트
            type: 'PUT', // PUT 요청
            contentType: 'application/json', // JSON 형식으로 데이터 전송
            data: JSON.stringify(data), // JSON 문자열로 변환
            success: function (response) {
                showAlertMsg(response.alertMsg)
            },
            error: function (xhr, status, error) {
                alert("회원 정보 수정 중 오류가 발생했습니다."); // 오류 메시지
            }
        });
    }

    /*카드컨테이너*/

// 구매 상태가 'Y'인 경우 카드 정보를 가져오는 함수
    function fetchSellingCards(memberNo) {
        $.ajax({
            url: `/member/api/sellingCard/purchase/${memberNo}`, // 판매 카드 정보를 가져오는 API 호출
            type: "GET",
            success: function (cards) {
                // sellingCard를 기반으로 HTML 생성 (하나의 카드만 처리)

                // 카드가 하나만 있다는 가정 하에 첫 번째 카드 가져오기
                const card = cards[0]; // 첫 번째 카드 객체를 가져옴

                const cardHTML = `         
        
      `;

                console.log(cardHTML);

                $("#card-container").append(cardHTML); // #card-container에 카드 영역 추가
                $("#root").append(`' />`)

                // 카드 혜택 정보를 비동기적으로 가져오기
                fetchCardBenefit(card.CARDNO).then(benefitHtml => {
                    // 카드 혜택 정보를 가져온 후 해당 카드의 혜택 컨테이너에 추가
                    $(`#benefit-container`).html(benefitHtml);
                    $("#card-container").show(); // card-container를 보이게 함
                });

            },
            error: function () {
                alert("판매 카드 정보를 불러오는 데 실패했습니다.");
            }
        });
    }


    function fetchCardBenefit(cardNo) {
        return $.ajax({
            url: '/member/api/sellingCard/cardBenefit/' + cardNo, // 카드 혜택 정보 API URL
            type: 'GET', // HTTP 요청 방법
            dataType: 'json', // 응답 데이터 타입
        }).then(function (data) {
            // data가 배열 형태로 반환된다고 가정
            let cardBenefitHTML = '';
            data.forEach(benefit => {
                const {cardPercentage, cardBenefitDetail, cardBenefitDivision} = benefit;
                // 카드 혜택 정보를 HTML로 조립
                cardBenefitHTML += `
                <div class="card-benefit">
                    <p> ${cardBenefitDetail} ${cardPercentage}% ${cardBenefitDivision} </p>
                </div>
            `;
            });

            // 조립한 HTML을 페이지에 삽입
            $('#cardBenefit').html(cardBenefitHTML);
            return cardBenefitHTML; // 생성된 HTML을 반환
        }).fail(function (xhr, status, error) {

            $('#cardBenefit').html('<p>카드 혜택 정보를 불러오는 데 실패했습니다.</p>');
            return '<p>카드 혜택 정보를 불러오는 데 실패했습니다.</p>';
        });
    }


// 탈퇴 버튼 클릭 시 호출될 함수
    function handleQuitButtonClick() {
        // 탈퇴 확인
        if (!confirm("정말 탈퇴하시겠습니까?")) {
            return;
        }

        const memberNo = $('#memberNo').val(); // 회원 번호 가져오기

        // 탈퇴 신청 AJAX 요청 호출
        submitQuitRequest(memberNo);
    }

// 탈퇴 신청 요청 함수
    function submitQuitRequest(memberNo) {
        $.ajax({
            type: "POST",
            url: "/member/api/user/quit",
            contentType: "application/json",
            data: JSON.stringify({memberNo: memberNo}),
            success: function (response) {
                showAlertMsg(response.alertMsg)
            },
            error: function (xhr, status, error) {
                const errorMsg = xhr.responseJSON ? xhr.responseJSON.message : "탈퇴 처리 중 오류가 발생했습니다.";
                alert(errorMsg);
            }
        });
    }


    /*여기서부터해지신청란*/
    $(document).on("click", "#terminationRequestButton", function () {
        const memberNo = $("#memberNo").val();
        fetchCardsForTerminationRequest(memberNo);
    });

    function fetchCardsForTerminationRequest(memberNo) {
        $.ajax({
            url: `/member/api/sellingCard/purchase/${memberNo}`, // 판매 카드 정보를 가져오는 API 호출
            type: "GET",
            success: function (cards) {
                console.log(cards);

                // sellingCard를 기반으로 HTML 생성 (하나의 카드만 처리)

                // 카드가 하나만 있다는 가정 하에 첫 번째 카드 가져오기
                const card = cards[0]; // 첫 번째 카드 객체를 가져옴

                const terminationPageHTML = `
        <div class="title-container" xmlns="http://www.w3.org/1999/html">
          <div class="title-containerSecond">
            <div class="title btn__blue text__white">
              카드 해지 신청
            </div>
            <div class="btn-back-container">
              <button id="backButton" class="btn btn__generate">돌아가기</button>
           </div>
         </div>
        </div>
             
        <main class="main-container">
          <div class="card-container">
            <div class="card-title text__semibold">보유 카드</div>
            <div class="card-header">
              <div class="header-item box__s text__semibold">카드 명</div>
              <div class="header-item box__s text__semibold">취소 상태</div>
              <div class="header-item box__m text__semibold">등록 날짜</div>
              <div class="header-item box__l text__semibold">카드 번호</div>
              <div class="header-item box__m text__semibold">비고</div>
            </div>
            <div class="bg__white">
              <div class="list-container list-content one-line">
                <div class="list-item box__s text__center">
                  <div class="card-name">${card.CARDNAME}</div>      
                  <div class="img-delete-container text__center">
                    <img class="card--delete--img" src="/img/card/${card.STOREDFILENAME}" alt="${card.CARDNAME}" />
                  </div>
                </div>
                <div class="list-item box__tem box__s">${card.CARDTERMINATION}</div>
                <div class="list-item box_delete_date text__center box__m">
                  ${formatDate(card.SELLINGDATE)} <!-- date formatting function must be implemented -->
                </div>
                <div class="list-item box__delete_no text__center box__l">${card.MEMBERCARDNO}</div>
                <div class="list-item box__btn box__m">
                  <div id="button-container" class="btn-container one-line">
                    <button id="deleteCardButton" class="btn btn__generate btn--vertical">해지 신청</button>
                  </div>
                </div>
              </div>
          </div>
          <div class="card-delete-container">
            <div class="card-title text__center text__semibold">카드 해지 사유</div>
            <div id="terminationReasonForm"  class="termination-input" >
              <textarea id="terminationReason" class="input__text" placeholder="해지 신청 사유를 작성한 후 해지 신청의 해지 버튼을 클릭해주세요"></textarea>
            </div>
          </div>
          <div class="benefit-container">
            <div class="list-item box_title">이런 <span class="underline">혜택</span>이 사라져요!</div>
            <div class="list-item box__l" id="benefit-container"></div>
          </div>
        </div>
      </main>
      `;

                $("#content").html(terminationPageHTML);  // #card-container에 카드 영역 추가

                // 카드 혜택 정보를 비동기적으로 가져오기
                fetchCardBenefit(card.CARDNO).then(benefitHtml => {
                    // 카드 혜택 정보를 가져온 후 해당 카드의 혜택 컨테이너에 추가
                    $(`#benefit-container`).html(benefitHtml);
                });

                $('#backButton').on('click', function () {
                    window.location.href = './myPage';  // myPage로 이동
                });

                $('#deleteCardButton').on('click', function () {
                    $.ajax({
                        url: '/member/api/sellingCard/cardSoftDelete/' + memberNo, // URL 설정
                        type: 'DELETE', // 요청 타입
                        dataType: 'json', // 응답 데이터 타입,
                        contentType: 'application/json',
                        data: JSON.stringify({
                            memberNo: memberNo,
                            sellingCardNo: $("#sellingCardNo").val(),
                            memberCardNo: card.MEMBERCARDNO,
                            terminationReason: $("#terminationReason").val()
                        }),
                        success: function (res) {
                            // 성공 시
                            console.log(res)
                            window.location.href = "./myPage?message=" + encodeURIComponent(res.alertMsg);
                        },
                        error: function (xhr, status, error) {
                            const msg = xhr.responseJSON ? xhr.responseJSON.alertMsg : "알 수 없는 오류가 발생했습니다.";

                            showAlertMsg(msg);
                        }
                    });
                });
            },
            error: function () {
                showAlertMsg("판매 카드 정보를 불러오는 데 실패했습니다.");
            }
        });

        /*
                      </main>
                  `;
                  $("#content").html(terminationPageHTML);  // #card-container에 카드 영역 추가
                  // 카드 혜택 정보를 비동기적으로 가져오기
                  fetchCardBenefit(card.CARDNO).then(benefitHtml => {
                      // 카드 혜택 정보를 가져온 후 해당 카드의 혜택 컨테이너에 추가
                      $(`#benefit-container`).html(benefitHtml);
                  });
                  $('#backButton').on('click', function () {
                      window.location.href = './myPage';  // myPage로 이동
                  });

                  $('#deleteCardButton').on('click', function() {

                      $.ajax({
                          url: '/member/api/sellingCard/cardSoftDelete/' + memberNo, // URL 설정
                          type: 'POST', // 요청 타입
                          dataType: 'json', // 응답 데이터 타입
                          success: function(response) {
                              // 성공 시

                              showAlertMsg(response.alertMsg); // alertMsg를 알림으로 표시
                          },
                          error: function(xhr, status, error) {
                              // 오류 발생 시
                              if (xhr.status === 404) {
                                  alert('카드 삭제에 실패했습니다. 카드가 존재하지 않습니다.');
                              } else {
                                  alert('서버 오류가 발생했습니다. 다시 시도해 주세요.');
                              }
                          }
                      });
                  });
              },
              error: function() {
                  alert("판매 카드 정보를 불러오는 데 실패했습니다.");
              }
          });
        */
    }


    $(document).on('click', '#cardUseDetail', function () {
        const memberNo = $('#memberNo').val();
        $.ajax({
            url: '/member/api/accountBook/myPage/detail',  // 요청할 API URL
            type: 'GET',  // GET 방식으로 요청
            data: {memberNo: memberNo},  // memberNo를 쿼리 파라미터로 보냄
            success: function (response) {
                // 응답이 성공적으로 오면, 데이터를 처리
                console.log(response);  // 응답 받은 데이터 출력

                // 데이터 처리 예시 (받은 데이터로 화면 갱신)
                let htmlContent = '';
                let totalPaybackAmount = 0;
                htmlContent += `
                  <div class="title-container">
                    <div class="title btn__blue text__white">
                      마이 페이지
                    </div>
                  </div>
                    <div class="payback-income" id="payback-income"></div>
                  <div class="entry-form one-line">
                  <select id="categorySelect" name="category">
                    <option value="">전체</option>
                  </select>
                      <input type="date" id="startDate" />
                       <button id="searchButton">검색</button> 
                       <button class="btn__generate" id="returnBtn">돌아가기</button>
                  </div>    
                   
                  <main class="main-container">
                  <div class="detail-payback-title">카드 상세 내역</div>
                  <div class="detail-payback-container">
                  <div class="entry-category info_head text__semibold one-line">
                   <span>번호 </span>
                   <span>사용날짜 </span>
                  <span>상세내용</span>
                  <span>분류</span>
                  <span>사용금액</span>
                  <span>페이백</span>
                    </div>
                       <div class="entry-list">    `
                response.forEach(item => {
                    const date = new Date(item.creDate);
                    const formattedDate = `${date.getFullYear()}-${String(date.getMonth() + 1).padStart(2, '0')}-${String(date.getDate()).padStart(2, '0')}`;
                    // formatNumber로 금액을 포맷팅
                    const formattedPaymentAmount = formatPaymentAmountNumber(item.paymentAmount);
                    const formattedPaybackAmount = formatPaymentAmountNumber(item.paybackAmount);
                    totalPaybackAmount += item.paybackAmount;
                    htmlContent += `
  
                            <div class="entry-info">
                               <span class="account-book-no"> ${item.accountBookNo}</span>
                                <span class="entry-date">${formattedDate}</span>
                                <span class="entry-content"> ${item.content}</span>
                                <span class="minus-category">${item.minusCategoryName}</span>
                                <span class="payment-amount">${formattedPaymentAmount}</span>
                                <span class="payback-amount">${formattedPaybackAmount}</span>
                            </div>
                       
                    `;
                });
                htmlContent += ` </div> </div></main>`
                // 결과를 화면에 추가

                $('#content').html(htmlContent);
                $('#returnBtn').on('click', function () {
                    window.location.href = './myPage';  // myPage로 이동
                });
                // 페이백 총합을 화면에 추가 (id="payback-income"에 삽입)
                const formattedTotalPaybackAmount = formatPaymentAmountNumber(totalPaybackAmount); // 포맷팅 처리
                $('#payback-income').html(`<span>총 페이백 금액: ${formattedTotalPaybackAmount}</span>`);
                // 지출 카테고리 요청
                $.ajax({
                    url: '/member/api/accountBook/minusCategories',  // 지출 카테고리 API
                    type: 'GET',
                    success: function (categories) {
                        const categorySelect = $('#categorySelect'); // 카테고리 셀렉트 박스

                        // 카테고리 추가
                        categories.forEach(function (category, index) {
                            if (category !== null) { // null 값 제외
                                const option = document.createElement("option");
                                option.value = index + 2; // 카테고리 ID를 1부터 시작하도록 설정
                                option.textContent = category; // 카테고리 이름
                                categorySelect.append(option); // 옵션 추가
                            }
                        });
                    },
                    error: function (xhr, status, error) {
                        console.error("지출 카테고리 가져오기 실패:", error);
                    }
                });

            },
            error: function (xhr, status, error) {
                // 요청 실패 시 처리
                console.error('AJAX 요청 실패:', error);
            }
        });
    });

    $(document).on('change', '#categorySelect', function () {
        const selectedCategory = $(this).val(); // 선택된 카테고리 값을 가져옴
        const memberNo = $('#memberNo').val();
        console.log('Selected Category:', selectedCategory);
        // 카테고리 값이 있을 때만 요청을 보냄
        $.ajax({
            url: '/member/api/accountBook/myPage/detail',  // 요청할 API URL
            type: 'GET',  // GET 방식으로 요청
            data: {categoryNo: selectedCategory, memberNo: memberNo},  // 선택한 카테고리를 쿼리 파라미터로 보냄
            success: function (response) {
                // 응답이 성공적으로 오면, 해당 부분만 업데이트
                let htmlContent = ''; // 기존 내용 초기화

                // 카드 상세 내역을 갱신
                response.forEach(item => {
                    const date = new Date(item.creDate);
                    const formattedDate = `${date.getFullYear()}-${String(date.getMonth() + 1).padStart(2, '0')}-${String(date.getDate()).padStart(2, '0')}`;
                    const formattedPaymentAmount = formatPaymentAmountNumber(item.paymentAmount);
                    const formattedPaybackAmount = formatPaymentAmountNumber(item.paybackAmount);

                    htmlContent += `
                    <div class="entry-info">
                        <span class="account-book-no"> ${item.accountBookNo}</span>
                        <span class="entry-date">${formattedDate}</span>
                        <span class="entry-content"> ${item.content}</span>
                        <span class="minus-category">${item.minusCategoryName}</span>
                        <span class="payment-amount">${formattedPaymentAmount}</span>
                        <span class="payback-amount">${formattedPaybackAmount}</span>
                    </div>
                `;
                });

                // 갱신된 내역을 entry-list에 넣기
                $('.entry-list').html(htmlContent);
            },
            error: function (xhr, status, error) {
                console.error('AJAX 요청 실패:', error);
            }
        });
    });


    $(document).on('click', '#searchButton', function () {
        // 선택된 카테고리 값과 날짜 값을 가져오기
        const selectedCategory = $('#categorySelect').val();
        const startDate = $('#startDate').val();
        const memberNo = $('#memberNo').val();

        // 날짜 값이 비어있지 않으면, 'yyyy-mm-dd' 형식으로 변환 (필요시)
        // 선택된 값이 없으면 빈 문자열을 보내서 전체를 조회
        console.log('Selected Category:', selectedCategory);
        console.log('Selected Start Date:', startDate);

        // AJAX 요청 보내기
        $.ajax({
            url: '/member/api/accountBook/myPage/detail',  // 요청할 API URL
            type: 'GET',  // GET 방식으로 요청
            data: {
                memberNo: memberNo,
                categoryNo: selectedCategory,  // 선택한 카테고리
                startDate: startDate         // 선택한 시작 날짜
            },
            success: function (response) {
                if (Array.isArray(response) && response.length > 0) {
                    let htmlContent = '';  // 기존 내용 초기화

                    // 서버에서 받은 데이터로 화면 갱신
                    response.forEach(item => {
                        const date = new Date(item.creDate);
                        const formattedDate = `${date.getFullYear()}-${String(date.getMonth() + 1).padStart(2, '0')}-${String(date.getDate()).padStart(2, '0')}`;
                        const formattedPaymentAmount = formatPaymentAmountNumber(item.paymentAmount);
                        const formattedPaybackAmount = formatPaymentAmountNumber(item.paybackAmount);

                        htmlContent += `
                        <div class="entry-info">
                            <span class="account-book-no">${item.accountBookNo}</span>
                            <span class="entry-date">${formattedDate}</span>
                            <span class="entry-content">${item.content}</span>
                            <span class="minus-category">${item.minusCategoryName}</span>
                            <span class="payment-amount">${formattedPaymentAmount}</span>
                            <span class="payback-amount">${formattedPaybackAmount}</span>
                        </div>
                    `;
                    });


                    // 갱신된 내역을 entry-list에 넣기
                    $('.entry-list').html(htmlContent);
                } else {
                    $('.entry-list').html('<p>데이터가 없습니다.</p>');
                    console.error("서버 응답이 예상과 다릅니다.");

                }
            },
            error: function (xhr, status, error) {
                console.error("AJAX 요청 오류:", status, error);
            }
        });
    });

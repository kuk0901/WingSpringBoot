import { formatPaymentAmountNumber, formatNumber, unformatNumber } from "../../util/format.js";
import {checkAndShowStoredMessage, showAlertMsg} from "../../util/toast.js";
checkAndShowStoredMessage();
// accountBook.jsp의 js파일 유저용
let memberNo;
const today = new Date();
let currentYear = today.getFullYear(); // 현재 연도
let currentMonth = today.getMonth() + 1; // 현재 월
let currentLimit = 5;
$(document).ready(function() {
  memberNo = $('#memberNo').val(); // hidden input에서 memberNo 가져오기
  currentLimit = 5;
  // 초기 설정
  // 현재 날짜를 기준으로 연도와 월 설정


  // 가계부 목록을 가져오는 함수 호출+월간내역+
  const { startDate, endDate } = getStartAndEndDates(currentYear, currentMonth);
  // 시작 날짜와 종료 날짜 설정
  /*    let startDate = `${currentYear}-${currentMonth < 10 ? '0' : ''}${currentMonth}-01`; // YYYY-MM-DD 형식
      let endDate = new Date(currentYear, currentMonth, 1).toISOString().split('T')[0];*/ // 해당 월의 마지막 날

  fetchAccountBooks(memberNo, startDate, endDate,currentLimit);
  updateCurrentMonthDisplay(currentYear,currentMonth);
  loadCategories(); // 초기 카테고리 목록 로드
  loadPaymentMethods()//결제수단 목록 로드
  fetchMonthlyPayback( startDate, endDate,memberNo)

  //가계부추가함수
  $('#accountBookForm').on('submit', function(event) {
    event.preventDefault(); // 기본 폼 제출 방지

    // 입력 값 가져오기
    const date = $('#datepicker').val();
    const incomeExpense = $('#incomeExpenseToggle').val();
    const categoryNo = getCategoryNo(incomeExpense); // 카테고리 번호 가져오기
    const contents = $('#contents').val();
    const paymentMethod = $('#paymentMethodSelect').val();
    const amount = $('#amount').val();


    // 카테고리 번호에 따른 plusCategoryNo와 minusCategoryNo 설정
    let plusCategoryNo;
    let minusCategoryNo;
    //expaens-지출 income-소비
    if (incomeExpense === "expense") {
      plusCategoryNo = 1; // 고정
      minusCategoryNo = categoryNo; // 선택된 지출 카테고리
      // 지출 카테고리의 값이 2 이상인지 확인
      if (minusCategoryNo < 2) {
        alert(' 카테고리는 2 이상의 값을 선택해야 합니다.');
        return; // AJAX 요청 중단
      }
    } else if (incomeExpense === "income") {
      plusCategoryNo = categoryNo; // 선택된 수입 카테고리
      minusCategoryNo = 1; // 고정
      // 수입 카테고리의 값이 2 이상인지 확인
      if (plusCategoryNo < 2) {
        alert('수입 카테고리는 2 이상의 값을 선택해야 합니다.');
        return; // AJAX 요청 중단
      }
    }
    // paymentMethod 선택되지 않았는지 확인
    if (!paymentMethod) {
      alert('결제 수단을 선택해 주세요.'); // 결제 수단 선택 요청
      return; // AJAX 요청 중단
    }
    // content와 paymentAmount 필수 입력 검증
    if (!contents.trim()) {
      alert('내용을 입력해 주세요.'); // 내용 입력 요청
      return; // AJAX 요청 중단
    }

    if (!amount.trim()) {
      alert('결제 금액을 입력해 주세요.'); // 결제 금액 입력 요청
      return; // AJAX 요청 중단
    }
    if (!date) {
      alert('날짜를 입력해주세요'); //
      return; // AJAX 요청 중단
    }


    // AJAX 요청
    $.ajax({
      url: '/member/api/accountBook/add',
      type: 'POST',
      contentType: 'application/json',
      data: JSON.stringify({
        // accountBookNo를 제외하고 나머지 데이터 전송
        plusCategoryNo: plusCategoryNo,
        minusCategoryNo: minusCategoryNo,
        memberNo: memberNo,
        paymentMethodNo: paymentMethod,
        creDate: date, // Date 형식에 맞게 변환 필요
        content: contents,
        paymentAmount: amount
      }),
      success: function(response) {
        showAlertMsg(response.alertMsg)
        $('#accountBookForm')[0].reset(); // 폼 초기화
        const { startDate, endDate } = getStartAndEndDates(currentYear, currentMonth);
        console.log(startDate, endDate);
        fetchAccountBooks(memberNo, startDate, endDate,currentLimit); // 월별 가계부 내역 및 총합 계산 호출
        // 월 표시 업데이트 및 데이터 재로드
        updateCurrentMonthDisplay(currentYear, currentMonth);
        calculateMonthlyTotal(memberNo, startDate, endDate);
        getMonthlyAccountBooks(currentYear, currentMonth, memberNo);
        fetchMonthlyPayback( startDate, endDate,memberNo)
      },
      error: function(xhr) {
        const msg = xhr.responseJSON ? xhr.responseJSON.alertMsg : "알 수 없는 오류가 발생했습니다.";
        showAlertMsg(msg)
      }
    });
  });
});

// 카테고리 번호 가져오는 함수
function getCategoryNo(incomeExpense) {
  // incomeExpense에 따라 적절한 카테고리 번호를 반환하는 로직 구현
  // 예: 지출 또는 수입 카테고리 선택에 따른 로직 추가
  return $('#categorySelect').val(); // 예시로 선택된 값을 반환
}

// 초반디폴트  항목 수

// 현재 연도와 월 표시를 업데이트하는 함수
function updateCurrentMonthDisplay(year, month) {
  const monthDisplay = document.getElementById("currentMonth");
  monthDisplay.innerText = `${year}년 ${month}월`;
}

// 월 기준으로 가계부 총내역수 가져오는 함수
function getMonthlyAccountBooks(year, month, memberNo) {
  // 가계부 목록을 가져오는 함수 호출+월간내역+
  const { startDate, endDate } = getStartAndEndDates(year, month);
  // 시작 날짜와 종료 날짜 설정
  /* const startDate = `${year}-${month < 10 ? '0' + month : month}-01`; // 시작 날짜 (YYYY-MM-DD 형식)
   const endDate = new Date(year, month, 1).toISOString().split('T')[0]; // 해당 월의 마지막 날*/

  $.ajax({
    url: '/member/api/accountBook/monthlyCount', // API 엔드포인트
    type: 'GET',
    data: { memberNo: memberNo, startDate: startDate, endDate: endDate },
    success: function(count) {

      $('#totalEntries').text(`월간 내역 수: ${count}건`); // 총 내역 수 업데이트

    },
    error: function(err) {
      console.error('Error fetching monthly count:', err);
      console.log('Request Data:', { memberNo: memberNo, startDate: startDate, endDate: endDate }); // 요청 데이터 출력
    }
  });
}

// 가계부함수
function fetchAccountBooks(memberNo, startDate, endDate,currentLimit) {

  $.ajax({
    url: '/member/api/accountBook/list/date',
    type: 'GET',
    data: {
      memberNo: memberNo,
      startDate: startDate,
      endDate: endDate,
      limit:currentLimit,
    },
    dataType: 'json',
    success: function(data) {

      // 가계부 데이터가 없을 경우 메시지 출력
      if (data.length === 0) {
        $('.entry-list').html('<div class="no-data-message text__semibold text__center">등록된 내역이 없습니다.</div>'); // 내역이 없음을 알리는 메시지
        return; // 함수 종료
      }
      renderAccountBooks(data);
      // 총 월가계부 내역출력
      getMonthlyAccountBooks(currentYear, currentMonth, memberNo);
      // 총합 계산 함수 호출
      calculateMonthlyTotal(memberNo, startDate, endDate); // 월 총합 계산
    },
    error: function(error) {
      console.error('Error fetching account books:', error);
    },

  });
}

// 월 이동 함수
function changeMonth(offset) {
  currentMonth += offset;
  if (currentMonth === 0) {
    currentMonth = 12;
    currentYear--;
  } else if (currentMonth === 13) {
    currentMonth = 1;
    currentYear++;
  }

  const { startDate, endDate } = getStartAndEndDates(currentYear, currentMonth);
  console.log(startDate, endDate);
  fetchAccountBooks(memberNo, startDate, endDate,currentLimit); // 월별 가계부 내역 및 총합 계산 호출
  // 월 표시 업데이트 및 데이터 재로드
  updateCurrentMonthDisplay(currentYear, currentMonth);
  calculateMonthlyTotal(memberNo, startDate, endDate);
  getMonthlyAccountBooks(currentYear, currentMonth, memberNo);
  fetchMonthlyPayback( startDate, endDate,memberNo)

}

// 월 이동 버튼 클릭 이벤트 설정
document.getElementById("prevMonth").addEventListener("click", () => changeMonth(-1));
document.getElementById("nextMonth").addEventListener("click", () => changeMonth(1));
// 가계부 데이터를 렌더링
function renderAccountBooks(accountBooks) {
  const entryList = $('.entry-list'); // 전체 내역 div를 가리킴
  entryList.empty(); // 기존 데이터 지우기

  let lastDate = ''; // 마지막으로 출력된 날짜 초기화

  accountBooks.forEach(accountBook => {
    if (!accountBook.creDate) {
      console.warn('creDate is null or undefined for accountBook:', accountBook); // 경고 메시지
      return; // 이 경우 이 반복을 건너뜁니다
    }
    const date = accountBook.formattedCreDate.substring(0, 10); // 날짜 포맷 조정

    // 날짜가 바뀌었을 경우만 날짜 박스 추가
    if (lastDate !== date) {
      const dateBox = $('<div class="date-box"></div>').text(date); // 날짜 박스 생성
      entryList.append(dateBox); // 날짜 박스 추가
      lastDate = date; // 마지막 날짜 업데이트
    }

    // 카테고리 및 결제 정보 생성
    let minusCategory = accountBook.minusCategoryName ? ` ${accountBook.minusCategoryName}` : '';
    let plusCategory = accountBook.plusCategoryName ? ` ${accountBook.plusCategoryName}` : '';
    // 조건에 따라 minusCategory 또는 plusCategory 출력 제어
    if (accountBook.minusCategoryNo === 1) {
      minusCategory = ''; // minusCategoryNo가 1이면 minusCategory 출력 안 함
    }
    if (accountBook.plusCategoryNo === 1) {
      plusCategory = ''; // plusCategoryNo가 1이면 plusCategory 출력 안 함
    }
    const entryInfo = `
    <div class="entry-info" data-accountbook="${accountBook.accountBookNo}" data-member="${accountBook.memberNo}"> <!-- entry-info 클래스로 변경 -->
        <span>${minusCategory || plusCategory ? minusCategory || plusCategory : '데이터없음'}</span>
        <span>${accountBook.content || '내용 없음'}</span>
        <span>${accountBook.paymentMethodName || '결제 수단 없음'}</span>
        <span>
            ${accountBook.paymentAmount
        ? (minusCategory ? '-' : (plusCategory ? '+' : '')) + formatPaymentAmountNumber(accountBook.paymentAmount) + '원'
        : '금액 없음'}
        </span>
    </div>
            `;

    // entry-info를 전체 내역에 추가
    entryList.append(entryInfo);
  });
}

const incomeExpenseToggle = document.getElementById("incomeExpenseToggle");
const categorySelect     = document.getElementById("categorySelect");

// 카테고리 목록을 서버에서 가져오는 함수
function loadCategories() {
  $.when(
      $.ajax({
        url: '/member/api/accountBook/minusCategories', // 지출 카테고리 API
        type: 'GET',
        success: function(data) {
          window.expenseCategories = data;
          // 지출 카테고리 저장
        },
        error: function(xhr, status, error) {
          console.error("지출 카테고리 가져오기 실패:", error);
        }
      }),
      $.ajax({
        url: '/member/api/accountBook/plusCategories', // 수입 카테고리 API
        type: 'GET',
        success: function(data) {
          window.incomeCategories = data;
          // 수입 카테고리 저장
        },
        error: function(xhr, status, error) {
          console.error("수입 카테고리 가져오기 실패:", error);
        }
      })
  ).then(function() {
    // 초기 카테고리 목록을 채우기
    categorySelect.innerHTML = `<option value="">지출 카테고리 선택</option>`;
    expenseCategories.forEach(function(category, index) {
      if (category !== null) { // null 값 제외
        const option = document.createElement("option");
        option.value = index + 2; // 카테고리 ID를 1부터 시작하도록 수정
        option.textContent = category; // 카테고리 이름
        categorySelect.appendChild(option); // 옵션 추가
      }
    });
  });
}

// 카테고리 전환용
incomeExpenseToggle.addEventListener("change", function() {
  const selectedValue = this.value;
  categorySelect.innerHTML = ""; // 기존 옵션 초기화

  if (selectedValue === "expense") {
    // 지출일 경우
    categorySelect.innerHTML = `<option value="">지출 카테고리 선택</option>`;
    expenseCategories.forEach(function(category, index) {
      if (category !== null) { // null 값 제외
        const option = document.createElement("option");
        option.value = index +2; // 카테고리 ID를 1부터 시작하도록 수정
        option.textContent = category; // 카테고리 이름
        categorySelect.appendChild(option); // 옵션 추가
      }
    });
  } else if (selectedValue === "income") {
    // 수입일 경우
    categorySelect.innerHTML = `<option value="">수입 카테고리 선택</option>`;
    incomeCategories.forEach(function(category, index) {
      if (category !== null) { // null 값 제외
        const option = document.createElement("option");
        option.value = index + 2; // 카테고리 ID를 1부터 시작하도록 수정
        option.textContent = category; // 카테고리 이름
        categorySelect.appendChild(option); // 옵션 추가
      }
    });
  }
});

// 결제 방법 로드 함수
function loadPaymentMethods() {
  $.ajax({
    url: '/member/api/accountBook/paymentMethods', // 결제 방법 API
    type: 'GET',
    success: function(data) {
      paymentMethodSelect.innerHTML = `<option value="">결제 수단 선택</option>`; // 초기 옵션
      data.forEach(function(method, index) {
        if (method !== null) { // null 값 제외
          const option = document.createElement("option");
          option.value = index + 1; // 결제 수단 ID를 1부터 시작하도록 수정
          option.textContent = method; // 결제 수단 이름
          paymentMethodSelect.appendChild(option); // 옵션 추가
        }
      });

    },
    error: function(xhr, status, error) {
      console.error("결제 수단 가져오기 실패:", error);
    }
  });
}

/**
 * 월간 수입과 지출 총합 계산 및 업데이트
 * @param {number} memberNo - 회원 번호
 * @param {string} startDate - 시작 날짜 (YYYY-MM-DD 형식)
 * @param {string} endDate - 종료 날짜 (YYYY-MM-DD 형식)
 */
function calculateMonthlyTotal(memberNo, startDate, endDate) {
  $.ajax({
    url: '/member/api/accountBook/monthlyEntries', // API 엔드포인트
    type: 'GET',
    data: { memberNo: memberNo, startDate: startDate, endDate: endDate },
    success: function(entries) {
      let totalIncome = 0; // 수입 총합
      let totalExpense = 0; // 지출 총합

      entries.forEach(entry => {
        if (entry.plusCategoryNo === 1) {
          totalExpense += parseInt(entry.paymentAmount, 10); // 수입 추가
        } else if (entry.minusCategoryNo === 1) {
          totalIncome += parseInt(entry.paymentAmount, 10); // 지출 추가
        }
      });

      // HTML 업데이트
      $('#totalIncome').text(`월 수입: ${formatPaymentAmountNumber(totalIncome)}원`);
      $('#totalExpense').text(`월 지출: ${formatPaymentAmountNumber(totalExpense)}원`);
    },
    error: function(err) {
      console.error('Error fetching monthly entries:', err);
    }
  });
}

// 더보기 버튼 클릭 이벤트 핸들러
$('#loadMore').on('click', function() {
  currentLimit += 50; // limit을 100씩 증가
  const { startDate, endDate } = getStartAndEndDates(currentYear, currentMonth);
  /* const startDate = `${currentYear}-${currentMonth < 10 ? '0' + currentMonth : currentMonth}-01`;
   const endDate = new Date(currentYear, currentMonth+1, 0).toISOString().split('T')[0];*/
  fetchAccountBooks(memberNo, startDate, endDate,currentLimit); // 업데이트된 limit으로 데이터 가져오기
});

//detailopen
$(document).on("click", ".entry-info", function(e) {
  e.preventDefault();

  $.ajax({
    url: '/member/api/accountBook/detail',
    type: 'GET',
    data: {
      accountBookNo: $(this).data("accountbook"),
      memberNo: $(this).data("member"),
    },
    dataType: 'json',
    success: function(data) {
      renderAccountBookDetail(data);
      loadCategoriesForDetail(data);
    },
    error: function(error) {
      console.error('Error fetching account book detail:', error);
      alert('상세 정보를 불러오는 데 실패했습니다.');
    }
  });
});

function goToDetail(accountBookNo, memberNo) {
  $.ajax({
    url: '/member/api/accountBook/detail',
    type: 'GET',
    data: {
      accountBookNo: accountBookNo,
      memberNo: memberNo,
    },
    dataType: 'json',
    success: function(data) {
      console.log(data)
      renderAccountBookDetail(data);
      loadCategoriesForDetail(data);

    },
    error: function(error) {
      console.error('Error fetching account book detail:', error);
      alert('상세 정보를 불러오는 데 실패했습니다.');
    }
  });
}

// 가계부 상세 정보를 렌더링하는 함수
function renderAccountBookDetail(accountBook) {
  const detailContainer = $('#content');
  detailContainer.empty(); // 기존 내용 지우기

  try {
    // 결제 수단 로드
    // 상세 정보 HTML 구성
    const detailHtml = `
        <div id="content">
        <div class="title-container">
            <div class="title btn_red text__white">
                가계부 수정
            </div>
        </div>
        <div class="button-container">
            <button onclick="window.location.href='/member/accountBook/list'"
            class="btn__generate rtBtn">돌아가기</button>
        </div>
        <main class="main-container">
            <div>
                <div class="detail-container">
                    <span class="detail-label">일자</span>
                   <input type="date"  id="dateInput" class="detail-value" value="${formatDate(accountBook.creDate)}"/>
                </div>
                <div class="detail-container">
                    <span class="detail-label">분류</span>
                    <select id="categorySelect" class="select-value">
                    <option value="">지출 카테고리 선택</option>
                    <option value="1">급여</option>
                    <option value="2">식비</option>
                </select>
                </div >
                <div class="detail-container">
                    <span class="detail-label">상세 내용:</span>
                    <input type="text"  id="contentInput" class="detail-value" value="${accountBook.content || '내용 없음'}" />
                </div>
                <div class="detail-container"> 
                    <span class="detail-label">결제 수단:</span>
                    <select id="paymentMethodSelect" class="select-value">
                        <option value="">결제 수단 선택</option>
                    </select>
                </div>
                <div class="detail-container">
                    <span class="detail-label" id="amountLabel">금액: </span>
                    <input class="detail-value" id="amountInput" value="${accountBook.paymentAmount ? formatPaymentAmountNumber(accountBook.paymentAmount) : 0}" />
                </div>
            </div>
            <div class="button-container-bottom ">
              <button id="deleteBtn" class="btn__generate bottom-btn" data-accountbook="${accountBook.accountBookNo}">삭제</button>
              <button id="editBtn" class="btn__generate bottom-btn" data-accountbook="${accountBook.accountBookNo}" 
                data-member="${accountBook.memberNo}">
                수정
               </button>
            </div>
        </main>
        </div>
      `;

    // 상세 정보 렌더링
    detailContainer.append(detailHtml);

    // 금액 레이블 업데이트
    updateAmountLabel(accountBook);
    loadPaymentMethodsForDetail(accountBook);
  } catch (error) {
    console.error('Error loading payment methods:', error);
    alert('결제 수단을 불러오는 데 실패했습니다.');
  }
}

// 금액 레이블을 업데이트하는 함수
function updateAmountLabel(accountBook) {
  const amountLabel = document.getElementById('amountLabel');
  if (accountBook.plusCategoryNo === 1) {
    amountLabel.textContent = '금액: -'; // 지출인 경우 "-" 추가
  } else if (accountBook.minusCategoryNo === 1) {
    amountLabel.textContent = '금액: +'; // 수입인 경우 "+" 추가
  } else {
    amountLabel.textContent = '금액:'; // 기본값
  }
}

//디테일용 결제수단가져오기
function loadPaymentMethodsForDetail(accountBook) {
  $.ajax({
    url: '/member/api/accountBook/paymentMethods', // 결제 방법 API
    type: 'GET',
    success: function(data) {
      const paymentMethods = data.filter(method => method !== null); // null 값 제외

      // 결제 수단 선택 요소 업데이트
      const paymentMethodSelect = document.getElementById('paymentMethodSelect');
      paymentMethodSelect.innerHTML = ''; // 기존 옵션 제거


      // 결제 수단 옵션 추가
      paymentMethods.forEach((method, index) => {
        const option = document.createElement("option");
        option.value = index + 1; // ID는 1부터 시작
        option.textContent = method; // 결제 수단 이름
        // accountBook의 paymentMethodName과 비교하여 선택 상태 설정
        if (method === accountBook.paymentMethodName) {
          option.selected = true; // 현재 선택된 결제 수단 설정
        }
        paymentMethodSelect.appendChild(option); // 옵션 추가
      });
    },
    error: function(xhr, status, error) {
      console.error("결제 수단 가져오기 실패:", error);
    }
  });
}

//디테일용 카테고리가져오기
function loadCategoriesForDetail(accountBook) {
  const categorySelect = document.getElementById('categorySelect'); // 카테고리 선택 요소 가져오기
  categorySelect.innerHTML = ''; // 기존 옵션 제거

  // accountBook의 상태에 따라 적절한 요청 처리
  if (accountBook.minusCategoryNo === 1) {
    // 수입 카테고리 요청
    $.ajax({
      url: '/member/api/accountBook/plusCategories', // 수입 카테고리 API
      type: 'GET',
      success: function(data) {
        window.incomeCategories = data; // 수입 카테고리 저장
        incomeCategories.forEach(function(category, index) {
          if (category !== null) { // null 값 제외
            const option = document.createElement("option");
            option.value = index + 2; // 카테고리 ID를 2부터 시작하도록 수정
            option.textContent = category; // 카테고리 이름
            option.setAttribute("data-category", "income"); // 수입 카테고리로 설정
            // 현재 선택된 카테고리 설정
            if (category === accountBook.plusCategoryName) {
              option.selected = true; // 현재 선택된 카테고리 설정
            }
            categorySelect.appendChild(option); // 옵션 추가
          }

        });
      },
      error: function(xhr, status, error) {
        console.error("수입 카테고리 가져오기 실패:", error);
      }
    });
  } else if (accountBook.plusCategoryNo === 1) {
    // 지출 카테고리 요청
    $.ajax({
      url: '/member/api/accountBook/minusCategories', // 지출 카테고리 API
      type: 'GET',
      success: function(data) {
        window.expenseCategories = data; // 지출 카테고리 저장
        expenseCategories.forEach(function(category, index) {
          if (category !== null) { // null 값 제외
            const option = document.createElement("option");
            option.value = index + 2; // 카테고리 ID를 1부터 시작하도록 수정
            option.textContent = category; // 카테고리 이름
            option.setAttribute("data-category", "expense"); // 지출 카테고리로 설정

            // 현재 선택된 카테고리 설정
            if (category === accountBook.minusCategoryName) {
              option.selected = true; // 현재 선택된 카테고리 설정
            }

            categorySelect.appendChild(option); // 옵션 추가
          }
        });
      },
      error: function(xhr, status, error) {
        console.error("지출 카테고리 가져오기 실패:", error);
      }
    });
  } else {
    // 기본적으로 카테고리가 없는 경우
    categorySelect.innerHTML = `<option value="">카테고리 없음</option>`;
  }
}

$(document).on("blur", "#amountInput", function(e) {
  e.preventDefault();
  formatNumber(this);
});


function formatDate(dateString) {
  if (!dateString) return '내용 없음'; // 값이 없으면 기본 메시지 반환
  const date = new Date(dateString);

  // 로컬 타임존을 기준으로 날짜 포맷 (YYYY-MM-DD 형식)
  const year = date.getFullYear();
  const month = String(date.getMonth() + 1).padStart(2, '0'); // 월은 0부터 시작하므로 +1
  const day = String(date.getDate()).padStart(2, '0');

  return `${year}-${month}-${day}`;
}
/*//가계부detail에서삭제
function deleteAccountBook(accountBookNo) {
  if (confirm("정말로 이 가계부 항목을 삭제하시겠습니까?")) {
    $.ajax({
      url: `/member/api/accountBook/${accountBookNo}`, // DELETE 요청을 보낼 URL
      type: 'PATCH',
      success: function() {
        alert("가계부 항목이 삭제되었습니다.");
        // 목록 페이지로 리다이렉트 또는 삭제 후 UI 업데이트
        window.location.href = '/member/accountBook/list'; // 목록 페이지로 이동
      },
      error: function(error) {
        console.error("가계부 항목 삭제 실패:", error);
        alert('가계부 항목 삭제에 실패했습니다.');
      }
    });
  }
}*/
//
// //가계부detail에서수정
// function editAccountBook(accountBookNo,memberNo) {
//   // 필요한 입력 필드 값 가져오기
//
//   const paymentMethodNo = parseInt(document.getElementById('paymentMethodSelect').value, 10); // 결제 수단 형변환
//
//   const categorySelect = document.getElementById('categorySelect'); // 카테고리 선택 요소
//   const amountValue = document.getElementById("amountInput").value;
//   const contentValue = document.getElementById("contentInput").value;
//   const dateValue = document.getElementById("dateInput").value;
//   const formattedDate = new Date(dateValue).toISOString().split('T')[0]; // "YYYY-MM-DD" 형식으로 변환
//
//   // 카테고리 선택 값에 따라 plusCategoryNo와 minusCategoryNo 설정
//   let plusCategoryNo = 1;
//   let minusCategoryNo = 1;
//   const selectedCategoryValue = parseInt(categorySelect.value, 10); // 카테고리 값 형변환
//
//   if (categorySelect.options[0].textContent === "수입 카테고리 선택") {
//     plusCategoryNo = selectedCategoryValue;
//   } else if (categorySelect.options[0].textContent === "지출 카테고리 선택") {
//     minusCategoryNo = selectedCategoryValue;
//   }
//
//   const updatedData = {
//     memberNo: memberNo,
//     creDate: formattedDate,
//     accountBookNo: accountBookNo,
//     plusCategoryNo: plusCategoryNo,
//     minusCategoryNo: minusCategoryNo,
//     content: contentValue,
//     paymentMethodNo: paymentMethodNo,
//     paymentAmount: unformatNumber(amountValue)
//   };
//   console.log(updatedData);
//   console.log("함수 내 memberNo:", memberNo);
//   // AJAX 요청 보내기
//   $.ajax({
//     url: `/member/api/accountBook/update`, // 수정 API 엔드포인트
//     type: 'PUT', // 또는 'PATCH'
//     contentType: 'application/json', // JSON 형식으로 요청
//     data: JSON.stringify(updatedData), // 데이터 객체를 JSON으로 변환
//     success: function(response) {
//       showAlertMsg(response.alertmsg);
//       goToDetail(accountBookNo, memberNo);
//     },
//     error: function(xhr, status, error) {
//
//       console.error('가계부 수정 실패:', error);
//     }
//   });
// }

function getStartAndEndDates(year, month) {
  // 시작 날짜 (YYYY-MM-DD 형식)
  const startDate = `${year}-${month < 10 ? '0' + month : month}-01`;

  // 각 월의 마지막 날을 하드코딩
  const lastDays = [0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30,31]; // 인덱스 0은 사용하지 않음 (1월부터 12월까지)

  let endDate;
  if (month === 2) {
    // 윤년 판단: 4로 나눠지면서 100으로 나눠지지 않거나 400으로 나눠질 때 윤년
    const isLeapYear = (year % 4 === 0 && year % 100 !== 0) || (year % 400 === 0);
    endDate = `${year}-${month < 10 ? '0' + month : month}-${isLeapYear ? '29' : '28'}`; // 2월의 마지막 날
  } else {
    endDate = `${year}-${month < 10 ? '0' + month : month}-${lastDays[month]}`; // 해당 월의 마지막 날
  }

  return { startDate, endDate };
}

//월수입 정렬함수
$('#totalExpense').on('click', function() {
  // 필요한 변수 정의 (예시로 memberNo, startDate, endDate를 지정)
  memberNo = $('#memberNo').val();
  const { startDate, endDate } = getStartAndEndDates(currentYear, currentMonth);
  //함수 호출
  fetchMonthlyExpenseBooks(memberNo, startDate, endDate);
});

// 월별 지출 조회 함수
function fetchMonthlyExpenseBooks(memberNo, startDate, endDate) {
  $.ajax({
    url: '/member/api/accountBook/list/monthlyExpense',
    type: 'GET',
    data: {
      memberNo: memberNo,
      startDate: startDate,
      endDate: endDate,
    },
    dataType: 'json',
    success: function(data) {
      console.log("Received monthly expense data:", data);

      // 월별 지출 데이터가 없을 경우 메시지 출력
      if (data.length === 0) {
        $('.entry-list').html('<div class="no-data-message text__semibold text__center">등록된 지출 내역이 없습니다.</div>'); // 지출 내역이 없음을 알리는 메시지
        return; // 함수 종료
      }

      renderAccountBooks(data); // 월별 지출 데이터를 렌더링하는 함수 호출

    },
    error: function(error) {
      console.error('Error fetching monthly expense books:', error);
    },
  });
}

//월수입 정렬함수
$('#totalIncome').on('click', function() {
  // 필요한 변수 정의 (예시로 memberNo, startDate, endDate를 지정)
  memberNo = $('#memberNo').val();
  const { startDate, endDate } = getStartAndEndDates(currentYear, currentMonth);

  // 월별 지출 조회 함수 호출
  fetchMonthlyIncomeBooks(memberNo, startDate, endDate);
});

function fetchMonthlyIncomeBooks(memberNo, startDate, endDate) {
  $.ajax({
    url: '/member/api/accountBook/list/monthlyIncome',
    type: 'GET',
    data: {
      memberNo: memberNo,
      startDate: startDate,
      endDate: endDate,
    },
    dataType: 'json',
    success: function(data) {
      console.log("Received monthly expense data:", data);

      // 월별 지출 데이터가 없을 경우 메시지 출력
      if (data.length === 0) {
        $('.entry-list').html('<div class="no-data-message">등록된 지출 내역이 없습니다.</div>'); // 지출 내역이 없음을 알리는 메시지
        return; // 함수 종료
      }

      renderAccountBooks(data); // 월별 지출 데이터를 렌더링하는 함수 호출

    },
    error: function(error) {
      console.error('Error fetching monthly expense books:', error);
    },
  });
}



$(document).on("click", "#editBtn", function(e) {
  e.preventDefault();

  const $this = $(this);
  const accountBookNo = $this.data("accountbook");
  const memberNo = $this.data("member");

  const paymentMethodNo = parseInt($('#paymentMethodSelect').val(), 10);
  const categorySelect = $('#categorySelect');
  const amountValue = $("#amountInput").val();
  const contentValue = $("#contentInput").val();
  const dateValue = $("#dateInput").val();
 /* const formattedDate = new Date(dateValue).toLocaleDateString("en-CA").split('T')[0];*/
  const localDate = new Date(dateValue);  // Date 객체로 변환
  const formattedDate = localDate.toLocaleDateString("en-CA"); // 'YYYY-MM-DD' 형식으로 포맷
  console.log(formattedDate); // 2024-11-01 (로컬 타임존에 맞춰 표시)
  let plusCategoryNo = 1;
  let minusCategoryNo = 1;
  const selectedCategoryValue = parseInt(categorySelect.val(), 10);

  // 선택된 카테고리의 data-category 값에 따라 분기
  const selectedCategoryData = categorySelect.find("option:selected").data("category");

  console.log(formattedDate);

// 카테고리 선택에 따른 조건 분기
  if (selectedCategoryData === "income") {
    // 수입 카테고리 선택된 경우
    plusCategoryNo = selectedCategoryValue;
  } else if (selectedCategoryData === "expense") {
    // 지출 카테고리 선택된 경우
    minusCategoryNo = selectedCategoryValue;
  }

  const updatedData = {
    memberNo: memberNo,
    creDate: formattedDate,
    accountBookNo: accountBookNo,
    plusCategoryNo: plusCategoryNo,
    minusCategoryNo: minusCategoryNo,
    content: contentValue,
    paymentMethodNo: paymentMethodNo,
    paymentAmount: unformatNumber(amountValue)
  };



  $.ajax({
    url: `/member/api/accountBook/update`,
    type: 'PUT',
    contentType: 'application/json',
    data: JSON.stringify(updatedData),
    success: function(res) {
      console.log(res);
      goToDetail(accountBookNo, memberNo);
      const message = encodeURIComponent(res.alertMsg || "가계부 수정에 성공했습니다");
      window.location.href = `/member/accountBook/list?message=${message}`; // 카테고리 목록 페이지로 이동
    },
    error: function(xhr, status, error) {
      console.error('가계부 수정 실패:', error);
    }
  });
});

$(document).on("click", "#deleteBtn", function(e) {
  e.preventDefault();

  const $this = $(this);
  const accountBookNo = $this.data("accountbook");

  if (confirm("정말로 이 가계부 항목을 삭제하시겠습니까?")) {
    $.ajax({
      url: `/member/api/accountBook/${accountBookNo}`, // DELETE 요청을 보낼 URL
      type: 'PATCH',
      success: function(response) {
        window.location.href = "./list?message=" + encodeURIComponent(response.alertMsg);

      },
      error: function(error) {
        console.error("가계부 항목 삭제 실패:", error);
        alert('가계부 항목 삭제에 실패했습니다.');
      }
    });
  }
});

function fetchMonthlyPayback(startDate, endDate, memberNo) {
  $.ajax({
    url: '/member/api/accountBook/list/monthlyPayback', // API 엔드포인트
    type: 'GET', // GET 요청
    data: {
      startDate: startDate,
      endDate: endDate,
      memberNo: memberNo
    },
    success: function(response) {
      // 응답 받은 데이터를 처리
      // 예: 페이백 금액 합산
      let totalPaybackAmount = 0;
      if (response && response.length > 0) {
        response.forEach(function(item) {
          totalPaybackAmount += item.paybackAmount; // 예시: paybackAmount 필드가 있는 경우
        });
      }

      // 페이백 예정 금액 업데이트
      $('#payBackIncome').text('다음달 페이백 금액: ' + formatPaymentAmountNumber(totalPaybackAmount) + ' 원');
    },
    error: function(xhr, status, error) {
      console.log("에러 발생: " + error);
      $('#payBackIncome').text('페이백예정 금액: 오류');
    }
  });
}
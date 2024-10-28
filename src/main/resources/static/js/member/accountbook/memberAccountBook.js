// accountBook.jsp의 js파일 유저용
var memberNo;
$(document).ready(function() {
     memberNo = $('#memberNo').val(); // hidden input에서 memberNo 가져오기
    var limit = 10;
    // 초기 설정
    let currentYear;
    let currentMonth;
    // 가계부 목록을 가져오는 함수 호출+월간내역+
    fetchAccountFirstBooks(memberNo, currentLimit);
    loadCategories(); // 초기 카테고리 목록 로드
    loadPaymentMethods()//결제수단 목록 로드

    //가계부추가함수
    $('#accountBookForm').on('submit', function(event) {
        event.preventDefault(); // 기본 폼 제출 방지

        // 입력 값 가져오기
        var date = $('#datepicker').val();
        var incomeExpense = $('#incomeExpenseToggle').val();
        var categoryNo = getCategoryNo(incomeExpense); // 카테고리 번호 가져오기
        var contents = $('#contents').val();
        var paymentMethod = $('#paymentMethodSelect').val();
        var amount = $('#amount').val();


        // 카테고리 번호에 따른 plusCategoryNo와 minusCategoryNo 설정
        let plusCategoryNo;
        let minusCategoryNo;

        if (incomeExpense === "expense") {
            plusCategoryNo = 1; // 고정
            minusCategoryNo = categoryNo; // 선택된 지출 카테고리
            // 지출 카테고리의 값이 2 이상인지 확인
            if (minusCategoryNo < 2) {
                alert('지출 카테고리는 2 이상의 값을 선택해야 합니다.');
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
                alert('가계부가 성공적으로 추가되었습니다!');
                $('#accountBookForm')[0].reset(); // 폼 초기화
            },
            error: function(xhr) {
                alert('오류 발생: ' + xhr.responseText);
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
let currentLimit = 5; // 초반디폴트  항목 수
// 초기화면용 가계부 목록을 가져오는 함수
let currentYear; // 현재 연도
let currentMonth; // 현재 월
//초기화면용
function fetchAccountFirstBooks(memberNo, limit) {
    $.ajax({
        url: "/member/api/accountBook/list",
        type: "GET",
        data: {
            memberNo: memberNo,
            limit: limit
        },
        success: function(data) {
            renderAccountBooks(data); // 가져온 데이터로 테이블 렌더링
            // 가장 최근의 credate 가져오기
            if (data.length > 0) {
                const lastEntry = data[data.length - 1]; // 마지막 내역
                const credateString = lastEntry.creDate;
                const lastCredate = new Date(credateString);
                // credate를 Date 객체로 변환

                // 연도와 월 추출
                currentYear = lastCredate.getFullYear(); // 연도 추출
                currentMonth = lastCredate.getMonth() + 1; // 월 추출 (0부터 시작하므로 +1)

                console.log(`추출된 연도: ${currentYear}, 월: ${currentMonth}`);

                // 시작 및 종료 날짜 생성
                const startDate = `${currentYear}-${currentMonth < 10 ? '0' : ''}${currentMonth}-01`; // 시작 날짜 (YYYY-MM-DD 형식)
                const endDate = new Date(currentYear, currentMonth, 0).toISOString().split('T')[0]; // 종료 날짜 (해당 월의 마지막 날)

                console.log(`시작 날짜: ${startDate}, 종료 날짜: ${endDate}`); // 디버깅용 로그

                // 총 월가계부 내역출력
                getMonthlyAccountBooks(currentYear, currentMonth, memberNo);
                calculateMonthlyTotal(memberNo, startDate, endDate);
                // 현재 월 표시 업데이트
                updateCurrentMonthDisplay(currentYear, currentMonth);
            }
        },
        error: function(err) {
            console.error('Error fetching account books:', err);
        }
    });
}
// 현재 연도와 월 표시를 업데이트하는 함수
function updateCurrentMonthDisplay(year, month) {
    const monthDisplay = document.getElementById("currentMonth");
    monthDisplay.innerText = `${year}년 ${month}월`;
}
// 월 기준으로 가계부 총내역수 가져오는 함수
function getMonthlyAccountBooks(year, month, memberNo) {
    // 시작 날짜와 종료 날짜 설정
    const startDate = `${year}-${month < 10 ? '0' + month : month}-01`; // 시작 날짜 (YYYY-MM-DD 형식)
    const endDate = `${year}-${month < 10 ? '0' + month : month + 1}-01`; // 다음 달의 첫 번째 날짜

    $.ajax({
        url: '/member/api/accountBook/monthlyCount', // API 엔드포인트
        type: 'GET',
        data: { memberNo: memberNo, startDate: startDate, endDate: endDate },
        success: function(count) {
            $('#totalEntries').text(`전체 내역 수: ${count}건`); // 총 내역 수 업데이트

        },
        error: function(err) {
            console.error('Error fetching monthly count:', err);
            console.log('Request Data:', { memberNo: memberNo, startDate: startDate, endDate: endDate }); // 요청 데이터 출력
        }
    });
}
//월이동용 가계부함수
function fetchAccountBooks(memberNo, startDate, endDate,limit) {
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
            renderAccountBooks(data);
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

    const startDate = `${currentYear}-${currentMonth < 10 ? '0' + currentMonth : currentMonth}-01`;
    const endDate = new Date(currentYear, currentMonth, 0).toISOString().split('T')[0];
    console.log("startDate,endDate", startDate, endDate);
    // 월 표시 업데이트 및 데이터 재로드
    updateCurrentMonthDisplay(currentYear, currentMonth);
    fetchAccountBooks(memberNo, startDate, endDate,currentLimit); // 월별 가계부 내역 및 총합 계산 호출

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
        const date = accountBook.creDate.substring(0, 10); // 날짜 포맷 조정

        // 날짜가 바뀌었을 경우만 날짜 박스 추가
        if (lastDate !== date) {
            const dateBox = $('<div class="date-box"></div>').text(date); // 날짜 박스 생성
            entryList.append(dateBox); // 날짜 박스 추가
            lastDate = date; // 마지막 날짜 업데이트
        }

        // 카테고리 및 결제 정보 생성
        const minusCategory = accountBook.minusCategoryName ? ` ${accountBook.minusCategoryName}` : '';
        const plusCategory = accountBook.plusCategoryName ? ` ${accountBook.plusCategoryName}` : '';

        const entryInfo = `
    <div class="entry-info" onclick="goToDetail(${accountBook.accountBookNo}, ${accountBook.memberNo})"> <!-- entry-info 클래스로 변경 -->
        <span>${minusCategory || plusCategory ? minusCategory || plusCategory : '데이터없음'}</span>
        <span>${accountBook.content || '내용 없음'}</span>
        <span>${accountBook.paymentMethodName || '결제 수단 없음'}</span>
        <span>
            ${accountBook.paymentAmount
            ? (minusCategory ? '-' : (plusCategory ? '+' : '')) + accountBook.paymentAmount.toLocaleString() + '원'
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
                option.value = index + 1; // 카테고리 ID를 1부터 시작하도록 수정
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
                option.value = index + 1; // 카테고리 ID를 1부터 시작하도록 수정
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
                option.value = index + 1; // 카테고리 ID를 1부터 시작하도록 수정
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

// // 월간내역ajax
// function fetchMonthlyCount() {
//     const memberNo = 14; // 예시: 현재 로그인한 사용자의 회원 번호
//     const startDate = '2024-10-01'; // 월의 첫 번째 날짜
//     const endDate = '2024-11-01'; // 다음 월의 첫 번째 날짜
//
//     $.ajax({
//         url: '/member/api/accountBook/monthlyCount',
//         type: 'GET',
//         data: { memberNo: memberNo, startDate: startDate, endDate: endDate },
//         success: function(count) {
//             $('#totalEntries').text(`전체 내역 수: ${count}건`);
//         },
//         error: function(err) {
//             console.error('Error fetching monthly count:', err);
//         }
//     });
// }


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
            $('#totalIncome').text(`월수입: ${totalIncome}원`);
            $('#totalExpense').text(`월지출: ${totalExpense}원`);
        },
        error: function(err) {
            console.error('Error fetching monthly entries:', err);
        }
    });
}

// 더보기 버튼 클릭 이벤트 핸들러
$('#loadMore').on('click', function() {
    currentLimit += 50; // limit을 100씩 증가
    fetchAccountFirstBooks(memberNo, currentLimit); // 업데이트된 limit으로 데이터 가져오기
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
            renderAccountBookDetail(data);
        },
        error: function(error) {
            console.error('Error fetching account book detail:', error);
            alert('상세 정보를 불러오는 데 실패했습니다.');
        }
    });
}

function renderAccountBookDetail(accountBook) {
    // 예를 들어, .detail-container에 내용을 렌더링
    const detailContainer = $('#content');
    detailContainer.empty(); // 기존 내용 지우기

    // 결제 수단 값을 저장할 배열
    var paymentMethodValues = []; // 결제 수단 값을 저장할 배열
    $('#paymentMethodSelect option').each(function() {
        var text = $(this).text(); // 각 옵션의 내부 텍스트 값을 가져오기
        if (text) { // 빈 값 제외
            paymentMethodValues.push(text); // 배열에 추가
        }
    });
    console.log("모든 결제 수단 이름:", paymentMethodValues);
    // 상세 정보 HTML 구성
    const detailHtml = `
        <div id="content">
        <div class="title-container">
            <div class="title btn_red text__white">
                가계부 수정
            </div>
        </div>
        <div>
       <button onclick="window.location.href='/member/accountBook/list'">돌아가기</button>
        </div>
        <!--우선 위치대략적으로 정한거임-->
       <main class="main-container">
        <div>
            <div>
                <span class="detail-label">카테고리: </span>
                <input type="text" class="category-value" value="${accountBook.minusCategoryName || accountBook.plusCategoryName || '데이터 없음'}" />
            </div>
            <div>
                <span class="detail-label">상세 내용:</span>
                <input type="text" class="detail-value" value="${accountBook.content || '내용 없음'}" />
            </div>
            <div>
                <span class="detail-label">결제 수단:</span>
                <select id="paymentMethodSelect" class="payment-value">
                    <option value="">결제 수단 선택</option>
                    ${paymentMethodValues.map((method, index) => `
                        <option value="${index + 1}" ${method === accountBook.paymentMethodName ? 'selected' : ''}>${method}</option>
                    `).join('')}
                </select>
            </div>
            <div>
                <span class="detail-label">금액: </span>
                <input type="number" class="amount-value" value="${accountBook.paymentAmount ? accountBook.paymentAmount : 0}" />
            </div>
            <button onclick="deleteAccountBook(${accountBook.accountBookNo}, ${accountBook.memberNo})">삭제</button>
            <button onclick="editAccountBook(${accountBook.accountBookNo}, ${accountBook.memberNo})">수정</button>
        </div>
    </main>
    `;

    // 상세 정보 렌더링
    detailContainer.append(detailHtml);
}




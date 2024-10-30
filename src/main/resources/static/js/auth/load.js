function updateGraph() {
  const graphLine = document.querySelector('.graph-line');
  let points = "0,200 ";
  for (let i = 0; i < 50; i++) {
    const x = i * 20;
    const y = Math.random() * 180 + 20;
    points += `${x},${y} `;
  }
  points += "1000,200";
  graphLine.setAttribute('points', points);
}

function resetAnimation() {
  const coins = document.querySelectorAll('.coin');
  const graphLine = document.querySelector('.graph-line');

  coins.forEach(coin => {
    coin.style.animation = 'none';
    setTimeout(() => coin.style.animation = '', 10);
  });

  graphLine.style.animation = 'none';
  setTimeout(() => {
    graphLine.style.animation = 'drawGraph 8s linear infinite'; // 애니메이션 시간을 8초로 설정
    updateGraph();
  }, 10);
}

// 동전 애니메이션 리셋 (3초마다)
setInterval(() => {
  const coins = document.querySelectorAll('.coin');
  coins.forEach(coin => {
    coin.style.animation = 'none';
    setTimeout(() => coin.style.animation = '', 10);
  });
}, 3000);

// 그래프 애니메이션 리셋 (8초마다)
setInterval(() => {
  const graphLine = document.querySelector('.graph-line');
  graphLine.style.animation = 'none';
  setTimeout(() => {
    graphLine.style.animation = 'drawGraph 8s linear infinite';
    updateGraph();
  }, 10);
}, 8000);

// 초기 그래프 생성
updateGraph();
// CSS 수정도 필요
const $totalCards = $("#totalCards");
const totalCardsData = JSON.parse($totalCards.attr("data-total-card-list"));

const $recommendedCards = $("#recommendedCards");
const recoCardsData = JSON.parse($recommendedCards.attr("data-reco-card-list"));

const $terminatedCards = $("#terminatedCards");
const termCardsData = JSON.parse($terminatedCards.attr("data-term-card-list"));

// chart 배경
const chartAreaPlugin = {
  id: 'chartAreaPlugin',
  beforeDraw: (chart, args, options) => {
    const {ctx, chartArea: {left, top, width, height}} = chart;
    ctx.save();
    ctx.fillStyle = options.backgroundColor;
    ctx.fillRect(left, top, width, height);
    ctx.restore();
  }
};

// x축 크기, 배경색, 외부여백
const xAxisLabelPlugin = {
  id: 'xAxisLabelPlugin',
  afterDraw: (chart, args, options) => {
    const {ctx, chartArea: {left, right, top, bottom}, scales: {x}} = chart;
    const labels = chart.data.labels;

    const marginTop = 10; // 원하는 margin-top 값

    labels.forEach((label, index) => {
      const xPosition = x.getPixelForTick(index);
      const yPosition = bottom + marginTop; // margin-top 적용

      // 레이블 배경 그리기
      ctx.save();
      ctx.fillStyle = options.backgroundColor || '#f0f0f0';
      ctx.fillRect(xPosition - options.width / 2, yPosition, options.width, 25);
      ctx.restore();

      // 레이블 텍스트 그리기
      ctx.save();
      ctx.textAlign = 'center';
      ctx.textBaseline = 'top';
      ctx.fillStyle = x.options.ticks.color;
      ctx.font = `${x.options.ticks.font.weight} ${x.options.ticks.font.size}px ${x.options.ticks.font.family}`;
      ctx.fillText(label, xPosition, yPosition + 5); // 텍스트 위치도 조정
      ctx.restore();
    });
  }
};

new Chart($totalCards, {
  type: 'bar',
  data: {
    labels: totalCardsData.map(data => data.SELLING_YEAR),
    datasets: [
      {
        label: '',  // 빈 레이블 추가
        backgroundColor: "#C3FCF2",
        data: totalCardsData.map(data => data.TOTAL_CARDS_SOLD)
      }
    ]
  },
  options: {
    plugins: {
      legend: {
        display: false
      },
      title: {
        display: false
      },
      datalabels: {
        display: false
      },
      chartAreaPlugin: {
        backgroundColor: "#d9d9d9"
      },
      xAxisLabelPlugin: {
        backgroundColor: '#fff', // x축 레이블 배경색
        width: 70 // x축 레이블 너비
      }
    },
    scales: {
      x: {
        ticks: {
          callback: function(value, index, ticks) {
            return value;
          },
          color: "#000",
          font: {
            size: 16,
            weight: 700
          },
          autoSkip: false,
          maxRotation: 0,
          minRotation: 0,
        }
      },
      y: {
        min: 0,
        max: 500, // 그래프 확인용 100
        ticks: {
          color: "#ffffff",
          font: {
            size: 16,
            weight: 700
          },
          stepSize: 100, // 그래프 확인용 25
          callback: function(value) {
            return value.toLocaleString();
          }
        }
      }
    }
  },
  plugins: [chartAreaPlugin, xAxisLabelPlugin]
});

new Chart($recommendedCards, {
  type: 'bar',
  data: {
    labels: recoCardsData.map(data => data.SELLING_YEAR),
    datasets: [
      {
        label: '',  // 빈 레이블 추가
        backgroundColor: "#C3FCF2",
        data: recoCardsData.map(data => data.TOTAL_CARDS_SOLD)
      }
    ]
  },
  options: {
    plugins: {
      legend: {
        display: false
      },
      title: {
        display: false
      },
      datalabels: {
        display: false
      },
      chartAreaPlugin: {
        backgroundColor: "#d9d9d9"
      },
      xAxisLabelPlugin: {
        backgroundColor: '#fff', // x축 레이블 배경색
        width: 70 // x축 레이블 너비
      }
    },
    scales: {
      x: {
        ticks: {
          callback: function(value, index, ticks) {
            return value;
          },
          color: "#000",
          font: {
            size: 16,
            weight: 700
          },
          autoSkip: false,
          maxRotation: 0,
          minRotation: 0,
        }
      },
      y: {
        min: 0,
        max: 500,
        ticks: {
          color: "#ffffff",
          font: {
            size: 16,
            weight: 700
          },
          stepSize: 100,
          callback: function(value) {
            return value.toLocaleString();
          }
        }
      }
    }
  },
  plugins: [chartAreaPlugin, xAxisLabelPlugin]
});

new Chart($terminatedCards, {
  type: 'bar',
  data: {
    labels: termCardsData.map(data => data.SELLING_YEAR),
    datasets: [
      {
        label: '',  // 빈 레이블 추가
        backgroundColor: "#C3FCF2",
        data: termCardsData.map(data => data.TOTAL_CARDS_SOLD)
      }
    ]
  },
  options: {
    plugins: {
      legend: {
        display: false
      },
      title: {
        display: false
      },
      datalabels: {
        display: false
      },
      chartAreaPlugin: {
        backgroundColor: "#d9d9d9"
      },
      xAxisLabelPlugin: {
        backgroundColor: '#fff', // x축 레이블 배경색
        width: 70 // x축 레이블 너비
      }
    },
    scales: {
      x: {
        ticks: {
          callback: function(value, index, ticks) {
            return value;
          },
          color: "#000",
          font: {
            size: 16,
            weight: 700
          },
          autoSkip: false,
          maxRotation: 0,
          minRotation: 0,
        }
      },
      y: {
        min: 0,
        max: 500,
        ticks: {
          color: "#ffffff",
          font: {
            size: 16,
            weight: 700
          },
          stepSize: 100,
          callback: function(value) {
            return value.toLocaleString();
          }
        }
      }
    }
  },
  plugins: [chartAreaPlugin, xAxisLabelPlugin]
});
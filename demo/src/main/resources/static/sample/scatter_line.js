option = {
    dataRange: {
        min: 0,
        max: 100,
        orient: 'horizontal',
        y: 'top',
        //text:['高','低'],           // 文本，默认为数值文本
        color: ['lightgreen', 'yellow'],
        splitNumber: 5
    },
    xAxis: [
        {
            type: 'category',
            boundaryGap: false,
            data: function () {
                var list = [];
                for (var i = 1; i <= 30; i++) {
                    list.push('2013-03-' + i);
                }
                return list;
            }()
        },
        {
            type: 'value',
            scale: true,
            splitNumber: 29,
            axisLabel: {show: false},
            splitLine: {show: false}
        }
    ],
    yAxis: [
        {
            type: 'value'
        },
        {
            type: 'value'
        }
    ],
    animation: false,
    series: [
        {
            name: '散点',
            type: 'scatter',
            yAxisIndex: 1,
            xAxisIndex: 1,
            symbol: 'circle',
            symbolSize: function (value) {
                return Math.round(value[2] / 10);
            },
            data: (function () {
                var d = [];
                var len = 200;
                var value;
                while (len--) {
                    d.push([
                        Math.round(Math.random() * 29) + 1,
                        (Math.random() * 30).toFixed(2) - 0,
                        (Math.random() * 100).toFixed(2) - 0
                    ]);
                }
                return d;
            })()
        },
        {
            name: '折线',
            type: 'line',
            data: function () {
                var list = [];
                for (var i = 1; i <= 30; i++) {
                    list.push(Math.round(Math.random() * 30));
                }
                return list;
            }()
        }
    ]
};

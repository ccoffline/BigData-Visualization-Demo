option = {

    // 标题
    title: {
        text: '未来一周气温变化',
        subtext: '纯属虚构'
    },

    // 图例
    legend: {
        data: ['最高气温', '最低气温']
    },

    // XY轴
    xAxis: [
        {
            type: 'category',
            boundaryGap: false,
            data: ['周一', '周二', '周三', '周四', '周五', '周六', '周日']
        }
    ],
    yAxis: [
        {
            type: 'value',
            axisLabel: {
                formatter: '{value} °C'
            }
        }
    ],

    // 数据
    series: [
        {
            name: '最高气温',
            type: 'line',
            data: [11, 11, 15, 13, 12, 13, 10]
        },
        {
            name: '最低气温',
            type: 'line',
            data: [1, -2, 2, 5, 3, 2, 0]
        }
    ]
};

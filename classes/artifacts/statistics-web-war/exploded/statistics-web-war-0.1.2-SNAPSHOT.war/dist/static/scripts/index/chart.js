define('static/scripts/index/chart', [], function(require, exports, module) {

	var Chart = {
		options:null,
		ele:null,
		handle: null,
		myChart:null,
		draw: function(options) {
			this.options = options.options;
			this.ele = options.ele ? document.getElementById(options.ele) : '';
			this.handle = options.handle;
			if (!this.ele) {
				return;
			}
			this.createChart();
		},
		initOptions: function() {
			return {
				title: {
					show: true,
					text: this.options.title || '',
					textStyle: {
						color: '#fff'
					}
				},
				tooltip: {
					trigger: 'axis',
					backgroundColor: 'rgba(255,255,255,0.5)'
				},
				legend: {
					show: true,
					x: 'center',
					y: 'bottom',
					data: this.options.legendData || [],
					textStyle: {
						color:'#fff'
					}
				},
				toolbox: {
					show: false
				},
				xAxis: [{
					type: this.options.type,
					splitNumber:10,
					boundaryGap: true,
					data: this.options.xAxisData || [],
					axisLabel: {
						textStyle: {
							color: '#fff'
						}
					}
				}],
				yAxis: [{
					type: 'value',
					axisLabel: {
						formatter: '{value}',
						textStyle: {
							color: '#fff'
						}
					}
				}],
				series: this.options.seriesData || [],
				grid: this.options.grid || {
					x: 80,
					y: 30,
					x2: 30,
					y2: 50
				}
			}
		},
		createChart: function() {
			var that = this;
			that.myChart = echarts.init(that.ele, 'macarons');
			window.onresize = that.myChart.resize;
			var setting = that.initOptions();
			if (that.options.legendData.length <= 0) {
				setting.legend.show = false;
			}

			if (!that.options.title) {
				setting.title.show = false;
			}
			that.myChart.setOption(setting);
			that.myChart.on('click', function(param) {
				if (typeof that.handle === 'function') {
					that.handle(param);
				}
			});
		},
		refresh: function() {
			this.myChart = echarts.init(this.ele, 'macarons');
			var setting = this.initOptions();
			if (this.options.legendData.length <= 0) {
				setting.legend.show = false;
			}

			if (!this.options.title) {
				setting.title.show = false;
			}
			this.myChart.setOption(setting);
		}
	}
	module.exports = Chart;
});
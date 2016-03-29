define('static/scripts/index/chart/chartController', ['static/scripts/index/chart', 'static/scripts/index/chart/chartDatatableController'], function(require, exports, module) {
	module.exports = (function() {
		var Chart = require('static/scripts/index/chart');
		
		var render = function(gridId) {
			return '<section class="tile transparent grid-1">'
						+ '<div class="tile-body transparent rounded-corners">'
							+ '<div class="table-responsive" id="' + gridId + '">'
							+ '</div>'
						+ '</div>'
					+ '</section>';
		};
		
		var lineChart = new Chart({
			ele: 'myChart',
			handle: function(param) {
				var gridModule = require('static/scripts/index/chart/chartDatatableController');
				var gridId = (+new Date()) + '_grid';
				if ($('section.grid-1')) {
					$('section.grid-1').remove();
				}
				$('#page_0').append(render(gridId));
				gridModule.render(param, gridId);
			},
			options: {
				title: '',
				legendData: ['直接访问', '间接访问'],
				xAxisData: ['周一', '周二', '周三', '周四', '周五', '周六', '周日'],
				seriesData: [{
					name: '直接访问',
					type: 'line',
					barMaxWidth: 30,
					data: [320, 332, 301, 334, 390, 330, 320]
				}, {
					name: '间接访问',
					type: 'line',
					barMaxWidth: 30,
					data: [20, 32, 1, 94, 90, 30, 20]
				}]
			}
		});

		return {
			lineChart: lineChart,
			init: function() {
				lineChart.draw();
			}
		};
	})();
});
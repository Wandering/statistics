define('static/scripts/index/chart/chartDatatableController', ['static/scripts/index/datatable'], function(require, exports, module) {
	module.exports = (function() {
		var Table = require('static/scripts/index/datatable');
		var col = [{
			data: 'id'
		}, {
			data: 'systemName',
			title: '平台名称'
		}, {
			data: 'systemUrl',
			title: '平台地址'
		}, {
			data: 'loginNumber',
			title: '管理员'
		}, {
			data: 'userName',
			title: '真实姓名'
		}];

		var columnDefs = [{
			"bVisible": false,
			"aTargets": [0]
		}];

		var model = function(param) {
			var data = [];
			for (var i = 0; i < 3; i++) {
				var curIndex = i + 1;
				data.push({
					id: curIndex,
					systemName: curIndex + '_' + param.value + '_n',
					systemUrl: curIndex + '_' + param.value + '_u',
					loginNumber: curIndex + '_' + param.value + '_N',
					userName: curIndex + '_' + param.value + '_un'
				});
			}
			return data;
		};

		var render = function(gridId, index) {
			return '<section class="tile transparent grid-"' + index + '>' + '<div class="tile-body transparent rounded-corners">' + '<div class="table-responsive" id="' + gridId + '">' + '</div>' + '</div>' + '</section>';
		};
		var n = 1;

		return {
			render: function(param, gridId) {
				function secondTable(gridId) {
					consol.log(gridId)
					return Table({
						data: model(param),
						columns: col,
						tableContentId: gridId,
						tableId: 'table_body_' + (+new Date()),
						columnDefs: columnDefs,
						trClickHandle: function(data) {
							//console.log(data);
							n++;
							var gridIdSecond = (+new Date()) + '_grid';
							if ($('section.grid-' + n)) {
								$('section.grid-' + n).remove();
							}
							$('#page_0').append(render(gridIdSecond, n));
						}
					});
				};
				var firstTable = Table({
					data: model(param),
					columns: col,
					tableContentId: gridId,
					tableId: 'table_body_' + (+new Date()),
					columnDefs: columnDefs,
					trClickHandle: function(data) {
						//console.log(data);
						n++;
						var gridIdSecond = (+new Date()) + '_grid';
						if ($('section.grid-' + n)) {
							$('section.grid-' + n).remove();
						}
						$('#page_0').append(render(gridIdSecond, n));
						secondTable().init();
					}
				});
				firstTable.init();
			}
		}
	})();
});
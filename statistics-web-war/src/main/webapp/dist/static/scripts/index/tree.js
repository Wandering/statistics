define('static/scripts/index/tree', ['sea-modules/jquery/ztree/jquery.ztree.core.3.5.min', 'sea-modules/jquery/ztree/jquery.ztree.excheck-3.5.min'], function(require, exports, module) {

	var Tree = (function() {
		var setOptions = function() {
			return {
				view: {
					selectedMulti: false
				},
				check: {
					enable: false,
					chkboxType: {
						"Y": "ps",
						"N": "s"
					}
				},
				async: {
					enable: true,
					url: '',
					autoParam: ["id", "name=n", "level=lv"],
					otherParam: {
						"otherParam": "zTreeAsyncTest"
					}
				},
				callback: {
					beforeClick: function(treeId, parentNode, childNodes) {},
					onAsyncSuccess: function(treeId, treeNode) {},
					beforeExpand: function(treeId, treeNode) {},
					onExpand: function(event, treeId, treeNode){}
				}
			}
		};
		return {
			treeObj: null,
			curExpandNode: null,
			beforeExpandPrivate: null,
			onExpand: function(event, treeId, treeNode) {
				Tree.curExpandNode = treeNode;
			},
			beforeExpand: function(treeId, treeNode) {
				var pNode = Tree.curExpandNode ? Tree.curExpandNode.getParentNode() : null;
				var treeNodeP = treeNode.parentTId ? treeNode.getParentNode() : null;
				var zTree = Tree.treeObj;
				for (var i = 0, l = !treeNodeP ? 0 : treeNodeP.children.length; i < l; i++) {
					if (treeNode !== treeNodeP.children[i]) {
						zTree.expandNode(treeNodeP.children[i], false);
					}
				}
				while (pNode) {
					if (pNode === treeNode) {
						break;
					}
					pNode = pNode.getParentNode();
				}
				if (!pNode) {
					Tree.singlePath(treeNode);
				}
				if (typeof Tree.beforeExpandPrivate === 'function') {
					Tree.beforeExpandPrivate(treeId, treeNode);
				}
			},
			singlePath: function(newNode) {
				if (newNode === this.curExpandNode) return;

				var zTree = this.treeObj,
					rootNodes, tmpRoot, tmpTId, i, j, n;

				if (!this.curExpandNode) {
					tmpRoot = newNode;
					while (tmpRoot) {
						tmpTId = tmpRoot.tId;
						tmpRoot = tmpRoot.getParentNode();
					}
					rootNodes = zTree.getNodes();
					for (i = 0, j = rootNodes.length; i < j; i++) {
						n = rootNodes[i];
						if (n.tId != tmpTId) {
							zTree.expandNode(n, false);
						}
					}
				} else if (this.curExpandNode && this.curExpandNode.open) {
					if (newNode.parentTId === this.curExpandNode.parentTId) {
						zTree.expandNode(this.curExpandNode, false);
					} else {
						var newParents = [];
						while (newNode) {
							newNode = newNode.getParentNode();
							if (newNode === this.curExpandNode) {
								newParents = null;
								break;
							} else if (newNode) {
								newParents.push(newNode);
							}
						}
						if (newParents != null) {
							var oldNode = this.curExpandNode;
							var oldParents = [];
							while (oldNode) {
								oldNode = oldNode.getParentNode();
								if (oldNode) {
									oldParents.push(oldNode);
								}
							}
							if (newParents.length > 0) {
								zTree.expandNode(oldParents[Math.abs(oldParents.length - newParents.length) - 1], false);
							} else {
								zTree.expandNode(oldParents[oldParents.length - 1], false);
							}
						}
					}
				}
				this.curExpandNode = newNode;
			},
			init: function(options) {
				require('sea-modules/jquery/ztree/jquery.ztree.core.3.5.min');
				require('sea-modules/jquery/ztree/jquery.ztree.excheck-3.5.min');
				var setting = setOptions();
				if (options.url) {
					setting.async.url = options.url;
				} else {
					setting.async.enable = false;
				}
				this.beforeExpandPrivate = options.beforeExpand;
				setting.check.enable = options.check || false;
				if (options.beforeClick) {
					setting.callback.beforeClick = options.beforeClick;
				}

				if (options.onAsyncSuccess) {
					setting.callback.onAsyncSuccess = options.onAsyncSuccess;
				}

				if (options.expand) {
					setting.callback.beforeExpand = this.beforeExpand;
					setting.callback.onExpand = this.onExpand;
				}

				if (!options.url && options.nodes && options.nodes.length > 0) {
					this.treeObj = $.fn.zTree.init($("#" + options.id), setting, options.nodes);
					return;
				};
				this.treeObj = $.fn.zTree.init($("#" + options.id), setting);
			},
			getCheckedNodes: function() {
				var nodes = this.treeObj.getCheckedNodes(true);
				return nodes;
				var checkedNodes = [];
				for (var i = 0, len = nodes.length; i < len; i++) {
					checkedNodes.push({
						id: nodes[i].id,
						pId: nodes[i].pId,
						isParent: nodes[i].isParent
					});
				}
				return this.structureCheckedTree(checkedNodes);
			},
			structureCheckedTree: function(nodes) {
				for (var i = 0; i < nodes.length; i++) {
					var id = nodes[i].id;
					if (nodes[i].isParent) {
						for (var j = 0; j < nodes.length; j++) {
							if (id == nodes[j].pId) {
								nodes[i].children = nodes[i].children || [];
								nodes[i].children.push(nodes[j]);
							}
						}
					}
				}

				var checkedTree = [];
				for (var n = 0; n < nodes.length; n++) {
					if (!nodes[n].pId) {
						checkedTree.push(nodes[n]);
					}
				}
				return checkedTree;
			}
		}
	})();

	module.exports = Tree;
})
/**
 * Created by kepeng on 15/10/12.
 */
define('static/scripts/index/overallSituation/treeSituation', ['sea-modules/jquery/cookie/jquery.cookie'], function(require, exports, module) {
    /**
     *
     * @param succ  有数据处理方式
     * @param err  没有数据和错误处理方式
     */
    module.exports = function(options) {

        require('sea-modules/jquery/cookie/jquery.cookie');
        var token = $.cookie('bizData');

        var Tree = null;
        /**
         * 通过选中树节点的ID获取每个节点下的数据表格
         * @param selectedId
         */
        var getGridByTree = function(selectedId) {
            require.async(['static/scripts/index/overallSituation/listSituation'], function(module) {
                module(selectedId);
            });
        };

        /**
         * 获取所有菜单
         */
        $.get('/statistics/op/getClassfyInfo.shtml?token=' + token, function(data) {
            if ('0000000' === data.rtnCode) {
                var rootNodes = data.bizData;
                if (rootNodes.length <= 0) {
                    options.err(data);
                    return;
                }

                var theFirstChildren = [];
                for (var i = 0; i < rootNodes.length; i++) {
                    theFirstChildren.push({
                        id: rootNodes[i].id,
                        name: rootNodes[i].name
                    });
                }

                options.succ();
                require.async(['static/scripts/index/tree'], function(Tree) {

                    Tree.init({
                        id: 'tree_menu',
                        url: '',
                        beforeClick: function(treeId, parentNode, childNodes) {
                            if (parentNode.id > 0) {
                                getGridByTree(parentNode.id);
                            }
                        },
                        onAsyncSuccess: function(treeId, treeNode) {

                        },
                        nodes: theFirstChildren
                    });
                    var node = theFirstChildren[0];
                    var selectNode = Tree.treeObj.getNodeByParam('id', node.id, null);
                    Tree.treeObj.selectNode(selectNode); //默认选中第一个节点
                    Tree.treeObj.expandNode(selectNode, true, false, true); //默认打开第一个节点
                    getGridByTree(node.id);
                });
            } else {
                options.err(data);
            }
        })
    };
});
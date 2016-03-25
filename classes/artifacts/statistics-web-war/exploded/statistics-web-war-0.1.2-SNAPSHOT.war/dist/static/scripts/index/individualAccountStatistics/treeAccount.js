/**
 * Created by kepeng on 15/10/12.
 */
define('static/scripts/index/individualAccountStatistics/treeAccount', ['sea-modules/jquery/cookie/jquery.cookie'], function(require, exports, module) {
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
            require.async(['static/scripts/index/individualAccountStatistics/listAccount'], function(module) {
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
                options.succ();
                require.async(['static/scripts/index/tree'], function(Tree) {
                    var selectNodeId = -1
                    var getLastNode = function(node) {
                        if (node.children && node.children.length > 0) {
                            getLastNode(node.children[0]);
                        } else {
                            selectNodeId = node.id;
                        }
                    }

                    Tree.init({
                        id: 'tree_menu',
                        url: '',
                        beforeClick: function(treeId, parentNode, childNodes) {
                            if (parentNode.children && parentNode.children.length > 0) {
                                getLastNode(parentNode);
                                if (selectNodeId < 0) {
                                    return;
                                }
                                var selectNode = Tree.treeObj.getNodeByParam('id', selectNodeId, null);
                                Tree.treeObj.expandNode(selectNode, true, false, true);
                                setTimeout(function() {
                                    Tree.treeObj.cancelSelectedNode(parentNode);
                                    Tree.treeObj.selectNode(selectNode);
                                }, 100);
                                getGridByTree(selectNodeId);
                            } else {
                                getGridByTree(parentNode.id);
                            }
                        },
                        onAsyncSuccess: function(treeId, treeNode) {},
                        expand: true,
                        beforeExpand: function(treeId, parentNode) {
                            if (parentNode.children && parentNode.children.length > 0) {
                                getLastNode(parentNode);
                                if (selectNodeId < 0) {
                                    return;
                                }
                                var selectNode = Tree.treeObj.getNodeByParam('id', selectNodeId, null);
                                setTimeout(function() {
                                    Tree.treeObj.cancelSelectedNode(parentNode);
                                    Tree.treeObj.selectNode(selectNode);
                                }, 100);
                                getGridByTree(selectNodeId);
                            } else {
                                getGridByTree(parentNode.id);
                            }
                        },
                        nodes:rootNodes
                    });
                    var node = rootNodes[0];
                    getLastNode(node);
                    if (selectNodeId < 0) {
                       return;
                    }
                    var selectNode = Tree.treeObj.getNodeByParam('id', selectNodeId, null);
                    Tree.treeObj.selectNode(selectNode);//默认选中第一个节点
                    Tree.treeObj.expandNode(selectNode, true, false, true);//默认打开第一个节点
                    getGridByTree(selectNodeId);
                });
            } else {
                options.err(data);
            }
        })
    };
});

/**
 * Created by kepeng on 15/9/9.
 */
define('static/scripts/index/authority/company', ['sea-modules/jquery/cookie/jquery.cookie', 'static/scripts/index/tree', 'static/scripts/index/authority/company_list'], function(require, exports, module) {
    /**
     *
     * @param succ  有数据处理方式
     * @param err  没有数据和错误处理方式
     */
    module.exports = function(options) {
        require('sea-modules/jquery/cookie/jquery.cookie');
        var token = $.cookie('bizData');
        /**
         * 获取所有产品
         */
        $.get('/statistics/product/queryTreeProduct?token=' + token, function(data) {

            if ('0000000' === data.rtnCode) {
                var nodes = data.bizData;
                if (nodes.length <= 0) {
                    options.err(data);
                    return;
                }
                options.succ();
                var Tree = require('static/scripts/index/tree');
                Tree.init({
                    id: 'tree_menu',
                    url: '',
                    beforeClick: function(treeId, parentNode, childNodes) {
                        var module = require('static/scripts/index/authority/company_list');
                        module(parentNode.id);
                    },
                    onAsyncSuccess: function(treeId, treeNode) {

                    },
                    nodes:nodes
                });

                var node = nodes[0];
                var selectNode = Tree.treeObj.getNodeByParam('id', node.id, null);
                Tree.treeObj.selectNode(selectNode);//默认选中第一个节点
                var module = require('static/scripts/index/authority/company_list');
                module(node.id);
            } else {
                options.err(data);
            }
        });
    };
});

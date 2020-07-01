layui.link(`${$WEB_ROOT_PATH}/plugins/layui_ext/dtree/dtree.css`)
layui.link(`${$WEB_ROOT_PATH}/plugins/layui_ext/dtree/font/dtreefont.css`)
layui.extend({
    dtree: `{/}${$WEB_ROOT_PATH}/plugins/layui_ext/dtree/dtree`
}).use(['dtree'], function () {
    let $ = layui.$,
        dtree = layui.dtree;

    let moduleTree = dtree.render({
        elem: '#module-tree',
        ficon: '-1',
        url: $WEB_ROOT_PATH + '/module-api/module-tree',
        method: 'GET',
        dataFormat: 'list',
        line: true,
        menubar: true,
        toolbar: true,
        toolbarStyle: {
            title: "模块",
            area: ["50%", "400px"]
        },
        scroll: '#module-tree-cover',
        menubarTips: {
            group: [{
                menubarId: 'search-module-btn',
                icon: 'dtree-icon-search2',
                title: '查询',
                handler: function (node, $div) {
                    searchModuleNode();
                }
            }, "moveDown", "moveUp", "refresh"]
        },
    });

    // 监听 search-input enter
    $('input#search-input').keyup(function (e) {
        if (e.key === 'Enter') {
            searchModuleNode();
        }
    });

    function searchModuleNode() {
        let value = $("#search-input").val();
        if (value) {
            let flag = moduleTree.searchNode(value); // 内置方法查找节点
            if (!flag) {
                layer.msg("该名称节点不存在！", {icon: 5});
            }
        } else {
            moduleTree.menubarMethod().refreshTree(); // 内置方法刷新树
        }
    }
});
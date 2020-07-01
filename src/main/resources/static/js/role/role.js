layui.link(`${$WEB_ROOT_PATH}/plugins/layui_ext/dtree/dtree.css`)
layui.link(`${$WEB_ROOT_PATH}/plugins/layui_ext/dtree/font/dtreefont.css`)
layui.extend({
    dataTable: `{/}${$WEB_ROOT_PATH}/plugins/extend-module/datatable/dataTable`,
    dtree: `{/}${$WEB_ROOT_PATH}/plugins/layui_ext/dtree/dtree`
}).use(['table', 'dataTable', 'dtree'], function () {
    let $ = layui.$,
        dataTable = layui.dataTable,
        table = layui.table,
        dtree = layui.dtree;

    (() => {
        // div#module-tree 高度自适应
        let winHeight = $(window).height();
        $('#module-tree-cover').height(winHeight - 22);
        $('#module-tree').height(winHeight - 47 - 40);
    })();

    let cols = [[
        {type: 'checkbox', fixed: 'left'},
        {field: 'roleId', hide: true},
        {field: 'roleName', title: '角色名称', sort: true, minWidth: 140},
        {field: 'roleDesc', title: '角色描述', minWidth: 200},
        {field: 'priority', title: '优先级', sort: true},
        {field: 'status', title: '状态代码', hide: true},
        {field: 'statusName', title: '状态'},
        {field: 'createDate', title: '创建日期', align: 'center', sort: true, width: 170},
        {field: 'creator', title: '创建人', width: 80},
        {field: 'action', title: '操作', toolbar: '#op-col', align: 'center', width: 190, fixed: 'right'}
    ]];

    // 弹出层传参
    class DataCarrier {
        constructor(roleId, roleName, roleDesc, priority, status, event) {
            this.roleId = roleId; // 主键
            this.roleName = roleName;
            this.roleDesc = roleDesc;
            this.priority = priority;
            this.status = status;
            this.event = event; // 操作事件
            this.isModified = false; // 数据是否修改
        }
    }

    let dataCarrier = {
        empty() {
            layui.dataCarrier = new DataCarrier();

            return dataCarrier;
        },
        set(data, event) { // 需要传递的行数据
            layui.dataCarrier = new DataCarrier(data.roleId, data.roleName, data.roleDesc, data.priority, data.status, event);

            return dataCarrier;
        },
        setEvent(event) {
            layui.dataCarrier.event = event;

            return dataCarrier;
        }
    };

    let url = $WEB_ROOT_PATH + '/role-api/roles';
    let popupUrl = $WEB_ROOT_PATH + '/role/modify';
    let popupWidth = 600, popupHeight = 440;
    // 页面传参对象
    let param = {
        title: '角色',
        elem: '#dt-cover', // 数据表格包裹层
        url: url, // 数据表格数据URL
        cols: cols, // 数据表格列
        withTable: true, // 是否根据数据表格列生成条件过滤器关键字
        dataCarrier: dataCarrier,
        status: status,
        popup: {
            url: popupUrl,
            width: popupWidth,
            height: popupHeight
        }
    };
    dataTable.init(param);

    // module tree
    let treeUrl = $WEB_ROOT_PATH + '/module-api/module-tree';
    let moduleTree = dtree.render({
        elem: '#module-tree',
        ficon: '-1',
        url: treeUrl,
        method: 'GET',
        dataFormat: 'list',
        line: true,
        checkbar: true,
        menubar:true,
        menubarTips:{
            group:["moveDown", "moveUp", "refresh", "searchNode"]
        },
        done: function (res, $ul, first) {
            if (first) {
                dtree.initAllCheck("module-tree"); // 半选初始化
            }
        }
    });

    // 监听行工具事件
    table.on('tool(data-table)', function (obj) {
        if (obj.event === 'grant') {
            grant(obj);

            return false;
        }

        dataTable.openPopup(popupUrl, popupWidth, popupHeight, obj.event, obj.data);
    });

    // 功能授权主逻辑
    // 为了后面还原按钮使用，把 roleId 和 roleName 放在外面
    let roleIdNow = -1, roleNameNow = '';
    let $saveBtn = $('#module-tree-cover #save');
    let $restoreBtn = $('#module-tree-cover #restore');

    function grant(obj) { // 角色授权
        roleIdNow = obj.data.roleId;
        roleNameNow = obj.data.roleName;

        moduleTreeReload(roleIdNow, roleNameNow);

        $saveBtn.removeClass('layui-btn-disabled');
        $saveBtn.removeAttr('disabled');
        $restoreBtn.removeClass('layui-btn-disabled');
        $restoreBtn.removeAttr('disabled');
    }

    // 复选框点击
    dtree.on('chooseDone("module-tree")', function (obj) {
        let flag = dtree.changeCheckbarNodes('module-tree');
        console.log(flag);
        if (flag) {
            $('#module-tree-cover button').forEach((e) => {
                e.removeClass('layui-btn-disabled');
            });
        }

    });

    // 保存
    $saveBtn.on('click', function (e) {
        let checkbarNodes = moduleTree.getCheckbarNodesParam();
        let data = JSON.stringify(checkbarNodes);
        let url = $WEB_ROOT_PATH + '/role-api/module-grant';
        $.ajax({
            type: 'post',
            url: url,
            async: true,
            dataType: 'json',
            contentType: 'application/json',
            data: data,
            success: function(response, status, xhr) {
                // 剧中显示
                // 用于计算弹出层坐标位置
                let windowW = $(window).width();
                let sidebarWidth = $MENU_WH.width;
                let msgWidth = 160;
                let msgOffsetX = math.evaluate(`${(windowW - msgWidth) / 2 - (sidebarWidth / 2)}`);
                layer.msg('授权完成！', {
                    time: 2000,
                    area: `${msgWidth}px`,
                    offset: ['t', `${msgOffsetX}px`]
                }, function () {
                    dataTable.reload();
                });
            },
            error: function (response, status, xhr) {
                layer.msg('出现系统错误，请联系管理员处理！');
            }
        });

        e.stopPropagation();
    });

    // 还原
    $restoreBtn.on('click', function (e) {
        moduleTreeReload(roleIdNow, roleNameNow);

        e.stopPropagation();
    })

    // 根据角色获取 module tree
    function moduleTreeReload(roleId, roleName) {
        moduleTree.reload({
            request: {
                'roleId': roleId,
                'roleName': roleName,
            }
        });
    }
});
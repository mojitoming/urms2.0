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
        $('#role-tree-cover').height(winHeight - 22);
        $('#role-tree').height(winHeight - 47 - 40);
    })();

    let cols = [[
        {type: 'checkbox', fixed: 'left'},
        {field: 'userId', hide: true},
        {field: 'username', title: '用户名', sort: true, minWidth: 140},
        {field: 'nickname', title: '昵称', minWidth: 160},
        {field: 'warrantStartDate', title: '有效期起始', sort: true, minWidth: 140},
        {field: 'warrantEndDate', title: '有效期截止', sort: true, minWidth: 140},
        {field: 'status', title: '状态代码', hide: true},
        {field: 'statusName', title: '状态'},
        {field: 'createDate', title: '创建日期', align: 'center', sort: true, width: 170},
        {field: 'creator', title: '创建人', width: 80},
        {field: 'action', title: '操作', toolbar: '#op-col', align: 'center', width: 190, fixed: 'right'}
    ]];

    // 弹出层传参
    class DataCarrier {
        constructor(userId, username, nickname, warrantStartDate, warrantEndDate, status, event) {
            this.userId = userId; // 主键
            this.username = username;
            this.nickname = nickname;
            this.warrantStartDate = warrantStartDate;
            this.warrantEndDate = warrantEndDate;
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
            layui.dataCarrier = new DataCarrier(data.userId, data.username, data.nickname, data.warrantStartDate, data.warrantEndDate, data.status, event);

            return dataCarrier;
        },
        setEvent(event) {
            layui.dataCarrier.event = event;

            return dataCarrier;
        }
    };

    let url = $WEB_ROOT_PATH + '/user-api/users';
    let popupUrl = $WEB_ROOT_PATH + '/user/modify';
    let popupWidth = 620, popupHeight = 440;
    // 页面传参对象
    let param = {
        title: '用户',
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

    // role tree
    let treeUrl = $WEB_ROOT_PATH + '/role-api/role-tree';
    let roleTree = dtree.render({
        elem: '#role-tree',
        ficon: '-1',
        url: treeUrl,
        method: 'GET',
        dataFormat: 'list',
        line: true,
        checkbar: true,
        checkbarType: "no-all",
        menubar: true,
        menubarTips: {
            group: ["moveDown", "moveUp", "refresh", "searchNode"]
        },
        done: function (res, $ul, first) {
            if (first) {
                dtree.initNoAllCheck("role-tree"); // 半选初始化
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
    let userIdNow = -1, nicknameNow = '';
    let $saveBtn = $('#role-tree-cover #save');
    let $restoreBtn = $('#role-tree-cover #restore');

    function grant(obj) { // 角色授权
        userIdNow = obj.data.userId;
        nicknameNow = obj.data.nickname;

        roleTreeReload(userIdNow, nicknameNow);

        $saveBtn.removeClass('layui-btn-disabled');
        $saveBtn.removeAttr('disabled');
        $restoreBtn.removeClass('layui-btn-disabled');
        $restoreBtn.removeAttr('disabled');
    }

    // 复选框点击
    dtree.on('chooseDone("role-tree")', function (obj) {
        let flag = dtree.changeCheckbarNodes('role-tree');
        console.log(flag);
        if (flag) {
            $('#role-tree-cover button').forEach((e) => {
                e.removeClass('layui-btn-disabled');
            });
        }

    });

    // 保存
    $saveBtn.on('click', function (e) {
        let checkbarNodes = roleTree.getCheckbarNodesParam();
        let data = JSON.stringify(checkbarNodes);
        let url = $WEB_ROOT_PATH + '/user-api/user-grant';
        $.ajax({
            type: 'post',
            url: url,
            async: true,
            dataType: 'json',
            contentType: 'application/json',
            data: data,
            success: function (response, status, xhr) {
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
        roleTreeReload(userIdNow, nicknameNow);

        e.stopPropagation();
    })

    // 根据角色获取 module tree
    function roleTreeReload(userId, nickname) {
        roleTree.reload({
            url: treeUrl,
            request: {
                'userId': userId,
                'nickname': nickname,
            }
        });
    }
});
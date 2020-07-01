layui.extend({
    dataTable: `{/}${$WEB_ROOT_PATH}/plugins/extend-module/datatable/dataTable`,
}).use(['dataTable'], function () {
    let dataTable = layui.dataTable;

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
        elem: '#datatable-cover', // 数据表格包裹层
        url: url, // 数据表格数据URL
        cols: cols, // 数据表格列
        withTable: true, // 是否根据数据表格列生成条件过滤器关键字
        dataCarrier: dataCarrier,
        status: status,
        popup: {
            url: popupUrl,
            width: popupWidth,
            height: popupHeight
        },
        rightTree: {
            url: $WEB_ROOT_PATH + '/role-api/role-tree',
            saveUrl: $WEB_ROOT_PATH + '/user-api/user-grant',
            checkbarType: 'no-all'
        }
    };
    dataTable.init(param);
});
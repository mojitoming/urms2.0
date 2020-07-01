layui.extend({
    dataTable: `{/}${$WEB_ROOT_PATH}/plugins/extend-module/datatable/dataTable`,
}).use(['dataTable'], function () {
    let dataTable = layui.dataTable;

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
            url: $WEB_ROOT_PATH + '/module-api/module-tree',
            saveUrl: $WEB_ROOT_PATH + '/role-api/module-grant',
            checkbarType: 'all',
        }
    };
    dataTable.init(param);
});
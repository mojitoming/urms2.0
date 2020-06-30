layui.link(`${$WEB_ROOT_PATH}/plugins/extend-module/datatable/dataTable.css`);
layui.extend({
    filter: `{/}${$WEB_ROOT_PATH}/plugins/extend-module/filter/filter`,
    tablePlug: `{/}${$WEB_ROOT_PATH}/plugins/layui_ext/tablePlug/tablePlug`,
}).define(['table', 'filter', 'tablePlug'], function (exports) {
    let table = layui.table,
        filter = layui.filter,
        $ = layui.$,
        tablePlug = layui.tablePlug;
    let isInit = true; // 判断是否第一次 render table

    tablePlug.smartReload.enable(true);

    let iframeId = $('iframe', parent.document).attr('id');
    let dataTable;
    let keywords, withTable;
    let conditions = {};
    let readonly = false;
    let dtTitle, dataCarrier, status;
    // 用于计算弹出层坐标位置
    let windowW = $(window).width();
    let sidebarWidth = $MENU_WH.width;
    let obj = {
        init(param) {
            dtTitle = '' || param.title;
            let elem = param.elem || '#dt-cover';
            let url = param.url;
            let cols = param.cols;
            let where = param.where || {};
            keywords = param.keywords;
            withTable = param.withTable && true;
            dataCarrier = param.dataCarrier || {};
            status = param.status;
            readonly = param.readonly || false;
            let popup = param.popup || {url: '', width: 0, height: 0};
            if (!elem) {
                console.log("Table selector is inevitable!")
                return false;
            }
            if (!url) {
                console.log("Request data address is inevitable!")
                return false;
            }
            if (!Array.isArray(cols)) {
                console.log("Custom columns should be put into the array!");
                return false;
            }

            this.construct(elem, url, cols, where, popup);

            return dataTable;
        },
        construct(elem, url, cols, where, popup) {
            let _this = this;
            let mainDom = `
                       <table id="data-table" class="layui-hide" lay-filter="data-table"></table>
           
                       <script type="text/html" id="toolbar-filter">
                            <div id="filter-container"></div>
                            
                            <button id="add-item" class="layui-btn layui-btn-normal layui-btn-sm" lay-event="add-item">新建</button>
                            <button id="del-items" class="layui-btn layui-btn-danger layui-btn-sm" lay-event="del-items">批量删除</button>
                       </script>
           
                       <script type="text/html" id="op-col">
                           <a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="grant">
                                <i class="layui-icon layui-icon-auz"></i>
                                授权
                           </a>
                           <a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="edit-item">编辑</a>
                           <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del-item">删除</a>
                       </script>`;
            $(elem).html(mainDom);

            dataTable = table.render({
                elem: '#data-table',
                url: url,
                toolbar: '#toolbar-filter', //开启头部工具栏，并为其绑定左侧模板
                defaultToolbar: ['filter'],
                cellMinWidth: 100,
                height: 'full-20',
                cols: cols,
                where: where,
                page: true,
                request: {
                    pageName: "pageModel.pageNo",
                    limitName: "pageModel.pageSize"
                },
                smartReloadModel: true,
                done: function (res, curr, count) {
                    if (isInit) {
                        _this.filterInit();

                        isInit = false;
                    }
                }
            });

            // override confirm event
            // event delegation
            $(document).on('click', '#confirm', e => {
                let result = filter.confirm();

                conditions = JSON.stringify(result);
                // console.log(conditions);

                // 加载条件
                _this.reload();

                e.stopPropagation();
            });

            let popupUrl = popup.url,
                popupWidth = popup.width,
                popupHeight = popup.height;
            //头工具栏事件
            table.on('toolbar(data-table)', function (obj) {
                switch (obj.event) {
                    case 'add-item': // 新建
                        _this.openPopup(popupUrl, popupWidth, popupHeight, obj.event);

                        break;
                    case 'del-items': // 批量删除
                        _this.deleteItems(obj, url)

                        break;
                }
            });

            // 监听行工具事件
            table.on('tool(data-table)', function (obj) {
                _this.openPopup(popupUrl, popupWidth, popupHeight, obj.event, obj.data);
            });

            // listen double click
            table.on('rowDouble(data-table)', function (obj) {
                _this.openPopup(popupUrl, popupWidth, popupHeight, 'show-item', obj.data);
            });
        },
        filterInit() { // 加载条件筛选组件
            let paramFilter = {
                moduleCode: iframeId,
                withTable: withTable,
                keywords: keywords
            }
            filter.init(paramFilter);
        },
        // 弹出层 新建/修改/删除
        openPopup(url, width, height, event, data) {
            // 水平剧中  x 坐标计算
            let offsetX = math.evaluate(`${(windowW - width) / 2 - (sidebarWidth / 2)}`);

            let title = '', skin, btn = [];
            switch (event) {
                case 'add-item':
                    dataCarrier.empty().setEvent(event);
                    title = `新建${dtTitle}`;
                    skin = 'modify-class';
                    btn = ['取消', '保存'];

                    break;
                case 'edit-item':
                    dataCarrier.set(data, event);
                    title = `修改${dtTitle}`;
                    skin = 'modify-class';
                    btn = ['取消', '保存'];

                    break;
                case 'del-item':
                    dataCarrier.set(data, event);
                    title = `删除${dtTitle}`;
                    skin = 'delete-class';
                    btn = ['取消', '删除'];

                    break;
                case 'show-item':
                    dataCarrier.set(data, event);
                    title = `查看${dtTitle}`;
                    skin = 'modify-class';
                    btn = ['关闭'];

                    break;
            }

            let openObj = {
                type: 2,
                title: title,
                skin: skin,
                area: [`${width}px`, `${height}px`],
                offset: ['40px', `${offsetX}px`],
                content: [url, 'no'],
                btn: btn,
                yes(index, layero) {
                    layer.close(index);

                    return false;
                },
                btn2(index, layero) {
                    let submitModify = layer.getChildFrame('#submit-modify', index);
                    submitModify.click();

                    return false;
                },
                end() {
                    if (layui.dataCarrier.isModified) {
                        dataTable.reload();
                    }

                    dataCarrier.empty();
                }
            };

            layer.open(openObj);
        },
        deleteItems(obj, url) { // 批量删除
            let checkStatus = table.checkStatus(obj.config.id);
            let checkData = checkStatus.data;
            if (checkData.length === 0) {
                // msg 剧中显示
                let msgWidth = 240;
                let msgOffsetX = math.evaluate(`${(windowW - msgWidth) / 2 - (sidebarWidth / 2)}`);
                layer.msg('请选择需要删除的数据！', {
                    area: `${msgWidth}px`,
                    offset: ['t', `${msgOffsetX}px`]
                });

                return false;
            }

            let confirmWidth = 400;
            let confirmOffsetX = math.evaluate(`${(windowW - confirmWidth) / 2 - (sidebarWidth / 2)}`);
            layer.confirm(`数据删除后无法恢复，请您再次确认，是否删除当前选定的${checkData.length}条数据？`, {
                    icon: 0,
                    title: '提示',
                    area: `${confirmWidth}px`,
                    offset: ['t', `${confirmOffsetX}px`]
                },
                function (index) {
                    layer.close(index);
                    let loadWidth = 32;
                    let loadOffsetX = math.evaluate(`${(windowW - loadWidth) / 2 - (sidebarWidth / 2)}`);
                    let loadIndex = layer.load(2, {
                        shade: [0.5, 'gray'], //0.5透明度的灰色背景
                        offset: ['t', `${loadOffsetX}px`],
                        content: '<span style="margin-left: -19px; color: #fff;">删除中...</span>',
                        success: function (layero) {
                            layero.find('.layui-layer-content').css({
                                'padding-top': '39px',
                                'width': `50px`,
                                'background-size': '32px'
                            });
                        }
                    });

                    let data = JSON.stringify(checkData);
                    $.ajax({
                        type: 'DELETE',
                        url: url,
                        async: true,
                        dataType: 'json',
                        contentType: 'application/json',
                        data: data,
                        success: function (response, status, xhr) {
                            layer.close(loadIndex);

                            // 剧中显示
                            let msgWidth = 160;
                            let msgOffsetX = math.evaluate(`${(windowW - msgWidth) / 2 - (sidebarWidth / 2)}`);
                            layer.msg('删除成功！', {
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
                });
        },
        reload() {
            dataTable.reload({
                where: {condJsonStr: conditions}
            });
        }
    };

    exports('dataTable', obj);
})
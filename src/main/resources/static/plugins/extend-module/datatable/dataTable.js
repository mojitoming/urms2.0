layui.link(`${$WEB_ROOT_PATH}/plugins/layui_ext/dtree/dtree.css`)
layui.link(`${$WEB_ROOT_PATH}/plugins/layui_ext/dtree/font/dtreefont.css`)
layui.link(`${$WEB_ROOT_PATH}/plugins/extend-module/datatable/dataTable.css`);
layui.extend({
    filter: `{/}${$WEB_ROOT_PATH}/plugins/extend-module/filter/filter`,
    tablePlug: `{/}${$WEB_ROOT_PATH}/plugins/layui_ext/tablePlug/tablePlug`,
    dtree: `{/}${$WEB_ROOT_PATH}/plugins/layui_ext/dtree/dtree`
}).define(['table', 'filter', 'tablePlug', 'dtree'], function (exports) {
    let table = layui.table,
        filter = layui.filter,
        $ = layui.$,
        tablePlug = layui.tablePlug,
        dtree = layui.dtree;
    let isInit = true; // 判断是否第一次 render table

    tablePlug.smartReload.enable(true);

    let iframeId = $('iframe', parent.document).attr('id');
    let dataTable, rTree;
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
            let elem = param.elem || '#datatable-cover';
            let url = param.url;
            let cols = param.cols;
            let where = param.where || {};
            keywords = param.keywords;
            withTable = param.withTable && true;
            dataCarrier = param.dataCarrier || {};
            status = param.status;
            readonly = param.readonly || false;
            let popup = param.popup || {url: '', width: 0, height: 0};
            let rightTree = param.rightTree || {url: ''};
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

            this.construct(elem, url, cols, where, popup, rightTree);

            return dataTable;
        },
        construct(elem, url, cols, where, popup, rightTree) {
            let _this = this;
            let dtDom = `
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
            let mainDom = `<div class="layui-fluid">
                               <div class="layui-row"> 
                                  <div id="dt-cover" class="layui-col-md9">  
                                      ${dtDom}
                                  </div>  
                                  <div id="right-tree-cover" class="layui-col-md3">  
                                      <ul id="right-tree" class="dtree" data-id="-1"></ul>  
                                      <div class="layui-btn-group">  
                                          <button id="save" type="button" class="layui-btn layui-btn-sm layui-btn-normal layui-btn-disabled" disabled="disabled">保存</button>  
                                          <button id="restore" type="button" class="layui-btn layui-btn-sm layui-btn-warm layui-btn-disabled" disabled="disabled">还原</button>  
                                      </div>  
                                  </div> 
                               </div>
                           </div>`
            $(elem).html(mainDom);

            (() => {
                // div#right-tree 高度自适应
                let winHeight = $(window).height();
                $('#right-tree').height(winHeight - 2 - 25 - 10 - 30);
            })();

            dataTable = table.render({
                elem: '#data-table',
                url: url,
                toolbar: '#toolbar-filter', //开启头部工具栏，并为其绑定左侧模板
                defaultToolbar: ['filter'],
                cellMinWidth: 100,
                height: 'full-0',
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
            let rawGrantData;
            table.on('tool(data-table)', function (obj) {
                if (obj.event === 'grant') {
                    rawGrantData = obj.data;
                    _this.rightTreeReload(rawGrantData);

                    return false;
                }

                _this.openPopup(popupUrl, popupWidth, popupHeight, obj.event, obj.data);
            });

            // listen double click
            table.on('rowDouble(data-table)', function (obj) {
                _this.openPopup(popupUrl, popupWidth, popupHeight, 'show-item', obj.data);
            });

            /**
             * right tree
             */
            rTree = dtree.render({
                elem: '#right-tree',
                ficon: '-1',
                url: rightTree.url,
                method: 'GET',
                dataFormat: 'list',
                line: true,
                checkbar: true,
                checkbarType: rightTree.checkbarType,
                menubar: true,
                request: {
                    'status': 'ACTIVE'
                },
                menubarTips: {
                    group: ["moveDown", "moveUp", "refresh", "searchNode"]
                },
                done: function (res, $ul, first) {
                    if (first) {
                        if (rightTree.checkbarType === 'all') {
                            dtree.initAllCheck("right-tree");
                        }
                        if (rightTree.checkbarType === 'no-all') {
                            dtree.initNoAllCheck("right-tree");
                        }
                    }
                }
            });
            let $rTreeSaveBtn = $('#right-tree-cover #save');
            let $rTreeRestoreBtn = $('#right-tree-cover #restore');

            // 保存
            $rTreeSaveBtn.on('click', function (e) {
                let checkbarNodes = rTree.getCheckbarNodesParam();
                let data = JSON.stringify(checkbarNodes);
                $.ajax({
                    type: 'post',
                    url: rightTree.saveUrl,
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
            $rTreeRestoreBtn.on('click', function (e) {
                _this.rightTreeReload(rawGrantData);

                e.stopPropagation();
            })
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
            let _this = this;
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
                        if (layui.dataCarrier.event === 'del-item') {
                            _this.rightTreeReload();
                        }
                    }

                    dataCarrier.empty();
                }
            };

            layer.open(openObj);
        },
        deleteItems(obj, url) { // 批量删除
            let _this = this;
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
                                _this.rightTreeReload();
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
        },
        rightTreeReload(data) {
            if (!data) {
                rTree.reload({
                    request: {
                        'status': 'ACTIVE'
                    }
                });

                this.treeBtnDisable();

                return false;
            }

            if (data.roleId && data.roleName) {
                rTree.reload({
                    request: {
                        'roleId': data.roleId,
                        'roleName': data.roleName,
                        'status': 'ACTIVE'
                    }
                });
            } else if (data.userId && data.nickname) {
                rTree.reload({
                    request: {
                        'userId': data.userId,
                        'nickname': data.nickname,
                        'status': 'ACTIVE'
                    }
                });
            } else {
                rTree.reload({
                    request: {
                        'status': 'ACTIVE'
                    }
                });
            }

            this.treeBtnEnable()
        },
        treeBtnEnable() {
            let $rTreeSaveBtn = $('#right-tree-cover #save');
            let $rTreeRestoreBtn = $('#right-tree-cover #restore');

            $rTreeSaveBtn.removeClass('layui-btn-disabled');
            $rTreeSaveBtn.removeAttr('disabled');
            $rTreeRestoreBtn.removeClass('layui-btn-disabled');
            $rTreeRestoreBtn.removeAttr('disabled');
        },
        treeBtnDisable() {
            let $rTreeSaveBtn = $('#right-tree-cover #save');
            let $rTreeRestoreBtn = $('#right-tree-cover #restore');

            $rTreeSaveBtn.addClass('layui-btn-disabled');
            $rTreeSaveBtn.attr('disabled', 'disabled');
            $rTreeRestoreBtn.addClass('layui-btn-disabled');
            $rTreeRestoreBtn.attr('disabled', 'disabled');
        }
    };

    exports('dataTable', obj);
})
layui.link(`${$WEB_ROOT_PATH}/plugins/layui_ext/dtree/dtree.css`)
layui.link(`${$WEB_ROOT_PATH}/plugins/layui_ext/dtree/font/dtreefont.css`)
layui.extend({
    dtree: `{/}${$WEB_ROOT_PATH}/plugins/layui_ext/dtree/dtree`
}).use(['dtree', 'element'], function () {
    let $ = layui.$,
        dtree = layui.dtree;

    // 计算弹出层水平剧中 x 坐标
    let windowW = $(window).width();
    let sidebarWidth = $MENU_WH.width;
    let openWidth = 500, openHeight = 500;
    let offsetX = math.evaluate(`${(windowW - openWidth) / 2 - (sidebarWidth / 2)}`);

    let moduleTree = dtree.render({
        elem: '#module-tree',
        ficon: '-1',
        url: $WEB_ROOT_PATH + '/module-api/module-tree',
        method: 'GET',
        dataFormat: 'list',
        line: true,
        menubar: true,
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
        toolbar: true,
        toolbarWay: 'follow',
        toolbarShow: [],
        toolbarFun: {
            loadToolbarBefore: function (buttons, param, $div) {
                if (param.nodeId === '0') {
                    buttons.moduleEdit = '';
                    buttons.moduleDel = '';
                }

                return buttons;
            }
        },
        toolbarExt: [
            {
                toolbarId: 'moduleAdd',
                icon: 'dtree-icon-roundadd',
                title: '新增模块',
                handler: function (node, $div) {
                    openPop(node, 'add-item');
                }
            },
            {
                toolbarId: 'moduleEdit',
                icon: 'dtree-icon-bianji',
                title: '编辑模块',
                handler: function (node, $div) {
                    openPop(node, 'edit-item');
                }
            },
            {
                toolbarId: 'moduleDel',
                icon: 'dtree-icon-roundclose',
                title: '删除模块',
                handler: function (node, $div) {
                    openPop(node, 'del-item');
                }
            }
        ],
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

    // 节点点击
    dtree.on('node("module-tree")', function (obj) {
        // 模块ID
        let moduleId = obj.param.nodeId;

        findModuleInfo(moduleId);
        subModulesOdn(moduleId);
    });

    // 节点点击 模块信息
    function findModuleInfo(moduleId) {
        let moduleVOArr = [];
        let $moduleInfo = $('#module-info');
        if (moduleId === '0') {
            moduleVOArr = ['模块树', '根', '', '', ''];

            $moduleInfo.children('tr').each((i, e) => {
                let temp = moduleVOArr[i] ? moduleVOArr[i] : '';
                $(e).children('td').last().text(temp);
            })

            return false;
        }

        $.ajax({
            type: 'get',
            url: $WEB_ROOT_PATH + '/module-api/module',
            async: true,
            dataType: 'json',
            data: {
                'moduleId': moduleId,
            },
            success: function (response, status, xhr) {
                let moduleVO = response.moduleVOList[0];

                moduleVOArr.push(moduleVO.moduleName);
                moduleVOArr.push(moduleVO.moduleTypeName);
                moduleVOArr.push(moduleVO.moduleAction);
                moduleVOArr.push(moduleVO.moduleIcon);
                moduleVOArr.push(moduleVO.statusName);
                // 填值
                $moduleInfo.children('tr').each((i, e) => {
                    let temp = moduleVOArr[i] ? moduleVOArr[i] : '';
                    $(e).children('td').last().text(temp);
                })
            },
            error: function (response, status, xhr) {
            }
        });
    }

    // 节点点击 子模块排序
    let odnModuleId;

    function subModulesOdn(moduleId) {
        odnModuleId = moduleId;
        saveBtnOdnDisable();
        if (moduleId === '0') {
            $('#drag-odn').empty();

            return false;
        }

        let url = $WEB_ROOT_PATH + '/module-api/sub-module';
        $.ajax({
            type: 'get',
            url: url,
            async: true,
            dataType: 'json',
            data: {
                'moduleId': moduleId,
            },
            success: function (response, status, xhr) {
                let moduleList = response.moduleList;
                sortableHandle(moduleList);
            },
            error: function (response, status, xhr) {
            }
        });
    }

    // sortable 使用
    let $saveOdn = $('#save-odn');

    function sortableHandle(data) {
        let dragOdnObj = document.getElementById('drag-odn');

        if (data.length === 0) {
            $(dragOdnObj).empty();

            return false;
        }
        let temp = '';
        data.forEach(e => {
            temp += `<li class="my-list-group-item" data-id="${e.moduleId}"><span class="iconfont icon-ketuozhuai my-handle"></span>${e.moduleName}</li>`;
        });
        $(dragOdnObj).html(temp);

        // 拖拽排序
        let sortable = new Sortable(dragOdnObj, {
            group: 'module-odn',
            handle: '.my-handle',
            ghostClass: 'my-ghost-blue',
            animation: 150,
            onUpdate: function (event) {
                saveBtnOdnEnable();
            }
        });

        // 保存
        $saveOdn.unbind('click').bind('click', function (e) {
            let moduleIdArr = sortable.toArray();

            let url = $WEB_ROOT_PATH + '/module-api/module-odn';
            $.ajax({
                type: 'put',
                url: url,
                async: true,
                dataType: 'json',
                traditional: true,
                data: {
                    'moduleIdList': moduleIdArr,
                },
                success: function (response, status, xhr) {
                    let msgWidth = 180;
                    let msgOffsetX = math.evaluate(`${(windowW - msgWidth) / 2 - (sidebarWidth / 2)}`);
                    layer.msg('模块顺序保存成功！', {
                        time: 2000,
                        area: `${msgWidth}px`,
                        offset: ['t', `${msgOffsetX}px`]
                    }, function () {
                        moduleTree.reload({
                            request: {
                                'moduleId': odnModuleId,
                            }
                        });
                    });
                },
                error: function (response, status, xhr) {
                    layer.msg('出现系统错误，请联系管理员处理！');
                }
            });

            e.stopPropagation();
        });
    }

    // 顺序保存按钮 启用/关闭
    function saveBtnOdnEnable() {
        $saveOdn.removeClass('layui-btn-disabled');
        $saveOdn.removeAttr('disabled');
    }

    function saveBtnOdnDisable() {
        $saveOdn.addClass('layui-btn-disabled');
        $saveOdn.attr('disabled', 'disabled');
    }

    // 弹出层传参
    class DataCarrier {
        constructor(node, event) {
            this.node = node;
            this.event = event; // 操作事件
            this.isModified = false; // 数据是否修改
        }
    }

    let dataCarrier = {
        empty() {
            layui.dataCarrier = new DataCarrier();

            return dataCarrier;
        },
        set(node, event) { // 需要传递的行数据
            layui.dataCarrier = new DataCarrier(node, event);

            return dataCarrier;
        },
        setEvent(event) {
            layui.dataCarrier.event = event;

            return dataCarrier;
        }
    };

    // 弹出层
    function openPop(node, event) {
        let title, skin, btn;
        switch (event) {
            case 'add-item':
                dataCarrier.set(node, event);
                if (node.nodeId === '0') {
                    title = '新建系统';
                } else {
                    title = `新建模块`;
                }
                skin = 'modify-class';
                btn = ['取消', '保存'];

                break;
            case 'edit-item':
                dataCarrier.set(node, event);

                if (node.parentId === '0') {
                    title = '修改系统';
                } else {
                    title = `修改模块`;
                }
                skin = 'modify-class';
                btn = ['取消', '保存'];

                break;
            case 'del-item':
                dataCarrier.set(node, event);
                if (node.parentId === '0') {
                    title = '删除系统';
                } else {
                    title = `删除模块`;
                }
                skin = 'delete-class';
                btn = ['取消', '删除'];

                break;
        }

        let openObj = {
            type: 2,
            title: title,
            skin: skin,
            area: [`${openWidth}px`, `${openHeight}px`],
            offset: ['40px', `${offsetX}px`],
            content: [$WEB_ROOT_PATH + '/module/modify', 'no'],
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
                    let moduleId;
                    if (layui.dataCarrier.event === 'del-item') {
                        moduleId = layui.dataCarrier.node.parentId;
                    } else {
                        moduleId = layui.dataCarrier.node.nodeId;
                    }

                    moduleTree.reload({
                        request: {
                            "moduleId": moduleId,
                        }
                    });
                }

                dataCarrier.empty();
            }
        };

        layer.open(openObj);
    }
});
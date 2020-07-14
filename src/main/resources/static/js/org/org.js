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
    let openWidth = 500, openHeight = 400;
    let offsetX = math.evaluate(`${(windowW - openWidth) / 2 - (sidebarWidth / 2)}`);

    // 计算排序部分高度
    let windowH = $(window).height();
    let odnHeight = windowH - 42 * 2 - 21 - 22 - 24;
    $('#drag-odn').height(odnHeight);

    let orgTree = dtree.render({
        elem: '#org-tree',
        ficon: '-1',
        url: $WEB_ROOT_PATH + '/org-api/org-all-tree',
        method: 'GET',
        dataFormat: 'list',
        line: true,
        menubar: true,
        toolbarStyle: {
            title: "模块",
            area: ["50%", "400px"]
        },
        scroll: '#org-tree-cover',
        menubarTips: {
            group: [{
                menubarId: 'search-org-btn',
                icon: 'dtree-icon-search2',
                title: '查询',
                handler: function (node, $div) {
                    searchOrgNode();
                }
            }, "moveDown", "moveUp", "refresh"]
        },
        toolbar: true,
        toolbarWay: 'follow',
        toolbarShow: [],
        toolbarFun: {
            loadToolbarBefore: function (buttons, param, $div) {
                if (param.nodeId === '0') {
                    buttons.orgEdit = '';
                    buttons.orgDel = '';
                }
                if (param.leaf && param.parentId !== '0' && param.parentId !== '-1') {
                    buttons.orgAdd = '';
                }

                return buttons;
            }
        },
        toolbarExt: [
            {
                toolbarId: 'orgAdd',
                icon: 'dtree-icon-roundadd',
                title: '新增节点',
                handler: function (node, $div) {
                    openPop(node, 'add-item');
                }
            },
            {
                toolbarId: 'orgEdit',
                icon: 'dtree-icon-bianji',
                title: '编辑节点',
                handler: function (node, $div) {
                    openPop(node, 'edit-item');
                }
            },
            {
                toolbarId: 'orgDel',
                icon: 'dtree-icon-roundclose',
                title: '删除节点',
                handler: function (node, $div) {
                    openPop(node, 'del-item');
                }
            }
        ],
    });

    // 监听 search-input enter
    $('input#search-input').keyup(function (e) {
        if (e.key === 'Enter') {
            searchOrgNode();
        }
    });

    function searchOrgNode() {
        let value = $("#search-input").val();
        if (value) {
            let flag = orgTree.searchNode(value); // 内置方法查找节点
            if (!flag) {
                layer.msg("该名称节点不存在！", {icon: 5});
            }
        } else {
            orgTree.menubarMethod().refreshTree(); // 内置方法刷新树
        }
    }

    // 节点点击
    let odnType;
    dtree.on('node("org-tree")', function (obj) {
        let type;
        if (obj.param.nodeId === '0') { // 根
            odnType = 'orgType';
            subOrgOdn(obj.param.nodeId, odnType);
        }
        if (obj.param.parentId === '0') { // 机构类型
            type = 'orgType';
            odnType = 'org';
            subOrgOdn(obj.param.nodeId, odnType);
        }

        if (obj.param.leaf && obj.param.parentId !== '0') { // 机构
            type = 'org';
        }
        findOrgInfo(obj.param.nodeId, type);
    });

    // 节点点击 模块信息
    function findOrgInfo(code, type) {
        let $orgInfo = $('#org-info');
        let orgInfoStr =
            '<tr>' +
            '    <td>根结点代码</td>' +
            '    <td>0</td>' +
            '</tr>' +
            '<tr>' +
            '    <td>根结点名称</td>' +
            '    <td>机构树</td>' +
            '</tr>' +
            '<tr>' +
            '    <td>状态</td>' +
            '    <td></td>' +
            '</tr>';
        let orgArr = ['0', '机构树', ''];
        let url, data;
        switch (type) {
            case 'orgType':
                orgInfoStr =
                    '<tr>' +
                    '    <td>机构类型代码</td>' +
                    '    <td></td>' +
                    '</tr>' +
                    '<tr>' +
                    '    <td>机构类型名称</td>' +
                    '    <td></td>' +
                    '</tr>' +
                    '<tr>' +
                    '    <td>状态</td>' +
                    '    <td></td>' +
                    '</tr>';
                url = $WEB_ROOT_PATH + '/org-api/org-type';
                data = {'orgTypeCode': code};

                break;
            case 'org':
                orgInfoStr =
                    '<tr>' +
                    '    <td>机构代码</td>' +
                    '    <td></td>' +
                    '</tr>' +
                    '<tr>' +
                    '    <td>机构名称</td>' +
                    '    <td></td>' +
                    '</tr>' +
                    '<tr>' +
                    '    <td>医保结算等级</td>' +
                    '    <td></td>' +
                    '</tr>' +
                    '<tr>' +
                    '    <td>状态</td>' +
                    '    <td></td>' +
                    '</tr>';
                url = $WEB_ROOT_PATH + '/org-api/org';
                data = {'orgCode': code};

                break;
        }

        $orgInfo.empty();
        $orgInfo.html(orgInfoStr);
        if (code === '0') {
            $orgInfo.children('tr').each((i, e) => {
                let valueTemp = orgArr[i] ? orgArr[i] : '';

                $(e).children('td').last().text(valueTemp);
            })

            return false;
        }

        $.ajax({
            type: 'get',
            url: url,
            async: true,
            dataType: 'json',
            data: data,
            success: function (response, status, xhr) {
                let orgVO = response.orgVOList[0];
                orgArr = [];
                if (type === 'orgType') {
                    orgArr.push(orgVO.orgTypeCode);
                    orgArr.push(orgVO.orgTypeName);
                    orgArr.push(orgVO.statusName);
                }
                if (type === 'org') {
                    orgArr.push(orgVO.orgCode);
                    orgArr.push(orgVO.orgName);
                    orgArr.push(orgVO.cisLevelName);
                    orgArr.push(orgVO.statusName);
                }

                // 填值
                $orgInfo.children('tr').each((i, e) => {
                    let valueTemp = orgArr[i] ? orgArr[i] : '';

                    $(e).children('td').last().text(valueTemp);
                })
            },
            error: function (response, status, xhr) {
            }
        });
    }

    // 节点点击 子模块排序
    function subOrgOdn(code, type) {
        saveBtnOdnDisable();

        let url, data;
        switch (type) {
            case 'orgType':
                url = $WEB_ROOT_PATH + '/org-api/org-type';
                data = {};

                break;
            case 'org':
                url = $WEB_ROOT_PATH + '/org-api/org-by-type';
                data = {'orgTypeCode': code};

                break;
        }

        $.ajax({
            type: 'get',
            url: url,
            async: true,
            dataType: 'json',
            data: data,
            success: function (response, status, xhr) {
                let orgVOList = response.orgVOList;
                sortableHandle(orgVOList, type);
            },
            error: function (response, status, xhr) {
            }
        });
    }

    // sortable 使用
    let $saveOdn = $('#save-odn');

    function sortableHandle(data, type) {
        let dragOdnObj = document.getElementById('drag-odn');

        if (data.length === 0) {
            $(dragOdnObj).empty();

            return false;
        }
        let temp = '';
        data.forEach(e => {
            let code, name
            if (type === 'orgType') {
                code = e.orgTypeCode;
                name = e.orgTypeName;
            }
            if (type === 'org') {
                code = e.orgCode;
                name = e.orgName;
            }
            temp += `<li class="my-list-group-item" data-id="${code}"><span class="iconfont icon-ketuozhuai my-handle"></span>${name}</li>`;
        });
        $(dragOdnObj).html(temp);

        // 拖拽排序
        let sortable = new Sortable(dragOdnObj, {
            group: 'org-odn',
            handle: '.my-handle',
            ghostClass: 'my-ghost-blue',
            animation: 150,
            onUpdate: function (event) {
                saveBtnOdnEnable();
            }
        });

        // 保存
        $saveOdn.unbind('click').bind('click', function (e) {
            let codeArr = sortable.toArray();
            let url, msg, data;
            switch (odnType) {
                case 'orgType':
                    url = $WEB_ROOT_PATH + '/org-api/org-type-odn';
                    msg = '机构类型顺序调整完成！';
                    data = {'orgTypeCodeList': codeArr};

                    break;
                case 'org':
                    url = $WEB_ROOT_PATH + '/org-api/org-odn';
                    msg = '机构顺序调整完成！';
                    data = {'orgCodeList': codeArr};

                    break;
            }

            $.ajax({
                type: 'put',
                url: url,
                async: true,
                dataType: 'json',
                traditional: true,
                data: data,
                success: function (response, status, xhr) {
                    let msgWidth = 210;
                    let msgOffsetX = math.evaluate(`${(windowW - msgWidth) / 2 - (sidebarWidth / 2)}`);
                    layer.msg(msg, {
                        time: 2000,
                        area: `${msgWidth}px`,
                        offset: ['t', `${msgOffsetX}px`]
                    }, function () {
                        orgTree.reload();
                    });
                },
                error: function (response, status, xhr) {
                    layer.msg('出现系统错误，请联系管理员处理！');
                }
            });

            e.stopPropagation();

            return false;
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
        let url;
        switch (event) {
            case 'add-item':
                dataCarrier.set(node, event);
                if (node.nodeId === '0') {
                    title = '新增机构类型';
                    url = $WEB_ROOT_PATH + '/org/type/modify';
                } else {
                    title = `新增机构`;
                    url = $WEB_ROOT_PATH + '/org/modify';
                }
                skin = 'modify-class';
                btn = ['取消', '保存'];

                break;
            case 'edit-item':
                dataCarrier.set(node, event);

                if (node.parentId === '0') {
                    title = '修改机构类型';
                    url = $WEB_ROOT_PATH + '/org/type/modify';
                } else {
                    title = `修改机构`;
                    url = $WEB_ROOT_PATH + '/org/modify';
                }
                skin = 'modify-class';
                btn = ['取消', '保存'];

                break;
            case 'del-item':
                dataCarrier.set(node, event);
                if (node.parentId === '0') {
                    title = '删除机构类型';
                    url = $WEB_ROOT_PATH + '/org/type/modify';
                } else {
                    title = `删除机构`;
                    url = $WEB_ROOT_PATH + '/org/modify';
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
                    orgTree.reload();
                }

                dataCarrier.empty();
            }
        };

        layer.open(openObj);
    }
});
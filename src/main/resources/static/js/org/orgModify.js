layui.use(['form'], function () {
    let $ = layui.$,
        form = layui.form;

    let dataCarrier = parent.layui.dataCarrier;
    (() => {
        // 获取 module-type 字典
        $.ajax({
            type: 'get',
            url: $WEB_ROOT_PATH + '/dict-api/dict',
            async: true,
            dataType: 'json',
            data: {
                'dictTable': 'T_DICT_ORG_TYPE',
                'dictValue': 'ORG_TYPE_CODE',
                'dictTitle': 'ORG_TYPE_NAME',
                'dictWhere': "ORG_TYPE_CODE <> 'DHCC' AND STATUS='ACTIVE'",
                'dictOrderBy': 'ODN',
            },
            success: function (response, status, xhr) {
                let dictList = response.dictList;
                let $orgTypeSelect = $('select[name="orgTypeCode"]');
                $orgTypeSelect.empty();
                let selectContent = '<option value=""></option>';
                dictList.forEach(e => {
                    selectContent += `<option value="${e.value}">${e.title}</option>`;
                });
                $orgTypeSelect.html(selectContent);
                form.render('select');

                initInfo();
            },
            error: function (response, status, xhr) {
            }
        });
    })();

    // init form information
    function initInfo() {
        // 根据 moduleId 获取数据
        $.ajax({
            type: 'get',
            url: $WEB_ROOT_PATH + '/org-api/org',
            async: true,
            dataType: 'json',
            data: {
                'orgCode': dataCarrier.node.nodeId,
            },
            success: function (response, status, xhr) {
                // 回填
                let orgVO = response.orgVOList[0];

                if (dataCarrier.event === 'add-item') {

                    form.val('modify-form', {
                        'orgTypeCode': dataCarrier.node.nodeId,
                    });

                    form.render('select');
                }

                if (dataCarrier.event !== 'add-item') {
                    form.val('modify-form', {
                        'orgTypeCode': dataCarrier.node.parentId,
                        'orgCode': orgVO.orgCode,
                        'orgName': orgVO.orgName,
                        'status': orgVO.status === 'ACTIVE',
                    });

                    form.render();
                }

                if (dataCarrier.event === 'edit-item') {
                    $('input[name="orgCode"]').prop('disabled', true);
                }

                if (dataCarrier.event === 'del-item') {
                    formReadonly($('form'));
                }
            },
            error: function (response, status, xhr) {
            }
        });
    }

    // 自定义验证
    form.verify({
        repeated(value, item) { // 重复性校验
            if (dataCarrier.event !== 'add-item') return false;

            let count = 0;
            let url = $WEB_ROOT_PATH + '/org-api/org';
            $.ajax({
                type: 'get',
                url: url,
                async: false,
                dataType: 'json',
                data: {
                    'orgCode': value
                },
                success: function (data) {
                    count = data.orgVOList.length;
                },
                error: function (data) {
                }
            });

            if (count > 0) {
                return '此机构代码已存在！';
            }
        }
    });

    // 提交表单
    form.on('submit(submit-modify)', function (data) {
        let content = JSON.stringify(data.field);

        let url = $WEB_ROOT_PATH + '/org-api/org/modify', type, message;
        switch (dataCarrier.event) {
            case 'add-item':
                type = 'POST';
                message = '新增成功！';

                break;
            case 'edit-item':
                type = 'PUT';
                message = '修改成功！';

                break;
            case 'del-item':
                type = 'DELETE';
                message = '删除成功！';
                let tips = '确定删除此机构？';

                layer.confirm(tips, {icon: 0, title: '提示'},
                    function (index) {
                        formSubmit(url, content, type, message);
                    });

                return false;
        }
        formSubmit(url, content, type, message);

        return false;
    });

    function formSubmit(url, data, type, message) {
        $.ajax({
            type: type,
            url: url,
            async: true,
            dataType: 'json',
            contentType: 'application/json',
            data: data,
            success: function (response, status, xhr) {
                dataCarrier.isModified = true;
                layer.msg(message, {time: 2000}, () => {
                    let index = parent.layer.getFrameIndex(window.name); // 先得到当前iframe层的索引
                    parent.layer.close(index); // 再执行关闭
                });
            },
            error: function (response, status, xhr) {
                layer.msg('出现系统错误，请联系管理员处理！');
            }
        });
    }

    // 整个 form 只读
    function formReadonly(obj) {
        obj.find('input,textarea,select').prop('disabled', true);
        obj.find('.layui-layedit iframe').contents().find('body').prop('contenteditable', false);
        form.render();
    }
});
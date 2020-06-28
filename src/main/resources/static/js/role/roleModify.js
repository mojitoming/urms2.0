layui.use(['form', 'layer'], function () {
    let form = layui.form,
        layer = layui.layer,
        $ = layui.$;
    let dataCarrier = parent.layui.dataCarrier;

    // 默认焦点
    $('input[name="clazz"]').focus();
    // 字典初始化
    (() => {
        // 初始化
        if (dataCarrier.event !== 'add-item') {
            form.val('modify-form', {
                'roleId': dataCarrier.roleId,
                'roleName': dataCarrier.roleName,
                'priority': dataCarrier.priority,
                'roleDesc': dataCarrier.roleDesc,
                'status': dataCarrier.status === 'ACTIVE'
            });
            form.render('select');
        }

        if (dataCarrier.event === 'del-item' || dataCarrier.event === 'show-item') {
            formReadonly($('form'));
        }
    })();

    form.on('submit(submit-modify)', function (data) {
        let content = JSON.stringify(data.field);

        let url = $WEB_ROOT_PATH + '/role-api/role/modify', type, message;
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

                layer.confirm('确定删除此角色？', {icon: 0, title: '提示'},
                    function (index) {
                        // TODO scheme 与 rule 之间修改的耦合影响
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
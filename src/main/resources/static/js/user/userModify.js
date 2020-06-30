layui.use(['form', 'layer', 'laydate'], function () {
    let form = layui.form,
        layer = layui.layer,
        laydate = layui.laydate,
        $ = layui.$;
    let dataCarrier = parent.layui.dataCarrier;

    // 默认焦点
    $('input[name="clazz"]').focus();
    // 字典初始化
    (() => {
        // 初始化
        if (dataCarrier.event !== 'add-item') {
            form.val('modify-form', {
                'userId': dataCarrier.userId,
                'username': dataCarrier.username,
                'nickname': dataCarrier.nickname,
                'warrantStartDate': dataCarrier.warrantStartDate,
                'warrantEndDate': dataCarrier.warrantEndDate,
                'status': dataCarrier.status === 'ACTIVE'
            });
            form.render('select');
        }

        if (dataCarrier.event === 'del-item' || dataCarrier.event === 'show-item') {
            formReadonly($('form'));
        }

        if (dataCarrier.event === 'edit-item') {
            $('#rest-password').css('display', 'inline-block');
        }
    })();

    // 有效期初始化
    laydate.render({
        elem: '#warrant-start-date',
        type: 'datetime',
        trigger: 'click'
    });
    laydate.render({
        elem: '#warrant-end-date',
        type: 'datetime',
        trigger: 'click'
    });

    // 自定义验证
    form.verify({
        repeated(value, item) { // 重复性校验
            if ((dataCarrier.event !== 'add-item' && dataCarrier.event !== 'edit-item') || value === dataCarrier.username) return false;

            let count = 0;
            let url = $WEB_ROOT_PATH + '/user-api/username';
            $.ajax({
                type: 'get',
                url: url,
                async: false,
                dataType: 'json',
                data: {
                    'user.username': value
                },
                success: function (data) {
                    count = data.count;
                },
                error: function (data) {
                }
            });

            if (count > 0) {
                return '此用户名已存在！';
            }
        }
    });

    form.on('submit(submit-modify)', function (data) {
        let content = JSON.stringify(data.field);

        let url = $WEB_ROOT_PATH + '/user-api/user/modify', type, message;
        switch (dataCarrier.event) {
            case 'add-item':
                type = 'POST';
                message = '新增成功！';

                break;
            case 'edit-item':
                type = 'PUT';
                message = '修改成功！';

                let resetPassword = data.field.resetPassword;
                if (resetPassword === 'on') {
                    layer.confirm('您将重置密码，请确认！', {icon: 0, title: '警告'}, () => {
                        formSubmit(url, content, type, message);
                    }, () => {
                        $('input[name="resetPassword"]').removeAttr('checked');
                        form.render('checkbox');
                    });
                } else {
                    formSubmit(url, content, type, message);
                }

                return false;
            case 'del-item':
                type = 'DELETE';
                message = '删除成功！';

                layer.confirm('确定删除此用户？', {icon: 0, title: '提示'},
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
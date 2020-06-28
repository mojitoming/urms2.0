layui.link($WEB_ROOT_PATH + '/plugins/aliconfont/iconfont.css');
layui.link($WEB_ROOT_PATH + '/plugins/extend-module/filter/filter.css');
layui.config({
    base: $WEB_ROOT_PATH + '/plugins/extend-module/'
}).extend({
    xmSelect: 'filter/xm-select'
}).define(['form', 'xmSelect', 'laydate'], function (exports) {
    let form = layui.form,
        $ = layui.$,
        xmSelect = layui.xmSelect,
        laydate = layui.laydate;
    let $doc = $(document);
    let $filterContainer;
    let _nextId = -1; // 定义一个id增长器
    let isShow = false, $filterBody;
    let multiSelectMap = new Map(), multiSelectValueArr = [];
    let dateRangeBegin, dateRangeEnd;
    let yearRangeBegin, yearRangeEnd;

    // 条件留痕 k-v : k ==> userId:moduleCode:conditions
    let moduleCode = 'module0000', kws;
    let obj = {
        init(param) {
            moduleCode = param.moduleCode || moduleCode;
            // 关键字处理 ==========>> start
            //   获取关键字参数
            let kwParam = param.keywords;
            //   根据 table header 构建关键字
            let kwTable = '';
            if (param.withTable) {
                let temp, excludeArr = ['0', 'action'];
                $('.layui-table-header th').each(function (index, element) {
                    temp = $(element).attr('data-field');
                    if (excludeArr.indexOf(temp) > -1) return;
                    kwTable += temp + ';';
                });
            }
            //    关键字参数和 table header 关键字同时存在，以关键字参数为先
            kws = kwParam || kwTable;
            // 关键字处理 ==========>> end

            $filterContainer = $('#filter-container');
            this.construct();
        },
        construct() {
            let _this = this;
            let content = `<div class="filter-head">
                               <input type="text" class="layui-input" placeholder="条件过滤器" readonly><span class="iconfont icon-guolv"></span>
                           </div>
                           <div class="filter-body">
                               <form class="layui-form" lay-filter="condition-form">
                                 <div class="layui-form-item first-line">
                                   <div class="layui-inline">
                                       <div class="layui-form-mid">
                                         <input type="checkbox" name="kw-check-all" lay-skin="primary" title="全选" lay-filter="kw-check-all">
                                       </div>
                                       <div class="layui-form-mid add-icon">
                                         <span class="iconfont icon-zengjia"></span>
                                       </div>
                                   </div>
                                 </div>
                                 <div class="form-body">
                                 </div>
                                 <div class="filter-btn">
                                   <button id="cancel" type="button" class="layui-btn layui-btn-primary layui-btn-sm">取消</button>
                                   <button id="confirm" type="button" class="layui-btn layui-btn-normal layui-btn-sm">确定</button>
                                 </div>
                               </form>
                           </div>`;
            $filterContainer.html(content);

            form.render();

            // 监听过滤图标
            $filterBody = $('.filter-body');
            $doc.off('click', 'div.filter-head').on('click', 'div.filter-head', () => {
                this.filterBodyToggle();
            });
            // click other area hide
            $doc.mouseup(e => {
                let $laydate = $('.layui-laydate')
                if (!$filterContainer.is(e.target) && $filterContainer.has(e.target).length === 0
                    && !$laydate.is(e.target) && $laydate.has(e.target).length === 0) {
                    this.filterBodyHide()
                }
            });

            // ajax 获取关键字字典
            let keywords, kwStr, condKwValue;
            let $add = $('.icon-zengjia'),
                $formBody = $('.filter-body .form-body');
            (() => {
                let url = $WEB_ROOT_PATH + '/cond-filter-api/keywords';
                $.ajax({
                    type: 'get',
                    url: url,
                    async: false,
                    dataType: 'json',
                    data: {
                        'condKwValue': kws
                    },
                    success: function (response, status, xhr) {
                        keywords = response.keywordList;
                        assembleKeywords();
                    },
                    error: function (response, status, xhr) {
                    }
                });
            })();

            // 关键字组装
            function assembleKeywords() {
                let kwStr1 = `<div class="layui-form-item">
                                <div class="layui-inline keyword-list">
                                  <div class="layui-form-mid">
                                    <input type="checkbox" name="kw-checkbox" lay-skin="primary" title="" lay-filter="kw-checkbox" checked>
                                  </div>
                                  <div class="layui-input-inline">
                                    <select name="keyword-select" lay-filter="keyword-select">
                                        <option value="">关键字</option>`,
                    kwStr3 = `      </select>
                                  </div>
                                </div>
                                <div class="layui-inline keyword-value">
                                    <!--<input type="text" class="layui-input">-->
                                </div>
                                <div class="layui-form-mid delete-icon">
                                    <span class="iconfont icon-shanchu kw-delete"></span>
                                </div>
                              </div>`,
                    kwStr2 = '';
                for (let i = 0; i < keywords.length; i++) {
                    kwStr2 += `<option value="${keywords[i].kwCode}" kws-index="${i}">${keywords[i].kwName}</option>`;
                }
                kwStr = kwStr1 + kwStr2 + kwStr3;

                // 初始化获取 condition 痕迹
                $.get(`${$WEB_ROOT_PATH}/cond-filter-api/conditions/mark`, {
                    'moduleCode': moduleCode
                }, response => {
                    condKwValue = response.condKwValue;
                    if (!condKwValue) {
                        return false;
                    }
                    let condKwArr = condKwValue.split(';');
                    condKwArr.forEach(function (value, index) {
                        if (!value) {
                            return;
                        }
                        $add.click();

                        let _thisSelect = $(`select[name="keyword-select-${_nextId}"]`);
                        _thisSelect.val(value);
                        handleKwSelectLogic(_thisSelect);
                    });
                });
            }

            // 监听增加图标
            $add.click(e => {
                $formBody.append(kwStr);
                $('select[name="keyword-select"]').attr('name', `keyword-select-${++_nextId}`);
                // 使用过的关键字 disabled
                this.keywordDisabled();
                form.render();

                this.bindEvent();
                this.scrollToEnd();

                e.stopPropagation()
            });
            // 监听关键字下拉框
            form.on('select(keyword-select)', data => {
                handleKwSelectLogic($(data.elem));
            });

            function handleKwSelectLogic(_theSelect) {
                let $theOption = _theSelect.find('option:selected');
                let index = $theOption.attr('kws-index');
                let $kwValue = _theSelect.parents('.form-body .layui-form-item').find('.keyword-value');
                let $kwCheckbox = _theSelect.parents('.form-body .layui-form-item').find(':checkbox[lay-filter="kw-checkbox"]');
                let tempStr = '';
                _this.keywordDisabled();
                $kwValue.empty();
                if (!index) {
                    return false;
                }
                $kwCheckbox.attr('value', `${keywords[index].kwCode}@@${keywords[index].kwName}@@${keywords[index].type}`);
                let id = '';
                switch (keywords[index].type) {
                    case 'multi-select': // 多选下拉框
                        id = `multi-select-${++_nextId}`;
                        tempStr = `<div id="${id}"></div>`;
                        $kwValue.html(tempStr);

                        let multiSelect = xmSelect.render({
                            el: `#${id}`,
                            filterable: true,
                            height: '260px',
                            toolbar: {
                                show: true
                            },
                            prop: {
                                name: 'title',
                            },
                            direction: 'down',
                            data: [],
                            on: function (data) {
                                multiSelectValueArr = data.arr;
                                _this.showResult();
                            }
                        });
                        $.get(`${$WEB_ROOT_PATH}/dict-api/dict`,
                            {
                                'dictTable': keywords[index].dictTable,
                                'dictValue': keywords[index].dictValue,
                                'dictTitle': keywords[index].dictTitle,
                                'dictWhere': keywords[index].dictWhere,
                                'dictOrderBy': keywords[index].dictOrderBy
                            },
                            response => {
                                let data = response.dictList;
                                multiSelect.update({
                                    data: data
                                });
                            });

                        multiSelectMap.set(keywords[index].kwCode, multiSelect);

                        break;
                    case 'multi-select-cascade':
                        id = `multi-select-cascade-${++_nextId}`;
                        tempStr = `<div id="${id}"></div>`;
                        $kwValue.html(tempStr);

                        let multiSelectCascade = xmSelect.render({
                            el: `#${id}`,
                            height: '260px',
                            prop: {
                                name: 'title'
                            },
                            cascader: {
                                show: true,
                                indent: 300,
                            },
                            direction: 'down',
                            // height: '200px',
                            data: [],
                            on: function (data) {
                                multiSelectValueArr = data.arr;
                                _this.showResult();
                            }
                        });
                        $.get(`${$WEB_ROOT_PATH}/dict-api/dict/cascade`,
                            {
                                'dictTable': keywords[index].dictTable,
                                'dictValue': keywords[index].dictValue,
                                'dictTitle': keywords[index].dictTitle,
                                'dictValueChild': keywords[index].dictValueChild,
                                'dictTitleChild': keywords[index].dictTitleChild,
                                'dictWhere': keywords[index].dictWhere,
                                'dictOrderBy': keywords[index].dictOrderBy
                            },
                            response => {
                                let data = response.dictGroupList;
                                multiSelectCascade.update({
                                    data: data
                                });
                            });

                        // layui.form.render();
                        ['strict'].forEach(function (key) {
                            layui.form.on('checkbox(' + key + ')', function (data) {
                                let config = {};
                                config[key] = data.elem.checked;
                                multiSelectCascade.update({
                                    cascader: config
                                });
                                _this.showResult();
                            });
                        });

                        multiSelectMap.set(keywords[index].kwCode, multiSelectCascade);
                        break;
                    case 'checkbox': // 复选框
                        tempStr = `<div class="layui-input-block checkbox">`;

                        $.ajax({
                            type: 'get',
                            url: `${$WEB_ROOT_PATH}/dict-api/dict`,
                            async: false,
                            dataType: 'json',
                            data: {
                                'dictTable': keywords[index].dictTable,
                                'dictValue': keywords[index].dictValue,
                                'dictTitle': keywords[index].dictTitle,
                            },
                            success: function (response, status, xhr) {
                                let data = response.dictList;
                                let temp = '';
                                for (let i = 0; i < data.length; i++) {
                                    temp += `<input type="checkbox" name="${keywords[index].kwCode}" lay-skin="primary" title="${data[i].title}" value="${data[i].value}" lay-filter="input-modify">`;
                                }
                                tempStr += temp + '</div>';
                                $kwValue.html(tempStr);
                            },
                            error: function (response, status, xhr) {
                            }
                        });

                        break;
                    case 'radio':
                        tempStr = `<div class="layui-input-block radio">
                                     <input type="radio" name="${keywords[index].kwCode}" title="是" value="Y" lay-filter="input-modify">
                                     <input type="radio" name="${keywords[index].kwCode}" title="否" value="N" lay-filter="input-modify">
                                   </div>`;
                        $kwValue.html(tempStr);

                        break;
                    case 'number': // 数字input
                        tempStr = `<div class="layui-input-inline number">
                            <input type="number" name="${keywords[index].kwCode}(min)" autocomplete="off" class="layui-input">
                          </div>
                            <div class="layui-form-mid">-</div>
                          <div class="layui-input-inline number"">
                            <input type="number" name="${keywords[index].kwCode}(max)" autocomplete="off" class="layui-input">
                          </div>`;
                        $kwValue.html(tempStr);

                        break;
                    case 'text':
                        tempStr = `<div class="layui-input-inline text">
                            <input type="text" name="${keywords[index].kwCode}" autocomplete="off" class="layui-input">
                          </div>`;
                        $kwValue.html(tempStr);

                        break;
                    case 'date-range':
                        id = `date-range-${++_nextId}`;
                        tempStr = `<div class="layui-input-inline date-range">
                            <input id="${id}" type="text" name="${keywords[index].kwCode}" class="layui-input" placeholder=" ~ " autocomplete="off">
                          </div>`;
                        $kwValue.html(tempStr);
                        laydate.render({
                            elem: `#${id}`,
                            range: '~',
                            done: function (value) {
                                if (!value) return false;
                                let begin = value.split('~')[0].trim(),
                                    end = value.split('~')[1].trim();
                                dateRangeBegin = begin;
                                dateRangeEnd = end;

                                _this.showResult();
                            }
                        });

                        break;
                    case 'year-range':
                        id = `year-range-${++_nextId}`;
                        tempStr = `<div class="layui-input-inline year-range">
                            <input id="${id}" type="text" name="${keywords[index].kwCode}" class="layui-input" placeholder=" ~ " autocomplete="off">
                          </div>`;
                        $kwValue.html(tempStr);
                        laydate.render({
                            elem: `#${id}`,
                            range: '~',
                            type: 'year',
                            done: function (value) {
                                if (!value) return false;
                                yearRangeBegin = value.split('~')[0].trim();
                                yearRangeEnd = value.split('~')[1].trim();

                                _this.showResult();
                            }
                        });

                        break;
                }

                form.render();
                // 监听 xm-select 点击
                _this.bindXmSelectClick();

                // 一些监听，用来显示结果
                form.on('checkbox(input-modify)', function () {
                    _this.showResult();
                });
                form.on('radio(input-modify)', function () {
                    _this.showResult();
                })
                $('.keyword-value input').on('change', function () {
                    _this.showResult();
                });
            }

            /*
             * 监听全选
             */
            form.on('checkbox(kw-check-all)', data => {
                $(':checkbox[lay-filter="kw-checkbox"]').prop('checked', data.elem.checked);

                form.render('checkbox');

                _this.showResult();
            });

            // 监听单个选择框
            form.on('checkbox(kw-checkbox)', data => {
                let isAll = !Boolean($(':checkbox[lay-filter="kw-checkbox"]').not(':checked').length);
                $(':checkbox[lay-filter="kw-check-all"]').prop('checked', isAll);

                form.render('checkbox');

                _this.showResult();
            });

            /*
             * 监听按钮
             */
            $('#cancel').unbind('click').bind('click', () => {
                this.filterBodyHide();
            });
        },
        confirm() { // 点击确定
            let result = this.getResult();
            let resultObj = this.strMapToObj(result);
            /*if (status && !resultObj.status) {
                Object.assign(resultObj, {'status': status});
            }
            let conditions = JSON.stringify(resultObj);*/

            this.filterBodyHide();

            // 操作留痕
            let keywords = '';
            result.forEach((value, key) => {
                keywords += `${key};`;
            });
            $.post(`${$WEB_ROOT_PATH}/cond-filter-api/conditions/mark`, {
                'moduleCode': moduleCode,
                'condKwValue': keywords
            }, function (data, textStatus, jqXHR) {
            });

            return resultObj;
        },
        getResult(type) { // 获取结果
            let result = new Map(); // 结果
            let checkedArr = $(':checkbox[lay-filter="kw-checkbox"]:checked');
            checkedArr.each(function () {
                let $this = $(this), tempValue;
                let kwCode = $this.val().split('@@')[0],
                    kwName = $this.val().split('@@')[1],
                    kwType = $this.val().split('@@')[2];
                if (!kwType) return;
                let valueType = type === 'String' ? 'nameStr' : 'valueStr';
                switch (kwType) {
                    case 'multi-select':
                        if (type === 'String') {
                            tempValue = '';
                            multiSelectValueArr.forEach(function (value) {
                                tempValue += value.title + ',';
                            });

                            tempValue = tempValue.substr(0, tempValue.length - 1);
                        } else {
                            tempValue = multiSelectMap.get(kwCode).getValue(valueType).replace(/,/g, '|');
                        }

                        break;
                    case 'multi-select-cascade':
                        tempValue = multiSelectMap.get(kwCode).getValue(valueType).replace(/,/g, '|');

                        break;
                    case 'checkbox':
                        tempValue = '';
                        $(`:checkbox[name=${kwCode}]:checked`).each(function () {
                            tempValue += (type === 'String' ? $(this).attr('title') : $(this).val()) + ',';
                        });
                        tempValue = tempValue.substr(0, tempValue.length - 1);

                        break;
                    case 'radio':
                        let temp = $(`:radio[name=${kwCode}]:checked`);
                        tempValue = type === 'String' ? temp.attr('title') : temp.val();

                        break;
                    case 'number':
                        let min = $(`input[name='${kwCode}(min)']`).val(),
                            max = $(`input[name='${kwCode}(max)']`).val();
                        tempValue = type === 'String' ? `${min}~${max}` : {min: min, max: max};

                        break;
                    case 'date-range':
                        if (type === 'String') {
                            /* let format = 'yyyy-mm-dd';
                             let beginStr = dateRangeBegin ? dateRangeBegin.format(format) : 'begin';
                             let endStr = dateRangeEnd ? dateRangeEnd.format(format) : 'end';*/
                            tempValue = `${dateRangeBegin}~${dateRangeEnd}`;
                        } else {
                            tempValue = {begin: dateRangeBegin, end: dateRangeEnd};
                        }

                        break;
                    case 'year-range':
                        if (type === 'String') {
                            let beginStr = yearRangeBegin ? yearRangeBegin : 'begin';
                            let endStr = yearRangeEnd ? yearRangeEnd : 'end';
                            tempValue = `${beginStr}~${endStr}`;
                        } else {
                            tempValue = {begin: yearRangeBegin, end: yearRangeEnd};
                        }

                        break;
                    case 'text':
                        tempValue = $('.keyword-value .text').find(`input[name="${kwCode}"]`).val();

                        break;
                    default:
                        return;
                }

                if (type === 'String') {
                    result.set(kwName, tempValue);
                } else {
                    result.set(kwCode, tempValue);
                }
            });

            return result;
        },
        showResult() {
            let result = this.getResult('String');
            let resultStr = '';
            result.forEach((value, key, map) => {
                resultStr += `${key}:${value};`;
            });

            let $input = $('div.filter-head > input');
            $input.val(resultStr);
            $input.attr('title', resultStr);
        },
        filterBodyToggle() {
            if (isShow) {
                this.filterBodyHide();
            } else {
                this.filterBodyShow();
            }
        },
        filterBodyShow(speed) {
            let v = speed || 1000;
            let $formBodyChildren = $filterBody.find('.form-body').children();
            $formBodyChildren.fadeIn(v);
            $filterBody.slideDown();
            isShow = true;
        },
        filterBodyHide(speed) {
            let v = speed || 140;
            let $formBodyChildren = $filterBody.find('.form-body').children();
            $formBodyChildren.fadeOut(v);
            $filterBody.slideUp();
            isShow = false;
        },
        keywordDisabled() {
            $('select[lay-filter=keyword-select] option').removeAttr('disabled').parent().find('option:selected').each(function () {
                let kwCode = $(this).val();
                if (!kwCode) return;
                $(`option[value=${kwCode}]`).not($(this)).attr('disabled', 'disabled');
            });
            form.render('select');
        },
        bindEvent() {
            let _this = this;
            // 监听删除图标
            let $del = $('.kw-delete');
            $del.unbind('click').bind('click', function (e) {
                let $this = $(this);
                $this.parents('.layui-form-item').remove();
                _this.keywordDisabled();
                _this.showResult();

                e.stopPropagation();
            });

            this.bindSelectClick();
        },
        bindSelectClick() {
            // 监听 select 点击
            $('.keyword-list').children('.layui-input-inline').unbind('click').bind('click', function (e) {
                let $this = $(this);
                let top = $this.offset().top + $this.height() + 4,
                    left = $this.offset().left;
                $this.find('.layui-form-selected dl').css({"top": top, "left": left});

                e.stopPropagation();
            });
        },
        bindXmSelectClick() {
            // 监听 xm-select 点击
            $('.keyword-value xm-select').unbind('click').bind('click', function (e) {
                let $this = $(this);
                let top = $this.offset().top + $this.height() + 4,
                    left = $this.offset().left;
                let $xmBody = $this.find('.xm-body');
                $xmBody.css('left', `${left}px`);

                e.stopPropagation();
            });
        },
        strMapToObj(strMap) { // Map to Object
            let obj = Object.create(null);
            for (let [k, v] of strMap) {
                obj[k] = v;
            }

            return obj;
        },
        scrollToEnd() { // 自动滚动
            let $formBody = $('.form-body');
            $formBody.scrollTop($formBody[0].scrollHeight);
        }
    };


    exports('filter', obj);
});
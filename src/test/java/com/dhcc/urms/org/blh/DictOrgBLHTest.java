package com.dhcc.urms.org.blh;

import com.dhcc.urms.UrmsApplicationTests;
import com.dhcc.urms.org.dto.OrgDTO;
import com.dhcc.urms.org.entity.DictOrg;
import com.dhcc.urms.org.entity.DictOrgTypeSub;
import com.dhcc.urms.org.service.IDictOrgService;
import org.junit.jupiter.api.Test;

import javax.annotation.Resource;

public class DictOrgBLHTest extends UrmsApplicationTests {

    @Resource
    private OrgDTO orgDTO;

    @Resource
    private IDictOrgService orgService;

    @Test
    public void test() {
        DictOrg org = new DictOrg();
        org.setOrgCode("test");
        org.setOrgName("测试");
        org.setStatus("ACTIVE");

        DictOrgTypeSub orgTypeSub = new DictOrgTypeSub();
        orgTypeSub.setOrgTypeCode("test-type");
        orgTypeSub.setOrgCode("test");

        orgDTO.setOrg(org);
        orgDTO.setOrgTypeSub(orgTypeSub);

        orgService.addOrg(orgDTO);
    }
}

package com.pbd.ms_imobilt.configuration;

import com.pbd.ms_imobilt.block.model.Block;
import com.pbd.ms_imobilt.block.repository.BlockRepositoryI;
import com.pbd.ms_imobilt.enterprise.model.Enterprise;
import com.pbd.ms_imobilt.enterprise.repository.EnterpriseRepositoryI;
import com.pbd.ms_imobilt.lote.dto.LoteRespDto;
import com.pbd.ms_imobilt.lote.model.Lote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TemplateConfig {

    @Autowired
    private BlockRepositoryI blockRepositoryI;

    @Autowired
    private EnterpriseRepositoryI enterpriseRepositoryI;

    public LoteRespDto formatterLote(Lote lote) {
        Block block = blockRepositoryI.findById(lote.getBlock().getId()).get();

        Enterprise enterprise = enterpriseRepositoryI.findById(
                block.getEnterprise().getId()
        ).get();

        return  new LoteRespDto(
                lote.getId(),
                lote.getDescription(),
                enterprise,
                block.getDescription()
        );

    }
}

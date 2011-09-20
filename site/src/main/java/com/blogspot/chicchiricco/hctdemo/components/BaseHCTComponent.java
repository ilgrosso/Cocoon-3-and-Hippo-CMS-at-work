package com.blogspot.chicchiricco.hctdemo.components;

import java.util.HashMap;
import java.util.Map;
import org.apache.cocoon.pipeline.NonCachingPipeline;
import org.apache.cocoon.pipeline.Pipeline;
import org.apache.cocoon.sax.SAXPipelineComponent;
import org.apache.cocoon.sax.component.XMLGenerator;
import org.hippoecm.hst.component.support.bean.BaseHstComponent;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.onehippo.forge.hct.cocoon.sax.HippoRepositoryTransformer;

public abstract class BaseHCTComponent extends BaseHstComponent {

    protected Pipeline<SAXPipelineComponent> buildBasePipeline(
            final HippoBean hippoBean) {

        final Pipeline<SAXPipelineComponent> pipeline =
                new NonCachingPipeline<SAXPipelineComponent>();

        pipeline.addComponent(new XMLGenerator("<hct:document "
                + "xmlns:hct=\"http://forge.onehippo.org/gf/project/hct/1.0\" "
                + "path=\"" + hippoBean.getPath() + "\"/>"));

        final Map<String, String> hrtParams = new HashMap<String, String>();
        hrtParams.put(HippoRepositoryTransformer.PARAM_REPOSITORY_ADDRESS,
                "rmi://localhost:1099/hipporepository");
        hrtParams.put(HippoRepositoryTransformer.PARAM_USERNAME, "admin");
        hrtParams.put(HippoRepositoryTransformer.PARAM_PASSWORD, "admin");
        final HippoRepositoryTransformer hrt = new HippoRepositoryTransformer();
        hrt.setConfiguration(hrtParams);
        pipeline.addComponent(hrt);

        return pipeline;
    }
}

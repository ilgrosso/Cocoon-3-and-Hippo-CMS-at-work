package com.blogspot.chicchiricco.hctdemo.components;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;
import org.apache.cocoon.optional.pipeline.components.sax.fop.FopSerializer;
import org.apache.cocoon.pipeline.Pipeline;
import org.apache.cocoon.sax.SAXPipelineComponent;
import org.apache.cocoon.sax.component.LogAsXMLTransformer;
import org.apache.cocoon.sax.component.XSLTTransformer;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.core.component.HstComponentException;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;

public class HCTPdf extends BaseHCTComponent {

    @Override
    public void doBeforeRender(final HstRequest request,
            final HstResponse response)
            throws HstComponentException {

        final HippoBean hippoBean = getContentBean(request);
        if (hippoBean == null) {
            return;
        }

        final Pipeline<SAXPipelineComponent> pipeline =
                buildBasePipeline(hippoBean);

        final Map<String, Object> params = new HashMap<String, Object>();
        params.put("scheme", request.getScheme());
        params.put("servername", request.getServerName());
        params.put("serverport", Integer.valueOf(request.getServerPort()));
        params.put("contextPath", request.getContextPath());
        final XSLTTransformer xslt = new XSLTTransformer(
                getClass().getResource("/xslt/document2fo.xsl"));
        xslt.setParameters(params);
        pipeline.addComponent(xslt);

        pipeline.addComponent(new FopSerializer());

        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            pipeline.setup(baos);
            pipeline.execute();
        } catch (Exception e) {
            throw new HstComponentException(e);
        }

        request.setAttribute("pdfArray", baos.toByteArray());
    }
}

package com.blogspot.chicchiricco.hctdemo.components;

import java.io.ByteArrayOutputStream;
import org.apache.cocoon.pipeline.Pipeline;
import org.apache.cocoon.sax.SAXPipelineComponent;
import org.apache.cocoon.sax.component.XMLSerializer;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.core.component.HstComponentException;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;

public class HCTXml extends BaseHCTComponent {

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

        final XMLSerializer serializer = XMLSerializer.createXMLSerializer();
        serializer.setIndent(true);
        pipeline.addComponent(serializer);

        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            pipeline.setup(baos);
            pipeline.execute();
        } catch (Exception e) {
            throw new HstComponentException(e);
        }

        final StringBuilder xml = new StringBuilder(new String(
                baos.toByteArray()));
        xml.insert(xml.indexOf("<hct:document ") + "<hct:document ".length(),
                "xmlns:hct=\"http://forge.onehippo.org/gf/project/hct/1.0\" ");

        request.setAttribute("xml", xml.toString());
    }
}

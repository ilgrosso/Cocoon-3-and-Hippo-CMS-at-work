
package com.blogspot.chicchiricco.hctdemo.beans;

import java.util.Calendar;

import org.hippoecm.hst.content.beans.Node;
import org.hippoecm.hst.content.beans.standard.HippoGalleryImageSetBean;

@Node(jcrType="hctdemo:newsdocument")
public class NewsDocument extends TextDocument{

    public Calendar getDate() {
        return getProperty("hctdemo:date");
    }

    /**
     * Get the imageset of the newspage
     *
     * @return the imageset of the newspage
     */
    public HippoGalleryImageSetBean getImage() {
        return getLinkedBean("hctdemo:image", HippoGalleryImageSetBean.class);
    }
    
}

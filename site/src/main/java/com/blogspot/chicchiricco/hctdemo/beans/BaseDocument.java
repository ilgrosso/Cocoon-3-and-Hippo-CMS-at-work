
package com.blogspot.chicchiricco.hctdemo.beans;

import org.hippoecm.hst.content.beans.Node;
import org.hippoecm.hst.content.beans.standard.HippoDocument;

@Node(jcrType="hctdemo:basedocument")
public class BaseDocument extends HippoDocument {

    public String getTitle() {
        return getProperty("hctdemo:title");
    }
    
    public String getSummary() {
        return getProperty("hctdemo:summary");
    }
}


package com.blogspot.chicchiricco.hctdemo.beans;

import org.hippoecm.hst.content.beans.Node;
import org.hippoecm.hst.content.beans.standard.HippoHtml;

@Node(jcrType="hctdemo:textdocument")
public class TextDocument extends BaseDocument{

    public HippoHtml getHtml(){
        return getHippoHtml("hctdemo:body");    
    }
}

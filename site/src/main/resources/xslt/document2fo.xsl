<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:str="http://xsltsl.org/string"
                xmlns:hct="http://forge.onehippo.org/gf/project/hct/1.0"
                xmlns:fo="http://www.w3.org/1999/XSL/Format"
                version="1.0">

    <xsl:import href="string.xsl"/>
    <xsl:import href="xhtml2fo.xsl"/>

    <xsl:output method="xml"/>
    
    <xsl:param name="scheme"/>
    <xsl:param name="servername"/>
    <xsl:param name="serverport"/>
    <xsl:param name="contextPath"/>

    <xsl:template match="/">
        <fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format" font-family="Helvetica" font-size="10pt">

            <!-- defines the layout master -->
            <fo:layout-master-set>
                <fo:simple-page-master master-name="first" page-height="29.7cm" page-width="21cm" 
                                       margin-top="1cm" margin-bottom="2cm" margin-left="2.5cm" margin-right="2.5cm">
                    <fo:region-body margin-top="1cm"/>
                    <fo:region-before extent="1cm"/>
                    <fo:region-after extent="1.5cm"/>
                </fo:simple-page-master>
            </fo:layout-master-set>

            <!-- starts actual layout -->
            <fo:page-sequence master-reference="first">

                <fo:flow flow-name="xsl-region-body">
                    <xsl:apply-templates/>
                </fo:flow>
            </fo:page-sequence>
        </fo:root>
    </xsl:template>
    
    <xsl:template match="hct:document">
        <fo:block font-size="16pt" text-align="center">
            <xsl:value-of select="@localizedName"/>
        </fo:block>
        <fo:block text-align="left">
            <fo:block font-weight="bold">type</fo:block>
            <xsl:value-of select="@type"/>
        </fo:block>
        <xsl:apply-templates/>
    </xsl:template>
    
    <xsl:template match="hct:field">
        <fo:block text-align="left">
            <fo:block font-weight="bold">
                <xsl:value-of select="@name"/>
            </fo:block> 
            <xsl:choose>
                <xsl:when test="not(html)">
                    <xsl:for-each select="hct:value">
                        <fo:block>
                            <xsl:value-of select="text()"/>
                        </fo:block>
                    </xsl:for-each>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:apply-templates select="html/body/*"/>
                </xsl:otherwise>
            </xsl:choose>
        </fo:block>
    </xsl:template>
    
    <xsl:template match="img">
        <xsl:variable name="embeddedPath" select="substring-before(@src, '/{_document}')"/>
        <xsl:variable name="imgURL">
            <xsl:call-template name="str:substring-before-last">
                <xsl:with-param name="text"
                                select="ancestor::hct:field/hct:images/hct:image[$embeddedPath = 
                                        substring-before(substring-after(@path, '/'), '/')]/@path"/>
                <xsl:with-param name="chars">/</xsl:with-param>
            </xsl:call-template>
        </xsl:variable>
        <fo:external-graphic src="{$scheme}://{$servername}:{$serverport}{$contextPath}/binaries/{$imgURL}"/>
    </xsl:template>
    
    <xsl:template match="hct:images">
        <fo:block text-align="center">
            <fo:block font-weight="bold">Images</fo:block> 

            <xsl:for-each select="hct:image">
                <xsl:variable name="imgURL">
                    <xsl:call-template name="str:substring-before-last">
                        <xsl:with-param name="text" 
                                        select="@path"/>
                        <xsl:with-param name="chars">/</xsl:with-param>
                    </xsl:call-template>
                </xsl:variable>
                <fo:external-graphic src="{$scheme}://{$servername}:{$serverport}{$contextPath}/binaries/{$imgURL}"/>
            </xsl:for-each>
        </fo:block>
    </xsl:template>

</xsl:stylesheet>

package com.we.mvp.annotation;

import javax.lang.model.element.Element;

/**
 * Created to :
 *
 * @author WANG
 * @date 2020/5/5
 */
public class ModelBean {
    private String fieldName;
    private String fieldClassName;
    private String modeFieldClassName;

    private Element enclosingElement;
    private Element element;

    public String getModeFieldClassName() {
        return modeFieldClassName;
    }

    public void setModeFieldClassName(String modeFieldClassName) {
        this.modeFieldClassName = modeFieldClassName;
    }

    public String getFieldClassName() {
        return fieldClassName;
    }

    public void setFieldClassName(String fieldClassName) {
        this.fieldClassName = fieldClassName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public Element getEnclosingElement() {
        return enclosingElement;
    }

    public void setEnclosingElement(Element enclosingElement) {
        this.enclosingElement = enclosingElement;
    }

    public Element getElement() {
        return element;
    }

    public void setElement(Element element) {
        this.element = element;
    }

    @Override
    public String toString() {
        return "ModelBean{" +
                "fieldName='" + fieldName + '\'' +
                ", fieldClassName='" + fieldClassName + '\'' +
                ", modeFieldClassName='" + modeFieldClassName + '\'' +
                ", enclosingElement=" + enclosingElement +
                ", element=" + element +
                '}';
    }
}

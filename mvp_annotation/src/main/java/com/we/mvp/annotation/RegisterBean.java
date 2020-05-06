package com.we.mvp.annotation;

import javax.lang.model.element.Element;
import javax.lang.model.type.TypeMirror;

/**
 * Created to :
 *
 * @author WANG
 * @date 2020/5/5
 */
public class RegisterBean {

    private String parentName;
    private String fieldName;
    private String viewFieldName;

    private String fieldClassName;
    private String modelName;

    private Element element;
    private TypeMirror typeMirror;
    private Element enclosingElement;

    public String getViewFieldName() {
        return viewFieldName;
    }

    public void setViewFieldName(String viewFieldName) {
        this.viewFieldName = viewFieldName;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public Element getElement() {
        return element;
    }

    public void setElement(Element element) {
        this.element = element;
    }

    public TypeMirror getTypeMirror() {
        return typeMirror;
    }

    public void setTypeMirror(TypeMirror typeMirror) {
        this.typeMirror = typeMirror;
    }

    public Element getEnclosingElement() {
        return enclosingElement;
    }

    public void setEnclosingElement(Element enclosingElement) {
        this.enclosingElement = enclosingElement;
    }

    public String getFieldClassName() {
        return fieldClassName;
    }

    public void setFieldClassName(String fieldClassName) {
        this.fieldClassName = fieldClassName;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    @Override
    public String toString() {
        return "RegisterBean{" +
                "parentName='" + parentName + '\'' +
                ", fieldName='" + fieldName + '\'' +
                ", viewFieldName='" + viewFieldName + '\'' +
                ", fieldClassName='" + fieldClassName + '\'' +
                ", modelName='" + modelName + '\'' +
                ", element=" + element +
                ", typeMirror=" + typeMirror +
                ", enclosingElement=" + enclosingElement +
                '}';
    }
}

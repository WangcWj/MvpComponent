package com.we.mvp.progress;


import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.we.mvp.annotation.ModelBean;
import com.we.mvp.annotation.MvpModel;
import com.we.mvp.annotation.MvpPresenter;
import com.we.mvp.annotation.MvpProvider;
import com.we.mvp.annotation.RegisterBean;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;


/**
 * Created to :
 *
 * @author WANG
 * @date 2019/9/11
 */
public class RegisterCompiler extends AbstractProcessor {

    private Messager mMessager;
    private Elements elementUtils;
    private Types typeUtils;
    private Filer mFiler;

    private Map<String, RegisterBean> mRegister = new HashMap<>();
    private Map<String, Element> mProvider = new HashMap<>();
    private Map<String, ModelBean> mModel = new HashMap<>();

    private String packageName;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        mMessager = processingEnvironment.getMessager();
        mFiler = processingEnvironment.getFiler();
        elementUtils = processingEnvironment.getElementUtils();
        typeUtils = processingEnvironment.getTypeUtils();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        mMessager.printMessage(Diagnostic.Kind.NOTE, "===========begin============");
        try {
            Set<? extends Element> pElements = roundEnvironment.getElementsAnnotatedWith(MvpProvider.class);
            if (null != pElements && pElements.size() > 0) {
                Iterator<? extends Element> iterator = pElements.iterator();
                while (iterator.hasNext()) {
                    Element element = iterator.next();
                    handleProvider(element);
                }
            }

            Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(MvpPresenter.class);
            if (null != elements && elements.size() > 0) {
                Iterator<? extends Element> iterator = elements.iterator();
                while (iterator.hasNext()) {
                    Element element = iterator.next();
                    handleElement(element);
                }
            }

            Set<? extends Element> models = roundEnvironment.getElementsAnnotatedWith(MvpModel.class);
            if (null != models && models.size() > 0) {
                Iterator<? extends Element> iterator = models.iterator();
                while (iterator.hasNext()) {
                    Element element = iterator.next();
                    handleModel(element);
                }
            }

            generaCode();

        } catch (Exception e) {
            mMessager.printMessage(Diagnostic.Kind.NOTE, "Exception " + e);
        }
        mMessager.printMessage(Diagnostic.Kind.NOTE, "===========end============ ");
        return true;
    }

    private void handleModel(Element element) {
        if (mProvider.size() <= 0) {
            return;
        }
        ElementKind kind = element.getKind();
        if (!kind.isField()) {
            return;
        }
        TypeElement enclosingElement = (TypeElement) element.getEnclosingElement();
        if (!canGeneratedCode(element, enclosingElement)) {
            return;
        }
        MvpModel annotation = element.getAnnotation(MvpModel.class);
        String modelName = annotation.modelName();
        List<? extends TypeMirror> interfaces = enclosingElement.getInterfaces();
        boolean continues = false;
        for (int i = 0; i < interfaces.size(); i++) {
            TypeMirror typeMirror = interfaces.get(i);
            if (typeMirror instanceof DeclaredType) {
                DeclaredType type = (DeclaredType) typeMirror;
                String s = type.asElement().getSimpleName().toString();
                if (modelName.equals(s)) {
                    continues = true;
                    break;
                }
            }
        }
        if (!continues) {
            return;
        }

        ModelBean modelBean = new ModelBean();
        TypeMirror typeMirror = element.asType();
        if (typeMirror instanceof DeclaredType) {
            DeclaredType f = (DeclaredType) typeMirror;
            String meName = f.asElement().getSimpleName().toString();
            modelBean.setFieldClassName(meName);
        }

        Element modelElement = mProvider.get(modelBean.getFieldClassName());
        modelBean.setFieldName(element.getSimpleName().toString());
        modelBean.setElement(modelElement);
        modelBean.setEnclosingElement(enclosingElement);
        modelBean.setModeFieldClassName(annotation.modelFieldName());
        mModel.put(enclosingElement.getSimpleName().toString(), modelBean);
    }

    private void handleProvider(Element element) {
        ElementKind kind = element.getKind();
        if (!kind.isClass()) {
            return;
        }
        TypeElement typeElement = (TypeElement) element;
        mMessager.printMessage(Diagnostic.Kind.NOTE, "typeElement " + typeElement);
        if (!canGeneratedCode(element, typeElement)) {
            return;
        }
        List<? extends TypeMirror> interfaces = typeElement.getInterfaces();
        for (TypeMirror typeMirror : interfaces) {
            if (typeMirror instanceof DeclaredType) {
                DeclaredType type = (DeclaredType) typeMirror;
                String s = type.asElement().getSimpleName().toString();
                mProvider.put(s, element);
            }
        }
    }

    private void handleElement(Element element) {
        mMessager.printMessage(Diagnostic.Kind.NOTE, "-----Element is = " + element + "  provider  " + mProvider.size());
        if (mProvider.size() <= 0) {
            return;
        }
        ElementKind kind = element.getKind();
        if (!kind.isField()) {
            return;
        }
        TypeElement enclosingElement = (TypeElement) element.getEnclosingElement();
        mMessager.printMessage(Diagnostic.Kind.NOTE, "enclosingElement " + enclosingElement);
        if (!canGeneratedCode(element, enclosingElement)) {
            return;
        }
        MvpPresenter annotation = element.getAnnotation(MvpPresenter.class);
        String className = annotation.viewName();
        mMessager.printMessage(Diagnostic.Kind.NOTE, "access " + className);
        List<? extends TypeMirror> interfaces = enclosingElement.getInterfaces();
        boolean continues = false;
        for (int i = 0; i < interfaces.size(); i++) {
            TypeMirror typeMirror = interfaces.get(i);
            if (typeMirror instanceof DeclaredType) {
                DeclaredType type = (DeclaredType) typeMirror;
                String s = type.asElement().getSimpleName().toString();
                if (className.equals(s)) {
                    continues = true;
                    break;
                }
            }
        }
        if (!continues) {
            return;
        }
        RegisterBean registerBean = new RegisterBean();
        TypeMirror typeMirror = element.asType();
        if (typeMirror instanceof DeclaredType) {
            DeclaredType f = (DeclaredType) typeMirror;
            String meName = f.asElement().getSimpleName().toString();
            registerBean.setFieldClassName(meName);
        }
        registerBean.setFieldName(element.getSimpleName().toString());
        registerBean.setParentName(enclosingElement.getSimpleName().toString());
        registerBean.setViewFieldName(annotation.viewFieldName());
        Element fieldElement = mProvider.get(registerBean.getFieldClassName());
        registerBean.setElement(fieldElement);
        registerBean.setEnclosingElement(enclosingElement);
        registerBean.setTypeMirror(typeMirror);
        mRegister.put(registerBean.getParentName(), registerBean);
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> annotataions = new LinkedHashSet<>();
        annotataions.add(MvpPresenter.class.getCanonicalName());
        annotataions.add(MvpProvider.class.getCanonicalName());
        annotataions.add(MvpModel.class.getCanonicalName());
        return annotataions;
    }

    /**
     * @param element
     * @return
     */
    private boolean canGeneratedCode(Element element, TypeElement typeElement) {
        boolean needContinue = true;

        Set<Modifier> modifiers = element.getModifiers();
        mMessager.printMessage(Diagnostic.Kind.NOTE, "access 11111 " + element);
        if (modifiers.contains(Modifier.PRIVATE) || modifiers.contains(Modifier.STATIC)) {
            needContinue = false;
        }

        Set<Modifier> typeElementModifiers = typeElement.getModifiers();
        if (typeElementModifiers.contains(Modifier.PRIVATE)) {
            mMessager.printMessage(Diagnostic.Kind.NOTE, "access 222222 ");
            needContinue = false;
        }
        return needContinue;
    }

    private void generaCode() throws Exception {
        mMessager.printMessage(Diagnostic.Kind.NOTE, "begin genera " + mRegister.size() + "   model  " + mModel.size() + "  provider  " + mProvider.size());
        if (mRegister.size() <= 0 || mModel.size() <= 0) {
            return;
        }
        TypeSpec.Builder builder = TypeSpec.classBuilder("MvpRegister")
                .addModifiers(Modifier.PUBLIC)
                .addJavadoc("PLEASE DO NOT EDIT THIS FILE!!! IT WAS GENERATED BY WANG.\n");
        Set<String> strings = mRegister.keySet();
        for (String key : strings) {
            RegisterBean registerBean = mRegister.get(key);
            TypeName fieldTypeName = ParameterizedTypeName.get(registerBean.getElement().asType());
            TypeName typeName = ParameterizedTypeName.get(registerBean.getEnclosingElement().asType());
            ParameterSpec ps = ParameterSpec.builder(typeName, "target").build();
            TypeName name = TypeName.get(registerBean.getTypeMirror());
            MethodSpec register = MethodSpec.methodBuilder("register"+registerBean.getEnclosingElement().getSimpleName())
                    .addModifiers(Modifier.PUBLIC)
                    .addParameter(ps)
                    .returns(name)
                    .addStatement("$T presenter = new $T()", fieldTypeName, fieldTypeName)
                    .addStatement("target.$L = presenter", registerBean.getFieldName())
                    .addStatement("presenter.$L = target", registerBean.getViewFieldName())
                    .addStatement("provider(presenter)")
                    .addStatement("return target." + registerBean.getFieldName())
                    .build();
            builder.addMethod(register);
        }
        Set<String> keySet = mModel.keySet();
        for (String key : keySet) {
            ModelBean modelBean = mModel.get(key);
            mMessager.printMessage(Diagnostic.Kind.NOTE, "begin genera ModelBean  " + modelBean);
            TypeName fieldTypeName = ParameterizedTypeName.get(modelBean.getElement().asType());
            TypeName typeName = ParameterizedTypeName.get(modelBean.getEnclosingElement().asType());
            ParameterSpec ps = ParameterSpec.builder(typeName, "presenter").build();
            MethodSpec register = MethodSpec.methodBuilder("provider")
                    .addModifiers(Modifier.PUBLIC)
                    .addParameter(ps)
                    .addStatement("$T model = new $T()", fieldTypeName, fieldTypeName)
                    .addStatement("presenter.$L = model", modelBean.getFieldName())
                    .addStatement("model.$L = presenter", modelBean.getModeFieldClassName())
                    .build();
            builder.addMethod(register);
        }


        JavaFile javaFile = JavaFile.builder("cn.we.mvp", builder.build()).build();
        javaFile.writeTo(mFiler);
    }


    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latest();
    }
}

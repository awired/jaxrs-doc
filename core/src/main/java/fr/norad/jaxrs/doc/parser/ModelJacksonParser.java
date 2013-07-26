/**
 *
 *     Copyright (C) norad.fr
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *             http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 */
package fr.norad.jaxrs.doc.parser;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.cfg.BaseSettings;
import com.fasterxml.jackson.databind.introspect.AnnotatedField;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import com.fasterxml.jackson.databind.introspect.BasicBeanDescription;
import com.fasterxml.jackson.databind.introspect.BasicClassIntrospector;
import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import com.fasterxml.jackson.databind.jsontype.impl.StdSubtypeResolver;
import com.fasterxml.jackson.databind.ser.BeanSerializerFactory;
import com.fasterxml.jackson.databind.ser.DefaultSerializerProvider;
import com.fasterxml.jackson.databind.type.SimpleType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import fr.norad.jaxrs.doc.DocConfig;
import fr.norad.jaxrs.doc.annotations.Description;
import fr.norad.jaxrs.doc.domain.ModelDefinition;
import fr.norad.jaxrs.doc.domain.ProjectDefinition;
import fr.norad.jaxrs.doc.domain.PropertyDefinition;
import fr.norad.jaxrs.doc.utils.AnnotationUtil;

public class ModelJacksonParser extends ModelParser {

    private FakeSerializer fakeSerializer = new FakeSerializer();

    public ModelJacksonParser(DocConfig config) {
        super(config);
    }

    @Override
    public void parse(ProjectDefinition project, Class<?> modelClass) {
        if (isIgnoreModel(modelClass)) {
            return;
        }
        ModelDefinition model = new ModelDefinition();
        model.setModelClass(modelClass);
        project.getModels().put(model.getModelClass(), model);

        Description description = AnnotationUtil.findAnnotation(modelClass, Description.class);
        model.setDescription(description != null ? description.value() : null);

        BasicBeanDescription beanDesc = fakeSerializer.getDescription(modelClass);

        List<BeanPropertyDefinition> findProperties = beanDesc.findProperties();
        for (BeanPropertyDefinition beanPropertyDefinition : findProperties) {
            AnnotatedMethod getterJackson = beanPropertyDefinition.getGetter();
            AnnotatedMethod setterJackson = beanPropertyDefinition.getSetter();
            AnnotatedField fieldJackson = beanPropertyDefinition.getField();

            Method getter = getterJackson == null ? null : getterJackson.getAnnotated();
            Method setter = setterJackson == null ? null : setterJackson.getAnnotated();
            Field field = fieldJackson == null ? null : fieldJackson.getAnnotated();

            PropertyDefinition property = config.getPropertyParser().parse(project, field, getter, setter);
            model.getProperties().put(beanPropertyDefinition.getName(), property);
        }

        //        for (BeanPropertyWriter property : description2) {
        //            PropertyDefinition prop = config.getPropertyParser().parse(project, modelClass, property.getName(),
        //                    property);
        //            model.getProperties().put(property.getName(), prop);
        //        }

    }

    ////////////////////////////////////////////////////////////

    class FakeSerializer extends BeanSerializerFactory {

        protected FakeSerializer() {
            super(null);
        }

        public BasicBeanDescription getDescription(Class<?> model) {
            if (Map.class.isAssignableFrom(model)) {
                throw new IllegalArgumentException("Can not construct SimpleType for a Map (class: "
                        + model.getName() + ")");
            }
            if (Collection.class.isAssignableFrom(model)) {
                throw new IllegalArgumentException("Can not construct SimpleType for a Collection (class: "
                        + model.getName() + ")");
            }
            // ... and while we are at it, not array types either
            if (model.isArray()) {
                throw new IllegalArgumentException("Can not construct SimpleType for an array (class: "
                        + model.getName() + ")");
            }

            JavaType origType = SimpleType.construct(model);

            BasicClassIntrospector basicClassIntrospector = new BasicClassIntrospector();
            JacksonAnnotationIntrospector annotationIntrospector = new JacksonAnnotationIntrospector();

            BaseSettings baseSettings = new BaseSettings(basicClassIntrospector, annotationIntrospector,
                    VisibilityChecker.Std.defaultInstance(), null, TypeFactory.defaultInstance(), null, null, null,
                    null, null, null);
            SerializationConfig config = new SerializationConfig(baseSettings, new StdSubtypeResolver(), null);
            DefaultSerializerProvider.Impl prov = new DefaultSerializerProvider.Impl().createInstance(config, this);
            BeanDescription beanDesc = config.introspect(origType);

            return (BasicBeanDescription) beanDesc;

            //            BeanSerializerBuilder builder = constructBeanSerializerBuilder(beanDesc);
            //            List<BeanPropertyWriter> properties;
            //            try {
            //                properties = findBeanProperties(prov, beanDesc, builder);
            //            } catch (JsonMappingException e) {
            //                throw new IllegalStateException(e);
            //            }
            //
            //            return properties;
        }
    }

}

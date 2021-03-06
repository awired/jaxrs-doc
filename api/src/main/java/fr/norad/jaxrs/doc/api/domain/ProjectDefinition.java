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
package fr.norad.jaxrs.doc.api.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.Data;

@Data
@XmlRootElement
public class ProjectDefinition {
    private String name;
    private String version;
    private List<ApiDefinition> apis = new ArrayList<>();
    private Map<String, ModelDefinition> models = new HashMap<>();
    private Map<Class<? extends Exception>, ErrorDefinition> errors = new HashMap();
    private Map<Locale, LocalizationDefinition> localizations = new HashMap<Locale, LocalizationDefinition>() {
        {
            put(Locale.getDefault(), new LocalizationDefinition());
        }
    };
    private Map<String, Object> extras = new HashMap<>();

}

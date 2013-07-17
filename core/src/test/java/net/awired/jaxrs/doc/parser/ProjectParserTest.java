/**
 *
 *     Copyright (C) Awired.net
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
package net.awired.jaxrs.doc.parser;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import java.util.Arrays;
import net.awired.jaxrs.doc.DocConfig;
import net.awired.jaxrs.doc.domain.ProjectDefinition;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ProjectParserTest {

    @Mock
    private ApiParser apiParser;

    @InjectMocks
    private DocConfig config = new DocConfig(Arrays.asList("net.awired"));

    @Test
    public void should_find_some_api() throws Exception {
        ProjectDefinition project = new ProjectParser(config).parse();
        verify(apiParser, atLeastOnce()).parse(eq(project), any(Class.class));
    }

}

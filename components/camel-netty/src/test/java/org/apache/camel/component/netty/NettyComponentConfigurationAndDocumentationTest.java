/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.camel.component.netty;

import org.apache.camel.CamelContext;
import org.apache.camel.ComponentConfiguration;
import org.apache.camel.EndpointConfiguration;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

public class NettyComponentConfigurationAndDocumentationTest extends CamelTestSupport {

    @Override
    public boolean isUseRouteBuilder() {
        return false;
    }

    @Test
    public void testComponentConfiguration() throws Exception {
        NettyComponent comp = context.getComponent("netty", NettyComponent.class);
        EndpointConfiguration conf = comp.createConfiguration("netty:tcp://localhost:5150?sync=true"
                + "&maximumPoolSize=32&ssl=true&passphrase=#password");

        assertEquals("true", conf.getParameter("sync"));
        assertEquals("32", conf.getParameter("maximumPoolSize"));

        ComponentConfiguration compConf = comp.createComponentConfiguration();
        String json = compConf.createParameterJsonSchema();
        assertNotNull(json);

        assertTrue(json.contains("\"host\": { \"kind\": \"path\", \"type\": \"string\""));
        assertTrue(json.contains("\"producerPoolMinEvictableIdle\": { \"kind\": \"parameter\", \"type\": \"integer\""));
        assertTrue(json.contains("\"allowDefaultCodec\": { \"kind\": \"parameter\", \"type\": \"boolean\""));
    }

    @Test
    public void testComponentDocumentation() throws Exception {
        CamelContext context = new DefaultCamelContext();
        String html = context.getComponentDocumentation("netty");
        assertNotNull("Should have found some auto-generated HTML", html);
    }

}

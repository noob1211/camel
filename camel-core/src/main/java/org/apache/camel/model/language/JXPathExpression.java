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
package org.apache.camel.model.language;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.camel.CamelContext;
import org.apache.camel.Expression;
import org.apache.camel.Predicate;
import org.apache.camel.spi.Label;

/**
 * For JXPath expressions and predicates
 *
 * @version 
 */
@Label("language")
@XmlRootElement(name = "jxpath")
@XmlAccessorType(XmlAccessType.FIELD)
public class JXPathExpression extends ExpressionDefinition {

    @XmlAttribute
    private Boolean lenient;

    public JXPathExpression() {
    }

    public JXPathExpression(String expression) {
        super(expression);
    }

    public String getLanguage() {
        return "jxpath";
    }

    public Boolean getLenient() {
        return lenient;
    }

    public void setLenient(Boolean lenient) {
        this.lenient = lenient;
    }

    @Override
    protected void configureExpression(CamelContext camelContext, Expression expression) {
        if (lenient != null) {
            setProperty(expression, "lenient", lenient);
        }
        super.configureExpression(camelContext, expression);
    }

    @Override
    protected void configurePredicate(CamelContext camelContext, Predicate predicate) {
        if (lenient != null) {
            setProperty(predicate, "lenient", lenient);
        }
        super.configurePredicate(camelContext, predicate);
    }
}

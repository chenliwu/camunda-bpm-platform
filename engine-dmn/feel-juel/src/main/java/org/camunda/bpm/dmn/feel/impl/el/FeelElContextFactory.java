/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.camunda.bpm.dmn.feel.impl.el;

import javax.el.ArrayELResolver;
import javax.el.CompositeELResolver;
import javax.el.ELContext;
import javax.el.ELResolver;
import javax.el.ExpressionFactory;
import javax.el.FunctionMapper;
import javax.el.ListELResolver;
import javax.el.MapELResolver;
import javax.el.VariableMapper;

import org.camunda.bpm.engine.variable.VariableContext;
import org.camunda.bpm.engine.variable.VariableMap;

public class FeelElContextFactory implements ElContextFactory {

  public ELContext createContext(ExpressionFactory expressionFactory, VariableContext varCtx) {
    ELResolver elResolver = createElResolver();
    FunctionMapper functionMapper = createFunctionMapper();
    VariableMapper variableMapper = createVariableMapper(expressionFactory, varCtx);
    return new FeelElContext(elResolver, functionMapper, variableMapper);
  }

  public ELResolver createElResolver() {
    CompositeELResolver elResolver = new CompositeELResolver();
    elResolver.add(new ArrayELResolver(true));
    elResolver.add(new ListELResolver(true));
    elResolver.add(new MapELResolver(true));

    return elResolver;
  }

  public FunctionMapper createFunctionMapper() {
    CompositeFunctionMapper functionMapper = new CompositeFunctionMapper();
    functionMapper.add(new FeelFunctionMapper());
    return functionMapper;
  }

  public VariableMapper createVariableMapper(ExpressionFactory expressionFactory, VariableContext varCtx) {
    return new FeelTypedVariableMapper(expressionFactory, varCtx);
  }

}

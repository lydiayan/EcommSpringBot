package com.example.mallordergraphserver01.controller;

import com.alibaba.cloud.ai.graph.CompiledGraph;
import com.alibaba.cloud.ai.graph.OverAllState;
import com.alibaba.cloud.ai.graph.RunnableConfig;
import com.alibaba.cloud.ai.graph.StateGraph;
import com.alibaba.cloud.ai.graph.exception.GraphRunnerException;
import com.alibaba.cloud.ai.graph.exception.GraphStateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/graph/mcp")
public class OrderGraphController {
    private static final Logger logger = LoggerFactory.getLogger(OrderGraphController.class);

    private final CompiledGraph compiledGraph;

    public OrderGraphController(@Qualifier("mcpGraph")StateGraph stateGraph) throws GraphStateException {
        this.compiledGraph=stateGraph.compile();
    }

    public Map<String,Object> call(@RequestParam(value = "query", defaultValue = "手机订单", required = false) String query)
                                    throws GraphRunnerException {
        Map<String,Object> objectMap=new HashMap<>();
        objectMap.put("query",query);
        Optional<OverAllState> invoke = this.compiledGraph.invoke(objectMap);
        return invoke.map(OverAllState::data).orElse(new HashMap<>());

    }
}

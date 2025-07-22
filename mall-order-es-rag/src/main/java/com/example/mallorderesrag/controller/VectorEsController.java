package com.example.mallorderesrag.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.ai.document.DocumentReader;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.elasticsearch.ElasticsearchVectorStore;
import org.springframework.ai.vectorstore.filter.Filter;
import org.springframework.ai.vectorstore.filter.FilterExpressionBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/vector/es")
public class VectorEsController {
    private static Logger log = LoggerFactory.getLogger(VectorEsController.class);
    @Value("classpath:/data/商城助手知识库文档.pdf")
    private Resource instructionManuale;

    private final ElasticsearchVectorStore vectorStore;
    @Autowired
    public VectorEsController(@Qualifier(value = "esVectorStore") ElasticsearchVectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    @GetMapping("/addInstructionManuale")
    public void addInstructionManuale() {

        // 1. parse document
        DocumentReader reader = new PagePdfDocumentReader(instructionManuale);
        List<Document> documents = reader.get();
        log.info("{} documents loaded", documents.size());

        // 2. split trunks
        List<Document> splitDocuments = new TokenTextSplitter().apply(documents);
        log.info("{} documents split", splitDocuments.size());

        // 3. create embedding and store to vector store
        log.info("create embedding and save to vector store");
        //createIndexIfNotExists();
        vectorStore.add(splitDocuments);
    }

    @GetMapping("/add")
    public void add() {
        log.info("开始导入规则");
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", "USER1002");
        map.put("year", "2025");
/*  List<Document> documents = List.of(
                new Document("The World is Big and Salvation Lurks Around the Corner"),
                new Document("You walk forward facing the past and you turn back toward the future.", Map.of("year", 2024)),
                new Document("Spring AI rocks!! Spring AI rocks!! Spring AI rocks!! Spring AI rocks!! Spring AI rocks!!", map));
*/
       List<Document> documents = List.of(
            new Document("""
                  未发货，可取消，系统允许直接取消，无需走退货流程 """ ),
                new Document("""
                 已发货，不可取消，转为售后退货流程，需用户提供拒签原因  """ ),
                new Document("""
                 已完成，可申请维修/退款，触发退货流程，需要商城凭证并填写原因  """ ),
                new Document("""
                  已签收，可申请退货/退款，触发退货流程，需上传凭证并填写原因 """ )
            );

              vectorStore.add(documents);
    }

    @GetMapping("delete-filter")
    public void searchFilter(){
        log.info("start search data with filter");

        FilterExpressionBuilder filterExpressionBuilder = new FilterExpressionBuilder();
        Filter.Expression expression = filterExpressionBuilder.and(
                filterExpressionBuilder.in("year",2025,2024),
                filterExpressionBuilder.eq("name","yingzi")
        ).build();
        vectorStore.delete(expression);
    }

}

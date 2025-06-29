package com.jiangying.controller;


import com.jiangying.service.MockInterviewsAssistant;
import com.jiangying.service.MockInterviewsFluxAgent;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.embedding.onnx.bgesmallenv15q.BgeSmallEnV15QuantizedEmbeddingModel;
import dev.langchain4j.model.output.Response;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import jakarta.annotation.Resource;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;

import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.PathMatcher;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/Ai")
public class AiController {


    @Resource
    private MockInterviewsAssistant mockInterviewsAssistant;
    @Resource
    private EmbeddingStore<TextSegment> embeddingStore;
//    @Resource
//    private RepositoryService repositoryService;

    @GetMapping("/testAi")
    public String test() {
        String chat = mockInterviewsAssistant.chat(1, "475695037565 的平方根是多少？");
        return chat;
    }

    @GetMapping("/chatAi")
    public String chat(@RequestParam("memoryId") int memoryId, @RequestParam("message") String message) {
        long useId = 1;
        //去查数据库是否有memoryId 为空则新建
        //memoryId = UUID.randomUUID();

        String chat = mockInterviewsAssistant.chat(memoryId, message);
        return chat;
    }

    /**
     * 添加avg向量
     *
     * @param file 上传的文件
     * @return 是否成功添加
     */
    @PostMapping("addAvg")
    public boolean addAvg(@RequestParam("file") MultipartFile file) {
        // 检查文件是否为空
        if (file == null || file.isEmpty()) {
            return false;
        }
        // 获取文件扩展名
        String fileExtension = "";
        String originalFilename = file.getOriginalFilename();
        if (originalFilename != null) {
            int lastIndexOfDot = originalFilename.lastIndexOf('.');
            if (lastIndexOfDot > 0) {
                fileExtension = originalFilename.substring(lastIndexOfDot + 1).toLowerCase();
            }
        }

        // 支持的文件类型
        String[] supportedExtensions = {"md", "pdf", "doc", "docx", "txt"};
        boolean isSupported = Arrays.asList(supportedExtensions).contains(fileExtension);
        // 如果文件类型不支持，返回 false
        if (!isSupported) {
            return false;
        }
        try {
            // 示例：将文件保存到服务器
            file.transferTo(new File("D:/rag/" + file.getOriginalFilename()));

            Document documents = FileSystemDocumentLoader.loadDocument("D:/rag/"+file.getOriginalFilename());


            EmbeddingStoreIngestor.builder().embeddingModel(new BgeSmallEnV15QuantizedEmbeddingModel()).embeddingStore(embeddingStore)
                    .build()
                    .ingest(documents);
            System.out.println("rag向量已保存" + originalFilename);
            //再删除
            try {
                File fileToDelete = new File("D:/rag/" + file.getOriginalFilename());
                if (fileToDelete.exists()) {
                    if (fileToDelete.delete()) {
                        System.out.println("文件已删除: " + fileToDelete.getAbsolutePath());
                    } else {
                        System.out.println("文件删除失败: " + fileToDelete.getAbsolutePath());
                    }
                } else {
                    System.out.println("文件不存在: " + fileToDelete.getAbsolutePath());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

}

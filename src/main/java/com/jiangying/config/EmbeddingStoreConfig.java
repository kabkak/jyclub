package com.jiangying.config;

import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.onnx.bgesmallenv15q.BgeSmallEnV15QuantizedEmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.pinecone.PineconeEmbeddingStore;
import dev.langchain4j.store.embedding.pinecone.PineconeServerlessIndexConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class EmbeddingStoreConfig {
    @Bean
    public EmbeddingStore<TextSegment> getEmbeddingStore() {
//        EmbeddingModel embeddingModel = new AllMiniLmL6V2QuantizedEmbeddingModel();
        return PineconeEmbeddingStore
                .builder()
                .apiKey("pcsk_6nmBuU_H8VsGMzbogQwS6N6JSBHVYaJBqEDZx5qf3JGkAysYcX2DqgqbcVBbQnjRKknuS7")
                .index("mock-interviews-index")
                .nameSpace("mock-interviews-namespace")
                .createIndex(PineconeServerlessIndexConfig.builder()
                        .cloud("AWS")
                        .region("us-east-1")
                        .dimension(new BgeSmallEnV15QuantizedEmbeddingModel().dimension())
                        .build())
                .build();
    }
}
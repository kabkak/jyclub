//package com.jiangying.store;
//
//
//import dev.langchain4j.data.segment.TextSegment;
//import dev.langchain4j.model.embedding.EmbeddingModel;
//import dev.langchain4j.store.embedding.EmbeddingStore;
//import dev.langchain4j.store.embedding.pinecone.PineconeServerlessIndexConfig;
//import org.springframework.stereotype.Component;
//
//import static dev.langchain4j.internal.Utils.randomUUID;
//
//
//public class PineconeEmbeddingStore {
//
//    public static void main(String[] args) throws Exception {
//
////        EmbeddingModel embeddingModel = new BgeSmallEnV15QuantizedEmbeddingModel();
//        EmbeddingModel embeddingModel = new AllMiniLmL6V2QuantizedEmbeddingModel();
//        EmbeddingStore<TextSegment> embeddingStore = dev.langchain4j.store.embedding.pinecone.PineconeEmbeddingStore.builder()
//                .apiKey(System.getenv("pcsk_ZeorD_7xYWusFboQHy8opTmwB5GG9GbW9aY7BWnAwHCJAejtQs76A8ua3a4PyxHfSg6wf"))
//                .index("mocktnterviews")
//                .nameSpace(randomUUID())
//                .createIndex(PineconeServerlessIndexConfig.builder()
//                        .cloud("AWS")
//                        .region("us-east-1")
//                        .dimension(embeddingModel.dimension())
//                        .build())
//                .build();
//
//
//    }
//}
package br.com.localstack.awsServices;//package br.com.localstack.awsServices;
//
//
//public class S3BucketLister {
//
//    public static void main(String[] args) {
//        // Crie um cliente Amazon S3
//        AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
//                .withEndpointConfiguration(LocalStackEndpointConfiguration.builder()
//                        .withServiceEndpoint("http://localhost:4566") // Verifique o endpoint correto do LocalStack
//                        .build())
//                .build();
//
//        // Liste os buckets existentes
//        listBuckets(s3Client);
//    }
//
//    public static void listBuckets(AmazonS3 s3Client) {
//        System.out.println("Buckets existentes:");
//        for (Bucket bucket : s3Client.listBuckets()) {
//            System.out.println(bucket.getName());
//        }
//    }
//}
